package com.sohaib.trackmystacks.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sohaib.trackmystacks.dto.BackupData;
import com.sohaib.trackmystacks.model.Category;
import com.sohaib.trackmystacks.model.Expense;
import com.sohaib.trackmystacks.model.User;
import com.sohaib.trackmystacks.repository.CategoryRepository;
import com.sohaib.trackmystacks.repository.ExpenseRepository;
import com.sohaib.trackmystacks.repository.UserRepository;

/**
 * Handles full-database export and import for backup / restore scenarios.
 *
 * Export  – serialises every user, category, and expense into a portable
 *           BackupData DTO (BCrypt password hashes are preserved verbatim).
 *
 * Import  – restores a previously exported snapshot:
 *           1. Wipes all expenses        (FK-safe first step)
 *           2. Replaces all categories
 *           3. Upserts users by username  (existing session user stays valid)
 *           4. Recreates all expenses     (references users by username)
 */
@Service
public class BackupService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    // -------------------------------------------------------------------------
    // Export
    // -------------------------------------------------------------------------

    @Transactional(readOnly = true)
    public BackupData exportData() {
        BackupData backup = new BackupData();
        backup.setExportedAt(LocalDateTime.now());

        // --- Users ---
        List<BackupData.UserBackup> userBackups = userRepository.findAll().stream().map(u -> {
            BackupData.UserBackup ub = new BackupData.UserBackup();
            ub.setOriginalId(u.getId());
            ub.setUsername(u.getUsername());
            ub.setEmail(u.getEmail());
            ub.setPasswordHash(u.getPassword());   // already BCrypt hashed
            ub.setAdmin(u.isAdmin());
            ub.setCreatedAt(u.getCreatedAt());
            return ub;
        }).collect(Collectors.toList());
        backup.setUsers(userBackups);

        // --- Categories ---
        List<BackupData.CategoryBackup> catBackups = categoryRepository.findAll().stream().map(c -> {
            BackupData.CategoryBackup cb = new BackupData.CategoryBackup();
            cb.setOriginalId(c.getId());
            cb.setName(c.getName());
            cb.setCreatedAt(c.getCreatedAt());
            return cb;
        }).collect(Collectors.toList());
        backup.setCategories(catBackups);

        // --- Expenses ---
        // findAll() loads all expenses; user is lazily fetched within this transaction.
        List<BackupData.ExpenseBackup> expBackups = expenseRepository.findAll().stream().map(e -> {
            BackupData.ExpenseBackup eb = new BackupData.ExpenseBackup();
            eb.setOriginalId(e.getId());
            eb.setUsername(e.getUser().getUsername());   // resolve lazy proxy here
            eb.setAmount(e.getAmount());
            eb.setCategory(e.getCategory());
            eb.setDescription(e.getDescription());
            eb.setDate(e.getDate());
            eb.setRecurring(e.isRecurring());
            eb.setCreatedAt(e.getCreatedAt());
            return eb;
        }).collect(Collectors.toList());
        backup.setExpenses(expBackups);

        return backup;
    }

    // -------------------------------------------------------------------------
    // Import
    // -------------------------------------------------------------------------

    @Transactional
    public void importData(BackupData backup) {

        // Step 1 – wipe expenses first to avoid FK violations when touching users/categories
        expenseRepository.deleteAll();
        expenseRepository.flush();

        // Step 2 – replace categories entirely for a clean restore
        categoryRepository.deleteAll();
        categoryRepository.flush();

        if (backup.getCategories() != null) {
            for (BackupData.CategoryBackup cb : backup.getCategories()) {
                Category cat = new Category(cb.getName());
                if (cb.getCreatedAt() != null) {
                    cat.setCreatedAt(cb.getCreatedAt());
                }
                categoryRepository.save(cat);
            }
        }

        // Step 3 – upsert users by username
        // Updating existing records keeps the current session user valid
        // (Spring Security won't force a logout when the same DB row is updated).
        Map<String, User> savedUsers = new HashMap<>();

        if (backup.getUsers() != null) {
            for (BackupData.UserBackup ub : backup.getUsers()) {
                Optional<User> existing = userRepository.findByUsername(ub.getUsername());
                User user;
                if (existing.isPresent()) {
                    // Update in-place – preserves the primary key so sessions stay valid
                    user = existing.get();
                    user.setEmail(ub.getEmail());
                    user.setPassword(ub.getPasswordHash());   // restore original BCrypt hash
                    user.setAdmin(ub.isAdmin());
                } else {
                    // Brand-new user – store the BCrypt hash directly (skip re-encoding)
                    user = new User(ub.getUsername(), ub.getEmail(), ub.getPasswordHash(), ub.isAdmin());
                    if (ub.getCreatedAt() != null) {
                        user.setCreatedAt(ub.getCreatedAt());
                    }
                }
                user = userRepository.saveAndFlush(user);
                savedUsers.put(user.getUsername(), user);
            }
        }

        // Step 4 – recreate expenses, resolving owners by username
        if (backup.getExpenses() != null) {
            for (BackupData.ExpenseBackup eb : backup.getExpenses()) {
                User owner = savedUsers.get(eb.getUsername());
                if (owner == null) {
                    // Referenced user not present in backup – skip to preserve integrity
                    continue;
                }
                Expense expense = new Expense(
                        owner,
                        eb.getAmount(),
                        eb.getCategory(),
                        eb.getDescription(),
                        eb.getDate(),
                        eb.isRecurring()
                );
                if (eb.getCreatedAt() != null) {
                    expense.setCreatedAt(eb.getCreatedAt());
                }
                expenseRepository.save(expense);
            }
        }
    }
}

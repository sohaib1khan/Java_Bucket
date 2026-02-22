package com.sohaib.trackmystacks.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Root DTO for the entire database backup.
 * Contains a portable, human-readable representation of all
 * users, categories, and expenses so the file can be imported
 * into any fresh deployment.
 */
public class BackupData {

    private String version = "1.0";
    private LocalDateTime exportedAt;
    private List<UserBackup> users;
    private List<CategoryBackup> categories;
    private List<ExpenseBackup> expenses;

    public BackupData() {}

    // -------------------------------------------------------------------------
    // Nested DTOs
    // -------------------------------------------------------------------------

    public static class UserBackup {
        private Long originalId;
        private String username;
        private String email;
        /** BCrypt hash is kept verbatim so passwords survive a round-trip. */
        private String passwordHash;
        private boolean admin;
        private LocalDateTime createdAt;

        public UserBackup() {}

        public Long getOriginalId() { return originalId; }
        public void setOriginalId(Long originalId) { this.originalId = originalId; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPasswordHash() { return passwordHash; }
        public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
        public boolean isAdmin() { return admin; }
        public void setAdmin(boolean admin) { this.admin = admin; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    }

    public static class CategoryBackup {
        private Long originalId;
        private String name;
        private LocalDateTime createdAt;

        public CategoryBackup() {}

        public Long getOriginalId() { return originalId; }
        public void setOriginalId(Long originalId) { this.originalId = originalId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    }

    public static class ExpenseBackup {
        private Long originalId;
        /** Expenses reference their owner by username (not numeric ID), so they
         *  survive re-import when auto-increment IDs are regenerated. */
        private String username;
        private BigDecimal amount;
        private String category;
        private String description;
        private LocalDate date;
        private boolean recurring;
        private LocalDateTime createdAt;

        public ExpenseBackup() {}

        public Long getOriginalId() { return originalId; }
        public void setOriginalId(Long originalId) { this.originalId = originalId; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public LocalDate getDate() { return date; }
        public void setDate(LocalDate date) { this.date = date; }
        public boolean isRecurring() { return recurring; }
        public void setRecurring(boolean recurring) { this.recurring = recurring; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    }

    // -------------------------------------------------------------------------
    // Root getters / setters
    // -------------------------------------------------------------------------

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public LocalDateTime getExportedAt() { return exportedAt; }
    public void setExportedAt(LocalDateTime exportedAt) { this.exportedAt = exportedAt; }
    public List<UserBackup> getUsers() { return users; }
    public void setUsers(List<UserBackup> users) { this.users = users; }
    public List<CategoryBackup> getCategories() { return categories; }
    public void setCategories(List<CategoryBackup> categories) { this.categories = categories; }
    public List<ExpenseBackup> getExpenses() { return expenses; }
    public void setExpenses(List<ExpenseBackup> expenses) { this.expenses = expenses; }
}

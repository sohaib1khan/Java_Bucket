package com.sohaib.trackmystacks.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sohaib.trackmystacks.dto.UserBackupData;
import com.sohaib.trackmystacks.model.Expense;
import com.sohaib.trackmystacks.model.User;
import com.sohaib.trackmystacks.service.ExpenseService;
import com.sohaib.trackmystacks.service.UserService;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {
    
    @Autowired
    private ExpenseService expenseService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/add")
    public String addExpense(
            @RequestParam BigDecimal amount,
            @RequestParam String category,
            @RequestParam String description,
            @RequestParam LocalDate date,
            @RequestParam(required = false) boolean recurring,
            Authentication auth,
            RedirectAttributes redirectAttributes) {
        
        User user = userService.findByUsername(auth.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        expenseService.createExpense(user, amount, category, description, date, recurring);
        redirectAttributes.addFlashAttribute("success", "Expense added successfully!");
        
        return "redirect:/dashboard";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteExpense(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        
        expenseService.deleteExpense(id);
        redirectAttributes.addFlashAttribute("success", "Expense deleted successfully!");
        
        return "redirect:/dashboard";
    }
    
    @PostMapping("/update/{id}")
    public String updateExpense(
            @PathVariable Long id,
            @RequestParam BigDecimal amount,
            @RequestParam String category,
            @RequestParam String description,
            @RequestParam LocalDate date,
            @RequestParam(required = false) boolean recurring,
            RedirectAttributes redirectAttributes) {
        
        expenseService.updateExpense(id, amount, category, description, date, recurring);
        redirectAttributes.addFlashAttribute("success", "Expense updated successfully!");
        
        return "redirect:/dashboard";
    }

    // -------------------------------------------------------------------------
    // Backup: Export  →  GET /expenses/backup/export
    // Downloads the current user's expenses as a JSON file.
    // -------------------------------------------------------------------------
    @GetMapping("/backup/export")
    public ResponseEntity<byte[]> exportMyBackup(Authentication auth) {
        try {
            User user = userService.findByUsername(auth.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            List<Expense> expenses = expenseService.getAllExpensesByUser(user);

            UserBackupData backup = new UserBackupData();
            backup.setUsername(user.getUsername());
            backup.setExportedAt(LocalDateTime.now());
            backup.setExpenses(expenses.stream().map(e -> {
                UserBackupData.ExpenseEntry entry = new UserBackupData.ExpenseEntry();
                entry.setAmount(e.getAmount());
                entry.setCategory(e.getCategory());
                entry.setDescription(e.getDescription());
                entry.setDate(e.getDate());
                entry.setRecurring(e.isRecurring());
                entry.setCreatedAt(e.getCreatedAt());
                return entry;
            }).collect(Collectors.toList()));

            byte[] json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(backup);
            String filename = "trackmystacks-" + user.getUsername() + "-" + LocalDate.now() + ".json";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(json);

        } catch (Exception e) {
            byte[] error = ("Export failed: " + e.getMessage()).getBytes();
            return ResponseEntity.internalServerError()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(error);
        }
    }

    // -------------------------------------------------------------------------
    // Backup: Import  →  POST /expenses/backup/import
    // Restores the current user's expenses from an uploaded JSON file.
    // Only the authenticated user's data is touched.
    // -------------------------------------------------------------------------
    @PostMapping("/backup/import")
    public String importMyBackup(
            @RequestParam("backupFile") MultipartFile file,
            Authentication auth,
            RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("success", null);
            redirectAttributes.addFlashAttribute("backupError", "Please select a backup file.");
            return "redirect:/dashboard";
        }

        if (!file.getOriginalFilename().endsWith(".json")) {
            redirectAttributes.addFlashAttribute("backupError", "Only .json backup files are supported.");
            return "redirect:/dashboard";
        }

        try {
            User user = userService.findByUsername(auth.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            UserBackupData backup = objectMapper.readValue(file.getBytes(), UserBackupData.class);

            // Wipe only this user's expenses, then recreate from backup
            expenseService.deleteAllExpensesByUser(user);

            if (backup.getExpenses() != null) {
                for (UserBackupData.ExpenseEntry entry : backup.getExpenses()) {
                    Expense expense = new Expense(
                            user,
                            entry.getAmount(),
                            entry.getCategory(),
                            entry.getDescription(),
                            entry.getDate(),
                            entry.isRecurring()
                    );
                    if (entry.getCreatedAt() != null) {
                        expense.setCreatedAt(entry.getCreatedAt());
                    }
                    expenseService.saveExpense(expense);
                }
            }

            redirectAttributes.addFlashAttribute("success",
                    "Backup restored! " + (backup.getExpenses() != null ? backup.getExpenses().size() : 0) + " expense(s) imported.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("backupError",
                    "Import failed — " + e.getMessage() + ". Your data has not been changed.");
        }

        return "redirect:/dashboard";
    }
}
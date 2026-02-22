package com.sohaib.trackmystacks.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Portable backup payload for a single user's expenses.
 * Contains only the data that belongs to the authenticated user —
 * no passwords, no other users' records.
 */
public class UserBackupData {

    private String version = "1.0";
    private String username;
    private LocalDateTime exportedAt;
    private List<ExpenseEntry> expenses;

    public UserBackupData() {}

    // -------------------------------------------------------------------------
    // Nested DTO
    // -------------------------------------------------------------------------

    public static class ExpenseEntry {
        private BigDecimal amount;
        private String category;
        private String description;
        private LocalDate date;
        private boolean recurring;
        private LocalDateTime createdAt;

        public ExpenseEntry() {}

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
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public LocalDateTime getExportedAt() { return exportedAt; }
    public void setExportedAt(LocalDateTime exportedAt) { this.exportedAt = exportedAt; }
    public List<ExpenseEntry> getExpenses() { return expenses; }
    public void setExpenses(List<ExpenseEntry> expenses) { this.expenses = expenses; }
}

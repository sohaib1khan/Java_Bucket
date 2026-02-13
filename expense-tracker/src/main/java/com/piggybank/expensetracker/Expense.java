package com.piggybank.expensetracker;

// JPA imports - these are for database mapping
// Python equivalent: from flask_sqlalchemy import db
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

// Date/time imports
import java.time.LocalDate;
import java.time.LocalDateTime;

// ============================================
// ENTITY CLASS (Database Model)
// ============================================
// @Entity = This class represents a database table
// Python SQLAlchemy equivalent: class Expense(db.Model):
@Entity
@Table(name = "expenses")  // Table name in database
public class Expense {
    
    // ============================================
    // FIELDS (Database Columns)
    // ============================================
    
    // PRIMARY KEY (unique ID for each expense)
    // @Id = This is the primary key
    // @GeneratedValue = Auto-generate the value (auto-increment)
    // Python SQLAlchemy equivalent: id = db.Column(db.Integer, primary_key=True)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // DESCRIPTION (what was purchased)
    // @Column = Configure column properties
    // @NotBlank = Validation: cannot be empty
    // Python SQLAlchemy equivalent: description = db.Column(db.String(200), nullable=False)
    // Python WTForms validation: validators=[DataRequired()]
    @Column(nullable = false, length = 200)
    @NotBlank(message = "Description is required")
    private String description;
    
    // AMOUNT (how much it cost)
    // @Positive = Validation: must be greater than 0
    // Python SQLAlchemy: amount = db.Column(db.Numeric(10, 2), nullable=False)
    // Python validation: validators=[NumberRange(min=0.01)]
    @Column(nullable = false)
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0")
    private Double amount;
    
    // CATEGORY (type of expense)
    // Examples: Food, Transport, Entertainment, Bills, Shopping
    // Python SQLAlchemy: category = db.Column(db.String(50), nullable=False)
    @Column(nullable = false, length = 50)
    @NotBlank(message = "Category is required")
    private String category;
    
    // DATE (when the expense occurred)
    // LocalDate = Date without time (2024-02-11)
    // Python equivalent: from datetime import date
    //                    expense_date = db.Column(db.Date, nullable=False)
    @Column(name = "expense_date", nullable = false)
    @NotNull(message = "Date is required")
    private LocalDate date;
    
    // CREATED AT (timestamp when added to database)
    // LocalDateTime = Date + Time (2024-02-11 14:30:00)
    // Python equivalent: from datetime import datetime
    //                    created_at = db.Column(db.DateTime, default=datetime.utcnow)
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    
    // ============================================
    // CONSTRUCTORS
    // ============================================
    
    // Default constructor (required by JPA)
    // Python equivalent: Not needed (Python handles this automatically)
    public Expense() {
    }
    
    // Constructor with all fields (except id and createdAt - those are auto-generated)
    // Python equivalent: def __init__(self, description, amount, category, date):
    public Expense(String description, Double amount, String category, LocalDate date) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }
    
    
    // ============================================
    // @PrePersist - Runs BEFORE saving to database
    // ============================================
    // Sets createdAt timestamp automatically
    // Python SQLAlchemy equivalent: default=datetime.utcnow in Column definition
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    
    
    // ============================================
    // GETTERS (Read values)
    // ============================================
    // In Java, we use getters to access private fields
    // Python equivalent: Just access directly like expense.id
    
    public Long getId() {
        return id;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Double getAmount() {
        return amount;
    }
    
    public String getCategory() {
        return category;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    
    // ============================================
    // SETTERS (Change values)
    // ============================================
    // In Java, we use setters to modify private fields
    // Python equivalent: Just set directly like expense.amount = 50.0
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    
    // ============================================
    // toString() - For debugging
    // ============================================
    // Prints object details nicely
    // Python equivalent: def __repr__(self):
    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", date=" + date +
                ", createdAt=" + createdAt +
                '}';
    }
}
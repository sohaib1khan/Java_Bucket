// The Expense class is a JPA entity that represents an expense record in the application. It includes fields for the expense ID, associated user, amount, category, description, date of the expense, and the timestamp when the record was created. The class is annotated with JPA annotations to define how it maps to the database table "expenses". It also includes constructors, getters, and setters for each field to allow for easy manipulation of expense data within the application.
package com.sohaib.trackmystacks.model;

// Importing necessary classes for JPA and date handling
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// The @Entity annotation specifies that this class is an entity and is mapped to a database table. The @Table annotation specifies the name of the database table to be used for mapping.
@Entity
@Table(name = "expenses")
public class Expense {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    
    @Column(nullable = false, length = 50)
    private String category;
    
    @Column(length = 255)
    private String description;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    // Constructors: The default constructor is required by JPA, and the parameterized constructor allows for easy creation of Expense objects with all necessary fields.
    public Expense() {}
    
    public Expense(User user, BigDecimal amount, String category, String description, LocalDate date) {
        this.user = user;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
    }
    
    // Getters and Setters: These methods allow us to access and modify the fields of the Expense class. They are essential for working with Expense objects in the application, enabling us to retrieve and update expense data as needed.
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
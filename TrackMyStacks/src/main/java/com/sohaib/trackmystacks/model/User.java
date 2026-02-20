// This file defines the User entity for the TrackMyStacks application. It includes fields for user information, such as username, email, password, and admin status. The class is annotated with JPA annotations to map it to a database table.
package com.sohaib.trackmystacks.model;

// Importing necessary classes for JPA and date handling
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// The @Entity annotation specifies that this class is an entity and is mapped to a database table. The @Table annotation specifies the name of the database table to be used for mapping.
@Entity
@Table(name = "users")
// The User class represents a user in the TrackMyStacks application. It includes fields for id, username, email, password, admin status, and createdAt timestamp. It also includes constructors, getters, and setters for these fields.
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 50)
    private String username;
    
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    
    @Column(nullable = false)
    private String password;  // Will be hashed!
    
    @Column(name = "is_admin")
    private boolean admin = false;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    // Constructors:
    // Default constructor is required by JPA
    public User() {}
    
    public User(String username, String email, String password, boolean admin) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }
    
    // Getters and Setters:
    // These methods allow us to access and modify the fields of the User class.
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isAdmin() {
        return admin;
    }
    
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
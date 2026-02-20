package com.sohaib.trackmystacks.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sohaib.trackmystacks.service.CategoryService;
import com.sohaib.trackmystacks.service.UserService;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Override
    public void run(String... args) {
        // Create admin user if it doesn't exist
        if (!userService.usernameExists("admin")) {
            userService.createUser("admin", "admin@trackmystacks.com", "admin123", true);
            System.out.println("Admin user created! Username: admin, Password: admin123");
        }
        
        // Create default categories if none exist
        if (categoryService.getAllCategories().isEmpty()) {
            categoryService.createCategory("Food");
            categoryService.createCategory("Transport");
            categoryService.createCategory("Entertainment");
            categoryService.createCategory("Bills");
            categoryService.createCategory("Shopping");
            categoryService.createCategory("Health");
            categoryService.createCategory("Tech");
            categoryService.createCategory("Other");
            System.out.println("Default categories created!");
        }
    }
}
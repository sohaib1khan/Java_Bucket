// This class initializes the database with a default admin user if it doesn't already exist. It uses a flag file to ensure that the initialization only happens once, preventing duplicate admin creation on subsequent application startups. The admin user is created with a default username and password, which should be changed immediately
package com.sohaib.trackmystacks.config;

// Importing necessary classes for file handling, Spring annotations, and services 
import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sohaib.trackmystacks.service.UserService;

// The @Component annotation indicates that this class is a Spring component, and the CommandLineRunner interface allows it to run code after the application context is loaded. The run method checks for the existence of a flag file to determine if the admin user has already been created, and if not, it creates the admin user and then creates the flag file to prevent future initialization.
@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserService userService;
    
    @Override
    public void run(String... args) {
        File initFlag = new File("/app/data/.initialized");
        
        // Only create admin if flag file doesn't exist
        if (!initFlag.exists()) {
            if (!userService.usernameExists("admin")) {
                userService.createUser("admin", "admin@trackmystacks.com", "admin123", true);
                System.out.println("Admin user created! Username: admin, Password: admin123");
            }
            
            // Create flag file
            try {
                initFlag.createNewFile();
                System.out.println("Initialization complete. Admin user will not be recreated.");
            } catch (IOException e) {
                System.err.println("Could not create init flag: " + e.getMessage());
            }
        } else {
            System.out.println("ℹ️ Database already initialized. Skipping admin creation.");
        }
    }
}
// This file is part of TrackMyStacks. It defines the UserService class, which provides methods for managing users in the application. It includes methods for retrieving all users, finding a user by username, creating a new user with hashed passwords, deleting a user by ID, and checking for the existence of usernames and emails. The service uses the UserRepository for database operations and the PasswordEncoder for hashing passwords.
package com.sohaib.trackmystacks.service;

// Importing necessary classes for list handling, optional values, and Spring annotations
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sohaib.trackmystacks.model.User;
import com.sohaib.trackmystacks.repository.UserRepository;

// The @Service annotation indicates that this class is a service component in the Spring framework. It is responsible for containing business logic and interacting with the UserRepository to perform operations related to users in the TrackMyStacks application.
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public User createUser(String username, String email, String password, boolean admin) {
        // Hash the password before saving!
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(username, email, hashedPassword, admin);
        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }
    
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
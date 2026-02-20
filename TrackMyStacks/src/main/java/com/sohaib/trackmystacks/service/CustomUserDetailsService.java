// This class implements the UserDetailsService interface to load user-specific data during authentication. It retrieves user information from the database using the UserRepository and constructs a UserDetails object that Spring Security can use for authentication and authorization. The user's role is determined based on whether they are an admin or not, and appropriate authorities are assigned accordingly.
package com.sohaib.trackmystacks.service;

// Importing necessary classes for user details and Spring annotations
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sohaib.trackmystacks.model.User;
import com.sohaib.trackmystacks.repository.UserRepository;

// The @Service annotation indicates that this class is a service component in the Spring framework. It is responsible for containing business logic related to user details and interacting with the UserRepository to retrieve user information from the database for authentication purposes.
@Service
public class CustomUserDetailsService implements UserDetailsService {
    // The UserRepository is injected into this service to allow it to access user data from the database. This is done using the @Autowired annotation, which tells Spring to automatically wire the UserRepository bean into this class.    
    @Autowired
    private UserRepository userRepository;
    // The loadUserByUsername method is overridden from the UserDetailsService interface. It takes a username as input and attempts to retrieve the corresponding User entity from the database using the UserRepository. If the user is not found, a UsernameNotFoundException is thrown. If the user is found, a UserDetails object is created and returned, containing the user's username, password, and authorities (roles). The role is determined based on whether the user is an admin or not, and appropriate authorities are assigned accordingly.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        
        String role = user.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER";
        
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }
}
// This file is part of TrackMyStacks. It defines the UserRepository interface, which extends JpaRepository to provide CRUD operations for the User entity. It also includes custom query methods to find users by username and email, and to check for the existence of users by these fields.
package com.sohaib.trackmystacks.repository;

// Importing necessary classes for JPA and Spring Data
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sohaib.trackmystacks.model.User;

// The @Repository annotation indicates that this interface is a Spring Data repository, which will be automatically implemented by Spring Data JPA. The UserRepository interface extends JpaRepository, which provides basic CRUD operations for the User entity. Additionally, it defines custom query methods to find users by username and email, and to check for the existence of users by these fields.
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
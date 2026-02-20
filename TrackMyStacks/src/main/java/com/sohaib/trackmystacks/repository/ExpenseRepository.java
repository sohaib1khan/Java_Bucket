// The ExpenseRepository interface extends JpaRepository, providing CRUD operations for the Expense entity. It includes custom query methods to find expenses by user, date range, and category, as well as methods to calculate the total expenses for a user and within a specific date range. The @Repository annotation indicates that this interface is a Spring Data repository, which will be automatically implemented by Spring at runtime.
package com.sohaib.trackmystacks.repository;

// Importing necessary classes for JPA and Spring Data
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sohaib.trackmystacks.model.Expense;
import com.sohaib.trackmystacks.model.User;

// The @Repository annotation indicates that this interface is a Spring Data repository, which will be automatically implemented by Spring Data JPA. The ExpenseRepository interface extends JpaRepository, which provides basic CRUD operations for the Expense entity. Additionally, it defines custom query methods to find expenses by user, date range, and category, as well as methods to calculate total expenses for a user and within a specific date range.
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    // Find all expenses for a specific user
    List<Expense> findByUserOrderByDateDesc(User user);
    
    // Find expenses by user and date range
    List<Expense> findByUserAndDateBetweenOrderByDateDesc(User user, LocalDate startDate, LocalDate endDate);
    
    // Find expenses by user and category
    List<Expense> findByUserAndCategoryOrderByDateDesc(User user, String category);
    
    // Calculate total for a user
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user = ?1")
    BigDecimal getTotalByUser(User user);
    
    // Calculate total by user and date range
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user = ?1 AND e.date BETWEEN ?2 AND ?3")
    BigDecimal getTotalByUserAndDateRange(User user, LocalDate startDate, LocalDate endDate);
}
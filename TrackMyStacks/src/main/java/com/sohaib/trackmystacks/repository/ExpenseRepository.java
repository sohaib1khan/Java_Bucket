package com.sohaib.trackmystacks.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sohaib.trackmystacks.model.Expense;
import com.sohaib.trackmystacks.model.User;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    // Find all expenses for a specific user, ordered by date descending
    @Query("SELECT e FROM Expense e WHERE e.user = :user ORDER BY e.date DESC")
    List<Expense> findByUserOrderByDateDesc(User user);
    
    // Find expenses by user and date range
    @Query("SELECT e FROM Expense e WHERE e.user = :user AND e.date BETWEEN :startDate AND :endDate ORDER BY e.date DESC")
    List<Expense> findByUserAndDateBetweenOrderByDateDesc(User user, LocalDate startDate, LocalDate endDate);
    
    // Find expenses by user and category
    @Query("SELECT e FROM Expense e WHERE e.user = :user AND e.category = :category ORDER BY e.date DESC")
    List<Expense> findByUserAndCategoryOrderByDateDesc(User user, String category);
    
    // Calculate total for a user
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user = :user")
    BigDecimal getTotalByUser(User user);
    
    // Calculate total by user and date range
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user = :user AND e.date BETWEEN :startDate AND :endDate")
    BigDecimal getTotalByUserAndDateRange(User user, LocalDate startDate, LocalDate endDate);

    // Delete all expenses belonging to a specific user (used during user-level restore)
    void deleteByUser(User user);
}
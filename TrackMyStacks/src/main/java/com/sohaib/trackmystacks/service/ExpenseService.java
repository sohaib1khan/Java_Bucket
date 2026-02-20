// The ExpenseService class is a Spring service that provides methods for managing expenses in the application. It interacts with the ExpenseRepository to perform CRUD operations on Expense entities. The service includes methods to create, retrieve, update, and delete expenses, as well as methods to calculate total expenses for a user and within specific date ranges. The @Service annotation indicates that this class is a service component in the Spring framework, allowing it to be automatically detected and managed by Spring's dependency injection mechanism.
package com.sohaib.trackmystacks.service;

// Importing necessary classes for list handling, optional values, and Spring annotations
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sohaib.trackmystacks.model.Expense;
import com.sohaib.trackmystacks.model.User;
import com.sohaib.trackmystacks.repository.ExpenseRepository;

// The ExpenseService class is a Spring service that provides methods for managing expenses in the application. It interacts with the ExpenseRepository to perform CRUD operations on Expense entities. The service includes methods to create, retrieve, update, and delete expenses, as well as methods to calculate total expenses for a user and within specific date ranges. The @Service annotation indicates that this class is a service component in the Spring framework, allowing it to be automatically detected and managed by Spring's dependency injection mechanism.
@Service
public class ExpenseService {
    
    @Autowired
    private ExpenseRepository expenseRepository;
    
    public Expense createExpense(User user, BigDecimal amount, String category, String description, LocalDate date) {
        Expense expense = new Expense(user, amount, category, description, date);
        return expenseRepository.save(expense);
    }
    
    public List<Expense> getAllExpensesByUser(User user) {
        return expenseRepository.findByUserOrderByDateDesc(user);
    }
    
    public List<Expense> getExpensesByUserAndDateRange(User user, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByUserAndDateBetweenOrderByDateDesc(user, startDate, endDate);
    }
    
    public List<Expense> getExpensesByUserAndCategory(User user, String category) {
        return expenseRepository.findByUserAndCategoryOrderByDateDesc(user, category);
    }
    
    public BigDecimal getTotalByUser(User user) {
        BigDecimal total = expenseRepository.getTotalByUser(user);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    public BigDecimal getTotalByUserAndDateRange(User user, LocalDate startDate, LocalDate endDate) {
        BigDecimal total = expenseRepository.getTotalByUserAndDateRange(user, startDate, endDate);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }
    
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
    
    public Expense updateExpense(Long id, BigDecimal amount, String category, String description, LocalDate date) {
        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Expense not found"));
        
        expense.setAmount(amount);
        expense.setCategory(category);
        expense.setDescription(description);
        expense.setDate(date);
        
        return expenseRepository.save(expense);
    }
}
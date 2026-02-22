package com.sohaib.trackmystacks.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sohaib.trackmystacks.model.Expense;
import com.sohaib.trackmystacks.model.User;
import com.sohaib.trackmystacks.repository.ExpenseRepository;

@Service
public class ExpenseService {
    
    @Autowired
    private ExpenseRepository expenseRepository;
    
    public Expense createExpense(User user, BigDecimal amount, String category, String description, LocalDate date, boolean recurring) {
        Expense expense = new Expense(user, amount, category, description, date, recurring);
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
    
    public Expense updateExpense(Long id, BigDecimal amount, String category, String description, LocalDate date, boolean recurring) {
        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Expense not found"));
        
        expense.setAmount(amount);
        expense.setCategory(category);
        expense.setDescription(description);
        expense.setDate(date);
        expense.setRecurring(recurring);
        
        return expenseRepository.save(expense);
    }

    public void deleteAllExpensesByUser(User user) {
        expenseRepository.deleteByUser(user);
    }

    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }
}
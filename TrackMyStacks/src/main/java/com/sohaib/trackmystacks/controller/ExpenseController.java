package com.sohaib.trackmystacks.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sohaib.trackmystacks.model.User;
import com.sohaib.trackmystacks.service.ExpenseService;
import com.sohaib.trackmystacks.service.UserService;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {
    
    @Autowired
    private ExpenseService expenseService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/add")
    public String addExpense(
            @RequestParam BigDecimal amount,
            @RequestParam String category,
            @RequestParam String description,
            @RequestParam LocalDate date,
            @RequestParam(required = false) boolean recurring,
            Authentication auth,
            RedirectAttributes redirectAttributes) {
        
        User user = userService.findByUsername(auth.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        expenseService.createExpense(user, amount, category, description, date, recurring);
        redirectAttributes.addFlashAttribute("success", "Expense added successfully!");
        
        return "redirect:/dashboard";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteExpense(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        
        expenseService.deleteExpense(id);
        redirectAttributes.addFlashAttribute("success", "Expense deleted successfully!");
        
        return "redirect:/dashboard";
    }
    
    @PostMapping("/update/{id}")
    public String updateExpense(
            @PathVariable Long id,
            @RequestParam BigDecimal amount,
            @RequestParam String category,
            @RequestParam String description,
            @RequestParam LocalDate date,
            @RequestParam(required = false) boolean recurring,
            RedirectAttributes redirectAttributes) {
        
        expenseService.updateExpense(id, amount, category, description, date, recurring);
        redirectAttributes.addFlashAttribute("success", "Expense updated successfully!");
        
        return "redirect:/dashboard";
    }
}
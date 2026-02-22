package com.sohaib.trackmystacks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sohaib.trackmystacks.model.User;
import com.sohaib.trackmystacks.service.CategoryService;
import com.sohaib.trackmystacks.service.ExpenseService;
import com.sohaib.trackmystacks.service.PaycheckService;
import com.sohaib.trackmystacks.service.UserService;

@Controller
public class DashboardController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ExpenseService expenseService;
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PaycheckService paycheckService;
    
    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        User user = userService.findByUsername(auth.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        model.addAttribute("username", auth.getName());
        model.addAttribute("expenses", expenseService.getAllExpensesByUser(user));
        model.addAttribute("total", expenseService.getTotalByUser(user));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("paychecks", paycheckService.getAllPaychecksByUser(user));
        model.addAttribute("monthlyData", paycheckService.getMonthlyComparison(user, 6));
        
        return "dashboard";
    }
}
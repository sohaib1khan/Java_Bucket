package com.sohaib.trackmystacks.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sohaib.trackmystacks.model.User;
import com.sohaib.trackmystacks.repository.ExpenseRepository;
import com.sohaib.trackmystacks.repository.PaycheckRepository;
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

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private PaycheckRepository paycheckRepository;
    
    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        User user = userService.findByUsername(auth.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Date ranges for stats tiles
        LocalDate today          = LocalDate.now();
        LocalDate firstOfMonth   = today.withDayOfMonth(1);
        LocalDate lastOfMonth    = today.withDayOfMonth(today.lengthOfMonth());
        LocalDate firstOfLast    = firstOfMonth.minusMonths(1);
        LocalDate lastOfLast     = firstOfMonth.minusDays(1);

        BigDecimal thisMonthExp  = expenseRepository.getTotalByUserAndDateRange(user, firstOfMonth, lastOfMonth);
        BigDecimal lastMonthExp  = expenseRepository.getTotalByUserAndDateRange(user, firstOfLast, lastOfLast);
        BigDecimal thisMonthInc  = paycheckRepository.getTotalByUserAndMonth(user, firstOfMonth);

        model.addAttribute("username", auth.getName());
        model.addAttribute("expenses", expenseService.getAllExpensesByUser(user));
        model.addAttribute("total", expenseService.getTotalByUser(user));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("paychecks", paycheckService.getAllPaychecksByUser(user));
        model.addAttribute("monthlyData", paycheckService.getMonthlyComparison(user, 6));

        // Stats tiles
        model.addAttribute("thisMonthExpenses", thisMonthExp);
        model.addAttribute("lastMonthExpenses", lastMonthExp);
        model.addAttribute("thisMonthIncome",   thisMonthInc);
        model.addAttribute("lastMonthDiff",     thisMonthExp.subtract(lastMonthExp).abs());
        model.addAttribute("spendingUp",        lastMonthExp.compareTo(BigDecimal.ZERO) > 0 && thisMonthExp.compareTo(lastMonthExp) > 0);
        model.addAttribute("spendingDown",      lastMonthExp.compareTo(BigDecimal.ZERO) > 0 && thisMonthExp.compareTo(lastMonthExp) < 0);
        model.addAttribute("netAmount",         thisMonthInc.subtract(thisMonthExp).abs());
        model.addAttribute("netPositive",       thisMonthInc.compareTo(BigDecimal.ZERO) > 0 && thisMonthInc.compareTo(thisMonthExp) >= 0);
        model.addAttribute("netNegative",       thisMonthInc.compareTo(BigDecimal.ZERO) > 0 && thisMonthInc.compareTo(thisMonthExp) < 0);
        
        return "dashboard";
    }
}
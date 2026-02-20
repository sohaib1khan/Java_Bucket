// The DashboardController class is a Spring MVC controller that handles requests to the dashboard page of the application. It retrieves the authenticated user's information and their associated expenses, then adds this data to the model to be displayed in the dashboard view. The @Controller annotation indicates that this class is a web controller, and the @GetMapping annotation specifies that the dashboard method will handle GET requests to the "/dashboard" endpoint.
package com.sohaib.trackmystacks.controller;

// Importing necessary classes for Spring MVC controller, authentication, and model handling
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sohaib.trackmystacks.model.User;
import com.sohaib.trackmystacks.service.ExpenseService;
import com.sohaib.trackmystacks.service.UserService;

// The DashboardController class is a Spring MVC controller that handles requests to the dashboard page of the application. It retrieves the authenticated user's information and their associated expenses, then adds this data to the model to be displayed in the dashboard view. The @Controller annotation indicates that this class is a web controller, and the @GetMapping annotation specifies that the dashboard method will handle GET requests to the "/dashboard" endpoint.
@Controller
public class DashboardController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ExpenseService expenseService;
    
    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        User user = userService.findByUsername(auth.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        model.addAttribute("username", auth.getName());
        model.addAttribute("expenses", expenseService.getAllExpensesByUser(user));
        model.addAttribute("total", expenseService.getTotalByUser(user));
        
        return "dashboard";
    }
}
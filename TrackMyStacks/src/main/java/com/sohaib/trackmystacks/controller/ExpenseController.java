// The ExpenseController class is a Spring MVC controller that handles requests related to managing expenses. It defines endpoints for adding, deleting, and updating expenses. The controller interacts with the ExpenseService to perform these operations and uses RedirectAttributes to pass success messages back to the view after each operation. The @Controller annotation indicates that this class is a web controller, and the @RequestMapping annotation specifies that all endpoints in this controller will be prefixed with "/expenses".
package com.sohaib.trackmystacks.controller;

// Importing necessary classes for Spring MVC controller and request mapping
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

// The ExpenseController class is a Spring MVC controller that handles requests related to managing expenses. It defines endpoints for adding, deleting, and updating expenses. The controller interacts with the ExpenseService to perform these operations and uses RedirectAttributes to pass success messages back to the view after each operation. The @Controller annotation indicates that this class is a web controller, and the @RequestMapping annotation specifies that all endpoints in this controller will be prefixed with "/expenses".
@Controller
@RequestMapping("/expenses")
public class ExpenseController {
    
    @Autowired
    private ExpenseService expenseService;
    
    @Autowired
    private UserService userService;
    // The addExpense method handles POST requests to add a new expense. It takes the expense details as request parameters, retrieves the currently authenticated user, and calls the ExpenseService to create the expense. After the operation is completed, it adds a success message to the RedirectAttributes and redirects the user back to the dashboard.
    @PostMapping("/add")
    public String addExpense(
            @RequestParam BigDecimal amount,
            @RequestParam String category,
            @RequestParam String description,
            @RequestParam LocalDate date,
            Authentication auth,
            RedirectAttributes redirectAttributes) {
        
        User user = userService.findByUsername(auth.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        expenseService.createExpense(user, amount, category, description, date);
        redirectAttributes.addFlashAttribute("success", "Expense added successfully!");
        
        return "redirect:/dashboard";
    }
    // The deleteExpense method handles POST requests to delete an expense by its ID. It takes the expense ID as a path variable and uses the ExpenseService to perform the deletion. After the operation is completed, it adds a success message to the RedirectAttributes and redirects the user back to the dashboard.
    @PostMapping("/delete/{id}")
    public String deleteExpense(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        
        expenseService.deleteExpense(id);
        redirectAttributes.addFlashAttribute("success", "Expense deleted successfully!");
        
        return "redirect:/dashboard";
    }
    // The updateExpense method handles POST requests to update an existing expense. It takes the expense ID as a path variable and the updated expense details as request parameters. The method calls the ExpenseService to perform the update and uses RedirectAttributes to pass a success message back to the view after the operation is completed.
    @PostMapping("/update/{id}")
    public String updateExpense(
            @PathVariable Long id,
            @RequestParam BigDecimal amount,
            @RequestParam String category,
            @RequestParam String description,
            @RequestParam LocalDate date,
            RedirectAttributes redirectAttributes) {
        
        expenseService.updateExpense(id, amount, category, description, date);
        redirectAttributes.addFlashAttribute("success", "Expense updated successfully!");
        
        return "redirect:/dashboard";
    }
}
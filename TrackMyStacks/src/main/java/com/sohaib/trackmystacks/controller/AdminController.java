// The AdminController class is a Spring MVC controller that handles requests to the "/admin" endpoint. It uses the @Controller annotation to indicate that it is a web controller, and the @RequestMapping annotation to specify the base URL path for all methods in this controller. The adminPanel method retrieves all users from the UserService and adds them to the model, returning the view name "admin/panel". The createUser method handles POST requests to create a new user, performing validation checks for existing usernames and emails before creating the user and redirecting back to the admin panel with success or error messages. The deleteUser method handles POST requests to delete a user by their ID, also redirecting back to the admin panel with a success message after deletion.
package com.sohaib.trackmystacks.controller;

// Importing necessary classes for Spring MVC controller and request mapping
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sohaib.trackmystacks.service.UserService;

// The @Controller annotation indicates that this class is a web controller, and the @RequestMapping annotation specifies the base URL path for all methods in this controller. The adminPanel method retrieves all users from the UserService and adds them to the model, returning the view name "admin/panel". The createUser method handles POST requests to create a new user, performing validation checks for existing usernames and emails before creating the user and redirecting back to the admin panel with success or error messages. The deleteUser method handles POST requests to delete a user by their ID, also redirecting back to the admin panel with a success message after deletion.
@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public String adminPanel(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/panel";
    }
    
    @PostMapping("/create-user")
    public String createUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) boolean admin,
            RedirectAttributes redirectAttributes) {
        
        // Validation
        if (userService.usernameExists(username)) {
            redirectAttributes.addFlashAttribute("error", "Username already exists!");
            return "redirect:/admin";
        }
        
        if (userService.emailExists(email)) {
            redirectAttributes.addFlashAttribute("error", "Email already exists!");
            return "redirect:/admin";
        }
        
        // Create user
        userService.createUser(username, email, password, admin);
        redirectAttributes.addFlashAttribute("success", "User created successfully!");
        return "redirect:/admin";
    }
    // The deleteUser method handles POST requests to delete a user by their ID, also redirecting back to the admin panel with a success message after deletion.
    @PostMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("success", "User deleted successfully!");
        return "redirect:/admin";
    }
}
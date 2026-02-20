package com.sohaib.trackmystacks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sohaib.trackmystacks.service.CategoryService;
import com.sohaib.trackmystacks.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping
    public String adminPanel(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/panel";
    }
    
    @PostMapping("/create-user")
    public String createUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) boolean admin,
            RedirectAttributes redirectAttributes) {
        
        if (userService.usernameExists(username)) {
            redirectAttributes.addFlashAttribute("error", "Username already exists!");
            return "redirect:/admin";
        }
        
        if (userService.emailExists(email)) {
            redirectAttributes.addFlashAttribute("error", "Email already exists!");
            return "redirect:/admin";
        }
        
        userService.createUser(username, email, password, admin);
        redirectAttributes.addFlashAttribute("success", "User created successfully!");
        return "redirect:/admin";
    }
    
    @PostMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("success", "User deleted successfully!");
        return "redirect:/admin";
    }
    
    @PostMapping("/create-category")
    public String createCategory(
            @RequestParam String name,
            RedirectAttributes redirectAttributes) {
        
        if (categoryService.categoryExists(name)) {
            redirectAttributes.addFlashAttribute("error", "Category already exists!");
            return "redirect:/admin";
        }
        
        categoryService.createCategory(name);
        redirectAttributes.addFlashAttribute("success", "Category created successfully!");
        return "redirect:/admin";
    }
    
    @PostMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoryService.deleteCategory(id);
        redirectAttributes.addFlashAttribute("success", "Category deleted successfully!");
        return "redirect:/admin";
    }
}
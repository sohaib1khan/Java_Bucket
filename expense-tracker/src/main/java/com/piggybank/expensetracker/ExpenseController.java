package com.piggybank.expensetracker;

// Spring MVC imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

// Validation import
import jakarta.validation.Valid;

// Date imports
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

// ============================================
// CONTROLLER (Routes & Logic)
// ============================================
// @Controller = This class handles web requests
// Python Flask equivalent: This file contains all @app.route() functions
@Controller
public class ExpenseController {
    
    // ============================================
    // DEPENDENCY INJECTION
    // ============================================
    // @Autowired = Spring Boot automatically injects the repository
    // Python Flask equivalent: 
    //   In Flask, you'd just import and use:
    //   from models import db, Expense
    //   Then use db.session directly
    //
    // Java advantage: Dependency Injection = cleaner, testable code
    @Autowired
    private ExpenseRepository expenseRepository;
    
    
    // ============================================
    // ROUTE 1: Display homepage with all expenses
    // ============================================
    // @GetMapping("/") = Handle GET requests to homepage
    // Python Flask equivalent: @app.route("/", methods=["GET"])
    @GetMapping("/")
    public String home(Model model) {
        // Model = container to pass data to HTML template
        // Python equivalent: render_template('index.html', expenses=expenses)
        
        // Get all expenses from database, ordered by date (newest first)
        // Python SQLAlchemy: expenses = Expense.query.order_by(Expense.date.desc()).all()
        List<Expense> expenses = expenseRepository.findAllByOrderByDateDesc();
        
        // Calculate total spending
        // Python: total = sum(expense.amount for expense in expenses)
        double totalSpending = expenses.stream()
                                       .mapToDouble(Expense::getAmount)
                                       .sum();
        
        // Get unique categories from expenses
        // Python: categories = set(expense.category for expense in expenses)
        Set<String> categories = new HashSet<>();
        for (Expense expense : expenses) {
            categories.add(expense.getCategory());
        }
        
        // Calculate spending by category for chart
        // Python: 
        //   category_totals = {}
        //   for expense in expenses:
        //       category_totals[expense.category] = category_totals.get(expense.category, 0) + expense.amount
        Map<String, Double> categoryTotals = new HashMap<>();
        for (Expense expense : expenses) {
            String category = expense.getCategory();
            categoryTotals.put(category, 
                categoryTotals.getOrDefault(category, 0.0) + expense.getAmount());
        }
        
        // Add data to model for HTML template
        model.addAttribute("expenses", expenses);
        model.addAttribute("totalSpending", totalSpending);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryTotals", categoryTotals);
        model.addAttribute("expenseCount", expenses.size());
        
        // For the add form - create empty Expense object
        model.addAttribute("newExpense", new Expense());
        
        // Return template name (Spring Boot looks for index.html in templates/)
        return "index";
    }
    
    
    // ============================================
    // ROUTE 2: Add new expense (form submission)
    // ============================================
    // @PostMapping("/add") = Handle POST requests to /add
    // Python Flask equivalent: @app.route("/add", methods=["POST"])
    @PostMapping("/add")
    public String addExpense(@Valid @ModelAttribute("newExpense") Expense expense,
                            BindingResult bindingResult,
                            Model model) {
        // @Valid = Validate the expense object (checks @NotBlank, @Positive, etc.)
        // @ModelAttribute = Bind form data to Expense object
        // BindingResult = Contains validation errors (if any)
        //
        // Python Flask equivalent:
        //   form = ExpenseForm()
        //   if form.validate_on_submit():
        //       expense = Expense(...)
        //       db.session.add(expense)
        //       db.session.commit()
        
        // If validation fails, return to homepage with error
        if (bindingResult.hasErrors()) {
            // Reload all data for the page
            List<Expense> expenses = expenseRepository.findAllByOrderByDateDesc();
            double totalSpending = expenses.stream()
                                           .mapToDouble(Expense::getAmount)
                                           .sum();
            model.addAttribute("expenses", expenses);
            model.addAttribute("totalSpending", totalSpending);
            model.addAttribute("expenseCount", expenses.size());
            return "index";
        }
        
        // Save expense to database
        // Python SQLAlchemy equivalent:
        //   db.session.add(expense)
        //   db.session.commit()
        expenseRepository.save(expense);
        
        // Redirect back to homepage (to see the updated list)
        // Python Flask: return redirect("/")
        return "redirect:/";
    }
    
    
    // ============================================
    // ROUTE 3: Delete expense
    // ============================================
    // @PostMapping("/delete/{id}") = Handle POST to /delete/1, /delete/2, etc.
    // {id} is a path variable (part of the URL)
    // Python Flask equivalent: @app.route("/delete/<int:id>", methods=["POST"])
    @PostMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        // @PathVariable = Extract {id} from URL
        // Python Flask equivalent: id from route parameter
        //
        // Example: POST /delete/5 → id = 5
        
        // Delete expense from database
        // Python SQLAlchemy equivalent:
        //   expense = Expense.query.get(id)
        //   db.session.delete(expense)
        //   db.session.commit()
        expenseRepository.deleteById(id);
        
        // Redirect back to homepage
        return "redirect:/";
    }
    
    
    // ============================================
    // ROUTE 4: Filter by category
    // ============================================
    // @GetMapping("/filter") = Handle GET /filter?category=Food
    // Python Flask equivalent: @app.route("/filter")
    @GetMapping("/filter")
    public String filterByCategory(@RequestParam String category, Model model) {
        // @RequestParam = Get query parameter from URL
        // Python Flask: category = request.args.get('category')
        
        // Get expenses in this category
        // Python SQLAlchemy: expenses = Expense.query.filter_by(category=category).all()
        List<Expense> expenses = expenseRepository.findByCategory(category);
        
        // Calculate total for this category
        double totalSpending = expenses.stream()
                                       .mapToDouble(Expense::getAmount)
                                       .sum();
        
        // Add data to model
        model.addAttribute("expenses", expenses);
        model.addAttribute("totalSpending", totalSpending);
        model.addAttribute("expenseCount", expenses.size());
        model.addAttribute("filterCategory", category);
        model.addAttribute("newExpense", new Expense());
        
        return "index";
    }
    
    
    // ============================================
    // ROUTE 5: Monthly report
    // ============================================
    // @GetMapping("/report") = Show spending report
    // Python Flask equivalent: @app.route("/report")
    @GetMapping("/report")
    public String monthlyReport(@RequestParam(required = false) String month, Model model) {
        // @RequestParam(required = false) = Optional parameter
        // Python Flask: month = request.args.get('month', default_value)
        
        // If no month specified, use current month
        YearMonth selectedMonth;
        if (month == null || month.isEmpty()) {
            selectedMonth = YearMonth.now();
        } else {
            selectedMonth = YearMonth.parse(month);
        }
        
        // Get first and last day of the month
        LocalDate startDate = selectedMonth.atDay(1);
        LocalDate endDate = selectedMonth.atEndOfMonth();
        
        // Get expenses in this date range
        // Python SQLAlchemy:
        //   expenses = Expense.query.filter(
        //       Expense.date >= start_date,
        //       Expense.date <= end_date
        //   ).all()
        List<Expense> expenses = expenseRepository.findByDateBetween(startDate, endDate);
        
        // Calculate total spending for the month
        double totalSpending = expenses.stream()
                                       .mapToDouble(Expense::getAmount)
                                       .sum();
        
        // Calculate spending by category
        Map<String, Double> categoryTotals = new HashMap<>();
        for (Expense expense : expenses) {
            String category = expense.getCategory();
            categoryTotals.put(category,
                categoryTotals.getOrDefault(category, 0.0) + expense.getAmount());
        }
        
        // Add data to model
        model.addAttribute("expenses", expenses);
        model.addAttribute("totalSpending", totalSpending);
        model.addAttribute("categoryTotals", categoryTotals);
        model.addAttribute("selectedMonth", selectedMonth);
        model.addAttribute("expenseCount", expenses.size());
        
        return "report";
    }
}
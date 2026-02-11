// Package declaration
package com.todo;

// Imports - bring in tools we need
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;                      // ← ADD THIS
import org.springframework.web.bind.annotation.GetMapping;  // ← ADD THIS
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// @Controller = This class handles web requests (like Flask routes)
// Python equivalent: This file contains all your @app.route() functions
@Controller
public class TodoController {
    
    // ============================================
    // STORAGE (In-memory list of tasks)
    // ============================================
    
    // ArrayList = Java's version of Python list
    // This stores ALL our tasks in memory (not saved to database)
    // Python equivalent: tasks = []
    private ArrayList<Task> tasks = new ArrayList<>();
    
    
    // ============================================
    // ROUTE 1: Display the homepage with all tasks
    // ============================================
    
    // @GetMapping("/") = Handle GET requests to homepage
    // Python equivalent: @app.route("/", methods=["GET"])
    @GetMapping("/")                                     // ← ADD THIS
    public String home(Model model) {                    // ← ADD THIS
        // Model = container to pass data to the HTML template
        // Python equivalent: render_template('index.html', tasks=tasks)
        
        // Add the tasks list to the model so HTML can access it
        model.addAttribute("tasks", tasks);              // ← "tasks" not "task"
        
        // Return the template name (looks for index.html in templates/)
        return "index";
    }
    
    
    // ============================================
    // ROUTE 2: Add a new task (form submission)
    // ============================================
    
    // @PostMapping("/add") = Handle POST requests to /add
    // Python equivalent: @app.route("/add", methods=["POST"])
    @PostMapping("/add")
    public String addTask(@RequestParam String description) {
        // @RequestParam = Get form data from the request
        // Python equivalent: request.form.get("description")
        
        // Create a new Task object
        // Python equivalent: task = Task(description)
        Task newTask = new Task(description);
        
        // Add it to our list
        // Python equivalent: tasks.append(task)
        tasks.add(newTask);
        
        // Redirect back to homepage (to see the updated list)
        // Python equivalent: return redirect("/")
        return "redirect:/";
    }
    
    
    // ============================================
    // ROUTE 3: Toggle task completion status
    // ============================================
    
    // Handle POST requests to /toggle
    // Python equivalent: @app.route("/toggle", methods=["POST"])
    @PostMapping("/toggle")
    public String toggleTask(@RequestParam int id) {
        // @RequestParam int id = Get the task ID from the form
        // Python equivalent: task_id = request.form.get("id")
        
        // Loop through all tasks to find the one with matching ID
        // Python equivalent: for task in tasks:
        for (Task task : tasks) {
            if (task.getId() == id) {                   // ← "task" not "tasks", dot not comma
                // Toggle: if true, make false; if false, make true
                // Python equivalent: task.completed = not task.completed
                task.setCompleted(!task.isCompleted());
                break;  // Stop looking once we found it
            }
        }
        
        // Redirect back to homepage
        return "redirect:/";
    }
    
    
    // ============================================
    // ROUTE 4: Delete a task
    // ============================================
    
    // Handle POST requests to /delete
    // Python equivalent: @app.route("/delete", methods=["POST"])
    @PostMapping("/delete")
    public String deleteTask(@RequestParam int id) {
        // @RequestParam int id = Get the task ID from the form
        
        // Remove the task with matching ID
        // Python equivalent: tasks = [t for t in tasks if t.id != id]
        tasks.removeIf(task -> task.getId() == id);
        
        // Redirect back to homepage
        return "redirect:/";
    }
}
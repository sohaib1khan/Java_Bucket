// Package declaration - where this file lives (matches folder structure)
// Python equivalent: this is in com/todo/ folder
package com.todo;

// This class defines what TASK looks like
// Python equivalent: class Task:
public class Task {

    // ============================================
    // VARIABLES (What data does each task have?)
    // ============================================

    // Private = Only this class can directly access these. 
    // Like Python's self.id, self.description, self.completed

    private int id; // Unique ID for each  task
    private String description; // What the task says ("Buy Milk")
    private boolean completed; // Is it done? (true/false)


    // Static counter to auto-generate IDs 
    // Shared by ALL tasks (not per-task)
    // Python equivalent: Task.nextId = 1 (class variable)
    private static int nextId = 1;


    // ============================================
    // CONSTRUCTOR (How to create a new task)
    // ============================================

    // Python equivalent: def __init__(self, description):
    public Task(String description) {
        this.id = nextId++;             // Assign ID and increment counter
        this.description = description; // Set the task description
        this.completed = false;         // New tasks start as not completed

    }


    // ============================================
    // GETTERS (Read the values)
    // ============================================
    // In Java, we use "getter" methods to access private variables
    // Python doesn't need these - you just use self.id directly


    // Get the task ID
    // Python equivalent: task.id (direct access)
    public int getId() {
        return id;
    }


    // Get the task description,
    // Python equivalent: task.description
    public String getDescription() {
        return description;
    }


    // Check if the task is competed.
    // Python equivalent: task.completed
    public boolean isCompleted() {
        return completed;
    }



    // ============================================
    // SETTERS (Change the values)
    // ============================================
    // In Java, we use "setter" methods to modify private variables
    // Python doesn't need these - you just do self.completed = True


    // Update the task description
    // Python equivalent: task.description = "New description"
    public void setDescription(String description) {
        this.description = description;
    }

    
    // Mark task as completed or not completed.
    // Python equivalent: task.completed = True
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
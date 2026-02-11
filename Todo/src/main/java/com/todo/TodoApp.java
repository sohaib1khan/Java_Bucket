// Package declaration - where this file lives (matches folder structure)
package com.todo;


// import Spring Boot classes
// Python equivalent: from flask import
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// This annotation tells Spring Boot: "this is the main application class"
// Python equivalent: app = flask(__name__)
@SpringBootApplication
public class TodoApp {
    // Main method - the starting point (entry point)
    // Should be: if __name__ == "__main__": app.run()
    public static void main(String[] args) {
        // Start the Spring Boot application 
        // This starts the web server on port 8082 (from applicatin.properties)
        SpringApplication.run(TodoApp.class, args);
    }

}
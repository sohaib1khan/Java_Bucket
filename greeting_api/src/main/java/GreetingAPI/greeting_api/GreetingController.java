// Package = folder location of this file (must match folder structure)
package GreetingAPI.greeting_api;

// Imports = bring in Spring Boot tools (like Python's import statements)
import org.springframework.stereotype.Controller;  // For serving HTML pages
import org.springframework.ui.Model;               // For passing data to HTML templates
import org.springframework.web.bind.annotation.GetMapping;      // For defining routes
import org.springframework.web.bind.annotation.RequestParam;    // For getting URL parameters

// @Controller = marks this class to serve HTML pages (not just data)
@Controller
// Class definition - groups related route methods together
public class GreetingController {

    // API Endpoint: Returns plain text (for testing with curl)
    // Visit: http://localhost:8081/greet?name=John
    @GetMapping("/greet")
    public String greet(@RequestParam(defaultValue = "Greet") String name) {
        // Returns plain text response (not HTML)
        return "Hello, " + name + "! Welcome to Spring Boot woooo!!!";
    }

    // Web Page Endpoint: Returns HTML template
    // Visit: http://localhost:8081/greet-page?name=John
    @GetMapping("/greet-page")
    public String greetPage(@RequestParam(required = false) String name, Model model) {
        // If name is provided in URL (?name=...)
        if (name != null && !name.isEmpty()) {
            // Create greeting message
            String greeting = "Hello, " + name + "! Welcome to Spring Boot Woooo!!!!";
            // Pass greeting to the HTML template (like Flask's render_template)
            model.addAttribute("greeting", greeting);
        }
        // Return template name "greeting" -> looks for greeting.html in templates/
        return "greeting";
    }
}
// This class is a Spring MVC controller that handles authentication-related requests. It defines two endpoints: one for displaying the login page and another for redirecting to the dashboard after a successful login. The @Controller annotation indicates that this class is a web controller, and the @GetMapping annotations specify the URL paths for the respective methods.
package com.sohaib.trackmystacks.controller;

// Importing necessary classes for Spring MVC controller and request mapping
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// The @Controller annotation indicates that this class is a web controller, and the @GetMapping annotations specify the URL paths for the respective methods.
@Controller
public class AuthController {
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }
}
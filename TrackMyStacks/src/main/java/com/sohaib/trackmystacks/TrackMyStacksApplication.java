// This is the main application class for the TrackMyStacks application. It is responsible for bootstrapping the Spring Boot application.
package com.sohaib.trackmystacks;

// Importing necessary Spring Boot classes
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// The @SpringBootApplication annotation is a convenience annotation that adds all of the following:
@SpringBootApplication
public class TrackMyStacksApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrackMyStacksApplication.class, args);
    }
}

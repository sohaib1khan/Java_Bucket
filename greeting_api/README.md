# 👋 Spring Boot Greeting API

A full-stack web application built with **Java Spring Boot** and **Thymeleaf** that demonstrates RESTful API development and server-side rendering. Perfect for developers transitioning from Python/Flask to Java/Spring Boot.

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.10-brightgreen)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-Wrapper-blue)](https://maven.apache.org/)

---

## 🎯 What This Project Does

This application provides two endpoints:

1. **REST API Endpoint** (`/greet`) - Returns a personalized greeting as plain text
2. **Web Page Endpoint** (`/greet-page`) - Renders an interactive HTML form with real-time greetings

**Try it live:**
- API: `http://localhost:8081/greet?name=John`
- Web UI: `http://localhost:8081/greet-page?name=John`
---

## 🛠️ Technologies Used

| Technology | Purpose |
|------------|---------|
| **Java 17** | Programming language |
| **Spring Boot 3.5** | Application framework |
| **Spring Web** | REST API support |
| **Thymeleaf** | Template engine (like Jinja2) |
| **Maven** | Dependency management |
| **Tomcat** | Embedded web server |

---

## 📁 Project Structure

```
greeting_api/
├── src/
│   └── main/
│       ├── java/
│       │   └── GreetingAPI/greeting_api/
│       │       ├── GreetingApiApplication.java    # Main entry point
│       │       └── GreetingController.java        # Route handlers
│       └── resources/
│           ├── application.properties             # Configuration
│           ├── static/                            # CSS, JS, images
│           └── templates/
│               └── greeting.html                  # HTML template
├── pom.xml                                        # Dependencies
└── mvnw / mvnw.cmd                               # Maven wrapper
```

---

## 🚀 Quick Start

### Prerequisites

- **Java 17** or higher

### Installation & Running

```bash
# 1. Clone the repository
git clone https://github.com/yourusername/greeting_api.git
cd greeting_api

# 2. Run the application (Maven will auto-download dependencies)
./mvnw spring-boot:run

# For Windows users:
mvnw.cmd spring-boot:run

# 3. Open your browser
# Visit: http://localhost:8081/greet-page
```

**That's it!** The server will start on port 8081.

---

## 📖 API Documentation

### 1. Text API Endpoint

**Endpoint:** `GET /greet`

**Parameters:**
- `name` (optional) - Your name for the greeting. Default: "Guest"

**Example Requests:**
```bash
# With name
curl "http://localhost:8081/greet?name=John"
# Response: Hello, John! Welcome to Spring Boot woooo!!!

# Without name (uses default)
curl http://localhost:8081/greet
# Response: Hello, Guest! Welcome to Spring Boot woooo!!!
```

---

### 2. Web Page Endpoint

**Endpoint:** `GET /greet-page`

**Parameters:**
- `name` (optional) - Your name for the greeting

**Example:**
```
http://localhost:8081/greet-page?name=Sarah
```

**What You'll See:**
- A clean web form
- Input field for your name
- "Greet Me!" button
- Personalized greeting displayed on the same page

---

## ⚙️ Configuration

### Change Server Port

Edit `src/main/resources/application.properties`:

```properties
# Default port
server.port=8081

# Change to another port
server.port=9000
```

### Add More Configuration

```properties
# Application name
spring.application.name=greeting_api

# Enable debug logging
logging.level.root=DEBUG
```

---

## 🧑‍💻 Code Walkthrough

### Main Application (`GreetingApiApplication.java`)

```java
@SpringBootApplication
public class GreetingApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(GreetingApiApplication.class, args);
    }
}
```

**What it does:** Bootstraps the Spring Boot application. This is the entry point - you rarely modify this file.

---

### Controller (`GreetingController.java`)

```java
@Controller
public class GreetingController {
    
    // API endpoint - returns plain text
    @GetMapping("/greet")
    public String greet(@RequestParam(defaultValue = "Guest") String name) {
        return "Hello, " + name + "! Welcome to Spring Boot!";
    }
    
    // Web page endpoint - returns HTML
    @GetMapping("/greet-page")
    public String greetPage(@RequestParam(required = false) String name, Model model) {
        if (name != null && !name.isEmpty()) {
            String greeting = "Hello, " + name + "!";
            model.addAttribute("greeting", greeting);
        }
        return "greeting";  // Returns greeting.html template
    }
}
```

**Key Concepts:**
- `@Controller` - Marks this class as a web controller
- `@GetMapping` - Maps HTTP GET requests to methods
- `@RequestParam` - Extracts URL parameters
- `Model` - Passes data to the HTML template

---

### Template (`greeting.html`)

```html
<form action="/greet-page" method="get">
    <input type="text" name="name" placeholder="Your name...">
    <button type="submit">Greet Me!</button>
</form>

<!-- Display greeting if present -->
<div th:if="${greeting}">
    <h2 th:text="${greeting}"></h2>
</div>
```

**Thymeleaf Syntax:**
- `th:if="${greeting}"` - Conditional rendering
- `th:text="${greeting}"` - Display variable content

---

## 🐍 For Python/Flask Developers

Coming from Flask? Here's how concepts map:

| Flask | Spring Boot | Purpose |
|-------|-------------|---------|
| `@app.route("/greet")` | `@GetMapping("/greet")` | Define route |
| `request.args.get('name')` | `@RequestParam String name` | Get URL parameter |
| `render_template('page.html', data=x)` | `model.addAttribute("data", x)` | Pass data to template |
| `{{ variable }}` | `th:text="${variable}"` | Display variable |
| `{% if condition %}` | `th:if="${condition}"` | Conditional |
| `requirements.txt` | `pom.xml` | Dependencies |
| `python app.py` | `./mvnw spring-boot:run` | Run server |

---

## 🎓 What You'll Learn

By exploring this project, you'll understand:

1. **Spring Boot Basics** - Project structure, annotations, auto-configuration
2. **REST API Development** - Creating GET endpoints with parameters
3. **MVC Pattern** - Model-View-Controller architecture
4. **Template Engines** - Server-side rendering with Thymeleaf
5. **Dependency Injection** - How Spring manages components
6. **Maven** - Java dependency management
7. **Configuration** - Using `application.properties`

---

## 🔧 Troubleshooting

### Port Already in Use

**Error:** `Port 8081 was already in use`

**Solution:** Change the port in `application.properties`:
```properties
server.port=9000
```

### Dependencies Not Downloading

**Error:** Maven can't download dependencies

**Solution:**
```bash
# Clean and rebuild
./mvnw clean install

# Or delete Maven cache and retry
rm -rf ~/.m2/repository
./mvnw spring-boot:run
```

### Template Not Found

**Error:** `Template 'greeting' not found`

**Solution:** Ensure `greeting.html` is in `src/main/resources/templates/`

---

## 📚 Next Steps

Want to extend this project? Try:

1. **Add POST support** - Handle form submissions with `@PostMapping`
2. **Database integration** - Store greetings with Spring Data JPA
3. **REST API expansion** - Add JSON responses with `@RestController`
4. **Authentication** - Secure endpoints with Spring Security
5. **Testing** - Write unit tests with JUnit and Mockito
6. **Docker** - Containerize the application
7. **Deploy** - Host on Heroku, AWS, or Google Cloud


---

## 🎯 Quick Commands Cheat Sheet

```bash
# Run the application
./mvnw spring-boot:run

# Clean build artifacts
./mvnw clean

# Package as JAR
./mvnw package

# Run tests
./mvnw test

# Skip tests and run
./mvnw spring-boot:run -DskipTests

# Run the packaged JAR
java -jar target/greeting_api-0.0.1-SNAPSHOT.jar
```

---
# Java Spring Boot for Python Developers

A beginner-friendly guide for Python developers learning Java Spring Boot. If you know Flask, you'll understand Spring Boot!

---

## Table of Contents

- [Quick Comparison](#quick-comparison)
- [Project Setup](#project-setup)
- [Project Structure Explained](#project-structure-explained)
- [Syntax Comparison](#syntax-comparison)
- [Your First API - Greeting Example](#your-first-api---greeting-example)
- [Running Your Application](#running-your-application)
- [Common Concepts Mapped](#common-concepts-mapped)
- [Configuration](#configuration)

---

## Quick Comparison

| Concept | Python/Flask | Java/Spring Boot |
|---------|--------------|------------------|
| **Package Manager** | `pip` | `Maven` (via `mvnw`) |
| **Dependencies File** | `requirements.txt` | `pom.xml` |
| **Virtual Environment** | `venv/` | Not needed (Maven uses `~/.m2/`) |
| **Main File** | `app.py` | `Application.java` |
| **Route Handler** | `@app.route()` | `@GetMapping()` |
| **Start Server** | `python app.py` | `./mvnw spring-boot:run` |
| **Default Port** | `5000` | `8080` |
| **Config File** | `.env` or `.flaskenv` | `application.properties` |

---

## Project Setup

### Python Flask Setup
```bash
mkdir myapp
cd myapp
python -m venv venv
source venv/bin/activate
pip install flask
touch app.py
python app.py
```

### Java Spring Boot Setup
```bash
# 1. Generate project at https://start.spring.io/
#    - Project: Maven
#    - Language: Java
#    - Spring Boot: 3.x.x
#    - Dependencies: Spring Web

# 2. Download and extract
unzip greeting_api.zip
cd greeting_api

# 3. Run (no venv needed!)
./mvnw spring-boot:run
```

---

## Project Structure Explained

### Flask Structure (Simple)
```
myapp/
├── app.py              ← Your routes and logic
├── requirements.txt    ← Dependencies
├── venv/              ← Virtual environment
├── templates/         ← HTML files
└── static/            ← CSS, JS, images
```

### Spring Boot Structure (Generated)
```
greeting_api/
├── src/
│   ├── main/
│   │   ├── java/                           ← Your Java code (Backend)
│   │   │   └── GreetingAPI/
│   │   │       └── greeting_api/
│   │   │           ├── GreetingApiApplication.java    ← Main entry point (starts server)
│   │   │           └── GreetingController.java        ← Your routes (like Flask @app.route)
│   │   │
│   │   └── resources/                      ← Configuration & Frontend
│   │       ├── application.properties      ← Config (like .env)
│   │       ├── static/                     ← CSS, JS, images
│   │       └── templates/                  ← HTML files
│   │
│   └── test/                               ← Unit tests
│       └── java/
│           └── GreetingAPI/
│               └── greeting_api/
│                   └── GreetingApiApplicationTests.java
│
├── target/                                 ← Compiled files (like Python's __pycache__)
├── pom.xml                                 ← Dependencies (like requirements.txt)
├── mvnw                                    ← Maven wrapper (Linux/Mac)
└── mvnw.cmd                                ← Maven wrapper (Windows)
```

**Key Folders:**
- **`src/main/java/`** = Your Python `.py` files (backend logic)
- **`src/main/resources/`** = Config + frontend files
- **`target/`** = Compiled output (auto-generated, ignore it)
- **`pom.xml`** = Like `requirements.txt`
- **`mvnw`** = Run Maven commands without installing Maven globally

---

## Syntax Comparison

### 1. Variables

**Python:**
```python
# Dynamically typed - no type declaration needed
name = "John"
age = 25
price = 19.99
is_active = True
```

**Java:**
```java
// Statically typed - must declare type
String name = "John";
int age = 25;
double price = 19.99;
boolean isActive = true;
```

---

### 2. Functions/Methods

**Python:**
```python
def greet(name):
    return f"Hello, {name}"

# With type hints (optional)
def greet(name: str) -> str:
    return f"Hello, {name}"
```

**Java:**
```java
public String greet(String name) {
    return "Hello, " + name;
}
```

**Breakdown:**
- `public` = visibility (anyone can use it)
- `String` = return type (what it gives back)
- `greet` = method name
- `(String name)` = parameter with type
- `{ }` = method body (like `:` and indentation in Python)

---

### 3. String Concatenation

**Python:**
```python
name = "John"
message = f"Hello, {name}!"        # f-string (modern)
message = "Hello, " + name + "!"   # concatenation
```

**Java:**
```java
String name = "John";
String message = "Hello, " + name + "!";  // concatenation with +
```

---

### 4. If Statements

**Python:**
```python
if age >= 18:
    print("Adult")
else:
    print("Minor")
```

**Java:**
```java
if (age >= 18) {
    System.out.println("Adult");
} else {
    System.out.println("Minor");
}
```

**Key differences:**
- Java requires `( )` around condition
- Java requires `{ }` for blocks
- Java uses `;` to end statements

---

### 5. Imports

**Python:**
```python
from flask import Flask
from flask import request
import os
```

**Java:**
```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
```

**Key differences:**
- Python: `from package import thing`
- Java: `import package.thing;` (full path with semicolon)

---

## Your First API - Greeting Example

### Python Flask Version

```python
from flask import Flask, request

app = Flask(__name__)

@app.route("/greet")
def greet():
    name = request.args.get('name', 'Guest')
    return f"Hello, {name}! Welcome to Flask!"

if __name__ == "__main__":
    app.run(port=5000)
```

### Java Spring Boot Version

**GreetingController.java:**
```java
package GreetingAPI.greeting_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/greet")
    public String greet(@RequestParam(defaultValue = "Guest") String name) {
        return "Hello, " + name + "! Welcome to Spring Boot!";
    }
}
```

### Side-by-Side Breakdown

| Python | Java | What It Does |
|--------|------|--------------|
| `from flask import Flask` | `import ...RestController;` | Import web framework |
| `app = Flask(__name__)` | `@RestController` | Mark class as web handler |
| `@app.route("/greet")` | `@GetMapping("/greet")` | Define URL route |
| `def greet():` | `public String greet(...)` | Define method/function |
| `request.args.get('name', 'Guest')` | `@RequestParam(defaultValue="Guest") String name` | Get URL parameter |
| `return f"Hello, {name}"` | `return "Hello, " + name` | Return response |

### Annotations Explained

**Java uses "annotations" (like Python decorators):**

```java
@RestController              // = This class handles web requests
@GetMapping("/greet")        // = Handle GET requests to /greet
@RequestParam(defaultValue = "Guest")  // = Get ?name= from URL
```

**Python equivalent:**
```python
@app.route("/greet")         # Decorator for routing

def greet():                 # Function to handle request
    pass
```

---

## Running Your Application

### Flask
```bash
python app.py
# Server starts on http://localhost:5000
```

### Spring Boot
```bash
./mvnw spring-boot:run
# Server starts on http://localhost:8080
```

### Testing the API

**Using curl:**
```bash
# With name parameter
curl "http://localhost:8081/greet?name=John"
# Output: Hello, John! Welcome to Spring Boot!

# Without name (uses default)
curl http://localhost:8081/greet
# Output: Hello, Guest! Welcome to Spring Boot!
```

**Or visit in browser:**
- `http://localhost:8081/greet?name=YourName`

---

## Common Concepts Mapped

### 1. Package Manager

**Python (pip):**
```bash
pip install flask
pip install requests
pip freeze > requirements.txt
```

**Java (Maven):**
```xml
<!-- In pom.xml -->
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```
```bash
./mvnw clean install  # Downloads dependencies
```

---

### 2. Virtual Environments

**Python:**
- Create isolated environment: `python -m venv venv`
- Activate: `source venv/bin/activate`
- Packages install in `venv/`

**Java:**
- No virtual environment needed!
- Maven downloads to `~/.m2/` (global cache)
- `mvnw` ensures consistent Maven version

---

### 3. Project Templates

**Flask:**
```bash
# Manual setup
mkdir myapp
cd myapp
touch app.py
# Write code from scratch
```

**Spring Boot:**
```bash
# Use generator (https://start.spring.io/)
# Downloads complete project structure
# Just unzip and start coding
```

Think: **Spring Initializr = Helm charts** (template generator)

---

### 4. Entry Point

**Python:**
```python
if __name__ == "__main__":
    app.run()
```

**Java:**
```java
public static void main(String[] args) {
    SpringApplication.run(GreetingApiApplication.class, args);
}
```

Both are the "start button" for your application.

---

## Configuration

### Change Server Port

**Python Flask:**
```python
# In app.py
if __name__ == "__main__":
    app.run(port=5002)
```

**Java Spring Boot:**
```properties
# In src/main/resources/application.properties
server.port=8081
```

### Environment Variables

**Python:**
```python
import os
DATABASE_URL = os.getenv('DATABASE_URL')
```

**Java:**
```properties
# In application.properties
database.url=${DATABASE_URL}
```

---

## Key Takeaways

1. **Java is verbose** - More boilerplate, but very structured
2. **Strong typing** - Must declare types: `String name`, `int age`
3. **Semicolons** - Every statement ends with `;`
4. **Braces** - Use `{ }` for blocks (not indentation)
5. **Annotations** - Like Python decorators: `@RestController`, `@GetMapping`
6. **Package = Folders** - Package name must match folder structure
7. **Class = File** - One public class per file, names must match
8. **Maven ≈ pip** - Manages dependencies automatically
9. **No venv needed** - Maven handles isolation differently

---

## Quick Start Checklist

- [ ] Go to https://start.spring.io/
- [ ] Select: Maven, Java, Spring Boot 3.x
- [ ] Add dependency: Spring Web
- [ ] Download and unzip
- [ ] Navigate to project root
- [ ] Run: `./mvnw spring-boot:run`
- [ ] Visit: `http://localhost:8080`
- [ ] Create your first Controller!

---

## Useful Commands

| Task | Flask | Spring Boot |
|------|-------|-------------|
| **Install dependencies** | `pip install -r requirements.txt` | `./mvnw clean install` |
| **Run server** | `python app.py` | `./mvnw spring-boot:run` |
| **Run tests** | `pytest` | `./mvnw test` |
| **Check syntax** | `pylint app.py` | Automatic (compile-time) |

---

## Next Steps

Once you're comfortable with the basics, explore:

1. **POST requests** - Handle form data
2. **JSON responses** - Return structured data
3. **Database integration** - Spring Data JPA (like SQLAlchemy)
4. **Templates** - Thymeleaf (like Jinja2)
5. **REST APIs** - Build full CRUD operations

---

## Resources

- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Spring Initializr](https://start.spring.io/)
- [Java Syntax Cheat Sheet](https://introcs.cs.princeton.edu/java/11cheatsheet/)
# Java Spring Boot Todo List Application

A full-stack todo list application built with **Java Spring Boot** and **Thymeleaf** to demonstrate fundamental web development concepts. Perfect for developers learning Java or transitioning from Python/Flask!

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.10-brightgreen)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue)](https://www.docker.com/)

---

## What This Application Does

A fully functional todo list web application that allows users to:
- ✅ Add new tasks
- ✅ Mark tasks as complete/incomplete
- ✅ Delete tasks
- ✅ View all tasks in a beautiful UI

**Built with pure Spring Boot - no frontend frameworks needed!**

---

## Features

- **CRUD Operations** - Create, Read, Update, Delete tasks
- **Responsive UI** - Beautiful gradient design with smooth interactions
- **Real-time Updates** - Changes reflect immediately
- **Docker Ready** - One command to run anywhere
- **Production Ready** - Deploy to Coolify, AWS, Heroku, etc.

---

## Technologies Used

| Technology | Purpose | Python Equivalent |
|------------|---------|-------------------|
| **Java 17** | Programming language | Python 3.x |
| **Spring Boot 3.5** | Web framework | Flask/Django |
| **Thymeleaf** | Template engine | Jinja2 |
| **Maven** | Dependency management | pip |
| **ArrayList** | In-memory storage | Python list |
| **Docker** | Containerization | Docker (same!) |

---

## Project Structure

```
Todo/
├── Dockerfile                          # Docker configuration
├── .dockerignore                       # Files to exclude from Docker
├── .gitignore                          # Files to exclude from Git
├── pom.xml                            # Maven dependencies (like requirements.txt)
├── README.md                          # This file
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── todo/
        │           ├── TodoApp.java          # Main entry point (starts server)
        │           ├── Task.java             # Task model (data structure)
        │           └── TodoController.java   # Routes & business logic
        └── resources/
            ├── application.properties        # Configuration (port, etc.)
            └── templates/
                └── index.html                # Frontend UI (Thymeleaf template)
```

---

##  Quick Start

### **Option 1: Docker (Recommended)**

```bash
# 1. Clone the repository
git clone https://github.com/yourusername/todo-app.git
cd todo-app

# 2. Build Docker image
docker build -t todo-app .

# 3. Run the container
docker run -p 8082:8082 todo-app

# 4. Open in browser
# Visit: http://localhost:8082
```

---

### **Option 2: Local Maven**

**Prerequisites:**
- Java 17+
- Maven 3.6+

```bash
# 1. Clone the repository
git clone https://github.com/yourusername/todo-app.git
cd todo-app

# 2. Run with Maven
mvn spring-boot:run

# 3. Open in browser
# Visit: http://localhost:8082
```

---

## Key Concepts & Vocabulary

### **Java Spring Boot Terminology Explained**

| Term | What It Means | Python/Flask Equivalent |
|------|---------------|-------------------------|
| **@SpringBootApplication** | Marks the main application class | `app = Flask(__name__)` |
| **@Controller** | Handles web requests | File with `@app.route()` decorators |
| **@GetMapping** | Handle GET requests | `@app.route("/", methods=["GET"])` |
| **@PostMapping** | Handle POST requests | `@app.route("/add", methods=["POST"])` |
| **@RequestParam** | Get URL/form parameters | `request.form.get("name")` |
| **Model** | Pass data to templates | `render_template('page.html', data=x)` |
| **Thymeleaf** | Template engine | Jinja2 |
| **ArrayList** | Dynamic array | Python `list` |
| **pom.xml** | Dependency file | `requirements.txt` |
| **application.properties** | Configuration file | `.env` or config file |

---

## Code Walkthrough

### **File 1: TodoApp.java (Entry Point)**

**Purpose:** Starts the Spring Boot application

```java
@SpringBootApplication
public class TodoApp {
    public static void main(String[] args) {
        SpringApplication.run(TodoApp.class, args);
    }
}
```

**Python Flask equivalent:**
```python
if __name__ == "__main__":
    app.run(port=8082)
```

**What it does:** This is the "ignition key" - it starts the web server on port 8082

---

### **File 2: Task.java (Data Model)**

**Purpose:** Defines what a "task" looks like

**Key Concepts:**

#### **Variables (Instance Variables)**
```java
private int id;
private String description;
private boolean completed;
```

**Python equivalent:**
```python
class Task:
    def __init__(self, description):
        self.id = 0
        self.description = ""
        self.completed = False
```

#### **Constructor**
```java
public Task(String description) {
    this.id = nextId++;
    this.description = description;
    this.completed = false;
}
```

**What it does:** Creates a new task with auto-incrementing ID

#### **Getters & Setters**
```java
public String getDescription() {
    return description;
}

public void setCompleted(boolean completed) {
    this.completed = completed;
}
```

**Why Java needs these:** Private variables can't be accessed directly. Getters "get" the value, setters "set" the value.

**Python equivalent:** Direct access like `task.description` or `task.completed = True`

---

### **File 3: TodoController.java (Routes & Logic)**

**Purpose:** Handles all web requests (like Flask routes)

#### **Storage (ArrayList)**
```java
private ArrayList<Task> tasks = new ArrayList<>();
```

**Python equivalent:**
```python
tasks = []
```

**What it is:** Java's version of a Python list - stores all tasks in memory

#### **Route 1: Display Homepage**
```java
@GetMapping("/")
public String home(Model model) {
    model.addAttribute("tasks", tasks);
    return "index";
}
```

**Python Flask equivalent:**
```python
@app.route("/")
def home():
    return render_template('index.html', tasks=tasks)
```

**What it does:**
1. Handle GET requests to homepage (`/`)
2. Pass tasks list to the template
3. Render `index.html`

#### **Route 2: Add New Task**
```java
@PostMapping("/add")
public String addTask(@RequestParam String description) {
    Task newTask = new Task(description);
    tasks.add(newTask);
    return "redirect:/";
}
```

**Python Flask equivalent:**
```python
@app.route("/add", methods=["POST"])
def add_task():
    description = request.form.get("description")
    task = Task(description)
    tasks.append(task)
    return redirect("/")
```

**What it does:**
1. Get task description from form
2. Create new Task object
3. Add to tasks list
4. Redirect back to homepage

#### **Route 3: Toggle Completion**
```java
@PostMapping("/toggle")
public String toggleTask(@RequestParam int id) {
    for (Task task : tasks) {
        if (task.getId() == id) {
            task.setCompleted(!task.isCompleted());
            break;
        }
    }
    return "redirect:/";
}
```

**Python equivalent:**
```python
@app.route("/toggle", methods=["POST"])
def toggle_task():
    task_id = request.form.get("id")
    for task in tasks:
        if task.id == task_id:
            task.completed = not task.completed
            break
    return redirect("/")
```

**What it does:**
1. Get task ID from form
2. Find task with matching ID
3. Toggle completed status (true → false, false → true)
4. Redirect back to homepage

#### **Route 4: Delete Task**
```java
@PostMapping("/delete")
public String deleteTask(@RequestParam int id) {
    tasks.removeIf(task -> task.getId() == id);
    return "redirect:/";
}
```

**Python equivalent:**
```python
@app.route("/delete", methods=["POST"])
def delete_task():
    task_id = request.form.get("id")
    tasks[:] = [t for t in tasks if t.id != task_id]
    return redirect("/")
```

**What it does:**
1. Get task ID from form
2. Remove task with matching ID (using lambda expression)
3. Redirect back to homepage

---

### **File 4: index.html (Frontend Template)**

**Purpose:** The web page users see

#### **Thymeleaf Syntax**

**Display variable:**
```html
<p th:text="${task.description}">Task text</p>
```
**Jinja2 equivalent:** `<p>{{ task.description }}</p>`

**Loop through list:**
```html
<ul th:each="task : ${tasks}">
    <li th:text="${task.description}"></li>
</ul>
```
**Jinja2 equivalent:** `{% for task in tasks %}<li>{{ task.description }}</li>{% endfor %}`

**Conditional:**
```html
<div th:if="${not #lists.isEmpty(tasks)}">
    <!-- Show if tasks exist -->
</div>
```
**Jinja2 equivalent:** `{% if tasks %} ... {% endif %}`

---

## Python vs Java Comparison

### **Complete Side-by-Side Example**

**Python Flask:**
```python
from flask import Flask, request, render_template, redirect

app = Flask(__name__)
tasks = []

@app.route("/")
def home():
    return render_template('index.html', tasks=tasks)

@app.route("/add", methods=["POST"])
def add_task():
    description = request.form.get("description")
    tasks.append({"id": len(tasks), "description": description})
    return redirect("/")

if __name__ == "__main__":
    app.run(port=8082)
```

**Java Spring Boot:**
```java
@SpringBootApplication
@Controller
public class TodoApp {
    private ArrayList<Task> tasks = new ArrayList<>();
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("tasks", tasks);
        return "index";
    }
    
    @PostMapping("/add")
    public String addTask(@RequestParam String description) {
        tasks.add(new Task(description));
        return "redirect:/";
    }
    
    public static void main(String[] args) {
        SpringApplication.run(TodoApp.class, args);
    }
}
```

---

## What You'll Learn

By building and studying this project, you'll understand:

1. **Java Fundamentals**
   - Variables and data types
   - Classes and objects
   - Methods (functions)
   - Constructors
   - Getters and setters

2. **Spring Boot Framework**
   - Project structure
   - Annotations (@Controller, @GetMapping, @PostMapping)
   - Dependency injection
   - MVC pattern

3. **Web Development**
   - HTTP GET vs POST
   - Form handling
   - Template rendering
   - Routing

4. **Data Structures**
   - ArrayList (Java's list)
   - Object manipulation
   - CRUD operations

5. **DevOps**
   - Docker containerization
   - Maven build tool
   - Deployment strategies

---

## Deployment

### **Deploy to Coolify**

1. Push code to GitHub
2. In Coolify, create new application
3. Select your GitHub repo
4. Coolify auto-detects Dockerfile
5. Deploy! 

### **Deploy to AWS Elastic Beanstalk**

```bash
# Package as JAR
mvn clean package

# Deploy (requires AWS CLI configured)
eb init -p docker
eb create todo-app-env
eb deploy
```

### **Deploy to Heroku**

```bash
# Login to Heroku
heroku login

# Create app
heroku create your-todo-app

# Deploy
git push heroku main
```

---

## Configuration

### **Change Port**

Edit `src/main/resources/application.properties`:
```properties
server.port=8082
```

### **Add More Properties**

```properties
# Application name
spring.application.name=Todo App

# Enable debug logging
logging.level.root=DEBUG

# Thymeleaf cache (disable for development)
spring.thymeleaf.cache=false
```

---

## Customization Ideas

Want to extend this project? Try adding:

1. **Persistence** - Save tasks to database (H2, PostgreSQL)
2. **Due Dates** - Add deadline field to tasks
3. **Categories** - Organize tasks by category (Work, Personal, etc.)
4. **Priority Levels** - Mark tasks as High/Medium/Low priority
5. **User Authentication** - Spring Security for login
6. **REST API** - Add JSON endpoints for mobile app
7. **Search** - Filter tasks by keyword
8. **Edit** - Update task descriptions
9. **Sorting** - Sort by date, priority, etc.
10. **Dark Mode** - Toggle theme

---

## Common Pitfalls & Solutions

### **Issue: Port Already in Use**
**Error:** `Port 8082 was already in use`

**Solution:**
```properties
# Change port in application.properties
server.port=9000
```

### **Issue: Cannot Find Task Class**
**Error:** `Cannot find symbol: class Task`

**Solution:** Ensure all files are in correct package structure:
```
src/main/java/com/todo/
├── TodoApp.java
├── Task.java
└── TodoController.java
```

---

##  Quick Command Reference

```bash
# Build Docker image
docker build -t todo-app .

# Run container
docker run -p 8082:8082 todo-app

# Run with Maven (local)
mvn spring-boot:run

# Package as JAR
mvn clean package

# Run JAR file
java -jar target/todo_app-1.0.0.jar

# Clean build
mvn clean

# Run tests
mvn test
```

---

A full-stack todo list application built with **Java Spring Boot** and **Thymeleaf** to demonstrate fundamental web development concepts. Perfect for developers learning Java or transitioning from Python/Flask!

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.10-brightgreen)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue)](https://www.docker.com/)

---

## 🎯 What This Application Does

A fully functional todo list web application that allows users to:
- ✅ Add new tasks
- ✅ Mark tasks as complete/incomplete
- ✅ Delete tasks
- ✅ View all tasks in a beautiful UI

**Built with pure Spring Boot - no frontend frameworks needed!**

---

## ✨ Features

- **CRUD Operations** - Create, Read, Update, Delete tasks
- **Responsive UI** - Beautiful gradient design with smooth interactions
- **Real-time Updates** - Changes reflect immediately
- **Docker Ready** - One command to run anywhere
- **Production Ready** - Deploy to Coolify, AWS, Heroku, etc.

---

## 🛠️ Technologies Used

| Technology | Purpose | Python Equivalent |
|------------|---------|-------------------|
| **Java 17** | Programming language | Python 3.x |
| **Spring Boot 3.5** | Web framework | Flask/Django |
| **Thymeleaf** | Template engine | Jinja2 |
| **Maven** | Dependency management | pip |
| **ArrayList** | In-memory storage | Python list |
| **Docker** | Containerization | Docker (same!) |

---

## 📁 Project Structure

```
Todo/
├── Dockerfile                          # Docker configuration
├── .dockerignore                       # Files to exclude from Docker
├── .gitignore                          # Files to exclude from Git
├── pom.xml                            # Maven dependencies (like requirements.txt)
├── README.md                          # This file
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── todo/
        │           ├── TodoApp.java          # Main entry point (starts server)
        │           ├── Task.java             # Task model (data structure)
        │           └── TodoController.java   # Routes & business logic
        └── resources/
            ├── application.properties        # Configuration (port, etc.)
            └── templates/
                └── index.html                # Frontend UI (Thymeleaf template)
```

---

## 🚀 Quick Start

### **Option 1: Docker (Recommended)** 🐳

```bash
# 1. Clone the repository
git clone https://github.com/yourusername/todo-app.git
cd todo-app

# 2. Build Docker image
docker build -t todo-app .

# 3. Run the container
docker run -p 8082:8082 todo-app

# 4. Open in browser
# Visit: http://localhost:8082
```

---

### **Option 2: Local Maven**

**Prerequisites:**
- Java 17+
- Maven 3.6+

```bash
# 1. Clone the repository
git clone https://github.com/yourusername/todo-app.git
cd todo-app

# 2. Run with Maven
mvn spring-boot:run

# 3. Open in browser
# Visit: http://localhost:8082
```

---

## 📚 Key Concepts & Vocabulary

### **Java Spring Boot Terminology Explained**

| Term | What It Means | Python/Flask Equivalent |
|------|---------------|-------------------------|
| **@SpringBootApplication** | Marks the main application class | `app = Flask(__name__)` |
| **@Controller** | Handles web requests | File with `@app.route()` decorators |
| **@GetMapping** | Handle GET requests | `@app.route("/", methods=["GET"])` |
| **@PostMapping** | Handle POST requests | `@app.route("/add", methods=["POST"])` |
| **@RequestParam** | Get URL/form parameters | `request.form.get("name")` |
| **Model** | Pass data to templates | `render_template('page.html', data=x)` |
| **Thymeleaf** | Template engine | Jinja2 |
| **ArrayList** | Dynamic array | Python `list` |
| **pom.xml** | Dependency file | `requirements.txt` |
| **application.properties** | Configuration file | `.env` or config file |

---

## 🔍 Code Walkthrough

### **File 1: TodoApp.java (Entry Point)**

**Purpose:** Starts the Spring Boot application

```java
@SpringBootApplication
public class TodoApp {
    public static void main(String[] args) {
        SpringApplication.run(TodoApp.class, args);
    }
}
```

**Python Flask equivalent:**
```python
if __name__ == "__main__":
    app.run(port=8082)
```

**What it does:** This is the "ignition key" - it starts the web server on port 8082

---

### **File 2: Task.java (Data Model)**

**Purpose:** Defines what a "task" looks like

**Key Concepts:**

#### **Variables (Instance Variables)**
```java
private int id;
private String description;
private boolean completed;
```

**Python equivalent:**
```python
class Task:
    def __init__(self, description):
        self.id = 0
        self.description = ""
        self.completed = False
```

#### **Constructor**
```java
public Task(String description) {
    this.id = nextId++;
    this.description = description;
    this.completed = false;
}
```

**What it does:** Creates a new task with auto-incrementing ID

#### **Getters & Setters**
```java
public String getDescription() {
    return description;
}

public void setCompleted(boolean completed) {
    this.completed = completed;
}
```

**Why Java needs these:** Private variables can't be accessed directly. Getters "get" the value, setters "set" the value.

**Python equivalent:** Direct access like `task.description` or `task.completed = True`

---

### **File 3: TodoController.java (Routes & Logic)**

**Purpose:** Handles all web requests (like Flask routes)

#### **Storage (ArrayList)**
```java
private ArrayList<Task> tasks = new ArrayList<>();
```

**Python equivalent:**
```python
tasks = []
```

**What it is:** Java's version of a Python list - stores all tasks in memory

#### **Route 1: Display Homepage**
```java
@GetMapping("/")
public String home(Model model) {
    model.addAttribute("tasks", tasks);
    return "index";
}
```

**Python Flask equivalent:**
```python
@app.route("/")
def home():
    return render_template('index.html', tasks=tasks)
```

**What it does:**
1. Handle GET requests to homepage (`/`)
2. Pass tasks list to the template
3. Render `index.html`

#### **Route 2: Add New Task**
```java
@PostMapping("/add")
public String addTask(@RequestParam String description) {
    Task newTask = new Task(description);
    tasks.add(newTask);
    return "redirect:/";
}
```

**Python Flask equivalent:**
```python
@app.route("/add", methods=["POST"])
def add_task():
    description = request.form.get("description")
    task = Task(description)
    tasks.append(task)
    return redirect("/")
```

**What it does:**
1. Get task description from form
2. Create new Task object
3. Add to tasks list
4. Redirect back to homepage

#### **Route 3: Toggle Completion**
```java
@PostMapping("/toggle")
public String toggleTask(@RequestParam int id) {
    for (Task task : tasks) {
        if (task.getId() == id) {
            task.setCompleted(!task.isCompleted());
            break;
        }
    }
    return "redirect:/";
}
```

**Python equivalent:**
```python
@app.route("/toggle", methods=["POST"])
def toggle_task():
    task_id = request.form.get("id")
    for task in tasks:
        if task.id == task_id:
            task.completed = not task.completed
            break
    return redirect("/")
```

**What it does:**
1. Get task ID from form
2. Find task with matching ID
3. Toggle completed status (true → false, false → true)
4. Redirect back to homepage

#### **Route 4: Delete Task**
```java
@PostMapping("/delete")
public String deleteTask(@RequestParam int id) {
    tasks.removeIf(task -> task.getId() == id);
    return "redirect:/";
}
```

**Python equivalent:**
```python
@app.route("/delete", methods=["POST"])
def delete_task():
    task_id = request.form.get("id")
    tasks[:] = [t for t in tasks if t.id != task_id]
    return redirect("/")
```

**What it does:**
1. Get task ID from form
2. Remove task with matching ID (using lambda expression)
3. Redirect back to homepage

---

### **File 4: index.html (Frontend Template)**

**Purpose:** The web page users see

#### **Thymeleaf Syntax**

**Display variable:**
```html
<p th:text="${task.description}">Task text</p>
```
**Jinja2 equivalent:** `<p>{{ task.description }}</p>`

**Loop through list:**
```html
<ul th:each="task : ${tasks}">
    <li th:text="${task.description}"></li>
</ul>
```
**Jinja2 equivalent:** `{% for task in tasks %}<li>{{ task.description }}</li>{% endfor %}`

**Conditional:**
```html
<div th:if="${not #lists.isEmpty(tasks)}">
    <!-- Show if tasks exist -->
</div>
```
**Jinja2 equivalent:** `{% if tasks %} ... {% endif %}`

---

## 🐍 Python vs Java Comparison

### **Complete Side-by-Side Example**

**Python Flask:**
```python
from flask import Flask, request, render_template, redirect

app = Flask(__name__)
tasks = []

@app.route("/")
def home():
    return render_template('index.html', tasks=tasks)

@app.route("/add", methods=["POST"])
def add_task():
    description = request.form.get("description")
    tasks.append({"id": len(tasks), "description": description})
    return redirect("/")

if __name__ == "__main__":
    app.run(port=8082)
```

**Java Spring Boot:**
```java
@SpringBootApplication
@Controller
public class TodoApp {
    private ArrayList<Task> tasks = new ArrayList<>();
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("tasks", tasks);
        return "index";
    }
    
    @PostMapping("/add")
    public String addTask(@RequestParam String description) {
        tasks.add(new Task(description));
        return "redirect:/";
    }
    
    public static void main(String[] args) {
        SpringApplication.run(TodoApp.class, args);
    }
}
```

---

## 🎓 What You'll Learn

By building and studying this project, you'll understand:

1. **Java Fundamentals**
   - Variables and data types
   - Classes and objects
   - Methods (functions)
   - Constructors
   - Getters and setters

2. **Spring Boot Framework**
   - Project structure
   - Annotations (@Controller, @GetMapping, @PostMapping)
   - Dependency injection
   - MVC pattern

3. **Web Development**
   - HTTP GET vs POST
   - Form handling
   - Template rendering
   - Routing

4. **Data Structures**
   - ArrayList (Java's list)
   - Object manipulation
   - CRUD operations

5. **DevOps**
   - Docker containerization
   - Maven build tool
   - Deployment strategies

---

## 🚢 Deployment

### **Deploy to Coolify**

1. Push code to GitHub
2. In Coolify, create new application
3. Select your GitHub repo
4. Coolify auto-detects Dockerfile
5. Deploy! 🎉

### **Deploy to AWS Elastic Beanstalk**

```bash
# Package as JAR
mvn clean package

# Deploy (requires AWS CLI configured)
eb init -p docker
eb create todo-app-env
eb deploy
```

### **Deploy to Heroku**

```bash
# Login to Heroku
heroku login

# Create app
heroku create your-todo-app

# Deploy
git push heroku main
```

---

## 🔧 Configuration

### **Change Port**

Edit `src/main/resources/application.properties`:
```properties
server.port=8082
```

### **Add More Properties**

```properties
# Application name
spring.application.name=Todo App

# Enable debug logging
logging.level.root=DEBUG

# Thymeleaf cache (disable for development)
spring.thymeleaf.cache=false
```

---

## 🎨 Customization Ideas

Want to extend this project? Try adding:

1. **Persistence** - Save tasks to database (H2, PostgreSQL)
2. **Due Dates** - Add deadline field to tasks
3. **Categories** - Organize tasks by category (Work, Personal, etc.)
4. **Priority Levels** - Mark tasks as High/Medium/Low priority
5. **User Authentication** - Spring Security for login
6. **REST API** - Add JSON endpoints for mobile app
7. **Search** - Filter tasks by keyword
8. **Edit** - Update task descriptions
9. **Sorting** - Sort by date, priority, etc.
10. **Dark Mode** - Toggle theme

---

## 💡 Common Pitfalls & Solutions

### **Issue: Port Already in Use**
**Error:** `Port 8082 was already in use`

**Solution:**
```properties
# Change port in application.properties
server.port=9000
```

### **Issue: Cannot Find Task Class**
**Error:** `Cannot find symbol: class Task`

**Solution:** Ensure all files are in correct package structure:
```
src/main/java/com/todo/
├── TodoApp.java
├── Task.java
└── TodoController.java
```

### **Issue: Template Not Found**
**Error:** `Template 'index' not found`

**Solution:** Ensure `index.html` is in:
```
src/main/resources/templates/index.html
```

---

## 📝 Interview-Ready Explanations

**"Explain your Todo app architecture."**
> "I built a full-stack web application using Spring Boot MVC pattern. The TodoController handles HTTP requests - GET for displaying tasks and POST for adding, toggling, and deleting. The Task class represents the data model with encapsulated fields. I used Thymeleaf for server-side rendering, which passes data from the controller to the HTML template. Tasks are stored in an ArrayList in memory, demonstrating CRUD operations without database complexity."

**"What's the difference between @GetMapping and @PostMapping?"**
> "@GetMapping handles HTTP GET requests, typically for retrieving and displaying data. @PostMapping handles POST requests, used for form submissions that create or modify data. In my app, GET displays the todo list, while POST adds, toggles, or deletes tasks."

**"Why use getters and setters instead of public variables?"**
> "Getters and setters provide encapsulation - they control access to private data. This allows validation, maintains data integrity, and lets me change internal implementation without breaking external code. For example, I could add logging to track when tasks are marked complete."

---

## 🤝 Contributing

Contributions welcome! Feel free to:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

## 🙏 Acknowledgments

- Built while learning Java Spring Boot
- Designed for Python developers transitioning to Java
- Docker-first approach for easy deployment
- Perfect for portfolio projects and learning

---

## 📬 Contact

**Your Name** - [@yourusername](https://github.com/yourusername)

**Project Link:** [https://github.com/yourusername/todo-app](https://github.com/yourusername/todo-app)

---

**⭐ If this helped you learn Java Spring Boot, please star the repo!**

---

## 🎯 Quick Command Reference

```bash
# Build Docker image
docker build -t todo-app .

# Run container
docker run -p 8082:8082 todo-app

# Run with Maven (local)
mvn spring-boot:run

# Package as JAR
mvn clean package

# Run JAR file
java -jar target/todo_app-1.0.0.jar

# Clean build
mvn clean

# Run tests
mvn test
```


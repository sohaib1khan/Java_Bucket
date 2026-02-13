# Expense Tracker

A full-stack expense tracking application built with Java Spring Boot and PostgreSQL. Track your daily expenses, visualize spending by category, and generate monthly reports.

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.10-brightgreen)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue)](https://www.docker.com/)

---

## Features

- Add, view, and delete expenses
- Categorize expenses (Food, Transport, Entertainment, Bills, Shopping, etc.)
- Filter expenses by category
- Monthly spending reports with charts
- Animated, responsive UI
- PostgreSQL database with persistent storage
- Docker Compose for easy deployment

---

## Tech Stack

| Technology | Purpose | Python Equivalent |
|------------|---------|-------------------|
| Java 17 | Programming language | Python 3.x |
| Spring Boot 3.5 | Web framework | Flask/Django |
| Spring Data JPA | Database ORM | SQLAlchemy |
| Thymeleaf | Template engine | Jinja2 |
| PostgreSQL 16 | Database | PostgreSQL (same) |
| H2 Database | Development database | SQLite |
| Maven | Dependency management | pip |
| Docker | Containerization | Docker (same) |
| Chart.js | Data visualization | Plotly/Matplotlib |

---

## Project Structure
```
expense-tracker/
├── docker-compose.yml              # Docker orchestration
├── Dockerfile                      # Container definition
├── pom.xml                        # Maven dependencies
└── src/main/
    ├── java/com/piggybank/expensetracker/
    │   ├── ExpensetrackerApplication.java  # Main entry point
    │   ├── Expense.java                    # Entity model
    │   ├── ExpenseRepository.java          # Database layer
    │   └── ExpenseController.java          # Routes and logic
    └── resources/
        ├── application.properties          # Configuration
        └── templates/
            ├── index.html                  # Main page
            └── report.html                 # Monthly report page
```

---

## Quick Start

### Prerequisites

- Docker and Docker Compose
- Java 17+ (only for local development without Docker)

### Run with Docker (Recommended)
```bash
# Clone the repository
git clone https://github.com/sohaib1khan/Java_Bucket.git
cd expense-tracker

# Start the application (PostgreSQL + App)
docker-compose up -d

# View logs
docker-compose logs -f

# Access the application
# Open: http://localhost:8083
```

**That's it!** Data persists in Docker volumes.

---

## Local Development (Without Docker)
```bash
# 1. Configure application.properties for H2
# (Already configured by default)

# 2. Run with Maven
./mvnw spring-boot:run

# 3. Access the application
# Open: http://localhost:8083
```

---

## Database Configuration

### PostgreSQL (Docker - Default)

The docker-compose.yml automatically configures PostgreSQL:
```yaml
SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/expenses
SPRING_DATASOURCE_USERNAME: admin
SPRING_DATASOURCE_PASSWORD: password
```

### H2 (Local Development)

application.properties includes H2 configuration:
```properties
spring.datasource.url=jdbc:h2:file:./data/expenses
spring.h2.console.enabled=true
```

Access H2 Console: http://localhost:8083/h2-console

---

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Display all expenses |
| POST | `/add` | Add new expense |
| POST | `/delete/{id}` | Delete expense by ID |
| GET | `/filter?category={cat}` | Filter by category |
| GET | `/report?month={yyyy-MM}` | Monthly report |

---

## Key Java Concepts Demonstrated

### 1. Entity Model (Database Mapping)

**Java (JPA):**
```java
@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    @NotBlank(message = "Description is required")
    private String description;
    
    @Column(nullable = false)
    @Positive(message = "Amount must be greater than 0")
    private Double amount;
}
```

**Python SQLAlchemy equivalent:**
```python
class Expense(db.Model):
    __tablename__ = 'expenses'
    id = db.Column(db.Integer, primary_key=True)
    description = db.Column(db.String(200), nullable=False)
    amount = db.Column(db.Numeric(10, 2), nullable=False)
```

**Key differences:**
- Java uses annotations for configuration
- Java requires explicit type declarations
- Java uses getters/setters for encapsulation

---

### 2. Repository Pattern (Database Access)

**Java (Spring Data JPA):**
```java
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByCategory(String category);
    List<Expense> findByDateBetween(LocalDate start, LocalDate end);
}
```

**Python SQLAlchemy equivalent:**
```python
# Manual queries
expenses = Expense.query.filter_by(category=category).all()
expenses = Expense.query.filter(
    Expense.date >= start_date,
    Expense.date <= end_date
).all()
```

**Java advantage:** Spring Data JPA generates queries from method names automatically. No SQL needed!

---

### 3. Controller (Routes and Logic)

**Java (Spring MVC):**
```java
@Controller
public class ExpenseController {
    @Autowired
    private ExpenseRepository repository;
    
    @GetMapping("/")
    public String home(Model model) {
        List<Expense> expenses = repository.findAll();
        model.addAttribute("expenses", expenses);
        return "index";
    }
    
    @PostMapping("/add")
    public String add(@Valid Expense expense) {
        repository.save(expense);
        return "redirect:/";
    }
}
```

**Python Flask equivalent:**
```python
@app.route("/")
def home():
    expenses = Expense.query.all()
    return render_template('index.html', expenses=expenses)

@app.route("/add", methods=["POST"])
def add():
    expense = Expense(...)
    db.session.add(expense)
    db.session.commit()
    return redirect("/")
```

**Key differences:**
- Java uses dependency injection (`@Autowired`)
- Java uses `Model` to pass data to templates
- Java validation is annotation-based (`@Valid`)

---

### 4. Form Validation

**Java:**
```java
@Column(nullable = false)
@NotBlank(message = "Description is required")
private String description;

@Positive(message = "Amount must be greater than 0")
private Double amount;
```

**Python WTForms equivalent:**
```python
class ExpenseForm(FlaskForm):
    description = StringField('Description', 
                             validators=[DataRequired()])
    amount = DecimalField('Amount', 
                         validators=[NumberRange(min=0.01)])
```

**Java advantage:** Validation is in the model itself, closer to the data.

---

## Docker Commands
```bash
# Start services
docker-compose up -d

# Stop services (keeps data)
docker-compose stop

# Restart services
docker-compose restart

# View logs
docker-compose logs -f
docker-compose logs -f app
docker-compose logs -f postgres

# Stop and remove containers (keeps data volumes)
docker-compose down

# Remove everything including data
docker-compose down -v

# Rebuild after code changes
docker-compose up -d --build

# Access PostgreSQL shell
docker-compose exec postgres psql -U admin -d expenses
```

---

## Database Access

### Using psql (Command Line)
```bash
# Connect to PostgreSQL
docker-compose exec postgres psql -U admin -d expenses

# View tables
\dt

# View expenses
SELECT * FROM expenses;

# Exit
\q
```

### Using GUI Tools

Connect with pgAdmin, DBeaver, or any PostgreSQL client:

- **Host:** localhost
- **Port:** 5432
- **Database:** expenses
- **Username:** admin
- **Password:** password

---

## Environment Variables

Override default configuration:
```bash
docker-compose up -d \
  -e POSTGRES_PASSWORD=newsecret \
  -e SPRING_DATASOURCE_PASSWORD=newsecret
```

Or edit docker-compose.yml directly.

---

## Troubleshooting

### Port 8083 already in use
```bash
# Change the port in docker-compose.yml:
ports:
  - "9000:8083"  # Use port 9000 instead
```

### Database connection errors
```bash
# Check if PostgreSQL is running
docker-compose ps

# View PostgreSQL logs
docker-compose logs postgres

# Restart services
docker-compose restart
```

### Data not persisting
```bash
# Check if volumes exist
docker volume ls | grep expense

# Named volumes should persist automatically
# If using bind mounts, check directory permissions
```

---



## Learning Resources

### Java & Spring Boot
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Guide](https://spring.io/guides/gs/accessing-data-jpa/)
- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)

### For Python Developers
- [Java for Python Programmers](https://runestone.academy/ns/books/published/java4python/index.html)
- [Spring Boot vs Flask Comparison](https://dzone.com/articles/spring-boot-vs-flask)



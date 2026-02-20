# TrackMyStacks

A secure, full-stack personal finance tracking application built with Spring Boot and modern web technologies.

## Overview

TrackMyStacks is a multi-user expense tracking application featuring user authentication, role-based access control, and a beautiful animated user interface. The application allows users to track their personal expenses while administrators can manage user accounts through a dedicated admin panel.

## Features

### Core Functionality
- User authentication with secure password hashing (BCrypt)
- Role-based access control (Admin and User roles)
- Personal expense tracking and management
- Real-time expense totals and calculations
- Admin panel for user management
- Responsive and animated user interface

### Security
- Spring Security integration
- Password hashing with BCrypt
- Session management
- CSRF protection (disabled for development)
- Admin-only routes protection

### User Management
- Admin-controlled user creation (no public signup)
- User list with role indicators
- User deletion capability
- Separate data isolation per user

### Expense Management
- Add expenses with amount, category, date, and description
- View all personal expenses in chronological order
- Delete expenses
- Automatic total calculation
- Category-based organization
- Date filtering support

## Technology Stack

### Backend
- Java 17
- Spring Boot 3.2.2
- Spring Security
- Spring Data JPA
- Hibernate
- H2 Database (file-based)

### Frontend
- Thymeleaf templating engine
- HTML5/CSS3
- Vanilla JavaScript
- Animated UI with CSS animations

### DevOps
- Docker
- Docker Compose
- Maven for build management

## Project Structure

```
TrackMyStacks/
├── docker-compose.yml          # Docker Compose configuration
├── Dockerfile                  # Docker image definition
├── pom.xml                     # Maven dependencies
└── src/main/
    ├── java/com/sohaib/trackmystacks/
    │   ├── config/
    │   │   ├── DataInitializer.java       # Initial admin user creation
    │   │   └── SecurityConfig.java        # Security configuration
    │   ├── controller/
    │   │   ├── AdminController.java       # Admin panel endpoints
    │   │   ├── AuthController.java        # Login/logout endpoints
    │   │   ├── DashboardController.java   # Main dashboard
    │   │   └── ExpenseController.java     # Expense CRUD operations
    │   ├── model/
    │   │   ├── Expense.java               # Expense entity
    │   │   └── User.java                  # User entity
    │   ├── repository/
    │   │   ├── ExpenseRepository.java     # Expense data access
    │   │   └── UserRepository.java        # User data access
    │   ├── service/
    │   │   ├── CustomUserDetailsService.java  # Authentication service
    │   │   ├── ExpenseService.java            # Business logic
    │   │   └── UserService.java               # User management
    │   └── TrackMyStacksApplication.java  # Main application entry
    └── resources/
        ├── application.properties         # Application configuration
        └── templates/
            ├── admin/panel.html          # Admin panel UI
            ├── dashboard.html            # Main dashboard UI
            └── login.html                # Login page UI
```

## Prerequisites

### Local Development
- Java 17 or higher
- Maven 3.6+

### Docker Deployment
- Docker
- Docker Compose

## Installation and Setup

### Option 1: Docker Deployment (Recommended)

1. Clone the repository:
```bash
git clone https://github.com/sohaib1khan/Java_Bucket.git
cd TrackMyStacks
```

2. Build and run with Docker Compose:
```bash
docker-compose up -d --build
```

3. Access the application:
```
http://localhost:8785/login
```

4. Login with default credentials:
```
Username: admin
Password: admin123
```

### Option 2: Local Development

1. Clone the repository:
```bash
git clone https://github.com/sohaib1khan/Java_Bucket.git
cd TrackMyStacks
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

4. Access the application:
```
http://localhost:8785/login
```

## Configuration

### Application Properties

Key configuration settings in `src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8785

# Database Configuration (H2)
spring.datasource.url=jdbc:h2:file:/app/data/trackmystacks
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# H2 Console (Development Only)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Thymeleaf
spring.thymeleaf.cache=false
```

### Docker Configuration

The Docker setup includes:
- Multi-stage build for optimized image size
- Persistent volume mounting for database
- Health checks
- Port mapping (8785:8785)

Volume mounting:
```yaml
volumes:
  - ./docker-data:/app/data
```

## Database

### H2 Database
- File-based storage for persistence
- Located at `/app/data/trackmystacks.mv.db` (Docker) or `./data/` (local)
- Automatic schema generation and updates via Hibernate

### Database Schema

**Users Table:**
```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_admin BOOLEAN,
    created_at TIMESTAMP
);
```

**Expenses Table:**
```sql
CREATE TABLE expenses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    category VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    date DATE NOT NULL,
    created_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

## Usage

### Admin Functions

1. **Access Admin Panel:**
   - Login as admin user
   - Click "Admin Panel" in navigation bar

2. **Create New User:**
   - Fill in username, email, and password
   - Optionally check "Admin User?" for admin privileges
   - Click "Create User"

3. **Delete User:**
   - View user list in admin panel
   - Click "Delete" button for target user
   - Confirm deletion

### User Functions

1. **Add Expense:**
   - Enter amount, select category
   - Add description and date
   - Click "Add" button

2. **View Expenses:**
   - All expenses displayed in chronological order
   - Total amount shown at top

3. **Delete Expense:**
   - Click "Delete" button on expense row
   - Confirm deletion

## Default Admin Account

The application creates a default admin account on first startup:

```
Username: admin
Password: admin123
```

**Security Note:** Change the default admin password immediately in production environments.

### Admin User Behavior

The `DataInitializer` component automatically creates the admin user if it doesn't exist. This runs on every application startup. To prevent recreation of the admin user after deletion, modify `DataInitializer.java` or remove the component entirely.

## Data Persistence

### Docker Environment
- Database stored in `./docker-data/` directory
- Survives container restarts and rebuilds
- To reset database: `rm -rf docker-data/`

### Local Environment
- Database stored in `./data/` directory
- Persistent across application restarts

## Development

### Building the Project

```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package as JAR
mvn clean package

# Run application
mvn spring-boot:run
```

### Docker Commands

```bash
# Build and start
docker-compose up -d --build

# View logs
docker-compose logs -f

# Stop application
docker-compose down

# Rebuild after changes
docker-compose up -d --build

# Access container shell
docker exec -it trackmystacks-app sh
```

## API Endpoints

### Authentication
- `GET /login` - Login page
- `POST /login` - Login submission
- `POST /logout` - Logout
- `GET /` - Redirects to dashboard

### Dashboard
- `GET /dashboard` - Main dashboard (authenticated users)

### Expenses
- `POST /expenses/add` - Create new expense
- `POST /expenses/delete/{id}` - Delete expense
- `POST /expenses/update/{id}` - Update expense

### Admin
- `GET /admin` - Admin panel (admin only)
- `POST /admin/create-user` - Create new user
- `POST /admin/delete-user/{id}` - Delete user

## Troubleshooting

### Common Issues

**Issue: Port 8785 already in use**
```bash
# Change port in application.properties
server.port=8786

# Or in docker-compose.yml
ports:
  - "8786:8785"
```

**Issue: Database locked**
```bash
# Stop application
docker-compose down

# Remove database lock
rm docker-data/trackmystacks.trace.db

# Restart
docker-compose up -d
```

**Issue: Admin user recreates after deletion**
- See "Default Admin Account" section
- Modify or remove `DataInitializer.java`

**Issue: Login fails after rebuild**
- Check if database was preserved
- Verify admin user exists in H2 console
- Clear browser cookies

### H2 Console Access

For debugging (development only):
1. Access: `http://localhost:8785/h2-console`
2. JDBC URL: `jdbc:h2:file:/app/data/trackmystacks`
3. Username: `sa`
4. Password: (empty)

## Security Considerations

### Production Deployment

1. **Change default admin password**
2. **Disable H2 console:**
   ```properties
   spring.h2.console.enabled=false
   ```

3. **Enable CSRF protection** in `SecurityConfig.java`

4. **Use environment variables for sensitive data**

5. **Implement HTTPS**

6. **Use production-grade database** (PostgreSQL, MySQL)

7. **Set strong password policies**

## Backup and Restore

### Backup Database

```bash
# Docker environment
tar -czf backup-$(date +%Y%m%d).tar.gz docker-data/

# Local environment
tar -czf backup-$(date +%Y%m%d).tar.gz data/
```

### Restore Database

```bash
# Stop application
docker-compose down

# Restore backup
tar -xzf backup-20260219.tar.gz

# Start application
docker-compose up -d
```

## Performance Considerations

- H2 is suitable for development and small deployments
- For production with multiple users, consider PostgreSQL or MySQL
- Implement connection pooling for production
- Add caching layer for frequently accessed data
- Implement pagination for large expense lists


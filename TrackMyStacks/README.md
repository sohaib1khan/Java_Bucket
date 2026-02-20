# TrackMyStacks

A secure, full-stack personal finance tracking application built with Spring Boot and modern web technologies. Features a beautiful animated UI, mobile-responsive design, and comprehensive expense management.

## Overview

TrackMyStacks is a multi-user expense tracking application featuring user authentication, role-based access control, and an animated, accessible user interface. The application allows users to track their personal expenses with recurring expense support, while administrators can manage users and categories through a dedicated admin panel.

## Features

### Core Functionality
- User authentication with secure password hashing (BCrypt)
- Role-based access control (Admin and User roles)
- Personal expense tracking and management
- Real-time expense totals and calculations
- Edit and delete expense operations
- Admin panel for user and category management
- Recurring expense tracking with visual indicators
- Responsive design (desktop table view, mobile card view)
- Beautiful animated user interface optimized for accessibility

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
- Add expenses with amount, category, date, description, and recurring flag
- Edit existing expenses with modal popup interface
- View all personal expenses in chronological order
- Delete expenses with confirmation
- Automatic total calculation
- Dynamic category system (admin-controlled)
- Date filtering support
- Recurring expense indicators (visual icon on recurring items)

### Category Management
- Admin-controlled category creation and deletion
- Dynamic category dropdown in forms
- All users share the same category list
- Default categories pre-populated on first run

### Mobile Responsive Design
- Adaptive layouts for all screen sizes
- Desktop: Full table view with all columns
- Mobile: Card-based layout with large touch targets
- Optimized for single-eye use (high contrast, large text, calm animations)
- Touch-friendly buttons (44px minimum)
- Vertical form stacking on mobile devices

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
- CSS Grid and Flexbox
- Responsive design with media queries
- CSS animations

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
    │   │   ├── DataInitializer.java       # Initial admin user and category creation
    │   │   └── SecurityConfig.java        # Security configuration
    │   ├── controller/
    │   │   ├── AdminController.java       # Admin panel endpoints (users + categories)
    │   │   ├── AuthController.java        # Login/logout endpoints
    │   │   ├── DashboardController.java   # Main dashboard
    │   │   └── ExpenseController.java     # Expense CRUD operations
    │   ├── model/
    │   │   ├── Category.java              # Category entity
    │   │   ├── Expense.java               # Expense entity (with recurring field)
    │   │   └── User.java                  # User entity
    │   ├── repository/
    │   │   ├── CategoryRepository.java    # Category data access
    │   │   ├── ExpenseRepository.java     # Expense data access
    │   │   └── UserRepository.java        # User data access
    │   ├── service/
    │   │   ├── CategoryService.java           # Category business logic
    │   │   ├── CustomUserDetailsService.java  # Authentication service
    │   │   ├── ExpenseService.java            # Expense business logic
    │   │   └── UserService.java               # User management
    │   └── TrackMyStacksApplication.java  # Main application entry
    └── resources/
        ├── application.properties         # Application configuration
        └── templates/
            ├── admin/panel.html          # Admin panel UI (users + categories)
            ├── dashboard.html            # Main dashboard UI (responsive)
            └── login.html                # Login page UI (animated)
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

**Categories Table:**
```sql
CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL,
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
    expense_date DATE NOT NULL,
    recurring BOOLEAN DEFAULT FALSE,
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

4. **Manage Categories:**
   - Enter new category name in the form
   - Click "Add Category"
   - Delete categories by clicking the X next to category name
   - Note: Deleting a category doesn't delete expenses using that category

### User Functions

1. **Add Expense:**
   - Enter amount, select category from dropdown
   - Add description and date
   - Check "Recurring" box for monthly bills (Netflix, rent, etc.)
   - Click "Add" button

2. **Edit Expense:**
   - Click "Edit" button on any expense
   - Modal popup appears with current values
   - Modify any field including recurring status
   - Click "Save Changes"

3. **View Expenses:**
   - Desktop: All expenses displayed in sortable table with recurring indicator
   - Mobile: Expenses shown as cards with large, readable text
   - Recurring expenses marked with green recycle icon (🔄)
   - Total amount shown at top

4. **Delete Expense:**
   - Click "Delete" button on expense row/card
   - Confirm deletion

## Default Admin Account

The application creates a default admin account on first startup:

```
Username: admin
Password: admin123
```

**Security Note:** Change the default admin password immediately in production environments.

### Default Categories

Pre-populated categories on first run:
- Food
- Transport
- Entertainment
- Bills
- Shopping
- Health
- Tech
- Other

Admins can add or remove categories as needed.

## Data Persistence

### Docker Environment
- Database stored in `./docker-data/` directory
- Survives container restarts and rebuilds
- To reset database: `rm -rf docker-data/`

### Local Environment
- Database stored in `./data/` directory
- Persistent across application restarts

### Resetting Database After Schema Changes

If you encounter column errors after updates:

```bash
# Stop container
docker-compose down

# Delete database
rm -rf docker-data/

# Rebuild
docker-compose up -d --build
```

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
- `POST /admin/create-category` - Create new category
- `POST /admin/delete-category/{id}` - Delete category

## Mobile Responsive Design

### Desktop View (768px+)
- Full table layout with all columns
- Horizontal form layout
- Hover effects on rows
- All features accessible

### Mobile View (<768px)
- Card-based expense display
- Vertical form stacking
- Large touch targets (44px minimum)
- Full-width buttons
- Optimized text sizes
- Simplified navigation

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

**Issue: Column not found error (after adding recurring)**
```bash
# Reset database
docker-compose down
rm -rf docker-data/
docker-compose up -d --build
```

**Issue: Categories not showing in dropdown**
- Check admin panel to verify categories exist
- Refresh the page
- Check browser console for errors

**Issue: Modal not opening**
- Check browser console for JavaScript errors
- Ensure page fully loaded before clicking Edit
- Try refreshing the page

**Issue: Mobile view not working**
- Clear browser cache
- Check viewport meta tag in HTML
- Test in actual mobile device, not just browser resize

### H2 Console Access

For debugging (development only):
1. Access: `http://localhost:8785/h2-console`
2. JDBC URL: `jdbc:h2:file:/app/data/trackmystacks`
3. Username: `sa`
4. Password: (empty)

## Security Considerations

### Production Deployment

1. **Change default admin password immediately**

2. **Disable H2 console:**
   ```properties
   spring.h2.console.enabled=false
   ```

3. **Enable CSRF protection** in `SecurityConfig.java`

4. **Use environment variables for sensitive data**

5. **Implement HTTPS**

6. **Use production-grade database** (PostgreSQL, MySQL)

7. **Set strong password policies**

8. **Add rate limiting**

9. **Implement session timeout**

10. **Add audit logging**

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
tar -xzf backup-20260220.tar.gz

# Start application
docker-compose up -d
```

## Performance Considerations

- H2 is suitable for development and small deployments
- For production with multiple users, consider PostgreSQL or MySQL
- Implement connection pooling for production
- Add caching layer for frequently accessed data
- Implement pagination for large expense lists

## Feature List

### Implemented Features
- User authentication and authorization
- Admin-controlled user management
- Dynamic category system
- Expense CRUD operations (Create, Read, Update, Delete)
- Recurring expense tracking
- Mobile responsive design
- Animated, accessible UI
- Docker containerization
- Data persistence
- Real-time calculations


## Design Philosophy


### Mobile-First Responsive
- Works beautifully on all devices
- Card layout on mobile for easier reading
- Table layout on desktop for efficiency
- No horizontal scrolling required

### Security by Design
- Admin-only user creation prevents spam
- Password hashing never stores plain text
- Session-based authentication
- Role-based access control

```

---


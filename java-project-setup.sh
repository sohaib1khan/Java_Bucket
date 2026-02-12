#!/bin/bash

################################################################################
# Java Spring Boot Project Setup Script
# Purpose: Interactive project generator and management tool
# Author: Sohaib
# Usage: ./java-project-setup.sh
################################################################################

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

# Script version
VERSION="1.0.0"

################################################################################
# Helper Functions
################################################################################

print_header() {
    echo -e "${CYAN}"
    echo "╔════════════════════════════════════════════════════════════╗"
    echo "║                                                            ║"
    echo "║        Java Spring Boot Project Setup Tool v${VERSION}        ║"
    echo "║                                                            ║"
    echo "╚════════════════════════════════════════════════════════════╝"
    echo -e "${NC}"
}

print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠ $1${NC}"
}

print_info() {
    echo -e "${BLUE}ℹ $1${NC}"
}

print_step() {
    echo -e "${CYAN}▶ $1${NC}"
}

# Detect OS
detect_os() {
    case "$(uname -s)" in
        Linux*)     OS="Linux";;
        Darwin*)    OS="Mac";;
        CYGWIN*)    OS="Windows";;
        MINGW*)     OS="Windows";;
        *)          OS="Unknown";;
    esac
    echo "$OS"
}

# Check if command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Pause for user to read
pause() {
    echo ""
    read -p "Press [Enter] to continue..."
    echo ""
}

################################################################################
# Environment Checks
################################################################################

check_java() {
    print_step "Checking Java installation..."
    
    if command_exists java; then
        JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
        print_success "Java is installed: Version $JAVA_VERSION"
        
        # Check if Java 17+
        JAVA_MAJOR=$(echo "$JAVA_VERSION" | cut -d'.' -f1)
        if [ "$JAVA_MAJOR" -ge 17 ]; then
            print_success "Java version is compatible (17+)"
            return 0
        else
            print_warning "Java version is below 17. Spring Boot 3.x requires Java 17+"
            return 1
        fi
    else
        print_error "Java is NOT installed"
        echo ""
        print_info "Install Java 17 or higher:"
        echo "  Ubuntu/Debian: sudo apt install openjdk-17-jdk"
        echo "  Mac: brew install openjdk@17"
        echo "  Or download from: https://adoptium.net/"
        return 1
    fi
}

check_maven() {
    print_step "Checking Maven installation..."
    
    if command_exists mvn; then
        MAVEN_VERSION=$(mvn -version | head -n 1 | awk '{print $3}')
        print_success "Maven is installed: Version $MAVEN_VERSION"
        return 0
    else
        print_warning "Maven is NOT installed (optional - can use Docker)"
        echo ""
        print_info "To install Maven:"
        echo "  Ubuntu/Debian: sudo apt install maven"
        echo "  Mac: brew install maven"
        echo ""
        print_info "Alternatively, use Docker to run without Maven!"
        return 1
    fi
}

check_docker() {
    print_step "Checking Docker installation..."
    
    if command_exists docker; then
        DOCKER_VERSION=$(docker --version | awk '{print $3}' | tr -d ',')
        print_success "Docker is installed: Version $DOCKER_VERSION"
        
        # Check if Docker daemon is running
        if docker ps >/dev/null 2>&1; then
            print_success "Docker daemon is running"
            return 0
        else
            print_warning "Docker is installed but daemon is not running"
            print_info "Start Docker daemon and try again"
            return 1
        fi
    else
        print_warning "Docker is NOT installed (optional but recommended)"
        echo ""
        print_info "To install Docker:"
        echo "  Visit: https://docs.docker.com/get-docker/"
        return 1
    fi
}

check_curl() {
    print_step "Checking curl installation..."
    
    if command_exists curl; then
        print_success "curl is installed"
        return 0
    else
        print_error "curl is NOT installed (required for template download)"
        echo ""
        print_info "Install curl:"
        echo "  Ubuntu/Debian: sudo apt install curl"
        echo "  Mac: curl is pre-installed"
        return 1
    fi
}

run_environment_check() {
    clear
    print_header
    echo ""
    echo -e "${CYAN}═══════════════════════════════════════════════════════════${NC}"
    echo -e "${CYAN}  Environment Check${NC}"
    echo -e "${CYAN}═══════════════════════════════════════════════════════════${NC}"
    echo ""
    
    OS=$(detect_os)
    print_info "Detected OS: $OS"
    echo ""
    
    JAVA_OK=0
    MAVEN_OK=0
    DOCKER_OK=0
    CURL_OK=0
    
    check_java && JAVA_OK=1
    echo ""
    check_maven && MAVEN_OK=1
    echo ""
    check_docker && DOCKER_OK=1
    echo ""
    check_curl && CURL_OK=1
    echo ""
    
    echo -e "${CYAN}═══════════════════════════════════════════════════════════${NC}"
    echo ""
    
    if [ $JAVA_OK -eq 1 ] && [ $CURL_OK -eq 1 ]; then
        print_success "Minimum requirements met! Ready to proceed."
    else
        print_error "Some requirements are missing. Please install them first."
    fi
    
    if [ $DOCKER_OK -eq 1 ]; then
        echo ""
        print_info "Docker is available - you can run without Maven!"
    fi
    
    pause
}

################################################################################
# Template Generation
################################################################################

generate_gitignore() {
    local PROJECT_DIR=$1
    
    print_step "Generating .gitignore..."
    
    cat > "$PROJECT_DIR/.gitignore" << 'EOF'
# Compiled class files
*.class

# Package Files
*.jar
*.war
*.ear

# Build directories
target/
build/
out/

# IDE files
.idea/
.vscode/
*.iml
*.ipr
*.iws
.project
.classpath
.settings/

# OS files
.DS_Store
Thumbs.db
*.swp
*~

# Log files
*.log

# Maven wrapper (optional - uncomment if not using)
# mvnw
# mvnw.cmd
# .mvn/

# Application specific
application-*.properties
!application.properties

# Test files
*.class
EOF

    print_success "Created .gitignore"
}

generate_dockerignore() {
    local PROJECT_DIR=$1
    
    print_step "Generating .dockerignore..."
    
    cat > "$PROJECT_DIR/.dockerignore" << 'EOF'
# Build directories
target/
build/

# IDE files
.idea/
.vscode/
*.iml

# Git
.git/
.gitignore

# Documentation
README.md
*.md

# OS files
.DS_Store
*.swp

# Maven wrapper (if using in Dockerfile)
# mvnw
# mvnw.cmd
# .mvn/

# Logs
*.log
EOF

    print_success "Created .dockerignore"
}

generate_dockerfile() {
    local PROJECT_DIR=$1
    
    print_step "Generating Dockerfile..."
    
    cat > "$PROJECT_DIR/Dockerfile" << 'EOF'
# Use official Maven image with Java 17
FROM maven:3.9-eclipse-temurin-17

# Set working directory
WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src

# Download dependencies (cached layer)
RUN mvn dependency:go-offline -B

# Expose port (update if needed)
EXPOSE 8080

# Run the application
CMD ["mvn", "spring-boot:run"]
EOF

    print_success "Created Dockerfile"
}

generate_minimal_structure() {
    local PROJECT_NAME=$1
    local GROUP_ID=$2
    local ARTIFACT_ID=$3
    local PACKAGE_PATH=$(echo "$GROUP_ID.$ARTIFACT_ID" | tr '.' '/')
    
    print_step "Creating minimal project structure..."
    
    # Create directory structure
    mkdir -p "$PROJECT_NAME/src/main/java/$PACKAGE_PATH"
    mkdir -p "$PROJECT_NAME/src/main/resources/templates"
    mkdir -p "$PROJECT_NAME/src/main/resources/static"
    
    print_success "Created directory structure"
    
    # Generate pom.xml
    print_step "Generating pom.xml..."
    
    cat > "$PROJECT_NAME/pom.xml" << EOF
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         https://maven.apache.org/xsd/maven-4.0.0.xsd">
    
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.10</version>
    </parent>
    
    <groupId>$GROUP_ID</groupId>
    <artifactId>$ARTIFACT_ID</artifactId>
    <version>1.0.0</version>
    <name>$PROJECT_NAME</name>
    <description>Spring Boot Application</description>
    
    <properties>
        <java.version>17</java.version>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
    
</project>
EOF

    print_success "Created pom.xml"
    
    # Generate application.properties
    cat > "$PROJECT_NAME/src/main/resources/application.properties" << EOF
# Server Configuration
server.port=8080

# Application Name
spring.application.name=$PROJECT_NAME
EOF

    print_success "Created application.properties"
    
    # Generate main application class
    local MAIN_CLASS_NAME=$(echo "$PROJECT_NAME" | sed 's/-//g' | awk '{print toupper(substr($0,1,1)) substr($0,2)}')
    
    cat > "$PROJECT_NAME/src/main/java/$PACKAGE_PATH/${MAIN_CLASS_NAME}Application.java" << EOF
package $GROUP_ID.$ARTIFACT_ID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ${MAIN_CLASS_NAME}Application {
    
    public static void main(String[] args) {
        SpringApplication.run(${MAIN_CLASS_NAME}Application.class, args);
    }
}
EOF

    print_success "Created main application class"
}

generate_full_structure() {
    local PROJECT_NAME=$1
    local GROUP_ID=$2
    local ARTIFACT_ID=$3
    
    print_step "Downloading full template from Spring Initializr..."
    
    # Use Spring Initializr to generate project
    curl -s https://start.spring.io/starter.zip \
        -d type=maven-project \
        -d language=java \
        -d bootVersion=3.5.10 \
        -d groupId="$GROUP_ID" \
        -d artifactId="$ARTIFACT_ID" \
        -d name="$PROJECT_NAME" \
        -d packageName="$GROUP_ID.$ARTIFACT_ID" \
        -d packaging=jar \
        -d javaVersion=17 \
        -d dependencies=web,thymeleaf \
        -o "$PROJECT_NAME.zip"
    
    if [ $? -eq 0 ]; then
        print_success "Downloaded template"
        
        # Unzip
        unzip -q "$PROJECT_NAME.zip" -d "$PROJECT_NAME"
        rm "$PROJECT_NAME.zip"
        
        print_success "Extracted project files"
        return 0
    else
        print_error "Failed to download template"
        return 1
    fi
}

remove_windows_files() {
    local PROJECT_DIR=$1
    local OS=$(detect_os)
    
    if [ "$OS" != "Windows" ]; then
        print_step "Detected $OS - Removing Windows-specific files..."
        
        REMOVED_COUNT=0
        
        if [ -f "$PROJECT_DIR/mvnw.cmd" ]; then
            rm "$PROJECT_DIR/mvnw.cmd"
            print_success "Removed mvnw.cmd (Windows batch file)"
            ((REMOVED_COUNT++))
        fi
        
        if [ -d "$PROJECT_DIR/.mvn" ]; then
            rm -rf "$PROJECT_DIR/.mvn"
            print_success "Removed .mvn/ directory"
            ((REMOVED_COUNT++))
        fi
        
        if [ $REMOVED_COUNT -eq 0 ]; then
            print_info "No Windows-specific files found"
        else
            print_success "Removed $REMOVED_COUNT Windows-specific item(s)"
        fi
    else
        print_info "Windows detected - keeping all files"
    fi
}

################################################################################
# Cleanup Functions
################################################################################

cleanup_project() {
    print_step "Scanning for cleanup targets..."
    
    # Find cleanup targets
    local TARGETS=()
    
    # Check for target directory
    if [ -d "target" ]; then
        TARGETS+=("target/")
    fi
    
    # Check for .class files
    local CLASS_COUNT=$(find . -name "*.class" 2>/dev/null | wc -l)
    if [ "$CLASS_COUNT" -gt 0 ]; then
        TARGETS+=("*.class files ($CLASS_COUNT found)")
    fi
    
    # Check for log files
    local LOG_COUNT=$(find . -name "*.log" 2>/dev/null | wc -l)
    if [ "$LOG_COUNT" -gt 0 ]; then
        TARGETS+=("*.log files ($LOG_COUNT found)")
    fi
    
    # Check for IDE directories
    if [ -d ".idea" ]; then
        TARGETS+=(".idea/")
    fi
    
    if [ -d ".vscode" ]; then
        TARGETS+=(".vscode/")
    fi
    
    # Check for OS files
    if [ -f ".DS_Store" ]; then
        TARGETS+=(".DS_Store")
    fi
    
    if [ ${#TARGETS[@]} -eq 0 ]; then
        print_success "Project is already clean! No cleanup needed."
        return
    fi
    
    # Display targets
    echo ""
    print_warning "Found the following items to clean:"
    for target in "${TARGETS[@]}"; do
        echo "  • $target"
    done
    echo ""
    
    # Confirm cleanup
    read -p "$(echo -e ${YELLOW}Do you want to remove these items? [y/N]: ${NC})" -n 1 -r
    echo ""
    
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        print_step "Cleaning up..."
        
        # Remove target directory
        if [ -d "target" ]; then
            rm -rf target/
            print_success "Removed target/"
        fi
        
        # Remove .class files
        if [ "$CLASS_COUNT" -gt 0 ]; then
            find . -name "*.class" -type f -delete
            print_success "Removed *.class files"
        fi
        
        # Remove log files
        if [ "$LOG_COUNT" -gt 0 ]; then
            find . -name "*.log" -type f -delete
            print_success "Removed *.log files"
        fi
        
        # Remove IDE directories
        if [ -d ".idea" ]; then
            rm -rf .idea/
            print_success "Removed .idea/"
        fi
        
        if [ -d ".vscode" ]; then
            rm -rf .vscode/
            print_success "Removed .vscode/"
        fi
        
        # Remove OS files
        find . -name ".DS_Store" -type f -delete 2>/dev/null
        
        echo ""
        print_success "Cleanup complete!"
    else
        print_info "Cleanup cancelled"
    fi
}

################################################################################
# Main Menu
################################################################################

show_menu() {
    clear
    print_header
    echo ""
    echo -e "${CYAN}═══════════════════════════════════════════════════════════${NC}"
    echo -e "${CYAN}  Main Menu${NC}"
    echo -e "${CYAN}═══════════════════════════════════════════════════════════${NC}"
    echo ""
    echo "  1) Check Environment (Java, Maven, Docker)"
    echo "  2) Generate New Project (Minimal Template)"
    echo "  3) Generate New Project (Full Template)"
    echo "  4) Generate .gitignore & .dockerignore"
    echo "  5) Cleanup Current Directory"
    echo "  6) Help & Documentation"
    echo "  0) Exit"
    echo ""
    echo -e "${CYAN}═══════════════════════════════════════════════════════════${NC}"
    echo ""
}

generate_project_interactive() {
    local TEMPLATE_TYPE=$1
    
    clear
    print_header
    echo ""
    echo -e "${CYAN}═══════════════════════════════════════════════════════════${NC}"
    echo -e "${CYAN}  Project Configuration${NC}"
    echo -e "${CYAN}═══════════════════════════════════════════════════════════${NC}"
    echo ""
    
    # Get project details
    read -p "Project Name (e.g., my-todo-app): " PROJECT_NAME
    if [ -z "$PROJECT_NAME" ]; then
        print_error "Project name cannot be empty!"
        pause
        return
    fi
    
    read -p "Group ID (e.g., com.mycompany): " GROUP_ID
    if [ -z "$GROUP_ID" ]; then
        GROUP_ID="com.example"
        print_info "Using default: $GROUP_ID"
    fi
    
    read -p "Artifact ID (e.g., myapp): " ARTIFACT_ID
    if [ -z "$ARTIFACT_ID" ]; then
        ARTIFACT_ID=$(echo "$PROJECT_NAME" | tr '-' '_' | tr '[:upper:]' '[:lower:]')
        print_info "Using default: $ARTIFACT_ID"
    fi
    
    echo ""
    print_info "Configuration:"
    echo "  Project Name: $PROJECT_NAME"
    echo "  Group ID: $GROUP_ID"
    echo "  Artifact ID: $ARTIFACT_ID"
    echo ""
    
    read -p "$(echo -e ${YELLOW}Proceed with these settings? [Y/n]: ${NC})" -n 1 -r
    echo ""
    
    if [[ ! $REPLY =~ ^[Yy]$ ]] && [ -n "$REPLY" ]; then
        print_info "Cancelled"
        pause
        return
    fi
    
    echo ""
    
    # Check if directory exists
    if [ -d "$PROJECT_NAME" ]; then
        print_error "Directory '$PROJECT_NAME' already exists!"
        pause
        return
    fi
    
    # Generate project based on template type
    if [ "$TEMPLATE_TYPE" == "minimal" ]; then
        generate_minimal_structure "$PROJECT_NAME" "$GROUP_ID" "$ARTIFACT_ID"
    else
        if ! command_exists curl; then
            print_error "curl is required for full template download"
            pause
            return
        fi
        
        generate_full_structure "$PROJECT_NAME" "$GROUP_ID" "$ARTIFACT_ID"
    fi
    
    # Ask about removing Windows files
    echo ""
    echo -e "${YELLOW}Remove Windows-specific files [recommended for Linux/Mac]? [Y/n]: ${NC}"
    read -n 1 -r
    echo ""
    
    if [[ $REPLY =~ ^[Yy]$ ]] || [ -z "$REPLY" ]; then
        remove_windows_files "$PROJECT_NAME"
    fi
    
    # Ask about Docker files
    echo ""
    read -p "$(echo -e ${YELLOW}Generate Dockerfile? [Y/n]: ${NC})" -n 1 -r
    echo ""
    
    if [[ $REPLY =~ ^[Yy]$ ]] || [ -z "$REPLY" ]; then
        generate_dockerfile "$PROJECT_NAME"
    fi
    
    # Ask about .gitignore
    echo ""
    read -p "$(echo -e ${YELLOW}Generate .gitignore? [Y/n]: ${NC})" -n 1 -r
    echo ""
    
    if [[ $REPLY =~ ^[Yy]$ ]] || [ -z "$REPLY" ]; then
        generate_gitignore "$PROJECT_NAME"
    fi
    
    # Ask about .dockerignore
    echo ""
    read -p "$(echo -e ${YELLOW}Generate .dockerignore? [Y/n]: ${NC})" -n 1 -r
    echo ""
    
    if [[ $REPLY =~ ^[Yy]$ ]] || [ -z "$REPLY" ]; then
        generate_dockerignore "$PROJECT_NAME"
    fi
    
    echo ""
    echo -e "${CYAN}═══════════════════════════════════════════════════════════${NC}"
    print_success "Project '$PROJECT_NAME' created successfully!"
    echo ""
    print_info "Next steps:"
    echo "  1. cd $PROJECT_NAME"
    echo "  2. Review and edit the generated files"
    echo "  3. Run with: mvn spring-boot:run"
    echo "  4. Or build Docker image: docker build -t $PROJECT_NAME ."
    echo ""
    
    pause
}

generate_files_only() {
    clear
    print_header
    echo ""
    echo -e "${CYAN}═══════════════════════════════════════════════════════════${NC}"
    echo -e "${CYAN}  Generate Configuration Files${NC}"
    echo -e "${CYAN}═══════════════════════════════════════════════════════════${NC}"
    echo ""
    
    local CWD=$(pwd)
    print_info "Current directory: $CWD"
    echo ""
    
    read -p "$(echo -e ${YELLOW}Generate files in current directory? [Y/n]: ${NC})" -n 1 -r
    echo ""
    
    if [[ ! $REPLY =~ ^[Yy]$ ]] && [ -n "$REPLY" ]; then
        print_info "Cancelled"
        pause
        return
    fi
    
    echo ""
    
    # Generate .gitignore
    if [ -f ".gitignore" ]; then
        print_warning ".gitignore already exists"
        read -p "$(echo -e ${YELLOW}Overwrite? [y/N]: ${NC})" -n 1 -r
        echo ""
        if [[ $REPLY =~ ^[Yy]$ ]]; then
            generate_gitignore "."
        fi
    else
        generate_gitignore "."
    fi
    
    echo ""
    
    # Generate .dockerignore
    if [ -f ".dockerignore" ]; then
        print_warning ".dockerignore already exists"
        read -p "$(echo -e ${YELLOW}Overwrite? [y/N]: ${NC})" -n 1 -r
        echo ""
        if [[ $REPLY =~ ^[Yy]$ ]]; then
            generate_dockerignore "."
        fi
    else
        generate_dockerignore "."
    fi
    
    echo ""
    print_success "Configuration files generated!"
    pause
}

show_help() {
    clear
    print_header
    echo ""
    echo -e "${CYAN}═══════════════════════════════════════════════════════════${NC}"
    echo -e "${CYAN}  Help & Documentation${NC}"
    echo -e "${CYAN}═══════════════════════════════════════════════════════════${NC}"
    echo ""
    echo "This script helps you set up Java Spring Boot projects quickly."
    echo ""
    echo -e "${YELLOW}Features:${NC}"
    echo "  • Environment checking (Java, Maven, Docker)"
    echo "  • Project template generation (minimal or full)"
    echo "  • Automatic .gitignore and .dockerignore creation"
    echo "  • Project cleanup utilities"
    echo "  • OS detection and Windows file removal"
    echo ""
    echo -e "${YELLOW}Requirements:${NC}"
    echo "  • Java 17+ (required)"
    echo "  • Maven 3.6+ (optional - can use Docker)"
    echo "  • Docker (optional but recommended)"
    echo "  • curl (for full template download)"
    echo ""
    echo -e "${YELLOW}Template Types:${NC}"
    echo "  Minimal: Basic structure, no Maven wrapper, lightweight"
    echo "  Full: Complete Spring Initializr template, all files"
    echo ""
    echo -e "${YELLOW}Cleanup:${NC}"
    echo "  Removes: target/, *.class, *.log, .idea/, .vscode/, .DS_Store"
    echo "  Always asks for confirmation before deleting"
    echo ""
    pause
}

################################################################################
# Main Program Loop
################################################################################

main() {
    while true; do
        show_menu
        read -p "$(echo -e ${CYAN}Enter your choice [0-6]: ${NC})" choice
        
        case $choice in
            1)
                run_environment_check
                ;;
            2)
                generate_project_interactive "minimal"
                ;;
            3)
                generate_project_interactive "full"
                ;;
            4)
                generate_files_only
                ;;
            5)
                clear
                print_header
                echo ""
                cleanup_project
                echo ""
                pause
                ;;
            6)
                show_help
                ;;
            0)
                clear
                print_success "Thanks for using Java Spring Boot Project Setup Tool!"
                echo ""
                exit 0
                ;;
            *)
                print_error "Invalid option. Please try again."
                sleep 2
                ;;
        esac
    done
}

# Run main program
main
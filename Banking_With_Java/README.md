# Banking With Java

A simple banking application built with **pure Java** to demonstrate fundamental programming concepts: **variables, methods, objects, and classes**. Perfect for developers coming from Python or learning Java basics.

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Pure Java](https://img.shields.io/badge/Framework-None-blue)](https://www.java.com/)

---

## Overview

A command-line banking application that demonstrates:
- Creating bank accounts with account numbers, owner names, and balances
- Depositing money with validation
- Withdrawing money with balance checks
- Displaying account information
- Managing multiple accounts

**No frameworks, no databases** - just **pure Java fundamentals**!

---

## Features

- **Account Creation** - Initialize accounts with owner details
- **Deposit Validation** - Only accept positive amounts
- **Withdrawal Validation** - Check sufficient funds before withdrawing
- **Balance Inquiry** - Get current account balance
- **Account Display** - Show formatted account information
- **Multiple Accounts** - Manage multiple accounts simultaneously

---

## Learning Objectives

This project teaches:

1. **Variables** - Different types (String, double, int) and scope
2. **Methods** - Parameters, return types, and void methods
3. **Objects & Classes** - Creating blueprints and instances
4. **Constructors** - Initializing objects with data
5. **Encapsulation** - Using private variables and public methods
6. **Validation Logic** - If-else statements for business rules

---

## Technologies Used

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 17+ | Programming language |
| **JDK** | 17+ | Java Development Kit |

**No external dependencies!** Just Java!

---

## Project Structure

```
Banking_With_Java/
├── PSEUDOCODE_Planning_Account.md        # Planning for Account class
├── PSEUDOCODE_mainapp_BankingApp.md      # Planning for main program
├── .gitignore                            # Git ignore rules
├── README.md                             # This file
└── src/
    └── main/
        └── java/
            └── com/
                └── banking/
                    ├── Account.java          # Account class (variables + methods)
                    └── BankingApp.java       # Main program (creates & tests accounts)
```

---

## Quick Start

### Prerequisites

- **Java 17+** installed
- **Terminal/Command Prompt**

### Check Java Installation

```bash
java -version
# Should show: java version "17.x.x" or higher
```

### Running the Application

```bash
# 1. Clone the repository
git clone https://github.com/sohaib1khan/Java_Bucket.git
cd Banking_With_Java

# 2. Compile the Java files
javac src/main/java/com/banking/*.java

# 3. Run the application
java -cp src/main/java com.banking.BankingApp
```

**That's it!** You'll see the banking system in action.

---

## Code Walkthrough

### Account Class (`Account.java`)

#### Variables (Instance Variables)
```java
private String accountNumber;
private String ownerName;
private double balance;
```

**What they do:** Store account data - each Account object has its own copy.

**Python equivalent:**
```python
class Account:
    def __init__(self):
        self.accountNumber = ""
        self.ownerName = ""
        self.balance = 0.0
```

---

#### Constructor
```java
public Account(String accountNumber, String ownerName, double initialDeposit) {
    this.accountNumber = accountNumber;
    this.ownerName = ownerName;
    this.balance = initialDeposit;
}
```

**What it does:** Creates a new account with initial data.

**Python equivalent:**
```python
def __init__(self, accountNumber, ownerName, initialDeposit):
    self.accountNumber = accountNumber
    self.ownerName = ownerName
    self.balance = initialDeposit
```

**Key difference:** 
- Java: Constructor has **same name as class**, no `__init__`
- Java: Must declare **parameter types**: `String accountNumber`

---

#### Deposit Method
```java
public String deposit(double amount) {
    if (amount > 0) {
        balance = balance + amount;
        return "Deposit successful! New balance: $" + balance;
    } else {
        return "Invalid amount. Must be positive.";
    }
}
```

**What it does:** Adds money to account with validation.

**Python equivalent:**
```python
def deposit(self, amount: float) -> str:
    if amount > 0:
        self.balance += amount
        return f"Deposit successful! New balance: ${self.balance}"
    else:
        return "Invalid amount. Must be positive."
```

**Key differences:**
- Java: Return type **before** method name (`String`)
- Java: Parameter type declared (`double amount`)
- Java: `+` for string concatenation (not f-strings)
- Java: Every statement ends with `;`

---

## Java vs Python - Complete Comparison

| Concept | Python | Java | Purpose |
|---------|--------|------|---------|
| **Variable declaration** | `balance = 1000.0` | `double balance = 1000.0;` | Store data |
| **String variable** | `name = "Alice"` | `String name = "Alice";` | Store text |
| **Class definition** | `class Account:` | `public class Account {` | Define blueprint |
| **Constructor** | `def __init__(self, ...):` | `public Account(...) {` | Initialize object |
| **Method definition** | `def deposit(self, amount):` | `public String deposit(double amount) {` | Define function |
| **Return value** | `return "Success"` | `return "Success";` | Send result back |
| **No return** | `def display(self):` (no return) | `public void display() {` | Print only |
| **Create object** | `acc = Account(...)` | `Account acc = new Account(...);` | Instantiate |
| **Call method** | `acc.deposit(100)` | `acc.deposit(100.0);` | Execute function |
| **If statement** | `if amount > 0:` | `if (amount > 0) {` | Conditional |
| **Else if** | `elif amount < 0:` | `else if (amount < 0) {` | Alternative condition |
| **Print** | `print("Hello")` | `System.out.println("Hello");` | Output text |
| **String concat** | `f"Balance: {balance}"` | `"Balance: " + balance` | Combine strings |
| **Comments** | `# This is a comment` | `// This is a comment` | Documentation |

---

## Key Java Concepts Explained

### 1. Static Typing

**Java:**
```java
String name = "Alice";    // Type declared: String
double balance = 1000.0;  // Type declared: double
int count = 5;            // Type declared: int
```

**Python:**
```python
name = "Alice"       # Type inferred automatically
balance = 1000.0     # Type inferred automatically
count = 5            # Type inferred automatically
```

**What it means:** Java requires you to declare types explicitly. Python figures it out.

---

### 2. Method Signatures

**Java method signature:**
```
public String deposit(double amount)
   │      │      │       │      │
   │      │      │       │      └─ Parameter name
   │      │      │       └──────── Parameter type
   │      │      └──────────────── Method name
   │      └─────────────────────── Return type
   └────────────────────────────── Access modifier
```

**Python equivalent:**
```python
def deposit(self, amount: float) -> str:
    │       │     │       │         │
    │       │     │       │         └─ Return type (optional)
    │       │     │       └─────────── Parameter type hint (optional)
    │       │     └─────────────────── Parameter name
    │       └───────────────────────── Self reference
    └───────────────────────────────── Function keyword
```

---

## Example Output

```
===================================
Welcome to Java Banking System!
===================================

Created Account 1:
====================
Account Number: ACC001
Owner: Alice
Balance: $1000.0
====================

Testing Deposit: $500
Deposit successful! New balance: $1500.0

Testing Withdrawal: $300
Withdrawal successful! New balance: $1200.0

Testing Invalid Withdrawal: $2000
Insufficient funds. Current balance: $1200.0

Current Balance using getBalance(): $1200.0

Created Account 2
====================
Account Number: ACC002
Owner: Bob
Balance: $500.0
====================

===================================
Final Account Summary: 
===================================
====================
Account Number: ACC001
Owner: Alice
Balance: $1200.0
====================
====================
Account Number: ACC002
Owner: Bob
Balance: $500.0
====================
```

---

## Common Pitfalls (Python Devs Learning Java)

### 1. Forgetting Semicolons
```java
// Wrong
System.out.println("Hello")

// Correct
System.out.println("Hello");
```

### 2. Forgetting Braces
```java
// Wrong (compiles but dangerous)
if (amount > 0)
    balance += amount;
    System.out.println("Done");  // Always runs!

// Correct
if (amount > 0) {
    balance += amount;
    System.out.println("Done");
}
```

### 3. Using Python String Formatting
```java
// Wrong
System.out.println(f"Balance: {balance}");  // f-strings don't exist

// Correct
System.out.println("Balance: " + balance);
```
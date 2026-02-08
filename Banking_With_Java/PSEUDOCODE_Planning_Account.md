# Banking App - Pseudocode Planning

## Account Class

### Variables
- accountNumber (String) - Unique identifier
- ownerName (String) - Account holder's name
- balance (double) - Current balance

### Methods

#### Constructor
```
FUNCTION Account(accountNumber, ownerName, initialDeposit):
    SET this.accountNumber = accountNumber
    SET this.ownerName = ownerName
    SET this.balance = initialDeposit
```

#### Deposit
```
FUNCTION deposit(amount):
    IF amount > 0:
        balance = balance + amount
        RETURN "Deposit successful"
    ELSE:
        RETURN "Invalid amount"
```

#### Withdraw
```
FUNCTION withdraw(amount):
    IF amount <= 0:
        RETURN "Invalid amount"
    ELSE IF amount > balance:
        RETURN "Insufficient funds"
    ELSE:
        balance = balance - amount
        RETURN "Withdrawal successful"
```

#### Get Balance
```
FUNCTION getBalance():
    RETURN balance
```

#### Display Info
```
FUNCTION displayInfo():
    PRINT account details
    NO RETURN
```
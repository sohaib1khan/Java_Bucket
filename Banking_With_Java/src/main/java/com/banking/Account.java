package com.banking;

public class Account {

    
    // VARIABLES (Instance Variables - belong to each account object)
    // Like Python: self.accountNumber, self.ownerName, self.balance
    private String accountNumber;
    private String ownerName;
    private double balance;

    // CONSTRUCTOR (Special method to create new accounts)
    // Like Python: def __init__(self, accountNumber, ownerName, initialDeposit)
    public Account(String accountNumber, String ownerName, double initialDeposit){

        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = initialDeposit;
    }



    // METHOD: Deposit money
    // Parameters: amount (double)
    // Returns: String (message)
    public String deposit(double amount) {
        if (amount > 0) {
            balance = balance + amount;  // or: balance += amount;
            return "Deposit successful! New balance: $" + balance;
        } else {
            return "Invalid amount. Must be positive.";
        }
    }



    // METHOD: Withdraw money
    // Parameters: amount (double)
    // Returns: String (message)
    public String withdraw(double amount) {
        if (amount <= 0) {
            return "Invalid amount. Must be positive.";
        } else if (amount > balance) {
            return "Insufficient funds. Current balance: $" + balance;
        } else {
            balance = balance - amount; // or balance -= amount 
            return "Withdrawal successful! New balance: $" + balance;
        }
    }



    // METHOD: Get balance
    // No parameters
    // Returns: double (the balance)
        public double getBalance() {
        return balance;
    }


    // METHOD: Display account info
    // No parameters
    // No return (void)
    public void displayInfo() {
        System.out.println("====================");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Owner: " + ownerName);
        System.out.println("Balance: $" + balance);
        System.out.println("====================");
    }
}
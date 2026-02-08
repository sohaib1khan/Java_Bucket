package com.banking;

public class BankingApp {
        
    // MAIN METHOD - Entry point (like if __name__ == "__main__" in Python)
    public static void main(String[] args) {

        // Print Welcome message.
        System.out.println("===================================");
        System.out.println("Welcome to Java Banking System!");
        System.out.println("===================================");
        System.out.println();


        // CREATE FIRST ACCOUNT (like: account1 = Account("ACC001", "Alice", 1000))
        // Variable type: Account, Variable name: account1
        Account account1 = new Account("ACC001", "Alice", 1000.0 );

        System.out.println("Created Account 1:");
        account1.displayInfo();
        System.out.println();

        
        // TEST DEPOSIT - Call the deposit method
        System.out.println("Testing Deposit: $500");
        String depositResult = account1.deposit(500.0);
        System.out.println(depositResult);
        System.out.println();


        // TEST WITHDRAWAL - Call the withdraw method
        System.out.println("Testing Withdrawal: $300");
        String withdrawResult = account1.withdraw(300.00);
        System.out.println(withdrawResult);
        System.out.println();


        // TEST INVALID WITHDRAWAL - Try to withdraw more than balance
        System.out.println("Testing Invalid Withdrawal: $2000");
        String invalidResult = account1.withdraw( 2000.0);
        System.out.println(invalidResult);
        System.out.println();

    
        // GET BALANCE - Call the getBalance method
        double currentBalance = account1.getBalance();
        System.out.println("Current Balance using getBalance(): $" + currentBalance);
        System.out.println();


        // CREATE SECOND ACCOUNT
        Account account2 = new Account("ACC002", "Bob", 500.00);

        System.out.println("Created Account 2");
        account2.displayInfo();
        System.out.println();


        // FINAL SUMMARY!!!!!!!!!!!!!
        System.out.println("===================================");
        System.out.println("Final Account Summary: ");
        System.out.println("===================================");
        account1.displayInfo();
        account2.displayInfo();

    }
    
}
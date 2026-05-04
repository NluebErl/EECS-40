package mybank;

import java.util.HashMap;
import java.util.Scanner;
import java.lang.Math;

/**
 * BankSystem class holds all bank functions and main function for all menu options.
 * Has functionality for creating an account, removing an account, displaying a single account, displaying all accounts, depositing and withdrawing money, and calculating interest.
 * Uses a HashMap to link names to a desired bank account.
 */
public class BankSystem
{
    // Initialize HashMap for the bank accounts
    public HashMap<String, BankAccount> bankAccounts = new HashMap<>();

    /**
     * Main function of the program. Runs a method called run() to run a main loop.
     * @param args Commandline input.
     */
    public static void main(String[] args)
    {
        BankSystem bankSystem = new BankSystem();
        bankSystem.run();
    }

    /**
     * Runs the main loop of the program. Utilizes a scanner to check and handle exceptions when a user inputs an incorrect input.
     * Breaks a main while loop when the user selects an exit option.
     */
    public void run()
    {
        Scanner scan = new Scanner(System.in);

        // Main function loop
        while (true)
        {
            // Display the menu
            printMenu();

            // Check if the user inputs an integer
            int choice;
            try {
                choice = Integer.parseInt(scan.nextLine());
            }
            catch (Exception e) {
                System.out.println("Wrong choice");
                continue;
            }

            // Switch case that handles all of the different menu options of the Bank System
            switch (choice)
            {
                case 1:
                    createAccount(scan);
                    break;
                case 2:
                    display(scan);
                    break;
                case 3:
                    withdraw(scan);
                    break;
                case 4:
                    deposit(scan);
                    break;
                case 5:
                    displayAll();
                    break;
                case 6:
                    removeAccount(scan);
                    break;
                case 7:
                    calculateInterest(scan);
                    break;
                case 8:
                    System.out.println("Thank you for banking with us!!");
                    return;
                // When none of the options are entered, consider this an incorrect choice.
                default:
                    System.out.println("Wrong choice");
            }
        }
    }

    /**
     * Prints out the menu UI. Prompts users for an input which is parsed in another method.
     */
    public void printMenu()
    {
        System.out.println();
        System.out.println("***  Menu  ***");
        System.out.println("1. Create Account");
        System.out.println("2. Display");
        System.out.println("3. Withdraw");
        System.out.println("4. Deposit");
        System.out.println("5. Display All");
        System.out.println("6. Remove Account");
        System.out.println("7. Calculate Interest");
        System.out.println("8. Exit\n");
        System.out.print("Enter your choice: ");
    }

    /**
     * Creates an account and is placed in the bankAccounts HashMap. Checks for incorrect inputs and passcodes.
     * @param scan Scanner object to read user inputs.
     */
    public void createAccount(Scanner scan)
    {
        System.out.println();
        System.out.println("**Create New Account**");
        System.out.println("1. Create Standard Account");
        System.out.println("2. Create VIP Account");
        System.out.print("Enter your choice: ");

        // Checks if the user inputs an integer
        int accType;
        try {
            accType = Integer.parseInt(scan.nextLine());
        } catch (Exception e) {
            System.out.println("Wrong choice");
            return;
        }

        // Parses for the user's name and passcode for the account
        System.out.print("Enter name: ");
        String name = scan.nextLine();
        System.out.print("Enter passcode: ");
        String password = scan.nextLine();

        // Uses regex to check if the user inputs a passcode with 4 integers only
        if (password.length() != 4 || !password.matches("\\d{4}"))
        {
            System.out.println("Invalid passcode");
            return;
        }

        // Parses user's starting balance input
        int passcode =  Integer.parseInt(password);
        System.out.print("Starting balance: ");

        // Checks if the input by the user is a valid number (or a number at all)
        double balance;
        try{
            balance = Double.parseDouble(scan.nextLine());
        } catch (Exception e){
            System.out.println("Invalid balance");
            return;
        }

        // Checks if the user input a correct account type number, then creates an account by inputting the object into the HashMap Bank System
        if (accType == 1)
        {
            bankAccounts.put(name, new StandardAccount(name, passcode, balance));
        }
        else if (accType == 2)
        {
            bankAccounts.put(name, new VIPAccount(name, passcode, balance));
        }
        else
        {
            System.out.println("Wrong choice");
            return;
        }

        System.out.println("Account Created!!");
    }

    /**
     * Displays the name, balance, and account type details for a single person.
     * Name is provided by the user.
     * @param scan Scanner object used to read user inputs.
     */
    public void display(Scanner scan)
    {
        // Checks if the name the user is trying to access is in the system (HashMap)
        System.out.print("Enter your name: ");
        String name = scan.nextLine();

        if (!bankAccounts.containsKey(name))
        {
            System.out.println("Name: " + name + " does not exist");
            return;
        }

        // Create a new BankAccount to access the account information using the name key. Then displays information
        BankAccount acc = bankAccounts.get(name);
        System.out.println("**Account Details**");
        System.out.println("Name: " + acc.getName());
        System.out.println("Account Type: " + (acc instanceof StandardAccount ? "Standard" : "VIP"));
        System.out.println("Balance: " + acc.getBalance());
    }

    /**
     * Withdraws a set amount of money input by the user from their account. Requires a passcode check.
     * Does not withdraw money if the user does not have enough money.
     * Subtracts the amount of money withdrawn if they can withdraw.
     * @param scan Scanner object used for reading user inputs.
     */
    public void withdraw(Scanner scan)
    {
        // Checks if the account under the name the user inputs is in the Bank System
        System.out.println();
        System.out.println("**Transaction - Withdraw**");
        System.out.print("Enter your name: ");
        String name = scan.nextLine();

        if (!bankAccounts.containsKey(name))
        {
            System.out.println("Name: " + name + " does not exist\n");
            return;
        }

        // Checks the bank account object in the HashMap and checks if the input passcode by the user matches the account passcode when the account was first created.
        BankAccount acc = bankAccounts.get(name);
        System.out.print("Enter passcode: ");
        int passcode = Integer.parseInt(scan.nextLine());
        if (passcode != acc.getPasscode())
        {
            System.out.println("Wrong passcode");
            return;
        }

        // Withdraws an amount of money from the user's balance if the user has enough money; error if not.
        System.out.print("Enter amount to withdraw: ");
        double withdraw = Double.parseDouble(scan.nextLine());
        if (!acc.withdraw(withdraw))
        {
            System.out.println("Not enough balance");
            return;
        }

        System.out.println("Name: " + acc.getName());
        System.out.println("Balance: " + acc.getBalance());
    }

    /**
     * Deposit allows the user to put money into their account without taking their passcode.
     * @param scan Scanner object used to read user inputs.
     */
    public void deposit(Scanner scan)
    {
        // Checks if the name of the user's input is in the bank system
        System.out.println();
        System.out.println("**Transaction - Deposit**");
        System.out.print("Enter your name: ");
        String name = scan.nextLine();
        if (!bankAccounts.containsKey(name))
        {
            System.out.println("Name: " + name + " does not exist\n");
            return;
        }

        // Access the account based on the name key and change the balance amount based on the deposit user input.
        BankAccount acc = bankAccounts.get(name);
        System.out.print("Enter the amount to deposit: ");
        double deposit = Double.parseDouble(scan.nextLine());
        acc.deposit(deposit);
        System.out.println("Name: " + acc.getName());
        System.out.println("Balance: " + acc.getBalance());
    }

    /**
     * Displays all accounts that are in the Bank System HashMap.
     * Categorized in both Standard and VIP Accounts.
     * Does not display anything for a category if no accounts of that type were made.
     * Prints them out in the terminal.
     */
    public void displayAll()
    {
        // Utilize a header variable to determine to print out a Standard Account or VIP Account header.
        // Doesn't display if it was already displayed once
        System.out.println();
        int header = 0;
        for (BankAccount acc : bankAccounts.values())
        {
            // Check if the account is a StandardAccount, otherwise move on
            if (acc instanceof StandardAccount)
            {
                // Display the header for Standard Accounts for the first time
                if (header == 0)
                {
                    System.out.println("Standard Account Details");
                    header++;
                }
                System.out.println("**Account Details**");
                System.out.println("Name: " + acc.getName());
                System.out.println("Account Type: Standard");
                System.out.println("Balance: " + acc.getBalance());
            }
        }

        // Change header back to 0 for VIP Accounts
        System.out.println();
        header = 0;
        for (BankAccount acc : bankAccounts.values())
        {
            // Check if the account is a VIPAccount, otherwise move on
            if (acc instanceof VIPAccount)
            {
                // Display the header for VIP Accounts for the first time
                if (header == 0)
                {
                    System.out.println("VIP Account Details");
                    header++;
                }
                System.out.println("**Account Details**");
                System.out.println("Name: " + acc.getName());
                System.out.println("Account Type: VIP");
                System.out.println("Balance: " + acc.getBalance());
            }
        }
    }

    /**
     * Removes an account from the BankSystem HashMap. Requires the name and passcode to remove.
     * @param scan Scanner object which reads user inputs.
     */
    public void removeAccount(Scanner scan)
    {
        // Checks if the name of the account that the user is trying to remove exists in the system
        System.out.println();
        System.out.println("**Transaction - Remove Account**");
        System.out.print("Enter your name: ");
        String name = scan.nextLine();
        if (!bankAccounts.containsKey(name))
        {
            System.out.println("Name: " + name + " does not exist\n");
            return;
        }

        // Compare passcodes with the account passcode from the BankSystem
        System.out.print("Enter passcode: ");
        int passcode = Integer.parseInt(scan.nextLine());
        BankAccount acc = bankAccounts.get(name);
        if (passcode != acc.getPasscode())
        {
            System.out.println("Wrong passcode");
            return;
        }
        // If passcodes match, we know it is the user trying to remove their account
        bankAccounts.remove(name);
        System.out.println("Account has been removed!!");
    }

    /**
     * Calculates the interest based on an amount of months the user inputs.
     * Calculated differently for VIP and Standard accounts, with VIP accounts having compound interest and Standard accounts having simple interest.
     * @param scan Scanner object used to read user inputs.
     */
    public void calculateInterest(Scanner scan)
    {
        // Checks if the account is in the system by checking the key (name)
        System.out.println();
        System.out.println("**Transaction - Calculate Interest**");
        System.out.print("Enter your name: ");
        String name = scan.nextLine();
        if (!bankAccounts.containsKey(name))
        {
            System.out.println("Name: " + name + " does not exist\n");
            return;
        }

        // Create a new account to get values of the user's account in the Bank System
        BankAccount acc = bankAccounts.get(name);

        // Ask for number of months the user wants to find interest for
        System.out.print("Enter the number of months: ");
        int months = Integer.parseInt(scan.nextLine());

        double rate = acc.getRate();
        double balance = acc.getBalance();

        // Calculates and rounds interest based on the account type. Either simple or compound interest
        double interest;
        if (acc instanceof StandardAccount)
        {
            interest = balance * rate * months;
        }
        else
        {
            interest = balance * (Math.pow((1 + rate), months) - 1);
        }
        interest = (double) Math.round(interest * 100.0) / 100;
        System.out.println("The expected interest is: " + interest);
    }
}

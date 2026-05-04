package mybank;

/**
 * BankAccount Interface: Define all abstract functions and variables used by StandardAccount and VIPAccount
 */
public interface BankAccount {
    /**
     * deposit method gets an amount input from the user to deposit into the bank account balance.
     * Does not require a passcode to run.
     * @param amount The amount of money that the user wishes to deposit into their balance on their account.
     */
    public void deposit(double amount);

    /**
     * getName method gets the name of the account and returns it.
     * @return The name under the account.
     */
    public String getName();

    /**
     * getPasscode returns the passcode for the account.
     * Passcode is provided when an account is first initialized.
     * @return The passcode (4 digit integer) for the account.
     */
    public int getPasscode();

    /**
     * getRate returns the interest rate of the account.
     * Different for both VIP and Standard accounts.
     * @return The constant insterest rate for an account based on account type.
     */
    public double getRate();

    /**
     * withdraw method subtracts the amount of money the user wants to take out of their account (subtracted from balance).
     * Returns either true or false if the user has enough money in the account or not.
     * Requires a passcode to run this method.
     * @param amount The amount of money that the user wishes to subtract from their balance.
     * @return Either true or false if the user has enough money to take out from their balance.
     */
    public boolean withdraw(double amount);

    /**
     * getBalance returns the balance of the account.
     * @return The amount of money (balance) that the account has.
     */
    public double getBalance();
}

/**
 * StandardAccount class: Implements BankAccount interface.
 * Defines variables for name, passcode, balance, and interest rate for calculation and methods.
 * Includes Constructor for StandardAccount to initialize a standard account.
 * Includes helper methods to get specific parameters from the object.
 */
class StandardAccount implements BankAccount{
    private String name;
    private int passcode;
    private double balance;
    final double RATE = 0.003;

    /**
     * StandardAccount constructor; takes a name, passcode, and balance
     * @param name String name which is the username of the user's account. Used as the key for the bankAccounts hashmap.
     * @param passcode Password for the account. Used for several functions to access some methods.
     * @param balance The amount of money inside of the bank account. Used for several functions for displaying, withdrawing, depsoiting, and interest calculation.
     */
    public StandardAccount(String name, int passcode, double balance)
    {
        this.name = name;
        this.passcode = passcode;
        this.balance = balance;
    }

    /**
     * getName method finds the name of which the account is under
     * @return The name of the owner of the account (or name under the account). Cannot be altered.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * getPasscode method gets the account passcode and returns it
     * @return The passcode stored in the passcode parameter of StandardAccount. Cannot be altered.
     */
    public int getPasscode()
    {
        return this.passcode;
    }

    /**
     * getBalance method gets the account balance (how much money the account has)
     * @return The value stored in the account balance. Can be altered.
     */
    public double getBalance()
    {
        return this.balance;
    }

    /**
     * getRate gets the constant interest rate of a StandardAccount.
     * @return The constant value of RATE for a StandardAccount.
     */
    public double getRate()
    {
        return this.RATE;
    }

    /**
     * deposit adds a desired amount provided by the user to their original balance.
     * @param amount The amount of money that the user wishes to add to their balance.
     */
    @Override
    public void deposit(double amount)
    {
        this.balance += amount;
    }

    /**
     * withdraw takes out a desired amount of money that the user chooses. Doesn't allow a withdraw if the user does not have enough money.
     * @param amount The amount of money that the user wants to take out of their account.
     * @return True or False if the user can or cannot take out the amount of money in their account (if they have enough funds).
     */
    @Override
    public boolean withdraw(double amount)
    {
        if (amount > balance)
        {
            return false;
        }
        balance -= amount;
        return true;
    }
}

/**
 * VIPAccount class: Implements BankAccount interface.
 * Defines variables for name, passcode, balance, and interest rate for calculation and methods.
 * Includes Constructor for VIPAccount to initialize the parameters for a VIP account.
 * Includes helper methods to get specific parameters from the object.
 * Same as a StandardAccount but has a higher interest rate, and interest is calculated compound.
 */
class VIPAccount implements BankAccount
{
    private String name;
    private int passcode;
    private double balance;
    final double RATE = 0.008;

    /**
     * VIPAccount constructor that initializes the string name, passcode, and balance of the account.
     * @param name The name of the user. Name under the account.
     * @param passcode The passcode to access specific methods that may alter parameters of the account. Must be 4 integers long.
     * @param balance The balance that the user wishes to initialize on their account.
     */
    public VIPAccount(String name, int passcode, double balance)
    {
        this.name = name;
        this.passcode = passcode;
        this.balance = balance;
    }

    /**
     * getName method finds the name of which the account is under
     * @return The name of the owner of the account (or name under the account). Cannot be altered.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * getPasscode method gets the account passcode and returns it
     * @return The passcode stored in the passcode parameter of VIPAccount. Cannot be altered.
     */
    public int getPasscode()
    {
        return this.passcode;
    }

    /**
     * getBalance method gets the account balance (how much money the account has)
     * @return The value stored in the account balance. Can be altered.
     */
    public double getBalance()
    {
        return this.balance;
    }

    /**
     * getRate gets the constant interest rate of a VIPAccount.
     * @return The constant value of RATE for a VIPAccount.
     */
    public double getRate()
    {
        return this.RATE;
    }

    /**
     * deposit adds a desired amount provided by the user to their original balance.
     * @param amount The amount of money that the user wishes to add to their balance.
     */
    @Override
    public void deposit(double amount)
    {
        this.balance += amount;
    }

    /**
     * withdraw takes out a desired amount of money that the user chooses. Doesn't allow a withdraw if the user does not have enough money.
     * @param amount The amount of money that the user wants to take out of their account.
     * @return True or False if the user can or cannot take out the amount of money in their account (if they have enough funds).
     */
    @Override
    public boolean withdraw(double amount)
    {
        if (amount > balance)
        {
            return false;
        }
        balance -= amount;
        return true;
    }
}

package Bookly;
/**
 * Book public class. Holds all methods relating to the Book class and stores variables for each book.
 * Variables stored include the title of the book, the borrowed status of the book, the name of the person who is borrowing the book (is nothing if not borrowed), and the price of the book.
 */
public class Book {

    String title;        // The title of the book
    boolean borrowed;    // Tracks whether the book is currently borrowed
    String borrowerName; // Name of the person who borrowed the book
    double price;        // Price of the book

    /**
     * Book constructor. Created with a bookTitle and a price for the book. Updates variables for the book title based on the input and the price of the book based on the input.
     * Sets borrowed status to false by default and correspondingly sets the borrowerName to nothing since the book hasn't been borrowed yet.
     * @param bookTitle The title of the book that is being created.
     * @param bookPrice The cost of the book in market price.
     */
    public Book(String bookTitle, double bookPrice) {
        // TODO: Implement the method
        this.title = bookTitle;
        this.price = bookPrice;
        this.borrowed = false;
        this.borrowerName = null;
    }

    /**
     * Changes the borrowing status of the book to true. Updates who borrowed the book based on the string input.
     * @param userName Name of the user who is borrowing the book. Updates the book's borrowerName variable.
     */
    // Marks the book as borrowed by a specific user
    public void borrowed(String userName) {
        // TODO: Implement the method
        this.borrowed = true;
        this.borrowerName = userName;
    }

    /**
     * Returns the book. Returns the book's borrowed and borrowerName status back to default, being false and nothing respectively.
     */
    // Marks the book as returned and clears borrower information
    public void returned() {
        // TODO: Implement the method
        this.borrowed = false;
        this.borrowerName = null;
    }

    /**
     * Returns the borrowing status of the book. This is based on the book's borrowed variable.
     * @return this.borrowed which is either true or false depending on if the book has been borrowed.
     */
    // Checks if the book is currently borrowed
    public boolean isBorrowed() {
        // TODO: Implement the method
        return this.borrowed;
    }

    /**
     * Gets the name of the borrower. Taken directly from the book's parameter.
     * @return borrowerName parameter, which can be nothing or something depending on if the book is borrowed or not.
     */
    // Gets the name of the current borrower
    public String getBorrowerName() {
        // TODO: Implement the method
        return this.borrowerName;
    }

    /**
     * Returns the title of the book. Used for comparison by other classes to check if a book is available or in a list.
     * @return The title of the book from the book's parameters.
     */
    // Gets the title of the book
    public String getTitle() {
        // TODO: Implement the method
        return title;
    }

    /**
     * Returns the original price of the book. This takes the book's price parameter.
     * @return The book's original price.
     */
    // Gets the price of the book
    public double getPrice() {
        // TODO: Implement the method
        return price;
    }

    /**
     * Calculates the price that a user has to pay depending on how many days that the user borrowed the book.
     * The price increases by 50 cents each day over 30 days that the book is borrowed. This is stored in a variable.
     * @param days The number of days that the user has borrowed the book in total.
     * @return payment, which is the amount of money the user owes to the library based on how many days that they borrowed the book.
     */
    // Calculates payment for a rental based on duration
    public double calculatePayment(int days) {
        // TODO: Implement the method
        double payment = 0.0;
        if (days <= 30)
        {
            return payment;
        }
        else
        {
            payment = (days - 30) * 0.5;
            if (payment > price)
            {
                return price;
            }
            return payment;
        }
    }

    /**
     * Main function. Holds test cases and defines variables for these test cases. Used to check if the functionality of book works as intended.
     * @param arguments The input arguments from the command line.
     */
    public static void main(String[] arguments) {
        // Small test of the Book class
        Book example = new Book("The Da Vinci Code", 10);

        // Test basic properties
        System.out.println("Title (should be The Da Vinci Code): " + example.getTitle());
        System.out.println("Price (should be 10.0): " + example.getPrice());
        System.out.println("Borrowed? (should be false): " + example.isBorrowed());

        // Test borrowing functionality
        example.borrowed("user1");
        System.out.println("Borrowed? (should be true): " + example.isBorrowed());
        System.out.println("Borrower Name (should be user1): " + example.getBorrowerName());

        // Test payment calculation
        System.out.println("Estimated Payment for 30 days: (should be 0.0): " + example.calculatePayment(30));
        System.out.println("Estimated Payment for 40 days: (should be 5.0): " + example.calculatePayment(40));
        System.out.println("Estimated Payment for 60 days: (should be 10.0): " + example.calculatePayment(60));

        // Test return functionality
        example.returned();
        System.out.println("Borrowed? (should be false): " + example.isBorrowed());
    }
}
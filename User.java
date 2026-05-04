package Bookly;

import java.util.ArrayList;

/**
 * User class holds all the methods and variables that need to be stored for each user at the library.
 * Holds information such as the name, id, list of borrowed books.
 * Holds a constant for the maximum number of books that a user is allowed to borrow at once, which is 3.
 */
public class User {
    String name;      // User's full name
    String id;        // Unique identifier for the user
    ArrayList<Book> borrowedBooks;  // List of books currently borrowed by the user
    public static final int MAX_BORROW = 3; // Maximum number of books a user can borrow at once

    /**
     * User constructor. Each user is provided an id and a name which is updated here. Sets the user id as the id input and the name as the name input as default.
     * Creates a new, empty ArrayList for each user for the list of books that they have borrowed. This list keeps track of the books that they are currently borrowing.
     * @param id The identification number of the user for this account.
     * @param name The name of the user trying to make an account.
     */
    public User(String id, String name) {
        // TODO: Implement the method
        this.id = id;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    /**
     * Returns the name of the user. Does this by accessing the name of the user that was created in the constructor.
     * @return The name of the user.
     */
    // Gets the user's name.
    public String getName() {
        // TODO: Implement the method
        return name;
    }

    /**
     * Returns the ID of the user that was provided in the constructor as a parameter.
     * @return The user's ID.
     */
    // Gets the user's ID.
    public String getId() {
        // TODO: Implement the method
        return id;
    }

    /**
     * Adds a book to the user's borrowed book list if the book can be borrowed. Checks if the user has less than the maximum number of books that they can borrow and if they do, then they can borrow the book.
     * This gets updated by adding the book to the user's borrowed book list.
     * @param book The book that the user wants to borrow.
     * @return Either true or false if the user can or cannot borrow the book.
     */
    // Attempts to add a book to the user's borrowed books list.
    public boolean addBorrowedBook(Book book) {
        // TODO: Implement the method
        if (borrowedBooks.size() >= MAX_BORROW)
        {
            return false;
        }
        borrowedBooks.add(book);
        return true;
    }

    /**
     * Removes a book from the user's borrowedBooks ArrayList
     * @param book The book that will be removed from the user's borrowedBook list.
     */
    // Removes a book from the user's borrowed books list when it's returned.
    public void removeBorrowedBook(Book book) {
        // TODO: Implement the method
        borrowedBooks.remove(book);
    }

    /**
     * Returns the size of the ArrayList for the books borrowed by the user. Does this by using the .siz() method.
     * @return The size of the user's borrowedBooks ArrayList.
     */
    // Gets the count of books currently borrowed by the user.
    public int getBorrowedCount() {
        // TODO: Implement the method
        return borrowedBooks.size();
    }

    /**
     * Checks if the book has been borrowed by a user or not. Done by checking if the book matches anything in the user's borrwedBooks list.
     * @param book The book that will be used to check if the user has borrowed it or not.
     * @return Either ture or false depending on if the book input was found in the user's borrowedBooks.
     */
    // Checks if the user has borrowed a specific book.
    public boolean hasBorrowed(Book book) {
        // TODO: Implement the method
        if (this.borrowedBooks.contains(book))
        {
            return true;
        }
        return false;
    }

    /**
     * Main function of user. Can be used to test any test cases.
     * @param arguments The argument inputs from the command line.
     */
    public static void main(String[] arguments) {
        // TODO: Implement the method
    }
}

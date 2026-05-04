package Bookly;

import java.util.ArrayList;

/**
 * Public library class: Holds all the methods used that are called for the library. Has values for address (library address), bookCollection (array of books in the library's collection), and a list of users (users in an array that have borrowed from the library).
 */
public class Library {
    // Add the missing implementation to this class
    String address;
    ArrayList<Book> bookCollection;
    ArrayList<User> users;

    /**
     * Constructor library class. When a new library is created, the address is stored as the input libAddress.
     * We default the bookCollection to be empty until new books are added and same with users as well.
     * @param libAddress Holds the address of which the library is located. This is set when the constructor is called.
     */
    public Library(String libAddress) {
        // TODO: Implement the method
        this.address = libAddress;
        this.bookCollection = new ArrayList<>();
        this.users = new ArrayList<>();
	}

    /**
     * Adds a new book to the library bookCollection. This is done by doing bookCollection.add(newBook), which is the parameter here.
     * @param newBook Name of the new book being added to the library. Gets appended to the ArrayList for books.
     */
    public void addBook(Book newBook){
        // TODO: Implement the method
        this.bookCollection.add(newBook);
    }

    /**
     * Registers a user to the library. This is added to the users ArrayList.
     * @param user Name of the user being added to the library system. It is appended to the ArrayList for users.
     */
    public void registerUser(User user) {
        // TODO: Implement the method
        this.users.add(user);
    }

    /**
     * Prints out the library opening hours. This is set from 9am to 5pm daily by default.
     */
    public static void printOpeningHours(){
        // TODO: Implement the method
        System.out.println("Libraries are open daily from 9am to 5pm.");
    }

    /**
     * Prints out the address of the library. This information is grabbed from the address inputted when the library constructor is called.
     */
    public void printAddress(){
        // TODO: Implement the method
        System.out.println(address);
    }


    // Borrow a book on behalf of a user. Each borrow operation lets the user take one book.
    // Enforces that a user may have at most User.MAX_BORROW concurrent borrows.

    /**
     * Allows a user to borrow a book and keeps track of who borrowed the book through adding the book the user borrowed into the array of BorrowedBooks from the User class.
     * Denies access to the book if the book is either borrowed already or the user has already borrowed 3 books (since the maximum number of books that a user can borrow at once is 3).
     * @param title The title of the book that the user wants to borrow. This is provided as an input.
     * @param user The name of the user that is trying to borrow the book.
     */
    public void borrowBook(String title, User user) {
        // TODO: Implement the method
        // Iterate through the book collection to find the book that matches the title that the user wants to borrow.
        for (Book book : bookCollection)
        {
            if (book.getTitle().equals(title))
            {
                // Checks if the book is already borrowed. Cannot borrow if a book is already borrowed.
                if (book.isBorrowed())
                {
                    System.out.println("Sorry, this book is already borrowed.");
                    return;
                }
                // Check if the user is already borrowing the maximum number of books. If so, they cannot borrow anymore.
                if (user.getBorrowedCount() >= User.MAX_BORROW)
                {
                    System.out.println("User " + user.getName() + " has already borrowed the maximum number of books (3).");
                    return;
                }
                // Let the user borrow the book if previous conditions aren't met.
                book.borrowed(user.getName());
                // Add the book to the array of borrowed books.
                user.addBorrowedBook(book);
                System.out.println("User " + user.getName() + " successfully borrowed " + book.getTitle());
                return;
            }
        }
    }

    /**
     * Prints out a list of all of the available books in the library.
     * Checks if the book has been borrowed or not already.
     */
    public void printAvailableBooks(){
        // TODO: Implement the method
        //System.out.println("Books available in the library:");
        for (Book book : bookCollection)
        {
            // If the book is already borrowed, it is not currently available. Skip it.
            if (book.isBorrowed())
                continue;
            System.out.println(book.getTitle());
        }
	}

    // Return a book on behalf of a user, and remove it from the borrower's list.

    /**
     * Allows a user to return a book that they have borrowed. Checks if the book title they are trying to return is part of the collection and checks if the book is actually borrowed by the user or not.
     * if the book is borrowed by the user, return the book using the book.returned() method which clears out its borrowing history and removes the book from the user's borrowed book list.
     * @param title The title of the book that the user wants to return.
     * @param user The name of the user that wishes to return the book.
     */
    public void returnBook(String title, User user){
        // TODO: Implement the method
        // Searches through the library book collection to see if the book the user is trying to return exists.
        for (Book book : bookCollection)
        {
            if (book.getTitle().equals(title))
            {
                // Checks if the book is borrowed currently and if the user is the person who is borrowing it.
                if (book.isBorrowed() && book.getBorrowerName().equals(user.getName()))
                {
                    // If the user is the borrower of the book, remove the book from the borrowed list of the user and change the borrowed status of the book.
                    book.returned();
                    user.removeBorrowedBook(book);
                    System.out.println(user.getName() + " successfully returned " + book.getTitle());
                    return;
                }
            }
        }
    }

    /**
     * Main function of the library class. Runs test cases to check the functionality of the library, user, and book class.
     * Holds the name for variables and other data used for testing.
     * @param args The input arguments from the command line.
     */
    public static void main(String[] args) {
        // Create the library
        Library library = new Library("10 Main St.");

        // Add books to the library
        library.addBook(new Book("The Da Vinci Code", 10.0));
        library.addBook(new Book("Le Petit Prince", 8.0));
        library.addBook(new Book("A Tale of Two Cities", 12.0));
        library.addBook(new Book("The Lord of the Rings", 15.0));

        // Print opening hours and the addresses
        System.out.println("Library hours:");
        printOpeningHours();
        System.out.println();

        System.out.println("Library address:");
        library.printAddress();
        System.out.println();

        // Create users and register them
        User alice = new User("U1", "Alice");
        User bob = new User("U2", "Bob");
        library.registerUser(alice);
        library.registerUser(bob);

        // Try to borrow The Lord of the Rings
        System.out.println("Borrowing The Lord of the Rings:");
        library.borrowBook("The Lord of the Rings", alice); // should succeed
        library.borrowBook("The Lord of the Rings", bob);   // should say already borrowed
        System.out.println();

        // Print the titles of all available books from both libraries
        System.out.println("Books available in the library:");
        library.printAvailableBooks();
        System.out.println();;
        System.out.println();

        // Return The Lord of the Rings to the library
        System.out.println("Returning The Lord of the Rings:");
        library.returnBook("The Lord of the Rings", alice);
        System.out.println();

        // Now demonstrate the per-user max-borrow (3 books)
        System.out.println("\nDemonstrate max 3 concurrent borrows for a user:");
        library.borrowBook("The Da Vinci Code", alice);
        library.borrowBook("Le Petit Prince", alice);
        library.borrowBook("A Tale of Two Cities", alice);
        // This fourth borrow should be rejected
        library.borrowBook("The Lord of the Rings", alice);

        // Show borrowed count for Alice
        System.out.println(alice.getName() + " has borrowed " + alice.getBorrowedCount() + " books.");

        // Final available books in library
        System.out.println("\nBooks available in the library:");
        library.printAvailableBooks();
    }
}
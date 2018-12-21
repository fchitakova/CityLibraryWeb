package main.java.view;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import main.java.Book;
import main.java.LibraryBook;
import main.java.exceptions.ReaderException;

/**
 * Base interface which provides methods for showing user interface of library
 * application and communicating with the user.This interface implementation can
 * support different UI languages.
 * 
 * @author I356406
 *
 */
public interface View {


	/**
	 * Shows all readers.
	 */
	public void showReaders();

	/**
	 * This method prints books to application's view
	 * 
	 * @param books       is the book set to print
	 * @param infoMessage  contains information about the set of books which is
	 *                     printed
	 * @param errorMessage contains the info message shown in case the @param book
	 *                     is empty set
	 */
	public void printBooks(Collection<? extends Book> books, String infoMessege, String errorMessage);


	/**
	 * Prints only available library books.
	 * @param infoMessage  contains information about the set of books which is
	 *                     printed
	 * @param errorMessage contains the info message shown in case the @param book
	 *                     is empty set
	 */
	public void printAvailableBooks(String infoMessage, String errorMessage);
	
	/**
	 * This method finds @param propertyName's right representation depending on
	 * current language and prints it to application main.java.view.
	 * 
	 * @param propertyName is name of message in the properties file
	 */
	public void printResource(String propertyName);

	/**
	 * Prints to application main.java.view the message passed as an argument .
	 */
	public void printMessage(String message);

	/**
	 * Prints list of readers from which user can choose.
	 * @return chosen Reader's name
	 */
	public abstract String chooseReader();


	/**
	 * Prints list of books from which user can choose to return.
	 * 
	 * @param books set contains the books from which user can choose
	 * @return chosen Book or null if the invalid choice attempts are greater or
	 *         equal to MAX_INVALID_CHOICE_NUMBER constant.
	 */
	public abstract String[] chooseFromBooks(Set<? extends Book> books);

	/**
	 * This method gets books information from the user input and if it is valid,
	 * new Book is created.Otherwise procedure for getting text information is
	 * repeated.
	 * 
	 * @return new Book object
	 * @throws IOException
	 */
	public abstract Book getBook() throws IOException;

	/**
	 * This method gets reader information from the user input and if it is valid,
	 * new Reader is created.Otherwise procedure for getting text information is
	 * repeated.
	 * 
	 * @return new Reader object
	 * @throws IOException
	 * @throws ReaderException 
	 */
	public abstract String getReader() throws IOException, ReaderException;

	public abstract char[] getUsername();

	public abstract char[] getPassword();

	void printAvailableBooks(Set<LibraryBook> books, String infoMessage, String errorMessage);

	/**
	 * This method checks if the user input is valid to be a reader or book.
	 * 
	 * @param input          is the input which will be checked
	 * @return true if the input is valid(which is when it does not contain numbers
	 *         and special symbols)
	 */
	


}

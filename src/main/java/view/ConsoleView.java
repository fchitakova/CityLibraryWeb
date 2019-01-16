package main.java.view;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import org.xml.sax.SAXException;
import main.java.Book;
import main.java.Constants;
import main.java.LibraryBook;
import main.java.LibraryModel;
import main.java.Reader;
import main.java.Validator;
import main.java.exceptions.BookException;
import main.java.exceptions.MissingReaderException;
import main.java.exceptions.ReaderException;

/**
 * This class extends extends View class and provides implementation for
 * printing information to the console and communicating with the user via
 * console.
 */
public class ConsoleView implements View {

	private static final String TRY_AGAIN_MESSAGE = "Try again:\n";
	/**
	 * The maximum possible invalid input tries.
	 */
	private static final int MAX_INVALID_INPUT_TRIES = 2;

	private LibraryModel libraryDataController;

	private ResourseManager languageResouces;

	/**
	 * Creates new ConsoleView.
	 * 
	 * @param language is the interface language of application main.java.view
	 * @throws PropertyVetoException
	 * @throws SQLException
	 * @throws SAXException
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 * @throws TransformerFactoryConfigurationError
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws TransformerConfigurationException
	 * @throws                                      jdk.internal.org.xml.sax.SAXException
	 * @throws Exception
	 */
	public ConsoleView() throws IOException, TransformerConfigurationException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, TransformerFactoryConfigurationError,
			ParserConfigurationException, TransformerException, SAXException, SQLException, PropertyVetoException,
			jdk.internal.org.xml.sax.SAXException {
		libraryDataController = LibraryModel.getInstance();
		languageResouces = new ResourseManager();
	}

	/**
	 * Prints @param registeredReaders to the console output.
	 */
	@Override
	public void showReaders() {
		Set<Reader> registeredReaders = libraryDataController.getReaders();
		if (registeredReaders.isEmpty()) {
			printResource(Constants.NOT_READERS);
			return;
		}
		printResource(Constants.REGISTERED_READERS_MESSAGE);
		System.out.println();
		for (Reader i : registeredReaders) {
			System.out.println(i.getName());
		}

	}

	/**
	 * 
	 * This method gets user choice as an argument and if it is valid, method
	 * 
	 * corresponding to the choice is invoked .
	 * 
	 * 
	 * 
	 * @param choice which user has entered
	 * 
	 * @throws IOException
	 * 
	 */

	public void runMenu() throws Exception {

		int choice;
		do {
			showUI();
			choice = getChoice(Constants.CONSOLE_MENU_LOWER_BOUND, Constants.CONSOLE_MENU_UPPER_BOUND);
			processUserChoice(choice);
		} while (choice != Constants.EXIT_COMMAND_ID);

	}

	private void processUserChoice(int choice) throws Exception {

		switch (choice) {

		case Constants.EXIT_COMMAND_ID: {

			printResource(Constants.GOODBYE);

			break;

		}

		case Constants.SEE_ALL_BOOKS_SORTED_BY_TITLE_ID: {
			List<LibraryBook> sortedBooks = libraryDataController.getBooksSorted(Constants.SORT_BY_TITLE_ID);
			String sortingInfoMessage = languageResouces.getResource(Constants.PRINT_ALL_BOOKS);
			printBooks(sortedBooks, sortingInfoMessage, Constants.NOT_ANY_BOOKS);
			break;
		}

		case Constants.SEE_ALL_BOOKS_SORTED_BY_AUTHOR_ID: {
			List<LibraryBook> sortedBooks = libraryDataController.getBooksSorted(Constants.SORT_BY_AUTHOR_ID);
			String sortingInfoMessage = languageResouces.getResource(Constants.PRINT_ALL_BOOKS);
			printBooks(sortedBooks, sortingInfoMessage, Constants.NOT_ANY_BOOKS);
			break;
		}

		case Constants.SEE_AVAILABLE_BOOKS_ID: {
			printAvailableBooks(getAvailableLibraryBooks(), Constants.PRINT_AVAILABLE_BOOKS, Constants.NOT_ANY_BOOKS);
			break;

		}

		case Constants.GIVE_BOOK_TO_READER_ID: {
			giveBookToReader();
			break;

		}

		case Constants.RETURN_BOOK_ID: {

			returnBook();

			break;
		}

		case Constants.ADD_BOOK_ID: {

			Book toAdd = getBook();
			if (toAdd != null) {
				libraryDataController.addBook(toAdd);
				printResource(Constants.SUCCESSFUL_BOOK_ADDING);
			}
			break;

		}

		case Constants.REGISTER_READER_ID: {

			registerReader();

			break;

		}

		case Constants.SEE_ALL_READERS_ID: {

			showReaders();

			break;

		}

		case Constants.SEARCH_BY_TITLE_ID: {
			searchByTitle();
			break;

		}

		case Constants.SEARCH_BY_AUTHOR_ID: {

			searchByAuthor();

			break;

		}

		case Constants.SHOW_BOOKS_OF_READER_ID: {

			String chosenReaderName = chooseReader();
			if (chosenReaderName != null) {
				String infoMessage = languageResouces.getResource(Constants.TAKEN_BOOKS_BY_READER) + chosenReaderName
						+ ":";
				Set<Book> readerBooks = libraryDataController.getBooksOfReader(chosenReaderName);
				printBooks(readerBooks, infoMessage, Constants.NOT_ANY_TAKEN_BOOKS);
			}
			break;
		}

		case Constants.CHANGE_LANGUAGE_ID: {

			languageResouces.changeLanguage();
		}

		}

	}

	@Override

	public void printAvailableBooks(Set<LibraryBook> books, String infoMessage, String errorMessage) {

		if (books.isEmpty()) {

			printResource(errorMessage);

			return;

		}

		printResource(infoMessage);

		System.out.println();

		for (LibraryBook b : books) {

			System.out.println(b.toString() + getResource(Constants.HAS_AVAILABLE_COPIES) + b.getCopies() + '\n');

		}

	}

	public void searchByAuthor() throws IOException, BookException {
		String author = getTextInput(Constants.ENTER_AUTHOR, Constants.NOT_VALID_AUTHOR_NAME);
		if (author == null) {
			printResource(Constants.TOO_MANY_INVALID_ATTEMPTS);
			return;
		}
		Set<Book> books = libraryDataController.searchBooksByAuthor(author);

		printBooks(books,Constants.FOUND_BOOKS, Constants.NOT_ANY_BOOKS_FROM_THIS_AUTHOR);
	}

	public void searchByTitle() throws IOException {
		String title = getTextInput(Constants.ENTER_TITLE, Constants.NOT_VALID_BOOK_TITLE);
		if (title == null) {
			printResource(Constants.TOO_MANY_INVALID_ATTEMPTS);
			return;
		}
		Set<Book> books = libraryDataController.searchBookByTitle(title);

		printBooks(books, Constants.FOUND_BOOKS, Constants.NOT_ANY_BOOKS_WITH_TITLE);
	}

	public void registerReader() throws IOException, ReaderException, Exception {
		String readerName = getReader();
		if (readerName != null) {
			boolean successfulRegistration = libraryDataController.registerReader(readerName);
			if (successfulRegistration) {
				printResource(Constants.SUCCESSFUL_READER_REGISTRATION);
			} else {
				printResource(Constants.EXISTING_READER);
			}
		}
	}

	public void returnBook()
			throws IOException, TransformerFactoryConfigurationError, SQLException, PropertyVetoException, Exception {
		String readerName = getReader();

		if (checkReaderValidity(readerName) == false) {
			return;
		}

		Reader returningReader = libraryDataController.getSpecificReader(readerName);
		if (returningReader.hasAnyTakenBooks() == false) {
			printResource(Constants.NOT_ANY_TAKEN_BOOKS);
			return;
		}

		Set<Book> booksToChoose = returningReader.getReaderBooks();
		String[] bookInfo = chooseFromBooks(booksToChoose);
		if (bookInfo == null) {
			return;
		}

		libraryDataController.returnBook(bookInfo[0], bookInfo[1], readerName);
		printResource(Constants.SUCCESSFULLY_RETURNED_BOOK);

	}

	public void giveBookToReader()
			throws IOException, ReaderException, BookException, TransformerConfigurationException, TransformerException,
			TransformerFactoryConfigurationError, ParserConfigurationException, SAXException, SQLException,
			PropertyVetoException, jdk.internal.org.xml.sax.SAXException {

		String readerName = getReader();

		if (checkReaderValidity(readerName) == false || checkForAvailableBooks() == false) {
			return;
		}

		Set<LibraryBook> booksToChoose = getAvailableLibraryBooks();
		String[] bookInfo = chooseFromBooks(booksToChoose);
		if (bookInfo == null) {
			return;
		}
		libraryDataController.giveBookToReader(bookInfo[0], bookInfo[1], readerName);
		printResource(Constants.SUCCESSFULLY_GIVEN_BOOK);
	}

	private Set<LibraryBook> getAvailableLibraryBooks() {
		return libraryDataController.getAvailableBooks();
	}

	/**
	 * Checks whether the library catalog contains available books.
	 * 
	 * @return
	 */
	private boolean checkForAvailableBooks() {
		if (libraryDataController.hasAnyAvailableBooks() == false) {
			printResource(Constants.NOT_ANY_BOOKS);
			return false;
		}
		return true;

	}

	/**
	 * This method checks if the provided reader name is valid and reader with this
	 * name is registered.
	 * 
	 * @return true if the above conditions are met.
	 */
	private boolean checkReaderValidity(String readerName) {
		if (readerName == null) {
			printResource(Constants.TOO_MANY_INVALID_ATTEMPTS);
			return false;
		}
		try {
			libraryDataController.getSpecificReader(readerName);
		} catch (MissingReaderException e) {
			printResource(Constants.NOT_REGISTERED_READER);
			return false;
		}
		return true;
	}

	/**
	 * Shows the user interface containing menu options.
	 */

	public void showUI() {
		System.out.print("\n\n\n");
		System.out.print(getResource(Constants.TITLE) + "\n\n\n");
		printResource(Constants.PRESS);
		System.out.print(Constants.SEE_ALL_BOOKS_SORTED_BY_TITLE_ID + Constants.DELIMITER);
		System.out.println(languageResouces.getResource(Constants.SEE_ALL_BOOKS) + " "
				+ languageResouces.getResource(Constants.SORTED_BY_TITLE));
		System.out.print(Constants.SEE_ALL_BOOKS_SORTED_BY_AUTHOR_ID + Constants.DELIMITER);
		System.out.println(languageResouces.getResource(Constants.SEE_ALL_BOOKS) + " "
				+ languageResouces.getResource(Constants.SORTED_BY_AUTHOR));
		System.out.print(Constants.SEE_AVAILABLE_BOOKS_ID + Constants.DELIMITER);
		printResource(Constants.SEE_AVAILABLE_BOOKS);
		System.out.print(Constants.GIVE_BOOK_TO_READER_ID + Constants.DELIMITER);
		printResource(Constants.GIVE_BOOK_TO_READER);
		System.out.print(Constants.RETURN_BOOK_ID + Constants.DELIMITER);
		printResource(Constants.RETURN_BOOK);
		System.out.print(Constants.ADD_BOOK_ID + Constants.DELIMITER);
		printResource(Constants.ADD_BOOK);
		System.out.print(Constants.REGISTER_READER_ID + Constants.DELIMITER);
		printResource(Constants.REGISTER_READER);
		System.out.print(Constants.SEE_ALL_READERS_ID + Constants.DELIMITER);
		printResource(Constants.SEE_ALL_READERS);
		System.out.print(Constants.SHOW_BOOKS_OF_READER_ID + Constants.DELIMITER);
		printResource(Constants.SHOW_BOOKS_OF_READER);
		System.out.print(Constants.SEARCH_BY_TITLE_ID + Constants.DELIMITER);
		printResource(Constants.SEARCH_BY_TITLE);
		System.out.print(Constants.SEARCH_BY_AUTHOR_ID + Constants.DELIMITER);
		printResource(Constants.SEARCH_BY_AUTHOR_RROPERTY);
		System.out.print(Constants.CHANGE_LANGUAGE_ID + Constants.DELIMITER);
		printResource(Constants.CHANGE_LANGUAGE);
		System.out.print(Constants.EXIT_COMMAND_ID + Constants.DELIMITER);
		printResource(Constants.EXIT);
		System.out.println();
		printResource(Constants.GET_CHOICE);

	}

	/**
	 * This method gets books information from user input and if it is valid new
	 * Book is returned.Otherwise null is returned.
	 * 
	 * @return new Book object
	 * @throws IOException
	 */
	@Override
	public Book getBook() throws IOException {
		String author = getTextInput(Constants.ENTER_AUTHOR, Constants.NOT_VALID_AUTHOR_NAME);
		if (author == null) {
			printResource(Constants.TOO_MANY_INVALID_ATTEMPTS);
			return null;
		}
		String title = getTextInput(Constants.ENTER_TITLE, Constants.NOT_VALID_BOOK_TITLE);
		if (title == null) {
			printResource(Constants.TOO_MANY_INVALID_ATTEMPTS);
			return null;
		}
		return new Book(author, title);

	}

	/**
	 * This method reads input and checks if it is valid. If the input is valid
	 * reader name it is returned. Otherwise information message is printed.
	 * 
	 * @return String or null
	 * @throws IOException
	 */
	@Override
	public String getReader() throws IOException {
		String name = getTextInput(Constants.ENTER_READER, Constants.NOT_VALID_READER_NAME);
		return name;
	}

	/**
	 * This method asks the user to enter information using inviting message. If
	 * user input is valid text it is returned. Otherwise the same procedure is
	 * repeated until try number is less than MAX_INVALID_INPUT_TRIES constant.
	 * 
	 * @param infoMessege       is the inviting text messages which tells the user
	 *                          what kind of information is needed.
	 * @param ifNotValidMessage contains the message which should be printed if the
	 *                          input is not valid
	 * @return string containing the user input or null
	 */
	public String getTextInput(String infoMessege, String ifNotValidMessage) throws IOException {
		String input;
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		int invalidTryNumber = 0;
		boolean isValidInput = false;
		do {
			if (invalidTryNumber > 0) {
				System.out.println(getResource(ifNotValidMessage) + TRY_AGAIN_MESSAGE);
			} else {
				printResource(infoMessege);
			}
			try {
				input = bf.readLine();
			} catch (IOException e) {
				input = "  ";
			}
			input = input.replaceAll("\\s+", " ").trim();
			isValidInput = Validator.checkInputTextValidity(input);
			++invalidTryNumber;
		} while (!isValidInput && invalidTryNumber < MAX_INVALID_INPUT_TRIES);

		if (invalidTryNumber == MAX_INVALID_INPUT_TRIES && !isValidInput) {
			return null;
		}
		return input;
	}

	/**
	 * This method finds @param propertyName's right representation depending on
	 * current language and prints it to console.
	 * 
	 * @param propertyName is name of message in the properties file
	 */
	public void printResource(String propertyName) {
		System.out.println(getResource(propertyName));
	}

	/**
	 * This method prints library books to console main.java.view.
	 * 
	 * @param books        is the book set to print
	 * @param infoMessage  contains information about the set of books which is
	 *                     printed
	 * @param errorMessage contains the info message shown in case the @param book
	 *                     is empty set
	 */
	@Override
	public void printBooks(Collection<? extends Book> books, String infoMessage, String errorMessage) {
		if (books.isEmpty()) {
			printResource(errorMessage);
			return;
		}
		printResource(infoMessage);
		System.out.print("\n\n\n");
		for (Book b : books) {
			System.out.println(b.toString() + '\n');
		}
	}

	/**
	 * Prints list of readers from which user can choose.
	 * 
	 * @return chosen Reader's name
	 */
	@Override
	public String chooseReader() {
		Set<Reader> readers = libraryDataController.getReaders();
		if (readers.isEmpty()) {
			printResource(Constants.NOT_READERS);
			return null;
		}
		printResource(Constants.CHOOSE_READER_MESSAGE);
		System.out.println();
		HashMap<Integer, Reader> readersMap = new HashMap<Integer, Reader>();
		int value = 1;
		for (Reader r : readers) {
			readersMap.put(value, r);
			System.out.println((value++) + Constants.DELIMITER + r.getName());
		}
		int choice = getChoice(Constants.CHOICE_MENU_LOWER_BOUND, readers.size());
		if (choice == Constants.INVALID_CHOICE_NUMBER) {
			return null;
		}

		return readersMap.get(choice).getName();
	}

	/**
	 * Prints list of books from which user can choose.
	 * 
	 * @param books set contains the books from which user can choose
	 * @return chosen Book's author and title.
	 */
	@Override
	public String[] chooseFromBooks(Set<? extends Book> books) {
		printResource(Constants.CHOOSE_BOOK_MESSAGE);
		System.out.println();
		HashMap<Integer, Book> booksMap = new HashMap<Integer, Book>();
		int value = 1;

		for (Book book : books) {
			booksMap.put(value, book);
			System.out.println((value++) + Constants.DELIMITER + book.toString());
		}
		int choice = getChoice(Constants.CHOICE_MENU_LOWER_BOUND, books.size());
		if (choice == Constants.INVALID_CHOICE_NUMBER) {
			printResource(Constants.TOO_MANY_INVALID_ATTEMPTS);
			return null;
		}
		return new String[] { booksMap.get(choice).getAuthor(), booksMap.get(choice).getTitle() };
	}

	/**
	 * Gets user choice and checks if it is valid. The choice is valid if it is
	 * between @param menuLowerBound and @param menuUpperBound values. If the choice
	 * is not valid the same procedure is repeated until input attempts are less
	 * than MAX_INVALID_CHOICE_NUMBER
	 * 
	 * @param menuLowerBound is the lower limit of the numbers which are mapped to
	 *                       command choices
	 * @param menuUpperBound is the upper limit of the numbers which are mapped to
	 *                       command choices
	 * 
	 * @return userChoice or INVALID_CHOICE constant
	 */
	public int getChoice(int menuLowerBound, int menuUpperBound) {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		int userChoice = Constants.INVALID_CHOICE_NUMBER;
		int numberOfTries = 0;
		boolean successfulChoice = false;
		do {
			successfulChoice = true;
			if (numberOfTries > 0) {
				printResource(Constants.INVALID_CHOICE);
			}
			try {
				String choice = bf.readLine().trim();
				userChoice = Integer.parseInt(choice);
				if (userChoice < menuLowerBound || userChoice > menuUpperBound) {
					throw new IndexOutOfBoundsException();
				}
			} catch (NumberFormatException | IOException | IndexOutOfBoundsException e) {
				successfulChoice = false;
			}
			++numberOfTries;
		} while (!successfulChoice && numberOfTries < MAX_INVALID_INPUT_TRIES);
		if (numberOfTries == MAX_INVALID_INPUT_TRIES && !successfulChoice) {
			return Constants.INVALID_CHOICE_NUMBER;
		}
		return userChoice;
	}

	@Override
	public char[] getUsername() {
		printResource(Constants.ENTER_USERNAME);
		Scanner sc = new Scanner(System.in);
		char[] a = sc.next().toCharArray();
		sc.close();
		return a;
	}

	@Override
	public char[] getPassword() {
		printResource(Constants.ENTER_PASSWORD);
		Scanner sc = new Scanner(System.in);
		char[] a = sc.next().toCharArray();
		sc.close();
		return a;
	}

	@Override
	public void printAvailableBooks(String infoMessage, String errorMessage) {
		Set<LibraryBook> books = getAvailableLibraryBooks();
		if (books.isEmpty()) {
			printResource(errorMessage);
			return;
		}
		printResource(infoMessage);
		System.out.println();
		for (LibraryBook b : books) {
			System.out.println(b.toString() + getResource(Constants.HAS_AVAILABLE_COPIES) + b.getCopies() + "\n");
		}
	}

	private String getResource(String key) {
		return languageResouces.getResource(key);
	}

}

package main.java.view;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.PropertyResourceBundle;
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
import main.java.exceptions.BookException;
import main.java.exceptions.ReaderException;

/**
 * This class extends extends View class and provides implementation for
 * printing information to the console and communicating with the user via
 * console.
 */
public class ConsoleView implements View {

	private static final String TRY_AGAIN_MESSAGE = "Try again:\n";
	private static final String HAS_AVAILABLE_COPIES = " \n              available copies:";
	public static final String RESOURCE_BUNDLE_BASE_PATH = "WebContent\\WEB-INF\\lib\\resourses\\languageResources_";
	public static final String BG_LANGUAGE_INITIALS = "bg_BG";
	public static final String EN_LANGUAGE_INITIALS = "en";
	/**
	 * The maximum possible invalid input tries.
	 */
	private static final int MAX_INVALID_INPUT_TRIES = 2;

	protected PropertyResourceBundle languageResources;

	private String currentLanguage;

	private LibraryModel libraryDataController;

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
	 * @throws Exception
	 */
	public ConsoleView(String language) throws IOException, TransformerConfigurationException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, TransformerFactoryConfigurationError,
			ParserConfigurationException, TransformerException, SAXException, SQLException, PropertyVetoException {
		this.libraryDataController = new LibraryModel("WebContent\\WEB-INF\\lib\\resourses\\library.properties");
		currentLanguage = language;
		initResourceBundle();
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
			printMessage(i.getName() + '\n');
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
			printBooks(sortedBooks, getResource(Constants.PRINT_ALL_BOOKS), Constants.NOT_ANY_BOOKS);
			break;
		}

		case Constants.SEE_ALL_BOOKS_SORTED_BY_AUTHOR_ID: {
			List<LibraryBook> sortedBooks = libraryDataController.getBooksSorted(Constants.SORT_BY_AUTHOR_ID);
			printBooks(sortedBooks, getResource(Constants.PRINT_ALL_BOOKS), Constants.NOT_ANY_BOOKS);
			break;
		}

		case Constants.SEE_AVAILABLE_BOOKS_ID: {
			printAvailableBooks(libraryDataController.getAvailableBooks(), Constants.PRINT_AVAILABLE_BOOKS, Constants.NOT_ANY_BOOKS);
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
				printResource(Constants.SUCCESSFUL_ADDED_BOOK);
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
				String infoMessage = getResource(Constants.TAKEN_BOOKS_BY_READER) + chosenReaderName + ":";
				printBooks(libraryDataController.getBooksOfReader(chosenReaderName), infoMessage, Constants.NOT_ANY_TAKEN_BOOKS);
			}
			break;
		}

		case Constants.CHANGE_LANGUAGE_ID: {

			changeLanguage();

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

			printMessage(b.toString() + HAS_AVAILABLE_COPIES + b.getCopies() + "\n\n");

		}

	}

	public void searchByAuthor() throws IOException, BookException {
		String author = getTextInputInfo(Constants.SEARCH_BY_AUTHOR_RROPERTY, Constants.NOT_VALID_AUTHOR_NAME);
		if (author == null) {
			printResource(Constants.TOO_MANY_INVALID_ATTEMPTS);
			return;
		}
		Set<Book> books = libraryDataController.searchBooksByAuthor(author);

		this.printBooks(books, getResource(Constants.PRINT_BOOKS_BY_AUTHOR), Constants.NOT_ANY_BOOKS_FROM_THIS_AUTHOR);
	}

	public void searchByTitle() throws IOException {
		String title = getTextInputInfo(Constants.SEARCH_BY_TITLE, Constants.NOT_VALID_BOOK_TITLE);
		if (title == null) {
			printResource(Constants.TOO_MANY_INVALID_ATTEMPTS);
			return;
		}
		Set<Book> books = libraryDataController.searchBookByTitle(title);

		this.printBooks(books, getResource(Constants.PRINT_BOOKS_BY_TITLE), Constants.NOT_ANY_BOOKS_WITH_TITLE);
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
		String readerName;
		try {
			readerName = getReader();
		} catch (ReaderException re) {
			printResource(re.getMessage());
			return;
		}

		Reader returningReader = libraryDataController.getSpecificReader(readerName);
		if (returningReader == null) {
			printResource(Constants.NOT_REGISTERED_READER);
		}

		Set<Book> booksToChoose = returningReader.getReaderBooks();
		if (booksToChoose.isEmpty()) {
			printResource(Constants.NOT_ANY_BOOKS);
			return;
		}

		String[] book = chooseFromBooks(booksToChoose);

		libraryDataController.returnBook(book[0], book[1], readerName);

	}

	public void giveBookToReader() throws IOException, ReaderException, BookException,
			TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError,
			ParserConfigurationException, SAXException, SQLException, PropertyVetoException {
		String readerName;
		try {
			readerName = getReader();
		} catch (ReaderException re) {
			String exceptionMessage = re.getMessage();
			printResource(exceptionMessage);
			return;
		}

		if (libraryDataController.getSpecificReader(readerName) == null) {
			printResource(Constants.NOT_REGISTERED_READER);
			return;
		}

		Set<LibraryBook> booksToChoose = libraryDataController.getAvailableBooks();
		if (booksToChoose.isEmpty()) {
			printResource(Constants.NOT_ANY_BOOKS);
			return;
		}

		String[] book = chooseFromBooks(booksToChoose);

		libraryDataController.giveBookToReader(book[0], book[1], readerName);
	}

	/**
	 * Shows the user interface containing menu options.
	 */

	public void showUI() {
		printMessage("\n\n\n");
		printMessage(getResource(Constants.TITLE) + "\n\n\n");
		printResource(Constants.PRESS);
		printMessage(Constants.SEE_ALL_BOOKS_SORTED_BY_TITLE_ID + Constants.DELIMITER);
		printResource(Constants.SEE_ALL_BOOKS_SORTED_BY_TITLE);
		printMessage(Constants.SEE_ALL_BOOKS_SORTED_BY_AUTHOR_ID + Constants.DELIMITER);
		printResource(Constants.SEE_ALL_BOOKS_SORTED_BY_AUTHOR);
		printMessage(Constants.SEE_AVAILABLE_BOOKS_ID + Constants.DELIMITER);
		printResource(Constants.SEE_AVAILABLE_BOOKS);
		printMessage(Constants.GIVE_BOOK_TO_READER_ID + Constants.DELIMITER);
		printResource(Constants.GIVE_BOOK_TO_READER);
		printMessage(Constants.RETURN_BOOK_ID + Constants.DELIMITER);
		printResource(Constants.RETURN_BOOK);
		printMessage(Constants.ADD_BOOK_ID + Constants.DELIMITER);
		printResource(Constants.ADD_BOOK);
		printMessage(Constants.REGISTER_READER_ID + Constants.DELIMITER);
		printResource(Constants.REGISTER_READER);
		printMessage(Constants.SEE_ALL_READERS_ID + Constants.DELIMITER);
		printResource(Constants.SEE_ALL_READERS);
		printMessage(Constants.SHOW_BOOKS_OF_READER_ID + Constants.DELIMITER);
		printResource(Constants.SHOW_BOOKS_OF_READER);
		printMessage(Constants.SEARCH_BY_TITLE_ID + Constants.DELIMITER);
		printResource(Constants.SEARCH_BY_TITLE);
		printMessage(Constants.SEARCH_BY_AUTHOR_ID + Constants.DELIMITER);
		printResource(Constants.SEARCH_BY_AUTHOR_RROPERTY);
		printMessage(Constants.CHANGE_LANGUAGE_ID + Constants.DELIMITER);
		printResource(Constants.CHANGE_LANGUAGE);
		printMessage(Constants.EXIT_COMMAND_ID + Constants.DELIMITER);
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
		String author = getTextInputInfo(Constants.ENTER_AUTHOR, Constants.NOT_VALID_AUTHOR_NAME);
		if (author == null) {
			printResource(Constants.TOO_MANY_INVALID_ATTEMPTS);
			return null;
		}
		String title = getTextInputInfo(Constants.ENTER_TITLE, Constants.NOT_VALID_BOOK_TITLE);
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
	 * @throws ReaderException indicates that too many invalid attempts for input
	 *                         are failed.
	 */
	@Override
	public String getReader() throws IOException, ReaderException {
		String name = getTextInputInfo(Constants.ENTER_READER, Constants.NOT_VALID_READER_NAME);
		if (name == null) {
			printResource(Constants.TOO_MANY_INVALID_ATTEMPTS);
			return null;
		}
		return name;
	}

	/**
	 * This method asks the user to enter information with inviting message stored
	 * in @param infoMessege. If user input is valid text,its content is returned.
	 * Otherwise the same procedure is repeated until try number is less than
	 * MAX_INVALID_INPUT_TRIES constant.
	 * 
	 * @param infoMessege       is the inviting text messages which tells the user
	 *                          what kind of information is needed.
	 * @param ifNotValidMessage contains the message which should be printed if the
	 *                          input is not valid
	 * @return string containing the user input or null
	 */
	public String getTextInputInfo(String infoMessege, String ifNotValidMessage) throws IOException {
		String input;
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		int invalidTryNumber = 0;
		boolean isValidInput = false;
		do {
			if (invalidTryNumber > 0) {
				printMessage(getResource(ifNotValidMessage) + TRY_AGAIN_MESSAGE);
			} else {
				printResource(infoMessege);
			}
			try {
				input = bf.readLine();
			} catch (IOException e) {
				input = "  ";
			}
			input = input.replaceAll("\\s+", " ").trim();
			isValidInput = LibraryModel.checkInputTextValidity(input);
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
	@Override
	public void printResource(String propertyName) {
		System.out.println(getResource(propertyName));
	}

	/**
	 * This method prints message string to the string and do not print new line
	 * after that.
	 */
	@Override
	public void printMessage(String message) {
		System.out.print(message);
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
		printMessage(infoMessage + "\n\n");
		for (Book b : books) {
			printMessage(b.toString() + '\n');
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
				printResource(Constants.INVALID_CHOICE_PROPERTY);
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
		return a;
	}

	@Override
	public char[] getPassword() {
		printResource(Constants.ENTER_PASSWORD);
		Scanner sc = new Scanner(System.in);
		char[] a = sc.next().toCharArray();
		return a;
	}

	@Override
	public void printAvailableBooks(String infoMessage, String errorMessage) {
		Set<LibraryBook> books = libraryDataController.getAvailableBooks();
		if (books.isEmpty()) {
			printResource(errorMessage);
			return;
		}
		printResource(infoMessage);
		System.out.println();
		for (LibraryBook b : books) {
			printMessage(b.toString() + HAS_AVAILABLE_COPIES + b.getCopies() + "\n\n");
		}
	}

	private void initResourceBundle() throws IOException {

		StringBuilder filePath = new StringBuilder(RESOURCE_BUNDLE_BASE_PATH);
		if (currentLanguage.equals(Constants.BG_LANGUAGE)) {
			filePath.append(BG_LANGUAGE_INITIALS);
		} else {
			filePath.append(EN_LANGUAGE_INITIALS);
		}
		filePath.append(Constants.PROPERTIES_FILE_EXTENSION);

		InputStream fileInput = new FileInputStream(new File(filePath.toString()));

		languageResources = new PropertyResourceBundle(
				new InputStreamReader(fileInput, Charset.forName(Constants.UTF_8_ENCODING)));
	}

	/**
	 * Changes the application main.java.view's language.
	 * 
	 * @throws IOException
	 */
	public void changeLanguage() throws IOException {
		currentLanguage = (currentLanguage.equals(Constants.BG_LANGUAGE)) ? Constants.EN_LANGUAGE
				: Constants.BG_LANGUAGE;
		initResourceBundle();
	}

	public String getResource(String propertyName) {
		if (!propertyName.equals(Constants.EMPTY_MESSAGE)) {
			return languageResources.getString(propertyName);
		}
		return propertyName;
	}
}

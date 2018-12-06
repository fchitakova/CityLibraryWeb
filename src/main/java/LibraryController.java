package main.java;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.xml.sax.SAXException;

import main.java.persistency.DBPersistency;
import main.java.persistency.Persistency;
import main.java.persistency.XmlDOMPersistency;
import main.java.view.ConsoleView;
import main.java.view.View;

/**
 * This class is responsible for the whole logic of library and managing stored
 * library information. It contains collections holding reader information and
 * books information. Plus it controls how the data is viewed,the communication
 * with the user,data persistency and application libraryProperties.
 * 
 * @author I356406
 *
 */
public class LibraryController implements Serializable {

	public static final String DATABASE_PROPERTIES_FILEPATH = "src\\main\\java\\resources\\databaseProperties.properties";

	/**
	 * This is the catalogue containing all the books in library.
	 */
	private Catalogue bookCatalogue;
	/**
	 * View is responsible for showing user interface and corresponding with the
	 * user.
	 */
	private View appView;
	/**
	 * Persistency is responsible for storing and managing information.
	 */
	private Persistency persistency;
	/**
	 * Contains library's reader information.
	 */
	private Readers readers;
	/**
	 * Property file contains library settings information.
	 */
	private Properties libraryProperties;

	/**
	 * Creates new LibraryController using the project setting properties in the
	 * property file. IMPORTANT:If property file reading attempt fails , created
	 * library is with default console main.java.view with english language and file
	 * system persistency. If the persistency type is database but the connection
	 * with the server fails again the default persistency will be the file system.
	 * 
	 * @param propertyFileName is the property file containing the library settings.
	 * @throws TransformerFactoryConfigurationError
	 * @throws PropertyVetoException
	 * @throws SQLException
	 * @throws SAXException
	 * @throws TransformerException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws TransformerConfigurationException
	 * @throws Exception
	 */
	public LibraryController(String propertyFileName)
			throws TransformerFactoryConfigurationError, TransformerConfigurationException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, ParserConfigurationException, IOException,
			TransformerException, SAXException, SQLException, PropertyVetoException {
		bookCatalogue = new Catalogue();
		readers = new Readers();
		initProperties(propertyFileName);
	}

	/**
	 * Parses libraryProperties file provided as and argument and set the library's
	 * main.java.view,language and persistency. If property file reading attempt
	 * fails.The created library is with default Console main.java.view with english
	 * language and File system persistency.
	 * 
	 * @throws TransformerFactoryConfigurationError
	 * @throws SAXException
	 * @throws TransformerException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws TransformerConfigurationException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private void initProperties(String propertyFileName) throws IOException, TransformerException, SAXException,
			SQLException, ClassNotFoundException, TransformerFactoryConfigurationError, ParserConfigurationException {

		libraryProperties = new Properties();
		FileInputStream fileInput = new FileInputStream(propertyFileName);
		libraryProperties.load(fileInput);

		String language = libraryProperties.getProperty(Constants.LANGUAGE);
		if (getProperty(Constants.VIEW).equals(Constants.CONSOLE_VIEW)) {
			appView = new ConsoleView(language);
		}
		if (getProperty(Constants.PERSISTENCY_PROPERTY).equals(Constants.FILE_SYSTEM_PERSISTENCY)) {
			persistency = new XmlDOMPersistency();
		}
		if (getProperty(Constants.PERSISTENCY_PROPERTY).equals(Constants.DATABASE_PERSISTENCY)) {
			// persistency = new DBPersistency(appView.getUsername()
			// ,appView.getPassword());
			persistency = new DBPersistency("fiffy".toCharArray(), "12345678.a".toCharArray());
		}
		readers = persistency.loadReaders();
		bookCatalogue = persistency.loadBookCatalogue();
	}

	/**
	 * Searches for the property with the specified key in library property list.
	 * 
	 * @param propertyKey
	 * @return
	 */
	private String getProperty(String propertyKey) {
		return libraryProperties.getProperty(propertyKey);
	}

	/**
	 * Invites the user to enter author name (via application main.java.view) and
	 * searches for books by this author. If there are any books by this author,
	 * they are printed otherwise information message is printed.
	 * 
	 * @throws IOException
	 */
	private void searchBooksByAuthor() throws IOException {
		String authorName = appView.getTextInputInfo(Constants.SEARCH_BY_AUTHOR_RROPERTY,
				Constants.NOT_VALID_AUTHOR_NAME);
		appView.printBooks(bookCatalogue.searchByAuthor(authorName), appView.getResource(Constants.PRINT_BOOKS_BY_AUTHOR),
				Constants.NOT_ANY_BOOKS_FROM_THIS_AUTHOR);

	}

	/**
	 * Invites the user to enter title (via application main.java.view) and searches
	 * for books by this author. If there are any books with this title, they are
	 * printed otherwise information message is printed.
	 * 
	 * @throws IOException
	 */
	private void searchBookByTitle() throws IOException {
		String title = appView.getTextInputInfo(Constants.SEARCH_BY_TITLE, Constants.NOT_VALID_BOOK_TITLE);
		appView.printBooks(bookCatalogue.searchByTitle(title), appView.getResource(Constants.PRINT_BOOKS_BY_TITLE),
				Constants.NOT_ANY_BOOKS_WITH_TITLE);
	}

	/**
	 * Shows user interface and waits for valid user choice.When user enters valid
	 * choice corresponding method is invoked.
	 * 
	 * @throws Exception
	 */
	public void runMenu() throws Exception {
		int choice;
		do {
			appView.showUI();
			choice = appView.getChoice(Constants.CONSOLE_MENU_LOWER_BOUND, Constants.CONSOLE_MENU_UPPER_BOUND);
			processUserChoice(choice);
		} while (choice != Constants.EXIT_COMMAND_ID);
	}

	/**
	 * This method gets user choice as an argument and if it is valid, method
	 * corresponding to the choice is invoked .
	 * 
	 * @param choice which user has entered
	 * @throws IOException
	 */
	private void processUserChoice(int choice) throws Exception {

		switch (choice) {

		case Constants.EXIT_COMMAND_ID: {
			appView.printResource(Constants.GOODBYE);
			break;
		}

		case Constants.SEE_ALL_BOOKS_SORTED_BY_TITLE_ID: {
			printBooksBySortingPreference(Constants.SEE_ALL_BOOKS_SORTED_BY_TITLE_ID);
			break;
		}
		case Constants.SEE_ALL_BOOKS_SORTED_BY_AUTHOR_ID: {
			printBooksBySortingPreference(Constants.SEE_ALL_BOOKS_SORTED_BY_AUTHOR_ID);
			break;
		}

		case Constants.SEE_AVAILABLE_BOOKS_ID: {
			appView.printAvailableBooks(bookCatalogue.getAvailableBooks(), Constants.PRINT_AVAILABLE_BOOKS,
					Constants.NOT_ANY_BOOKS);
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
			addBook();
			break;
		}
		case Constants.REGISTER_READER_ID: {
			registerReader();
			break;
		}
		case Constants.SEE_ALL_READERS_ID: {
			appView.printReaders(readers.getReaders());
			break;
		}
		case Constants.SEARCH_BY_TITLE_ID: {
			searchBookByTitle();
			break;
		}
		case Constants.SEARCH_BY_AUTHOR_ID: {
			searchBooksByAuthor();
			break;
		}

		case Constants.SHOW_BOOKS_OF_READER_ID: {
			showBooksOfReader();
			break;
		}

		case Constants.CHANGE_LANGUAGE_ID: {
			appView.changeLanguage();
		}
		}
	}

	/**
	 * This method gets a reader from user input.If the reader name is valid and the
	 * reader is registered his/her taken books are printed.If the reader has not
	 * any taken books info message for user is printed.
	 */
	private void showBooksOfReader() {
		Set<Reader> readersToChoose = readers.getReaders();
		if (readersToChoose.isEmpty()) {
			appView.printResource(Constants.NOT_READERS);
			return;
		}
		Reader chosenReader = appView.chooseReader(readersToChoose);
		if (chosenReader == null) {
			appView.printMessage("\n");
			appView.printResource(Constants.TOO_MANY_INVALID_ATTEMPTS);
			return;
		}
		if (!readers.contains(chosenReader)) {
			appView.printResource(Constants.NOT_REGISTERED_READER);
			return;
		}
		Set<Book> chosenReadersBooks = chosenReader.getReaderBooks();
		String infoMessage=appView.getResource(Constants.TAKEN_BOOKS_BY_READER) + chosenReader.getName() + ":";

		appView.printBooks(chosenReadersBooks, infoMessage, Constants.NOT_ANY_TAKEN_BOOKS);
	}

	/**
	 * This method get reader's info from the application main.java.view input and
	 * checks if the reader with the same name is already in the readers set .If it
	 * is registration is unsuccessfull.Otherwise the new reader is added to the
	 * reader's list.
	 * 
	 * @return true if the reader is registered.False it is already registered.
	 * @throws Exception
	 */
	private void registerReader() throws Exception {
		Reader readerToAdd = appView.getReader();
		if (readerToAdd == null) {
			appView.printMessage("\n");
			appView.printResource(Constants.TOO_MANY_INVALID_ATTEMPTS);
			return;
		}
		boolean isSuccessful = readers.add(readerToAdd);
		System.out.println();
		if (isSuccessful) {
			persistency.addReader(readerToAdd);
			appView.printResource(Constants.SUCCESSFUL_READER_REGISTRATION);
		} else {
			appView.printResource(Constants.EXISTING_READER);
		}

	}

	/**
	 * This method prints books if there are any books to print and chosen printing
	 * order is valid.
	 * 
	 * @param onlyAvailable indicates if only the available books should be
	 *                      printed.If it is false all the books in the catalogue
	 *                      are printed.
	 * @throws IOException
	 */
	private void printBooksBySortingPreference(int sortingOrder) throws IOException {
		List<LibraryBook> sorted = null;
		Set<LibraryBook> toSort = bookCatalogue.getLibraryBooks();

		if (sortingOrder == Constants.SORT_BY_AUTHOR_ID) {
			sorted = bookCatalogue.sortBooks(new AuthorComparator(), toSort);
		}
		if (sortingOrder == Constants.SORT_BY_TITLE_ID) {
			sorted = bookCatalogue.sortBooks(new BookTitleComparator(), toSort);
		}
		appView.printBooks(sorted, appView.getResource(Constants.PRINT_ALL_BOOKS), Constants.NOT_ANY_BOOKS);
	}

	/**
	 * This methods adds Book to the booklist of Reader passed as first argument but
	 * only if the reader is registered and the the library has this book available
	 * 
	 * @param reader    is the reader which want to get the book
	 * @param bookToGet is the book which is wanted
	 */
	private void giveBookToReader() throws Exception {

		Reader searchedReader = appView.getReader();
		if (searchedReader == null) {
			appView.printMessage("\n");
			appView.printResource(Constants.TOO_MANY_INVALID_ATTEMPTS);
			return;
		}
		Reader reader = readers.getReaderFromSet(searchedReader);
		if (reader == null) {
			appView.printResource(Constants.NOT_REGISTERED_READER);
			return;
		}
		Set<LibraryBook> booksToChoose = bookCatalogue.getAvailableBooks();
		if (booksToChoose.isEmpty()) {
			appView.printResource(Constants.NOT_ANY_BOOKS);
			return;
		}

		Book book = appView.chooseFromAvaialbale(booksToChoose);

		if (book == null) {
			appView.printMessage("\n");
			appView.printResource(Constants.TOO_MANY_INVALID_ATTEMPTS);
			return;
		}
		if (!bookCatalogue.isAvailableBook(book)) {
			appView.printResource(Constants.NOT_AVAILABLE_BOOK);
			return;
		}
		persistency.giveBookToReader(reader, book);
		reader.addBook(book);
		bookCatalogue.removeBook(book);
		appView.printResource(Constants.SUCCESSFULLY_GIVEN_BOOK);

	}

	/**
	 * This method is invoked when the user wants to return a book.If reader is not
	 * registered or the book is not a library book return attempt is unsuccessful.
	 * 
	 * @throws Exception
	 */
	private void returnBook() throws Exception {
		Reader r = appView.getReader();

		if (r == null) {
			appView.printMessage("\n");
			appView.printResource(Constants.TOO_MANY_INVALID_ATTEMPTS);
			return;
		}

		Reader returningReader = readers.getReaderFromSet(r);
		if (returningReader == null) {
			appView.printResource(Constants.NOT_REGISTERED_READER);
			return;
		}

		Set<Book> booksToReturn = returningReader.getReaderBooks();
		if (booksToReturn.isEmpty()) {
			appView.printResource(Constants.NOT_ANY_TAKEN_BOOKS);
			return;
		}

		Book bookToReturn = appView.chooseFromTakenBooks(booksToReturn);

		if (bookToReturn == null) {
			appView.printMessage("\n");
			appView.printResource(Constants.TOO_MANY_INVALID_ATTEMPTS);
			return;
		}

		if (bookCatalogue.checkForBook(bookToReturn) == null) {
			appView.printResource(Constants.NOT_LIBRARY_BOOK);
			return;
		}

		persistency.returnBook(returningReader, bookToReturn);
		bookCatalogue.addBook(bookToReturn);
		returningReader.returnBook(bookToReturn);
		appView.printResource(Constants.SUCCESSFULLY_RETURNED_BOOK);

	}

	/**
	 * This method get book information from main.java.view's input and the book is
	 * added to the catalogue . If the book is already in the catalogue a new copy
	 * of it is added.
	 * 
	 * @throws Exception
	 */
	private void addBook() throws Exception {
		Book newBook = appView.getBook();
		if (newBook == null) {
			appView.printMessage("\n");
			appView.printResource(Constants.TOO_MANY_INVALID_ATTEMPTS);
			return;
		}
		persistency.addBook(newBook);
		bookCatalogue.addBook(newBook);
		appView.printResource(Constants.SUCCESSFUL_ADDED_BOOK);

	}

	public Catalogue getBookCatalogue() {
		return bookCatalogue;
	}

	public void setLibraryBooks(Catalogue libraryBooks) {
		this.bookCatalogue = libraryBooks;
	}

	public Readers getReaders() {
		return readers;
	}

	public void setReaders(Readers rs) {
		readers = rs;
	}

	public Persistency getPersistency() {
		return persistency;
	}

};
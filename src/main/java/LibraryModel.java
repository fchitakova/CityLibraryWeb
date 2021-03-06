package main.java;

import main.java.exceptions.ReaderException;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import jdk.internal.org.xml.sax.SAXException;
import main.java.exceptions.BookException;
import main.java.exceptions.MissingReaderException;
import main.java.persistency.DBPersistency;
import main.java.persistency.Persistency;
import main.java.persistency.XmlDOMPersistency;

/**
 * This class is responsible for managing library information. 
 * It contains collections holding readers' and books' information and
 * managing data transactions with persistent storage. 
 * Plus it controls application properties.
 * 
 * @author I356406
 *
 */
/**
 * @author I356406
 *
 */
public class LibraryModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Catalog containing all the books in library.
	 */
	private static Catalog bookCatalogue;

	/**
	 * Persistence is responsible for storing and managing information. This can be
	 * any class which implements Persistency interface.
	 * 
	 * @see {@link Persistency}
	 */
	private static Persistency persistency;
	/**
	 * Contains library's reader information.
	 */
	private static Readers readers;
	/**
	 * Property file contains library settings information.
	 */
	private static Properties libraryProperties;

	private static LibraryModel libraryModel = null;

	/**
	 * Creates new LibraryController using the project setting properties in the
	 * property file.
	 * 
	 * @param propertyFileName is the property file containing the library settings.
	 * @throws                                      org.xml.sax.SAXException
	 * 
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
	 * @throws                                      org.xml.sax.SAXException
	 * @throws Exception
	 */

	public static LibraryModel getInstance()
			throws ClassNotFoundException, IOException, TransformerException, SAXException, SQLException,
			TransformerFactoryConfigurationError, ParserConfigurationException, org.xml.sax.SAXException {
		if (libraryModel == null) {
			bookCatalogue = new Catalog();
			readers = new Readers();
			initProperties(Constants.LIBRARY_PROPERTIES_FILE_PATH);
			libraryModel = new LibraryModel();
		}
		return libraryModel;
	}

	private LibraryModel() {

	}

	/**
	 * Parses libraryProperties file provided as an argument and set up library's
	 * persistence type.
	 * 
	 * @throws TransformerFactoryConfigurationError
	 * @throws SAXException
	 * @throws TransformerException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws TransformerConfigurationException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws                                      org.xml.sax.SAXException
	 */
	private static void initProperties(String propertyFileName)
			throws IOException, TransformerException, SAXException, SQLException, ClassNotFoundException,
			TransformerFactoryConfigurationError, ParserConfigurationException, org.xml.sax.SAXException {

		libraryProperties = new Properties();
		FileInputStream fileInput = new FileInputStream(propertyFileName);
		libraryProperties.load(fileInput);

		String language = libraryProperties.getProperty(Constants.LANGUAGE);

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
	 * Searches for the property with the specified key in the property file.
	 * 
	 * @param propertyKey is the key value of searched property
	 * @return
	 */
	private static String getProperty(String propertyKey) {
		return libraryProperties.getProperty(propertyKey);
	}

	/**
	 * This method searches for books by author name.
	 * @param authorName is the name of the author whom books are searched
	 * 
	 * @return set containing the books written by this author
*/
	public Set<Book> searchBooksByAuthor(String authorName){
		Set<Book> searchedBooks = bookCatalogue.searchByAuthor(authorName);
		return searchedBooks;

	}

	/**
	 * This method searches for books by title.
	 * 
	 * @param bookTitle is title which is searched
	 * 
	 * @return set containing the books with this title
	 * 
	 * @throws IOException
	 */
	public Set<Book> searchBookByTitle(String bookTitle) throws IOException {
		return bookCatalogue.searchByTitle(bookTitle);
	}

	/**
	 * This method returns set of books taken by specific reader.If the reader is
	 * not registered MissingReaderException is thrown.
	 * 
	 * @param readerName is the name of the reader who`s books are wanted
	 * @return set of books
	 * @throws MissingReaderException
	 */
	public Set<Book> getBooksOfReader(String readerName) throws MissingReaderException {

		Reader reader = readers.getReaderFromSet(readerName);

		return reader.getReaderBooks();
	}

	/**
	 * This method is used for readers registration. If the reader is not
	 * registered, new reader object is added to readers collection and database
	 * persistence.
	 * 
	 * 
	 * @return true if the registration is successful and false if the provided
	 *         readerName is already registered reader.
	 * 
	 * @return true if the reader is registered.False it is already registered.
	 * @throws Exception
	 */
	public boolean registerReader(String readerName) throws Exception {
		Reader readerToAdd = new Reader(readerName);
		boolean isSuccessfullyRegistered = readers.add(readerToAdd);
		if (isSuccessfullyRegistered) {
			persistency.addReader(readerToAdd);
			return true;
		}
		return false;
	}

	/**
	 * This method sorts library books in a specific order and returns sorted list
	 * of library books.
	 * 
	 * @param sortingOrder indicates the order.
	 * 
	 * 
	 * @throws IOException
	 * 
	 */
	public List<LibraryBook> getBooksSorted(int sortingOrder) throws IOException {
		List<LibraryBook> sortedBooksList = null;
		Set<LibraryBook> booksToSort = bookCatalogue.getLibraryBooks();
		if (sortingOrder == Constants.SORT_BY_AUTHOR_ID) {
			sortedBooksList = bookCatalogue.sortBooks(new AuthorComparator(), booksToSort);
		}
		if (sortingOrder == Constants.SORT_BY_TITLE_ID) {
			sortedBooksList = bookCatalogue.sortBooks(new BookTitleComparator(), booksToSort);
		}
		return sortedBooksList;
	}

	public void giveBookToReader(String authorName, String title, String readerName)
			throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError,
			ParserConfigurationException, SAXException, IOException, SQLException, PropertyVetoException,
			org.xml.sax.SAXException {
		Book bookToAdd = new Book(authorName, title);
		Reader reader = new Reader(readerName);
		persistency.giveBookToReader(reader, bookToAdd);
		try {
			System.out.println(readerName);
			readers.getReaderFromSet(readerName).addBook(bookToAdd);
		} catch (MissingReaderException e) {
			e.printStackTrace();
		}
		bookCatalogue.removeBook(bookToAdd);
	}

	/**
	 * This method is used for returning a book. If the reader is registered and the
	 * book is a library book, it is successfully removed from reader's book list
	 * and added to library catalog.
	 * 
	 * @throws Exception
	 * @throws TransformerFactoryConfigurationError
	 * @throws MissingReaderException
	 * @throws IOException
	 * @throws PropertyVetoException
	 * @throws SQLException
	 * @throws TransformerException 
	 * @throws TransformerConfigurationException 
	 */
	public void returnBook(String authorName, String title, String readerName)
			throws TransformerFactoryConfigurationError, MissingReaderException, SQLException, PropertyVetoException,
			IOException, TransformerConfigurationException, TransformerException {
		Book bookToReturn = new Book(authorName, title);
		persistency.returnBook(new Reader(readerName), bookToReturn);
		bookCatalogue.addBook(bookToReturn);
		readers.getReaderFromSet(readerName).returnBook(bookToReturn);

	}

	/**
	 * Adds new book to library catalog.
	 * @throws PropertyVetoException 
	 * @throws SQLException 
	 * @throws TransformerFactoryConfigurationError 
	 * @throws TransformerException 
	 * @throws IOException 
	 * @throws TransformerConfigurationException 
	 * 
	 * @throws Exception
	 */
	public void addBook(Book newBook) throws TransformerConfigurationException, IOException, TransformerException, TransformerFactoryConfigurationError, SQLException, PropertyVetoException {
		persistency.addBook(newBook);
		bookCatalogue.addBook(newBook);
	}

	/**
	 * Returns set containing the available books.
	 * 
	 * @return
	 */
	public Set<LibraryBook> getAvailableBooks() {
		return bookCatalogue.getAvailableBooks();
	}

	/**
	 * This method checks if reader with name @param readerName is registered. If
	 * reader with this name exists it is returner.Otherwise MissingReaderException
	 * is thrown.
	 * 
	 * @throws MissingReaderException
	 */
	public Reader getSpecificReader(String readerName) throws MissingReaderException {
		return readers.getReaderFromSet(readerName);
	}

	public boolean checkForBook(Book toCheck) {
		if (bookCatalogue.checkForBook(toCheck) != null) {
			return true;
		}
		return false;
	}

	public Set<Reader> getReaders() {
		return readers.getReaders();
	}

	public boolean hasAnyAvailableBooks() {
		if (bookCatalogue.hasAvailableBooks()) {
			return true;
		}
		return false;
	}
};
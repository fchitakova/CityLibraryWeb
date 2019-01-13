package main.java.persistency;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import main.java.Book;
import main.java.ConnectionPool;
import main.java.Catalog;
import main.java.Constants;
import main.java.LibraryBook;
import main.java.LibraryModel;
import main.java.Reader;
import main.java.Readers;
import main.java.exceptions.MissingReaderException;

/**
 * This class load ,store and manage CityLibrary information in a database. SQL
 * is used for communicating with the database. DBPersistency implements
 * Persistency interface and provides implementation for storing and loading
 * library data and other operations like adding and removing library entities
 * for performing any necessary operation(giving book to reader,returning taken
 * book etc.).
 * 
 * @author I356406
 *
 */
public class DBPersistency implements Persistency {

	private static final int NOT_EXISTING_BOOK = -1;
	private static final String DELETE = "delete ";
	private static final String SET = " set ";
	private static final String UPDATE = "update ";
	private static final String AND = " and ";
	private static final String VALUES = " values";
	private static final String INSERT_INTO = "insert into ";
	private static final String SELECT_ALL = "select * ";
	private static final String NOT_IN = " not in ";
	private static final String DISTINCT = " distinct ";
	private static final String RELATIONSHIP_TABLE = "RELATIONSHIP_TABLE";
	private static final String READERS_TABLE = "READERS_TABLE";
	private static final String BOOKS_TABLE = "BOOKS_TABLE";
	private static final String READER_NAME_COL = "name";
	private static final String BOOK_TITLE_COL = "title";
	private static final String BOOK_AUTHOR_COL = "author";
	private static final String COUNT_COL = "count";
	private static final String COUNT_ALL = " count (*) ";
	private static final String AS = " as ";
	private static final String BOOK_COPIES_COL = "copies";
	private static final String SELECT = "select ";
	private static final String FROM = " from ";
	private static final String WHERE = " where ";
	private static final int INITIAL_CONNECTIONS_SIZE = 10;
	private String takenBooksTable;
	private String readersTable;
	private String booksTable;
	ConnectionPool connectionPool;

	/**
	 * Creates connection to a database using the provided server information in
	 * properties file and the username and password passed as arguments.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public DBPersistency(char[] username, char[] password) throws SQLException, IOException, ClassNotFoundException {

		connectionPool = new ConnectionPool(INITIAL_CONNECTIONS_SIZE, true, username, password);
		Properties databaseProperties = new Properties();
		FileInputStream fileInput = new FileInputStream(Constants.DATABASE_PROPERTIES_FILEPATH);
		databaseProperties.load(fileInput);
		takenBooksTable = databaseProperties.getProperty(RELATIONSHIP_TABLE);
		readersTable = databaseProperties.getProperty(READERS_TABLE);
		booksTable = databaseProperties.getProperty(BOOKS_TABLE);
	}

	/**
	 * Loads all readers from the database.
	 * 
	 * @throws IOException
	 * @throws PropertyVetoException
	 * @throws SQLException
	 * @throws Exception
	 */
	@Override
	public Readers loadReaders() throws SQLException, IOException {
		Readers readersToLoad = new Readers();

		loadReadersWithTakenBooks(readersToLoad);

		loadReadersFromReadersTable(readersToLoad);
		return readersToLoad;
	}

	/**
	 * Loads registered readers which have not any taken books.
	 * 
	 * @param readersToLoad
	 * @throws SQLException
	 * @throws IOException
	 * @throws PropertyVetoException
	 */
	private void loadReadersFromReadersTable(Readers readersToLoad) throws SQLException, IOException {
		StringBuilder selectReadersWithNoTakenBooks = new StringBuilder(
				SELECT + READER_NAME_COL + FROM + readersTable + WHERE + READER_NAME_COL + NOT_IN + '(' + SELECT
						+ DISTINCT + READER_NAME_COL + FROM + takenBooksTable + ");");
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet queryResult = null;
		try {
			connection = connectionPool.getConnection();
			statement = connection.prepareStatement(selectReadersWithNoTakenBooks.toString());
			queryResult = statement.executeQuery();
			while (queryResult.next()) {
				String readerName = queryResult.getString(READER_NAME_COL);
				Reader newReader = new Reader(readerName);
				readersToLoad.add(newReader);
			}
		} finally {
			if (connection != null) {
				connectionPool.free(connection);
			}
			if (statement != null) {
				statement.close();
			}
			if (queryResult != null) {
				queryResult.close();
			}
		}
	}

	/**
	 * Loads readers and their books from the table which holds the readers which
	 * have taken books.
	 * 
	 * @param readersToLoad
	 * @throws SQLException
	 * @throws IOException
	 * @throws PropertyVetoException
	 */
	private void loadReadersWithTakenBooks(Readers readersToLoad) throws IOException, SQLException {
		StringBuilder selectReadersWithTakenBooks = new StringBuilder(
				SELECT + READER_NAME_COL + ',' + BOOK_TITLE_COL + ',' + BOOK_AUTHOR_COL + FROM + takenBooksTable + ';');

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet queryResult = null;
		try {
			connection = connectionPool.getConnection();
			statement = connection.prepareStatement(selectReadersWithTakenBooks.toString());
			queryResult = statement.executeQuery();
			while (queryResult.next()) {
				String name = queryResult.getString(READER_NAME_COL);
				String title = queryResult.getString(BOOK_TITLE_COL);
				String author = queryResult.getString(BOOK_AUTHOR_COL);
				Book bookToAdd = new Book(author, title);
				Reader searchedReader=null;
				try {
					searchedReader = readersToLoad.getReaderFromSet(name);
				} catch (MissingReaderException e) {
					searchedReader=new Reader(name);
					readersToLoad.add(searchedReader);
				} finally {
					searchedReader.addBook(bookToAdd);
				}
			}

		} finally {
			if (connection != null) {
				connectionPool.free(connection);
			}
			if (statement != null) {
				statement.close();
			}
			if (queryResult != null) {
				queryResult.close();
			}
		}
	}

	/**
	 * Loads books from the database's books table.
	 * 
	 * @throws SQLException
	 */
	@Override
	public Catalog loadBookCatalogue() throws SQLException {
		Catalog toLoad = new Catalog();
		StringBuilder selectAllBooks = new StringBuilder(SELECT_ALL + FROM + booksTable);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet queryResult = null;
		try {
			connection = connectionPool.getConnection();
			statement = connection.prepareStatement(selectAllBooks.toString());
			queryResult = statement.executeQuery();
			while (queryResult.next()) {
				String title = queryResult.getString(BOOK_TITLE_COL);
				String author = queryResult.getString(BOOK_AUTHOR_COL);
				Integer copies = queryResult.getInt(BOOK_COPIES_COL);
				toLoad.addLibraryBook(new LibraryBook(author, title, copies));
			}
		} finally {
			if (connection != null) {
				connectionPool.free(connection);
			}
			if (statement != null) {
				statement.close();
			}
			if (queryResult != null) {
				queryResult.close();
			}
		}
		return toLoad;
	}

	/**
	 * This method registers new reader if he/she is not already registered.
	 * 
	 * @throws IOException
	 * @throws PropertyVetoException
	 * @throws SQLException
	 */
	@Override
	public void addReader(Reader reader) throws SQLException, PropertyVetoException, IOException {
		if (checkIfReaderExists(reader)) {
			return;
		}
		StringBuilder insertQuery = new StringBuilder(
				INSERT_INTO + readersTable + VALUES + "('" + reader.getName() + "');");
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = connectionPool.getConnection();
			statement = connection.prepareStatement(insertQuery.toString());
			statement.execute();
		} finally {
			if (connection != null) {
				connectionPool.free(connection);
			}
			if (statement != null) {
				statement.close();
			}
		}

	}

	/**
	 * Checks if @param reader is registered.
	 * 
	 * @param reader
	 * @return true if the reader is registered and false if not.
	 * @throws SQLException
	 * @throws IOException
	 * @throws PropertyVetoException
	 */
	private boolean checkIfReaderExists(Reader reader) throws SQLException, PropertyVetoException, IOException {
		StringBuilder checkIfExistsQuery = new StringBuilder(SELECT + COUNT_ALL + AS + COUNT_COL + FROM + readersTable
				+ WHERE + READER_NAME_COL + "='" + reader.getName() + "';");
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int resCount = 0;
		try {
			connection = connectionPool.getConnection();
			statement = connection.prepareStatement(checkIfExistsQuery.toString());
			result = statement.executeQuery();
			result.next();
			resCount = result.getInt(COUNT_COL);
		} finally {
			if (connection != null) {
				connectionPool.free(connection);
			}
			if (statement != null) {
				statement.close();
			}
			if (result != null) {
				result.close();
			}
		}
		return resCount > 0;
	}

	/**
	 * Adds @param book to the database's book table.If the book is already there
	 * its copies number is updated.
	 * 
	 * @throws SQLException
	 * @throws IOException
	 * @throws PropertyVetoException
	 */
	@Override
	public void addBook(Book book) throws SQLException, PropertyVetoException, IOException {
		if (retrieveBooksCopies(book) != NOT_EXISTING_BOOK) {
			updateBookCopies(book, Constants.STANDARD_COPY_ADD_NUM);
		}
		StringBuilder insertQuery = new StringBuilder(INSERT_INTO + booksTable + VALUES + "('" + book.getTitle() + "','"
				+ book.getAuthor() + "'," + Constants.STANDARD_COPY_ADD_NUM + ");");

		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = connectionPool.getConnection();
			statement = connection.prepareStatement(insertQuery.toString());
			statement.execute();
		} finally {
			if (connection != null) {
				connectionPool.free(connection);
			}
			if (statement != null) {
				statement.close();
			}
		}
	}

	/**
	 * Updates the copies number of @param book if the book is in the database. The
	 * method does not allow to set book copies to be negative number.
	 * 
	 * @param book
	 * @throws SQLException
	 * @throws IOException
	 * @throws PropertyVetoException
	 */
	private void updateBookCopies(Book book, int changeAmount) throws SQLException, PropertyVetoException, IOException {
		int oldCopiesNumber = retrieveBooksCopies(book);
		int newCopiesNumber = oldCopiesNumber + changeAmount;
		if (oldCopiesNumber == NOT_EXISTING_BOOK || newCopiesNumber < 0) {
			return;
		}
		StringBuilder query = new StringBuilder(
				UPDATE + booksTable + SET + BOOK_COPIES_COL + "=" + newCopiesNumber + WHERE + BOOK_TITLE_COL + "='"
						+ book.getTitle() + "'" + AND + BOOK_AUTHOR_COL + "='" + book.getAuthor() + "';");
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = connectionPool.getConnection();
			statement = connection.prepareStatement(query.toString());
			statement.execute();
		} finally {
			if (connection != null) {
				connectionPool.free(connection);
			}
			if (statement != null) {
				statement.close();
			}
		}
	}

	/**
	 * Checks if @param book is in the database.If the book is there returns its
	 * copies number. If the book is not in the database NOT_EXISTING_BOOK constant
	 * is returned.
	 * 
	 * @throws SQLException
	 * @throws IOException
	 * @throws PropertyVetoException
	 */
	private int retrieveBooksCopies(Book book) throws SQLException, PropertyVetoException, IOException {
		StringBuilder query = new StringBuilder(
				SELECT + BOOK_COPIES_COL + AS + BOOK_COPIES_COL + FROM + booksTable + WHERE + BOOK_TITLE_COL + "='"
						+ book.getTitle() + "'" + AND + BOOK_AUTHOR_COL + "='" + book.getAuthor() + "';");
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int copies = NOT_EXISTING_BOOK;
		try {
			connection = connectionPool.getConnection();
			statement = connection.prepareStatement(query.toString());
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				copies = resultSet.getInt(BOOK_COPIES_COL);
			}
		} finally {
			if (connection != null) {
				connectionPool.free(connection);
			}
			if (statement != null) {
				statement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
		}
		return copies;
	}

	/**
	 * Updates the copy number of the returned book and deletes it from the
	 * returning reader's book list.
	 * 
	 * @throws IOException
	 * @throws PropertyVetoException
	 * @throws SQLException
	 */
	@Override
	public void returnBook(Reader reader, Book book) throws SQLException, PropertyVetoException, IOException {
		updateBookCopies(book, Constants.STANDARD_COPY_ADD_NUM);
		StringBuilder deleteBookQuery = new StringBuilder(DELETE + FROM + takenBooksTable + WHERE + READER_NAME_COL
				+ "='" + reader.getName() + "'" + AND + BOOK_TITLE_COL + "='" + book.getTitle() + "'" + AND
				+ BOOK_AUTHOR_COL + "='" + book.getAuthor() + "';");
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = connectionPool.getConnection();
			statement = connection.prepareStatement(deleteBookQuery.toString());
			statement.execute();
		} finally {
			if (connection != null) {
				connectionPool.free(connection);
			}
			if (statement != null) {
				statement.close();
			}
		}
	}

	/**
	 * This method checks if the reader exists and the book is available and if this
	 * reader has not already taken that book. If all conditions are true @param
	 * book is added to @param reader's booklist and book's available copies are
	 * decremented.
	 * 
	 * @throws SQLException
	 * @throws IOException
	 * @throws PropertyVetoException
	 */
	@Override
	public void giveBookToReader(Reader reader, Book book) throws SQLException, PropertyVetoException, IOException {
		boolean readerExists = checkIfReaderExists(reader);
		boolean availableBook = retrieveBooksCopies(book) > 0;
		boolean notAlreadyTaken = !bookAlreadyTakenFromReader(reader, book);
		Connection connection = null;
		PreparedStatement statement = null;
		if (notAlreadyTaken && readerExists && availableBook) {
			updateBookCopies(book, Constants.STANDARD_COPY_EXTRACT_NUM);
			StringBuilder addToReadersBookListQuery = new StringBuilder(INSERT_INTO + takenBooksTable + VALUES + "('"
					+ reader.getName() + "','" + book.getTitle() + "','" + book.getAuthor() + "');");
			try {
				connection = connectionPool.getConnection();
				statement = connection.prepareStatement(addToReadersBookListQuery.toString());
				statement.execute();
			} finally {
				if (connection != null) {
					connectionPool.free(connection);
				}
				if (statement != null) {
					statement.close();
				}
			}

		}
	}

	/**
	 * Checks if @param reader has already taken @param book.
	 * 
	 * @return true if the book is already in reader's booklist and false otherwise.
	 * @throws SQLException
	 * @throws IOException
	 * @throws PropertyVetoException
	 */
	private boolean bookAlreadyTakenFromReader(Reader reader, Book book)
			throws SQLException, PropertyVetoException, IOException {
		StringBuilder checkIfBookIsAlreadyTaken = new StringBuilder(SELECT + COUNT_ALL + AS + COUNT_COL + FROM
				+ takenBooksTable + WHERE + READER_NAME_COL + "='" + reader.getName() + "'" + AND + BOOK_TITLE_COL
				+ "='" + book.getTitle() + "'" + AND + BOOK_AUTHOR_COL + "='" + book.getAuthor() + "';");
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		int resCount = 0;
		try {
			connection = connectionPool.getConnection();
			;
			statement = connection.prepareStatement(checkIfBookIsAlreadyTaken.toString());
			result = statement.executeQuery();
			result.next();
			resCount = result.getInt(COUNT_COL);
		} finally {
			if (connection != null) {
				connectionPool.free(connection);
			}
			if (statement != null) {
				statement.close();
			}
			if (result != null) {
				result.close();
			}
		}

		return resCount > 0;
	}

}

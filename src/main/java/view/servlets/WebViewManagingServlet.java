package main.java.view.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jdk.internal.org.xml.sax.SAXException;
import main.java.Book;
import main.java.Constants;
import main.java.LibraryModel;
import main.java.Validator;
import main.java.exceptions.MissingReaderException;

public class WebViewManagingServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String HAS_TAKEN_BOOKS = "hasAnyTakenBooks";
	protected static final String VALID_READER_NAME_JSON_PROPERTY = "validReaderName";
	protected static final String REGISTERED_READER_JSON_PPROPERTY = "registeredReader";
	protected static final String READER_NAME_REQUEST_PARAMETER = "readerName";
	protected static final String CHOSEN_BOOK_REQUEST_PARAMETER = "chosenBook";
	protected static final String BOOK_TITLE_REQUEST_PARAMETER = "title";
	protected static final String BOOK_AUTHOR_REQUEST_PARAMETER = "author";
	protected static final String CONTENT_TYPE_JSON = "application/json";
	protected static final String SORTING_ORDER_REQ_PARAM = "sortingOrder";
	protected static final String APPLY_BY_TITLE_JSON_STR = "byTitle";
	protected static final String SHOW_ALL_BOOKS_URL = "/allBooks";
	protected static final String SHOW_AVAILABLE_BOOKS_URL = "availableBooks";
	protected static final String GIVE_BOOK_TO_READER_URL = "giveBook";
	protected static final String GIVE_BOOK_JSP_FILE_NAME = "giveBook.jsp";
	protected static final String RETURN_BOOK_JSP_FILE_NAME = "returnBook.jsp";
	protected static final String SHOW_ALL_BOOKS_JSP_FILE_NAME = "showBooks.jsp";
	protected static final String SEE_READERS_JSP_FILE_NAME = "seeReaders.jsp";
	protected static final String SHOW_AVAILABLE_BOOKS_JSP_FILE_NAME = "availableBooks.jsp";
	protected static final String SHOW_READER_BOOKS_JSP_FILE_NAME = "showReaderBooks.jsp";
	protected static final String SEARCH_BOOKS_JSP_FILE_NAME="searchBooks.jsp";
	protected static final String ADD_BOOK_JSP_FILE_NAME = "addBook.jsp";
	protected static final String REGISTER_READER_JSP_FILE_NAME="registerReader.jsp";
	protected static final String EMPTY_JSON_ARRAY = "[]";
	protected LibraryModel libraryDataController;

	protected WebViewManagingServlet() throws ClassNotFoundException, IOException, TransformerException, SAXException,
			SQLException, TransformerFactoryConfigurationError, ParserConfigurationException, org.xml.sax.SAXException {
		super();
		libraryDataController = LibraryModel.getInstance();
	}

	protected String getReaderValidityAsString(String readerName) {
		boolean validReaderName = Validator.checkInputTextValidity(readerName);
		boolean registeredReader = true;
		boolean hasAnyTakenBooks = false;

		Set<Book> readerBooks = null;
		if (validReaderName) {
			try {
				readerBooks = libraryDataController.getSpecificReader(readerName).getReaderBooks();
			} catch (MissingReaderException e) {
				registeredReader = false;
			} finally {
				hasAnyTakenBooks = (readerBooks != null);
			}

		}
		JsonObject responseJson = createReaderValidityJsonObject(validReaderName, registeredReader, hasAnyTakenBooks);
		return responseJson.toString();
	}

	private JsonObject createReaderValidityJsonObject(boolean validReaderName, boolean registeredReader,
			boolean hasAnyTakenBooks) {
		JsonObject responseJson = new JsonObject();
		responseJson.addProperty(VALID_READER_NAME_JSON_PROPERTY, validReaderName);
		responseJson.addProperty(REGISTERED_READER_JSON_PPROPERTY, registeredReader);
		responseJson.addProperty(HAS_TAKEN_BOOKS, hasAnyTakenBooks);
		return responseJson;
	}

	protected void sendReadersBooks(HttpServletResponse response, String readerName) throws IOException {
		Set<Book> readerBooks = null;
		String jsonResponse = null;
		try {
			readerBooks = libraryDataController.getBooksOfReader(readerName);
			jsonResponse = new Gson().toJson(readerBooks);
		} catch (MissingReaderException e) {
			jsonResponse = EMPTY_JSON_ARRAY;
		} finally {
			sendJsonResponse(response, jsonResponse);
		}
	}

	protected void sendJsonResponse(HttpServletResponse response, String json) throws IOException {
		response.setContentType(CONTENT_TYPE_JSON);
		response.setCharacterEncoding(Constants.UTF_8_ENCODING);
		response.getWriter().write(json);
	}

}

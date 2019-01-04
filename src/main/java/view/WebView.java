package main.java.view;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jdk.internal.org.xml.sax.SAXException;
import main.java.Constants;
import main.java.LibraryBook;
import main.java.LibraryModel;
import main.java.Reader;
import main.java.exceptions.BookException;

/**
 * Servlet implementation class WebView
 */
@WebServlet(urlPatterns = { WebView.SHOW_ALL_BOOKS_URL, WebView.SHOW_AVAILABLE_BOOKS_URL,
		WebView.GIVE_BOOK_TO_READER_URL })
public class WebView extends HttpServlet {
	private static final String IS_VALID_READER_NAME_JSON_PROPERTY = "isValidReaderName";
	private static final String IS_REGISTERED_READER_JSON_PROPERTY = "isRegisteredReader";
	private static final String READER_NAME_REQUEST_PARAMETER = "readerName";
	private static final String CHECK_READER_NAME_REQUEST = "checkReaderName";
	private static final String GET_AVAILALBLE_BOOKS_REQUEST = "getAvailableBooks";
	private static final String CONTENT_TYPE_JSON = "application/json";
	private static final String SORTING_ORDER_REQ_PARAM = "sortingOrder";
	private static final String SORT_BY_TITLE_JSON_STR = "\"byTitle\"";
	public static final String SHOW_ALL_BOOKS_URL = "/allBooks";
	public static final String SHOW_AVAILABLE_BOOKS_URL = "/availableBooks";
	public static final String GIVE_BOOK_TO_READER_URL = "/giveBook";
	private static final long serialVersionUID = 1L;

	private LibraryModel libraryDataController;

	/**
	 * @throws                                      org.xml.sax.SAXException
	 * @throws PropertyVetoException
	 * @throws SQLException
	 * @throws SAXException
	 * @throws TransformerException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws TransformerFactoryConfigurationError
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws TransformerConfigurationException
	 * @see HttpServlet#HttpServlet()
	 */
	public WebView() throws TransformerConfigurationException, InstantiationException, IllegalAccessException,
			ClassNotFoundException, TransformerFactoryConfigurationError, ParserConfigurationException, IOException,
			TransformerException, SAXException, SQLException, PropertyVetoException, org.xml.sax.SAXException {
		super();
		libraryDataController = new LibraryModel(Constants.LIBRARY_PROPERTIES_FILE_PATH);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getRequestURI().equals(request.getContextPath() + SHOW_ALL_BOOKS_URL)) {
			sendAllBooksToClient(request, response);
		}

		if (request.getRequestURI().equals(request.getContextPath() + SHOW_AVAILABLE_BOOKS_URL)) {
			sendAvailableBooksToClient(response);
		}
		if (request.getRequestURI().equals(request.getContextPath() + GIVE_BOOK_TO_READER_URL)) {
            String getAvailableBooksRequest=request.getParameter(GET_AVAILALBLE_BOOKS_REQUEST);
			if (getAvailableBooksRequest != null) {
				sendAvailableBooksToClient(response);
			}
			String checkReaderNameRequest = request.getParameter(CHECK_READER_NAME_REQUEST);
			if (checkReaderNameRequest != null) {
				String readerName=request.getParameter(READER_NAME_REQUEST_PARAMETER).replaceAll("\\s+", " ").trim();
				boolean isValidReaderName = LibraryModel.checkInputTextValidity(readerName);
				Reader reader = libraryDataController.getSpecificReader(readerName);
				boolean isRegisteredReader = false;
				if (reader != null) {
					isRegisteredReader = true;
				}
				JsonObject jsonResponseObject = new JsonObject();
				jsonResponseObject.addProperty(IS_VALID_READER_NAME_JSON_PROPERTY, isValidReaderName);
				jsonResponseObject.addProperty(IS_REGISTERED_READER_JSON_PROPERTY, isRegisteredReader);
				String json = new Gson().toJson(jsonResponseObject);
				sendJsonResponse(response, json);
			}
		}

	}

	private void sendAllBooksToClient(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String chosenSortingOrder = request.getParameter(SORTING_ORDER_REQ_PARAM);

		int sortingOrderChoice = Constants.INVALID_CHOICE_NUMBER;
		List<LibraryBook> sortedBooks = null;

		if (chosenSortingOrder.equals(SORT_BY_TITLE_JSON_STR)) {
			sortingOrderChoice = Constants.SORT_BY_TITLE_ID;

		} else {
			sortingOrderChoice = Constants.SORT_BY_AUTHOR_ID;
		}

		try {
			sortedBooks = libraryDataController.getBooksSorted(sortingOrderChoice);
		} catch (BookException e) {

		}

		String json = new Gson().toJson(sortedBooks);
		sendJsonResponse(response, json);
	}

	private void sendJsonResponse(HttpServletResponse response, String json) throws IOException {
		response.setContentType(CONTENT_TYPE_JSON);
		response.setCharacterEncoding(Constants.UTF_8_ENCODING);
		response.getWriter().write(json);
	}

	private void sendAvailableBooksToClient(HttpServletResponse response) throws IOException {
		Set<LibraryBook> availableBooks = libraryDataController.getAvailableBooks();
		String json = new Gson().toJson(availableBooks);
		sendJsonResponse(response, json);
	}

}

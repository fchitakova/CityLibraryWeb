package main.java.view;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import jdk.internal.org.xml.sax.SAXException;
import main.java.Constants;
import main.java.LibraryModel;

public class WebViewManagingServlet extends HttpServlet {
	protected static final String IS_VALID_READER_NAME_JSON_PROPERTY = "isValidReaderName";
	protected static final String IS_REGISTERED_READER_JSON_PROPERTY = "isRegisteredReader";
	protected static final String READER_NAME_REQUEST_PARAMETER = "readerName";
	protected static final String CHECK_READER_NAME_REQUEST = "checkReaderName";
	protected static final String GET_AVAILALBLE_BOOKS_REQUEST = "getAvailableBooks";
	protected static final String CONTENT_TYPE_JSON = "application/json";
	protected static final String SORTING_ORDER_REQ_PARAM = "sortingOrder";
	protected static final String SORT_BY_TITLE_JSON_STR = "\"byTitle\"";
	public static final String SHOW_ALL_BOOKS_URL = "/allBooks";
	public static final String SHOW_AVAILABLE_BOOKS_URL = "/availableBooks";
	public static final String GIVE_BOOK_TO_READER_URL = "/giveBook";
	protected LibraryModel libraryDataController;

	protected WebViewManagingServlet() throws ClassNotFoundException, IOException, TransformerException, SAXException,
			SQLException, TransformerFactoryConfigurationError, ParserConfigurationException, org.xml.sax.SAXException {
		super();
		libraryDataController = LibraryModel.getInstance();
	}
	
	
	protected void sendJsonResponse(HttpServletResponse response, String json) throws IOException {
		response.setContentType(CONTENT_TYPE_JSON);
		response.setCharacterEncoding(Constants.UTF_8_ENCODING);
		response.getWriter().write(json);
	}
}

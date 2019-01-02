package main.java.view;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
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

import jdk.internal.org.xml.sax.SAXException;
import main.java.Constants;
import main.java.LibraryBook;
import main.java.LibraryModel;
import main.java.exceptions.BookException;

/**
 * Servlet implementation class WebView
 */
@WebServlet(urlPatterns = { WebView.SHOW_ALL_BOOKS_URL, WebView.SHOW_AVAILABLE_BOOKS_URL })
public class WebView extends HttpServlet {
	private static final String SORTING_ORDER_REQ_PARAM = "sortingOrder";
	private static final String SORT_BY_TITLE_JSON_STR = "\"byTitle\"";
	public static final String SHOW_ALL_BOOKS_URL = "/allBooks";
	public static final String SHOW_AVAILABLE_BOOKS_URL = "/availableBooks";
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
			printBooks(request, response);
		}

		if (request.getRequestURI().equals(request.getContextPath() + SHOW_AVAILABLE_BOOKS_URL)) {
            Set<LibraryBook>availableBooks=libraryDataController.getAvailableBooks();
            String json = new Gson().toJson(availableBooks);
    		response.setContentType("application/json");
    		response.setCharacterEncoding(Constants.UTF_8_ENCODING);
    		response.getWriter().write(json);
		}

	}

	private void printBooks(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sortingOrderRequestParam = request.getParameter(SORTING_ORDER_REQ_PARAM);

		int sortingOrderChoice = Constants.INVALID_CHOICE_NUMBER;
		List<LibraryBook> sortedBooks = null;

		if (sortingOrderRequestParam.equals(SORT_BY_TITLE_JSON_STR)) {
			sortingOrderChoice = Constants.SORT_BY_TITLE_ID;

		} else {
			sortingOrderChoice = Constants.SORT_BY_AUTHOR_ID;
		}

		try {
			sortedBooks = libraryDataController.getBooksSorted(sortingOrderChoice);
		} catch (NullPointerException | BookException e) {

		}

		String json = new Gson().toJson(sortedBooks);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
}

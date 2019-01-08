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

@WebServlet(urlPatterns = { ShowAllBooksServlet.SHOW_ALL_BOOKS_URL })

public class ShowAllBooksServlet extends WebViewManagingServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @throws                                      org.xml.sax.SAXException
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
	public ShowAllBooksServlet() throws ClassNotFoundException, IOException, TransformerException, SAXException,
			SQLException, TransformerFactoryConfigurationError, ParserConfigurationException, org.xml.sax.SAXException {
		super();
	}

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

		sendAllBooksToClient(request, response);
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



}

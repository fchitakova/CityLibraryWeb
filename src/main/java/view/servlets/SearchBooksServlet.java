package main.java.view.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import com.google.gson.Gson;

import jdk.internal.org.xml.sax.SAXException;
import main.java.Book;

@WebServlet(urlPatterns = {"/searchBooks"})
public class SearchBooksServlet extends WebViewManagingServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SearchBooksServlet() throws ClassNotFoundException, IOException, TransformerException, SAXException,
			SQLException, TransformerFactoryConfigurationError, ParserConfigurationException, org.xml.sax.SAXException {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(SEARCH_BOOKS_JSP_FILE_NAME);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String searchType = request.getParameter("searchType");
		String searchText = request.getParameter("searchText");
		System.out.println("searchType"+searchType);
		System.out.println("searchText:"+searchText);;
		Set<Book> searchedBooks = getSearchedBooks(searchType, searchText);

		for(Book b:searchedBooks) {
			System.out.println(b.toString());
		}
		String json = null;
		if (searchedBooks.isEmpty()) {
			json = EMPTY_JSON_ARRAY;
		} else {
			json = new Gson().toJson(searchedBooks);
		}
		sendJsonResponse(response, json);

	}

	private Set<Book> getSearchedBooks(String searchType, String searchText) throws IOException {

		if (searchType.equals(APPLY_BY_TITLE_JSON_STR)) {
			return libraryDataController.searchBookByTitle(searchText);
		}

		return libraryDataController.searchBooksByAuthor(searchText);
	}
}

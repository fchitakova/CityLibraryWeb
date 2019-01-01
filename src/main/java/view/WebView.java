package main.java.view;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
@WebServlet(urlPatterns = { "/allBooks" })
public class WebView extends HttpServlet {
	private static final String SHOW_ALL_BOOKS_URL = "/allBooks";
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
		libraryDataController = new LibraryModel(
				"C:\\Users\\MyPC\\Desktop\\CityLibraryWeb\\WebContent\\WEB-INF\\lib\\resourses\\library.properties");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getRequestURI().equals(request.getContextPath() + SHOW_ALL_BOOKS_URL)) {
			System.out.println(request.getParameter("sortingOrder"));

			List<LibraryBook> sortedBooks = null;
			
			//if()
			
			try {
				sortedBooks = libraryDataController.getBooksSorted(Constants.SORT_BY_TITLE_ID);
			} catch (NullPointerException | BookException e) {

			}
			
			String json = new Gson().toJson(sortedBooks);
			System.out.println(json);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		}

	}
}

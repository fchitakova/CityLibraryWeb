package main.java.view.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import com.google.gson.Gson;

import jdk.internal.org.xml.sax.SAXException;
import main.java.LibraryBook;
import main.java.view.WebViewManagingServlet;

@WebServlet(urlPatterns = { WebViewManagingServlet.SHOW_AVAILABLE_BOOKS_URL})
public class ShowAvaialbleBooksServlet extends WebViewManagingServlet {

	private static final long serialVersionUID = 1L;

	public ShowAvaialbleBooksServlet()
			throws ClassNotFoundException, IOException, TransformerException, SAXException, SQLException,
			TransformerFactoryConfigurationError, ParserConfigurationException, org.xml.sax.SAXException {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		sendAvailableBooksToClient(response);
	}

	private void sendAvailableBooksToClient(HttpServletResponse response) throws IOException {
		Set<LibraryBook> availableBooks = libraryDataController.getAvailableBooks();
		String json = new Gson().toJson(availableBooks);
		sendJsonResponse(response, json);

	}

}
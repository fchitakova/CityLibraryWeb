package main.java.view.servlets;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import com.google.gson.JsonObject;

import jdk.internal.org.xml.sax.SAXException;
import main.java.Book;
import main.java.LibraryModel;
import main.java.Validator;

@WebServlet("/addBook")
public class AddBook extends WebViewManagingServlet {
	private static final long serialVersionUID = 1L;

	public AddBook() throws ClassNotFoundException, IOException, TransformerException, SAXException, SQLException,
			TransformerFactoryConfigurationError, ParserConfigurationException, org.xml.sax.SAXException {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(ADD_BOOK_JSP_FILE_NAME);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String title = request.getParameter(BOOK_TITLE_REQUEST_PARAMETER);
		String author = request.getParameter(BOOK_AUTHOR_REQUEST_PARAMETER);
		Boolean responseValue = Validator.checkInputTextValidity(title)
				&& Validator.checkInputTextValidity(author);

		if (responseValue == true) {
			try {
				libraryDataController.addBook(new Book(author, title));
			} catch (IOException | TransformerException | TransformerFactoryConfigurationError | SQLException
					| PropertyVetoException e) {
				e.printStackTrace();
			}
		}
		JsonObject responseJson = new JsonObject();
		responseJson.addProperty("validBookInfo", responseValue);
		sendJsonResponse(response, responseValue.toString());
	}

}

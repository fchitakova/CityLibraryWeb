package main.java.view.servlets;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.RequestDispatcher;
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
import main.java.Book;
import main.java.LibraryBook;
import main.java.LibraryModel;
import main.java.exceptions.MissingReaderException;
import main.java.exceptions.ReaderException;

@WebServlet("/returnBook")
public class ReturnBookServlet extends WebViewManagingServlet {

	private String readerName;
	private static final long serialVersionUID = 1L;

	/**
	 * @throws                                      org.xml.sax.SAXException
	 * @throws ParserConfigurationException
	 * @throws TransformerFactoryConfigurationError
	 * @throws SQLException
	 * @throws SAXException
	 * @throws TransformerException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @see HttpServlet#HttpServlet()
	 */
	public ReturnBookServlet() throws ClassNotFoundException, IOException, TransformerException, SAXException,
			SQLException, TransformerFactoryConfigurationError, ParserConfigurationException, org.xml.sax.SAXException {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(RETURN_BOOK_JSP_FILE_NAME);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestReaderName = request.getParameter(READER_NAME_REQUEST_PARAMETER);
		String chosenBookRequest = request.getParameter(CHOSEN_BOOK_REQUEST_PARAMETER);
		if (requestReaderName != null) {
			readerName = requestReaderName;
			String jsonResponse = getReaderValidityAsString(readerName);
			sendJsonResponse(response, jsonResponse);
			return;
		}
		if (chosenBookRequest != null) {
			returnBook(readerName, chosenBookRequest);
			return;
		} else {
			sendReadersBooks(response, readerName);
		}
	}

	private void returnBook(String readerName, String chosenBookRequest) {
		String[] authorAndTitle = chosenBookRequest.replaceAll("\"", "").split(" ");

		try {
			libraryDataController.returnBook(authorAndTitle[0], authorAndTitle[1], readerName);
		} catch (TransformerFactoryConfigurationError | MissingReaderException | SQLException | PropertyVetoException
				| IOException | TransformerException e) {
			e.printStackTrace();
		}
	}

}

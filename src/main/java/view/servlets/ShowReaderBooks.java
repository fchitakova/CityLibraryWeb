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
import com.google.gson.JsonObject;

import jdk.internal.org.xml.sax.SAXException;
import main.java.Book;
import main.java.LibraryModel;
import main.java.exceptions.MissingReaderException;

@WebServlet("/showReaderBooks")
public class ShowReaderBooks extends WebViewManagingServlet {

	private static final long serialVersionUID = 1L;
	private String readerName;

	public ShowReaderBooks() throws ClassNotFoundException, IOException, TransformerException, SAXException,
			SQLException, TransformerFactoryConfigurationError, ParserConfigurationException, org.xml.sax.SAXException {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("showReaderBooks.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestReaderName = request.getParameter(READER_NAME_REQUEST_PARAMETER);
		if (requestReaderName != null) {
			readerName = requestReaderName;
			String jsonResponse = getReaderValidityAsString(readerName);
			sendJsonResponse(response, jsonResponse);
			return;
		} else {
			sendReadersBooks(response, readerName);
		}

	}

}

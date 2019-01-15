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
import main.java.LibraryModel;
import main.java.exceptions.MissingReaderException;

@WebServlet(urlPatterns = { "/giveBook" })
public class GiveBookToReaderServlet extends WebViewManagingServlet {
	private  String readerName;

	public GiveBookToReaderServlet() throws ClassNotFoundException, IOException, TransformerException, SAXException,
			SQLException, TransformerFactoryConfigurationError, ParserConfigurationException, org.xml.sax.SAXException {
		super();

	}

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(GIVE_BOOK_JSP_FILE_NAME);
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
			giveBookToReader(readerName, chosenBookRequest);
		}
	}



	private void giveBookToReader(String readerName, String chosenBookRequest) throws IOException {
		String[] authorAndTitle = chosenBookRequest.replaceAll("\"", "").split(" ");
		try {
			libraryDataController.giveBookToReader(authorAndTitle[0], authorAndTitle[1], readerName);
		} catch (TransformerException | TransformerFactoryConfigurationError | ParserConfigurationException
				| SAXException | SQLException | PropertyVetoException | org.xml.sax.SAXException e) {
			e.printStackTrace();
		}
	}
}
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
import main.java.view.WebViewManagingServlet;

@WebServlet(urlPatterns = { "/giveBook" })
public class GiveBookToReaderServlet extends WebViewManagingServlet {
	private String readerName;

	public GiveBookToReaderServlet() throws ClassNotFoundException, IOException, TransformerException, SAXException,
			SQLException, TransformerFactoryConfigurationError, ParserConfigurationException, org.xml.sax.SAXException {
		super();
		readerName = null;

	}

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String readerName = request.getParameter("readerName");
		if (readerName != null) {
			boolean validReaderName = LibraryModel.checkInputTextValidity(readerName);
			boolean registeredReader = false;
			if (libraryDataController.getSpecificReader(readerName) != null) {
				registeredReader = true;
			}
			sendValidityObject(response, validReaderName, registeredReader);

			if (validReaderName && registeredReader) {
				this.readerName = readerName;
			}
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("giveBook.jsp");
			rd.forward(request, response);
		}
	}

	private void sendValidityObject(HttpServletResponse response, boolean validReaderName, boolean registeredReader)
			throws IOException {
		JsonObject responseJson = new JsonObject();
		responseJson.addProperty("validReaderName", validReaderName);
		responseJson.addProperty("registeredReader", registeredReader);
		sendJsonResponse(response, responseJson.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String chosenBookRequest=request.getParameter("chosenBook");
		
		if (chosenBookRequest!=null) {
			String[] titleAndAuthor = chosenBookRequest.replaceAll("\"", "").split(" ");
			try {
				System.out.println(titleAndAuthor[0] + " " + titleAndAuthor[1] + " " + readerName);
				libraryDataController.giveBookToReader(titleAndAuthor[0], titleAndAuthor[1], readerName);
			} catch (TransformerException | TransformerFactoryConfigurationError | ParserConfigurationException
					| SAXException | SQLException | PropertyVetoException | org.xml.sax.SAXException e) {
				e.printStackTrace();
			}

		} else {
			RequestDispatcher rd = request.getRequestDispatcher("giveBook.jsp");
			rd.forward(request, response);
		}

	}

}

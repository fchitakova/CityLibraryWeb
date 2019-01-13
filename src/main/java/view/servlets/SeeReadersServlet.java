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
import main.java.Reader;

@WebServlet("/seeReaders")
public class SeeReadersServlet extends WebViewManagingServlet {
	private static final long serialVersionUID = 1L;

	public SeeReadersServlet() throws ClassNotFoundException, IOException, TransformerException, SAXException,
			SQLException, TransformerFactoryConfigurationError, ParserConfigurationException, org.xml.sax.SAXException {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(SEE_READERS_JSP_FILE_NAME);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		sendReaders(response);
	}

	void sendReaders(HttpServletResponse response) throws IOException {
		Set<Reader> registeredReaders = libraryDataController.getReaders();
		String json = null;
		if (registeredReaders.isEmpty()) {
			json = EMPTY_JSON_ARRAY;
		} else {
			json = new Gson().toJson(registeredReaders);
		}
		sendJsonResponse(response, json);
	}
}

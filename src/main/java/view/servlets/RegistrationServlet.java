package main.java.view.servlets;

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
import main.java.Validator;


@WebServlet("/registerReader")
public class RegistrationServlet extends WebViewManagingServlet{
	private static final long serialVersionUID = 1L;
       

    public RegistrationServlet() throws ClassNotFoundException, IOException, TransformerException, SAXException, SQLException, TransformerFactoryConfigurationError, ParserConfigurationException, org.xml.sax.SAXException {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd=request.getRequestDispatcher(REGISTER_READER_JSP_FILE_NAME);
		rd.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String readerName=request.getParameter(READER_NAME_REQUEST_PARAMETER);
		boolean validName=Validator.checkInputTextValidity(readerName);
		boolean alreadyRegistered=false;
		try {
			alreadyRegistered=libraryDataController.registerReader(readerName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String readerValidity = createValidityJsonResponse(validName, alreadyRegistered);
		sendJsonResponse(response, readerValidity);
	}





	private String createValidityJsonResponse(boolean validName, boolean alreadyRegistered) {
		JsonObject readerValidityJson=new JsonObject();
		readerValidityJson.addProperty(VALID_READER_NAME_JSON_PROPERTY, validName);
		readerValidityJson.addProperty(REGISTERED_READER_JSON_PPROPERTY, alreadyRegistered);
		return readerValidityJson.toString();
	}

}

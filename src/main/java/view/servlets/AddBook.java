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

import jdk.internal.org.xml.sax.SAXException;;


@WebServlet("/addBook")
public class AddBook extends WebViewManagingServlet {
	private static final long serialVersionUID = 1L;
 
    public AddBook() throws ClassNotFoundException, IOException, TransformerException, SAXException, SQLException, TransformerFactoryConfigurationError, ParserConfigurationException, org.xml.sax.SAXException {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd=request.getRequestDispatcher(ADD_BOOK_JSP_FILE_NAME);
		rd.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title=request.getParameter(BOOK_TITLE_REQUEST_PARAMETER);
		String author=request.getParameter(BOOK_AUTHOR_REQUEST_PARAMETER);
		System.out.println(author+" "+title);
		// TODO Auto-generated method stub
		//doGet(request, response);
	}

}

package main.java.view.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import jdk.internal.org.xml.sax.SAXException;
import main.java.view.WebViewManagingServlet;

@WebServlet("/returnBook")
public class ReturnBookServlet extends WebViewManagingServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @throws org.xml.sax.SAXException 
     * @throws ParserConfigurationException 
     * @throws TransformerFactoryConfigurationError 
     * @throws SQLException 
     * @throws SAXException 
     * @throws TransformerException 
     * @throws IOException 
     * @throws ClassNotFoundException 
     * @see HttpServlet#HttpServlet()
     */
    public ReturnBookServlet() throws ClassNotFoundException, IOException, TransformerException, SAXException, SQLException, TransformerFactoryConfigurationError, ParserConfigurationException, org.xml.sax.SAXException {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd=request.getRequestDispatcher("returnBook.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//doGet(request, response);
	}

}

package main.java.view.servlets;

import java.io.IOException;
import java.sql.SQLException;

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


@WebServlet(urlPatterns= {WebViewManagingServlet.GIVE_BOOK_TO_READER_URL})
public class GiveBookToReaderServlet extends WebViewManagingServlet{
	public GiveBookToReaderServlet() throws ClassNotFoundException, IOException, TransformerException, SAXException,
			SQLException, TransformerFactoryConfigurationError, ParserConfigurationException, org.xml.sax.SAXException {
		super();
	}

	private static final long serialVersionUID = 1L;
       

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("readerName"));
		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

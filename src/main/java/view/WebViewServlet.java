package main.java.view;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.xml.sax.SAXException;

import main.java.Constants;
import main.java.LibraryBook;
import main.java.LibraryModel;
import main.java.exceptions.BookException;

/**
 * Servlet implementation class WebViewServlet
 */
@WebServlet("/WebViewServlet")
public class WebViewServlet extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
	
	private LibraryModel libraryDataController;
       
    /**
     * @throws PropertyVetoException 
     * @throws SQLException 
     * @throws SAXException 
     * @throws TransformerException 
     * @throws IOException 
     * @throws ParserConfigurationException 
     * @throws TransformerFactoryConfigurationError 
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws TransformerConfigurationException 
     * @see HttpServlet#HttpServlet()
     */
    public WebViewServlet() throws TransformerConfigurationException, InstantiationException, IllegalAccessException, ClassNotFoundException, TransformerFactoryConfigurationError, ParserConfigurationException, IOException, TransformerException, SAXException, SQLException, PropertyVetoException {
        super();
        File f=new File(".");
        System.out.println(f.getAbsolutePath());
        libraryDataController=new  LibraryModel("C:\\Users\\i356406\\EEeclipse-workspace\\CityLibraryWeb\\WebContent\\WEB-INF\\lib\\resourses\\library.properties");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
              doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		pw.println("Yes"); 
		List<LibraryBook>sortedBooks=null;
		if (request.getParameter("button1") != null) {

			try {
				sortedBooks=libraryDataController.getBooksSorted(Constants.SORT_BY_TITLE_ID);
			} catch (BookException e) {
				// there are no empty books
			}
			request.setAttribute("booksSortedByTitle", sortedBooks);          
			request.setAttribute("infoMessage", "");
			RequestDispatcher rdst =  request.getRequestDispatcher("showBooks.jsp");
			rdst.forward(request, response);
		}
		if(request.getParameter(button2))
	}

}

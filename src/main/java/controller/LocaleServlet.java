package main.java.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.PropertyResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.Constants;


@WebServlet("/LocaleServlet")
public class LocaleServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PropertyResourceBundle prb;

	/**
	 * @throws IOException
	 * @see HttpServlet#HttpServlet()
	 */
	public LocaleServlet() throws IOException {
		super();
	}

	public String getResource(String propertyName) {
		if (!propertyName.equals(Constants.EMPTY_MESSAGE)) {
			return prb.getString(propertyName);
		}
		return propertyName;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("language");
       System.out.println(action);
		File file = null;

		if (action.equals("en")) {
			response.getWriter().write("Yes");
		}
		else {
			request.setAttribute("addBook", "Добави книга");
		}
		
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
            request.setAttribute("addBook", "addBook");
		    RequestDispatcher dispatcher =  request.getRequestDispatcher("http://localhost:8080/CityLibraryWeb/index.jsp");
	}
		
	
	
}

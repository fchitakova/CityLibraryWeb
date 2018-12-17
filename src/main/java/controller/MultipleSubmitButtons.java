package main.java.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/MultipleSubmitButtons")
public class MultipleSubmitButtons extends HttpServlet{


	/**
	 * @throws IOException
	 * @see HttpServlet#HttpServlet()
	 */



	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		  response.setContentType( "text/html" ) ;
		  ServletOutputStream sos = response.getOutputStream();
		    
		
		String str1=request.getParameter("p1");
		if(str1!=null) {
			sos.println("Pressed");
		}
		
	}
	
	
	public void a()
	{
		
	}
}

<%@page import="org.eclipse.jdt.internal.compiler.ast.ThisReference"%>
<%@page import="main.java.view.ResourseManagerSingleton"%>
<%@page import="main.java.view.ResourseManager"%>
<%@page import="main.java.Constants"%>

<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
 ResourseManager languageResources = ResourseManagerSingleton.getInstance();
%>


<html>
<title>
<%=languageResources.getResource(Constants.TITLE)%>
</title>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">


</head>
<body>
	<h3>CityLibrary</h3>

		<a href="showBooks.jsp" class="indexPageButton"> <b><%=languageResources.getResource(Constants.SEE_ALL_BOOKS)%></b> </a>
		<a href="availableBooks.jsp" class="indexPageButton"><b><%=languageResources.getResource(Constants.SEE_AVAILABLE_BOOKS)%></b></a>
		<a href="giveBook.jsp" class="indexPageButton"><b><%=languageResources.getResource(Constants.GIVE_BOOK_TO_READER)%></b></a>
		<a href="returnBook.jsp" class="indexPageButton"><b><%=languageResources.getResource(Constants.RETURN_BOOK)%></b></a>
		<a href="showReaderBooks.jsp" class="indexPageButton"><b><%=languageResources.getResource(Constants.SEE_TAKEN_BOOKS)%></b></a>
		<a href="seeReaders.jsp" class="indexPageButton"><b><%=languageResources.getResource(Constants.SEE_ALL_READERS)%></b></a>
		<a href="addBook.jsp" class="indexPageButton"><b><%=languageResources.getResource(Constants.ADD_BOOK)%></b> </a>
		<a href="registerReader.jsp" class="indexPageButton"> <b><%=languageResources.getResource(Constants.REGISTER_READER)%></b></a>
		<a href="searchBooks.jsp" class="indexPageButton"> <b><%=languageResources.getResource(Constants.SEARCH_BOOKS_LABEL) %></b></a>

</body>
</html>
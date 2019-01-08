<%@page import="main.java.view.ResourseManager"%>
<%@page import="main.java.view.ResourseManagerSingleton" %>
<%@page import="main.java.Constants"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
ResourseManager languageResources = ResourseManagerSingleton.getInstance();
%>

<html>
<head>
<script src='http://code.jquery.com/jquery-1.7.1.min.js'></script>
<script src="jsFiles/showAvaialableBooks.js"></script>

<title><%=languageResources.getResource(Constants.AVAILABLE_BOOKS_LABEL)%>
</title>
</head>
<div id="menu"></div>


<h2 id="infoMessage" style="visibility: hidden"><%=languageResources.getResource(Constants.PRINT_AVAILABLE_BOOKS)%>
</h2>

<h2 id="missingBooksMessage" style="visibility: hidden">
<%=languageResources.getResource(Constants.NOT_ANY_BOOKS)%>
</h2>

<table id="showAvailableBooks" style="visibility: hidden">
	<tr>
		<th><%=languageResources.getResource(Constants.BOOK_TITLE_LABEL)%></th>
		<th><%=languageResources.getResource(Constants.BOOK_AUTHOR_LABEL)%></th>
		<th><%=languageResources.getResource(Constants.BOOK_COPIES_LABEL)%></th>
	</tr>
</table>



<body>

</body>
</html>
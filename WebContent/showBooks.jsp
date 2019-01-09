<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="main.java.view.*"%>
<%@page import="main.java.Constants"%>
<%@page import="main.java.view.ResourseManagerSingleton"%>


<!DOCTYPE html SYSTEM "about:legacy-compat">

<%
	ResourseManager languageResources = ResourseManagerSingleton.getInstance();
%>


<html>
<head>
<script src='http://code.jquery.com/jquery-1.7.1.min.js'></script>
<script src="jsFiles\\showBooks.js"></script>
<title><%=languageResources.getResource(Constants.ALL_BOOKS_LABEL)%>
</title>

</head>
<div id="menu"></div>

<body>
	<h1>
		<%=languageResources.getResource(Constants.SEE_ALL_BOOKS)%>
	</h1>
	<form id="chooseSortingOrder">
		<input type="radio" name="sortingOrder" value="byTitle">


		<%=languageResources.getResource(Constants.SORTED_BY_TITLE)%>
		<input type="radio" name="sortingOrder" value="byAuthor">
	</form>
	<%=languageResources.getResource(Constants.SORTED_BY_AUTHOR)%>
	<button id='showBooksButton'>Choose</button>



	<h2 id='errorMessage' style="display: none">
		<%=languageResources.getResource(Constants.NOT_ANY_BOOKS)%>
	</h2>
	<h2 id="invalidChoice" style="display: none">
		<%=languageResources.getResource(Constants.INVALID_CHOICE)%>
	</h2>
	<h2 id='infoMessage' style="display: none">
		<%=languageResources.getResource(Constants.PRINT_ALL_BOOKS)%>
	</h2>
	<table id="showBooks" style="display: none">
		<tr>
			<th><%=languageResources.getResource(Constants.BOOK_TITLE_LABEL)%></th>
			<th><%=languageResources.getResource(Constants.BOOK_AUTHOR_LABEL)%></th>
		</tr>
	 <table id="fillBooksInfo"></table>
	</table>

</body>

</html>
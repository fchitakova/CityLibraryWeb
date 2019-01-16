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
	<div id="chooseSortingOrder">
	    <label for="sortByTitle"><%=languageResources.getResource(Constants.SORTED_BY_TITLE)%></label>
		<input type="radio" name="sortingOrder" id="sortByTitle" value="byTitle">
		<label for="sortByAuthor"><%=languageResources.getResource(Constants.SORTED_BY_AUTHOR)%></label>
		<input type="radio" name="sortingOrder" id="sortByAuthor" value="byAuthor">
		<button id='showBooksButton'><%=languageResources.getResource(Constants.SUBMIT_BUTTON_LABEL)%></button>
	</div>
	



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
	</table>
	<table id="fillBooksInfo"></table>

</body>

</html>
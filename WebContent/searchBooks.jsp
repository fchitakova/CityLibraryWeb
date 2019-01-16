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
<script src="jsFiles\\searchBooks.js"></script>
<title><%=languageResources.getResource(Constants.SEARCH_BOOKS_LABEL)%>
</title>

</head>

<body>
	<div id="menu"></div>
	<h1>
		<%=languageResources.getResource(Constants.SEARCH_BOOKS_LABEL)%>
	</h1>

	<div id="searchTextInput">
		<br> <input type="text" id="searchedText"> <br> <label
			for="byTitle"><%=languageResources.getResource(Constants.SEARCH_BY_TITLE)%></label>
		<input type="radio" name="searchType" id="byTitle"
			value="byTitle"> <br> <label for="byAuthor"><%=languageResources.getResource(Constants.SEARCH_BY_AUTHOR_RROPERTY)%></label>
		<input type="radio" name="searchType" id="byAuthor"
			value="byAuthor"> <br>
		<button id='showBooksButton' onclick="processInput()"><%=languageResources.getResource(Constants.SEARCH_BUTTON_LABEL)%></button>
	</div>



	<h2 id='invalidInputMessage' style="display: none">
		<%=languageResources.getResource(Constants.INVALID_INPUT_MESSAGE)%>
	</h2>
	<h2 id="invalidChoice" style="display: none">
		<%=languageResources.getResource(Constants.INVALID_CHOICE)%>
	</h2>
	
	<h2 id='notAnyBooksMessage' style="display:none"><%=languageResources.getResource(Constants.NOT_ANY_BOOKS) %></h2>
	<h2 id='foundBooksMessage' style="display:none"><%=languageResources.getResource(Constants.FOUND_BOOKS) %></h2>
	<table id="showBooks" style="display: none">
		<tr>
			<th><%=languageResources.getResource(Constants.BOOK_TITLE_LABEL)%></th>
			<th><%=languageResources.getResource(Constants.BOOK_AUTHOR_LABEL)%></th>
		</tr>
	</table>
	<table id="fillBooksInfo"></table>

</body>

</html>
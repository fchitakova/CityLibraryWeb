<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="main.java.view.ResourseManager"%>
<%@page import="main.java.view.ResourseManagerSingleton"%>
<%@page import="main.java.Constants"%>

<!DOCTYPE html>
<%
	ResourseManager languageResources = ResourseManagerSingleton.getInstance();
%>
<html>
<head>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.7.1.min.js"></script>

<script src="jsFiles\addBook.js"></script>
<title><%=languageResources.getResource(Constants.ADD_BOOK)%></title>
</head>
<body>
	<div id="menu"></div>
    <br>
    <br>
    <br>
	<div id="submitBook">
		<label for="title"><%=languageResources.getResource(Constants.ENTER_TITLE)%></label>
		<input type="text" id="title" name="title">
		<br>
		 <label for="author"><%=languageResources.getResource(Constants.ENTER_AUTHOR)%></label>
		<input type="text" id="author" name="author">
		<br>
		<button id="submit"><%=languageResources.getResource(Constants.SUBMIT_BUTTON_LABEL)%></button>
	</div>
	
	<h2 id="successfullyAddedBook" style="display:none">
	   <%=languageResources.getResource(Constants.SUCCESSFULLY_ADDED_BOOK)%>
	</h2>
	
	<h2 id="notValidTitleOrAuthor" style="display: none">
	
	</h2>



</body>
</html>
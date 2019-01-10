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
<script src="jsFiles\\showAvaialableBooks.js"></script>
<script src="jsFiles\\returnBook.js"></script>
<title><%=languageResources.getResource(Constants.RETURN_BOOK_LABEL)%>
</title>
</head>
<body>
	<div id="menu"></div>

	<div id="readerNameSubmit" style="display: none">
		<h2><%=languageResources.getResource(Constants.ENTER_READER)%></h2>
		<br> <input type="text" id="readerName">
		<button id="submitReaderNameButton"><%=languageResources.getResource(Constants.SUBMIT_BUTTON_LABEL)%></button>
	</div>

	<h2 id="notValidReaderName" style="display: none">
		<%=languageResources.getResource(Constants.NOT_VALID_READER_NAME)%>
	</h2>

	<h2 id="notRegisteredReader" style="display: none">
		<%=languageResources.getResource(Constants.NOT_REGISTERED_READER)%>
	</h2>

	<h2 id="notAnyBooksMessage" style="display: none">
		<%=languageResources.getResource(Constants.NOT_ANY_TAKEN_BOOKS)%>
	</h2>

	<div id="showResult" style="display: none">

		<h2><%=languageResources.getResource(Constants.CHOOSE_BOOK_MESSAGE)%></h2>
		<table id="booksLabels" style="display: none">
			<tr>
				<th><%=languageResources.getResource(Constants.BOOK_TITLE_LABEL)%></th>
				<th><%=languageResources.getResource(Constants.BOOK_AUTHOR_LABEL)%></th>
			</tr>
		</table>
		<table id="booksTable">
		</table>
		<button id="submitBookButton"><%=languageResources.getResource(Constants.CHOOSE_BOOK_MESSAGE)%></button>
		<h2 id="successfulOperation" style="display: none">
			<%=languageResources.getResource(Constants.SUCCESSFULLY_RETURNED_BOOK)%>
		</h2>

	</div>

	<h2 id="invalidChoice" style="display: none">
		<%=languageResources.getResource(Constants.INVALID_CHOICE)%>
	</h2>




</body>
</html>
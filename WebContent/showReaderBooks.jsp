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
	
<script src="jsFiles\showReaderBooks.js"></script>
<title><%=languageResources.getResource(Constants.SEE_TAKEN_BOOKS)%>
</title>
</head>
<body>
	<div id="menu"></div>

	<div id="readerNameSubmit">
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

		<h2 id="appendReaderName"><%=languageResources.getResource(Constants.TAKEN_BOOKS_BY_READER)%></h2>
		<table id="booksLabels" style="display: none">
			<tr>
				<th><%=languageResources.getResource(Constants.BOOK_TITLE_LABEL)%></th>
				<th><%=languageResources.getResource(Constants.BOOK_AUTHOR_LABEL)%></th>
			</tr>
		</table>
		<table id="booksTable">
		</table>

	</div>




</body>
</html>
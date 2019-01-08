<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="main.java.view.ResourseManager"%>
<%@page import="main.java.view.ResourseManagerSingleton"%>
<%@page import="main.java.LibraryModel"%>
<%@page import="main.java.Constants"%>

<!DOCTYPE html>
<%
	ResourseManager languageResources = ResourseManagerSingleton.getInstance();
%>
<html>
<head>
<script type="text/javascript"
		src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
	<script src="jsFiles\\giveBookToReader.js"></script>
<title><%=languageResources.getResource(Constants.GIVE_BOOK_LABEL)%>
</title>
</head>
<body>
	<div id="menu"></div>

	<div id="readerNameSubmit" style="visbility: hidden">
		<h2><%=languageResources.getResource(Constants.ENTER_READER)%></h2>
		<br> <input type="text" id="readerName">
		<button id="submitReaderNameButton"><%=languageResources.getResource(Constants.SUBMIT_BUTTON_LABEL)%></button>

	</div>

	<h2 id="notAnyBooksMessage" style="visibility: hidden">
		'<%=languageResources.getResource(Constants.NOT_ANY_BOOKS)%></h2>

	<div id="showResult" style="visibility: hidden">
		<h2><%=languageResources.getResource(Constants.PRINT_AVAILABLE_BOOKS)%></h2>
		<table id="booksTable">
			<tr>
				<th><%=languageResources.getResource(Constants.BOOK_TITLE_LABEL)%></th>
				<th><%=languageResources.getResource(Constants.BOOK_AUTHOR_LABEL)%></th>
			</tr>

		</table>
		<button id="submitBookButton"><%=languageResources.getResource(Constants.GIVE_BOOK_LABEL)%></button>
	</div>



	
		
	</script>



</body>
</html>
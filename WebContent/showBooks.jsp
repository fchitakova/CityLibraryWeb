<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="main.java.view.*"%>
<%@page import="main.java.Constants"%>
<%@page import="main.java.view.ResourseManager"%>

<!DOCTYPE html SYSTEM "about:legacy-compat">

<%
	ResourseManager languageResources = ((ResourseManager) session.getAttribute("languageResources"));
%>


<html>
<head>
<script src= 'http://code.jquery.com/jquery-1.7.1.min.js'></script>
 <script src="jsFiles\\showBooks.js"></script>


<title><%=languageResources.getResource(Constants.ALL_BOOKS_LABEL)%>
</title>
</head>

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


    <h2 id='infoMessage' visibility:hidden>
    
    </h2>

	<div id="showBooks">
	
	</div>


</body>

</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="main.java.view.*"%>
<%@page import="main.java.Constants"%>

<!DOCTYPE html>

<%
	ResourseManager languageResources = new ResourseManager();
	session.setAttribute("languageResources", languageResources);
%>


<html>
<head>
<meta charset="ISO-8859-1">
<!--  <link href="<c:url value="myCss.css" />" rel="stylesheet"> -->

<title>

      
</title>
</head>

<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
<body>

	<h1>
		<%=languageResources.getResource(Constants.SEE_ALL_BOOKS)%>
	</h1>
	<form id="chooseSortingOrder">
	<input type="radio" name="sortingOrder"value="byTitle">

	<!-- ((ResourseManager) request.getAttribute("languageResources")) -->
	<%=languageResources.getResource(Constants.SORTED_BY_TITLE)%>
	<input type="radio" name="sortingOrder" value="ByAuthor">
	</form>
	<%=languageResources.getResource(Constants.SORTED_BY_AUTHOR)%>
	<button id='showBooksButton'>Choose</button>

	<div id="showBooks"></div>

<script  type="text/javascript">
	$(document).ready(function() {
						$('#showBooksButton').click(function() {
							var sortingOrder =$('input[name=sortingOrder]:checked', '#chooseSortingOrder').val();
											$.ajax({
														url : "${pageContext.request.contextPath}/allBooks",
														data : 
														{
														   "sortingOrder":JSON.stringify(sortingOrder)
														},
													    dataType: "json",
														type : "POST",
														success : function(responseJson) {
														if ($.trim(responseJson)){ 
																alert("yes");
															var $table = $("<table>").appendTo($("#showBooks"));
															 $.each(responseJson, function(index, sortedBooks) { 
														            $("<tr>").appendTo($table)                    
														                .append($("<td>").text(sortedBooks.title))       
														                .append($("<td>").text(sortedBooks.author));    
														        });
															}
															else{
																var infoMessage='<%=languageResources.getResource(Constants.NOT_ANY_BOOKS)%>'
															
																$("#showBooks").append("<p>");
																$("#showBooks").append(infoMessage);
																$("#showBooks").append("</p>");
																}
														  
														}
														
													});
										});
					});

   
</script>
</body>

</html>
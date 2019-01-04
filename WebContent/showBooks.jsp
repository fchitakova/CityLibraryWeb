<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="main.java.view.*"%>
<%@page import="main.java.Constants"%>
<%@page import="main.java.view.ResourseManager"%>

<!DOCTYPE html>

<%
	ResourseManager languageResources = ((ResourseManager) session.getAttribute("languageResources"));
%>


<html>
<head>
<meta charset="ISO-8859-1">


<title><%=languageResources.getResource(Constants.ALL_BOOKS_LABEL)%>
</title>
</head>

<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
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


	<div id="showBooks"></div>

	<script type="text/javascript">
	$(document).ready(function() {
						$('#showBooksButton').click(function() {
							var sortingOrder =$('input[name=sortingOrder]:checked', '#chooseSortingOrder').val();
							if(sortingOrder==null){
								var infoMessage='<%=languageResources.getResource(Constants.INVALID_CHOICE)%>';
								alert(infoMessage);
								return;
							}
											$.ajax({
														url : "${pageContext.request.contextPath}/allBooks",
														data : 
														{
														   "sortingOrder":JSON.stringify(sortingOrder)
														},
													    dataType: "json",
														type : "POST",
														success : function(responseJson) {
															$('#showBooks').html("");
														if (responseJson!==null){ 
															$("<h2>").appendTo($("#showBooks"))
															         .append("<%=languageResources.getResource(Constants.PRINT_ALL_BOOKS)%>");
															
															var $table = $("<table>").appendTo($("#showBooks"));
															 $.each(responseJson, function(index, sortedBooks) { 
														            $("<tr>").appendTo($table)                    
														                .append($("<td>").text(sortedBooks.title))       
														                .append($("<td>").text(sortedBooks.author));    
														        });
															}
															else
															{
																var infoMessage='<%=languageResources.getResource(Constants.NOT_ANY_BOOKS)%>';
																	$("<h2>").appendTo($("#showBooks")).append(infoMessage);
																}

															}

														});
											});
						});
	</script>
</body>

</html>
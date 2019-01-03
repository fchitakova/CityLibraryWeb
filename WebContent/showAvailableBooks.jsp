<%@page import="main.java.view.ResourseManager"%>
<%@page import="main.java.Constants"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
	ResourseManager languageResources = ((ResourseManager) session.getAttribute("languageResources"));
%>

<html>
<head>

<title>

   <%=languageResources.getResource(Constants.AVAILABLE_BOOKS_LABEL)%> 
</title>
</head>

<div id="showAvailableBooks">

</div>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
   $(document).ready(
    function(){
    	$.ajax({
			url : "${pageContext.request.contextPath}/availableBooks",
		    dataType: "json",
			type : "POST",
			success : function(responseJson) {
				$('#showAvailableBooks').html("");
			if (responseJson!='null'){ 
				var $message='<%=languageResources.getResource(Constants.PRINT_AVAILABLE_BOOKS)%>';
				$("<h1>").appendTo($('#showAvailableBooks')).append($message);

				var $table = $("<table>").appendTo($("#showAvailableBooks"));
				$("<tr>").appendTo($table)
				.append($("<th>").text("<%=languageResources.getResource(Constants.BOOK_TITLE_LABEL)%>"))
				.append($("<th>").text("<%=languageResources.getResource(Constants.BOOK_AUTHOR_LABEL)%>"))
				.append($("<th>").text("<%=languageResources.getResource(Constants.BOOK_COPIES_LABEL)%>"));
				 $.each(responseJson, function(index, sortedBooks) { 
			            $("<tr>").appendTo($table)                    
			                .append($("<td>").text(sortedBooks.title))       
			                
			                .append($("<td>").text(sortedBooks.author)) 
			                .append($("<td>").text(sortedBooks.copies));
			        });
				}
			
				else{
					var $infoMessage='<%=languageResources.getResource(Constants.NOT_ANY_BOOKS)%>';
					$("<p>").appendTo($("#showAvailableBooks")).append($infoMessage);
			  
			}
			
		}
    });
    });
</script>

<body>

</body>
</html>
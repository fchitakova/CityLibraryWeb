<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="main.java.view.ResourseManager"%>
<%@page import="main.java.LibraryModel"%>
<%@page import="main.java.Constants"%>

<!DOCTYPE html>
<%
	ResourseManager languageResources = ((ResourseManager) session.getAttribute("languageResources"));
%>
<html>
<head>
<title><%=languageResources.getResource(Constants.GIVE_BOOK_LABEL)%>
</title>
</head>
<body>


	<div id="readerNameSubmit"></div>
	<div id="showResult"></div>



	<script type="text/javascript"
		src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
	<script type="text/javascript">
	var availableBooks;
	
	function getAvailableBooks(){
		$.ajax({
			url:"${pageContext.request.contextPath}/giveBook",
			type:"POST",
			dataType:"text",
			data:{"getAvailableBooks":"getAvailableBooks"},
			success:
			 function(returnedBooks){
					availableBooks=returnedBooks;
			}
	});
	}
	
	$(document).ready(function(){
		availableBooks=getAvailableBooks();
		if (availableBooks!=='null'){
					postReaderInputForm();
				}
				else{
					var $message='<%=languageResources.getResource(Constants.NOT_ANY_BOOKS)%>';
					$("<h1>").appendTo($('#readerNameSubmit')).append($message);

				}
				
			});

	
	function postReaderInputForm(){
		 var message='<%=languageResources.getResource(Constants.ENTER_READER)%>';
		 var buttonLabel='<%=languageResources.getResource(Constants.SUBMIT_BUTTON_LABEL)%>';
		 $("<h2>").appendTo($("#readerNameSubmit")).append(message);
		 $("<b>").appendTo($("#readerNameSubmit"));
		 $("#readerNameSubmit").append("<input type=\"text\" id=\"readerName\">");
		 $("<button id=\"submitReaderNameButton\">").appendTo($("#readerNameSubmit")).append(buttonLabel);
	}
	
	
	$(document).on('click', '#submitReaderNameButton', function(){
		var readerName=$("#readerName").val();
		$.ajax({
			url:"${pageContext.request.contextPath}/giveBook",
			type:"POST",
			dataType:"text",
			data:{"checkReaderName":"checkReaderName",
				  readerName:readerName },
				  success:function(readerValidityObject){
					  $('#showResult').html("");
					  if(checkReaderValidity(readerValidityObject)===true){
						  printAvailableBooksOptions();
						}
				
				  }
			});
			
		});
	
	
	function printAvailableBooksOptions(){
		var message='<%=languageResources.getResource(Constants.PRINT_AVAILABLE_BOOKS)%>';
		  $("<h2>").appendTo($('#showResult')).append(message);
		  var $table = $("<table>").appendTo($("#showResult"))
			.append($("<th>").text("<%=languageResources.getResource(Constants.BOOK_TITLE_LABEL)%>"))
			.append($("<th>").text("<%=languageResources.getResource(Constants.BOOK_AUTHOR_LABEL)%>"));
			 $.each(JSON.parse(availableBooks), function(index, availableBook) {
		            $("<tr>").appendTo($table)
		                .append("<input type=\"radio\" name=\"booksToChoose\" value=index>")
		                .append($("<td>").text(availableBook.title))      
		                .append($("<td>").text(availableBook.author));
		        });
			 var buttonLabel="<%=languageResources.getResource(Constants.GIVE_BOOK_LABEL)%>";
			 $("<button id=\"submitBookButton\">").appendTo($("#showResult")).append(buttonLabel);
	}
	
	
	
	function checkReaderValidity(readerValidityObject){
		 var parsedReaderValidity=JSON.parse(readerValidityObject);
		  if(parsedReaderValidity.isValidReaderName===false){
			  $("<h2>").appendTo($("#showResult")).append('<%=languageResources.getResource(Constants.NOT_VALID_READER_NAME)%>');
            return false;
		  }
		  if(parsedReaderValidity.isRegisteredReader===false){
			  $("<h2>").appendTo($("#showResult")).append('<%=languageResources.getResource(Constants.NOT_REGISTERED_READER)%>');
				return false;
			}
			return true;
		}
	</script>



</body>
</html>
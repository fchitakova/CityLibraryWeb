<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="main.java.view.ResourseManager"%>
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


	<div id="showSubmitForm"></div>



	<script type="text/javascript"
		src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
	<script type="text/javascript">
	var availableBooks;
	$(document).ready(function(){
		$.ajax({
			url:"${pageContext.request.contextPath}/giveBook",
			type:"POST",
			dataType:"text",
			data:{"getAvaialbleBooks":"getAvaialbleBooks"},
			success:
			 function(returnedBooks){
				if (returnedBooks!=='null'){
					availableBooks=returnedBooks;
					postReaderInputForm();
				}
				else{
					var $message='<%=languageResources.getResource(Constants.NOT_ANY_BOOKS)%>';
					$("<h1>").appendTo($('#showSubmitForm')).append($message);

				}
				
			}
		});
	});
	
	function postReaderInputForm(){
		 var message='<%=languageResources.getResource(Constants.ENTER_READER)%>';
		 var buttonLabel='<%=languageResources.getResource(Constants.SUBMIT_BUTTON_LABEL)%>';
		 var $userInputForm=$("<form>").appendTo($("#showSubmitForm"));
		 ($userInputForm).append("<h2>").
		 append(message).append("</h2>").append("<br>").append("<input type=\"text\" id=\"readerName\">").
		 append("<button type=\"submit\" id=\"submitReaderNameButton\" onClick=\"passReaderName()\">").
		 append(buttonLabel).append("</button>");
	}
	
	<%--
	   function passReaderName(){
		   var readerName=$("#readerName").val();
		   alert(readerName);
			$.ajax({
				url:"${pageContext.request.contextPath}/giveBook",
				type:'post',
				dataType:"text",
				data:{readerName:readerName},
		        success:function(){
		        	alert(readerName);
		        },
		        fail:function(){
		        	alert("fail");
		        }
			});
	   }
				<%--
				success:
				function(isValidInput,isRegistered,availableBooks){
					if(isValidInput===false){
						var infoMessage='<%= languageResources.getResource(Constants.NOT_VALID_READER_NAME) %>';
						var $infoParagraph=$("<p>").appendTo($("#showAvailableBooks"));
						("<b>").appendTo($infoParagraph).append(infoMessage);
					}
			   }
				*/
				--%>
				
			
	</script>



</body>
</html>
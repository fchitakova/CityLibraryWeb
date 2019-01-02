<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="main.java.view.ResourseManager"%>
<%@page import="main.java.Constants"%>

<!DOCTYPE html>
<%
	//ResourseManager languageResources = ((ResourseManager) session.getAttribute("languageResources"));
%>
<html>
<head>
<title>
<%-- 
<%=languageResources.getResource(Constants.GIVE_BOOK_LABEL)%>--%>
</title>
</head>
<body>



	<form>
		<h2>
		<%-- languageResources.getResource(Constants.ENTER_READER) --%>
		</h2>
		<br> <input type="text" name="readerName">
		<button type="submit" name="submitReaderNameButton"><%--<%=languageResources.getResource(Constants.SUBMIT_BUTTON_LABEL)--%></button>
	</form>
	<script type="text/javascript"
		src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
	<script type="text/javascript">
		$("#submitReaderNameButton").click(function (){
			var $readerName=$('#readerName');
			$.ajax({
				url:"${pageContext.request.contextPath}/giveBook",
				type:"POST",
				dataType:"json",
				data:{"readerName":JSON.stringify(readerName)},
		        success:function(){
		        	alert("yes");
		        }
			})});
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
	<div id="showBooksOfReader"></div>



</body>
</html>
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
<script src="jsFiles\\registerReader.js"></script>
<meta charset="ISO-8859-1">
<title><%=languageResources.getResource(Constants.REGISTER_READER)%></title>
</head>
<body>
<div id="menu"> </div>

<div id="readerNameSubmit">
		<h2><%=languageResources.getResource(Constants.ENTER_READER)%></h2>
		<br> <input type="text" id="readerName">
		<button id="submitReaderNameButton"><%=languageResources.getResource(Constants.SUBMIT_BUTTON_LABEL)%></button>
	</div>

	<h2 id="notValidReaderName" style="display: none">
		<%=languageResources.getResource(Constants.NOT_VALID_READER_NAME)%>
	</h2>

	<h2 id="notRegisteredReader" style="display: none">
		<%=languageResources.getResource(Constants.EXISTING_READER)%>
	</h2>
	
	<h2 id="successfulRegistration" style="display:none">
	<%=languageResources.getResource(Constants.SUCCESSFUL_READER_REGISTRATION) %>
	</h2>


</body>
</html>
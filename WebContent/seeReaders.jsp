<%@page import="main.java.view.ResourseManager"%>
<%@page import="main.java.view.ResourseManagerSingleton" %>
<%@page import="main.java.Constants"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
ResourseManager languageResources = ResourseManagerSingleton.getInstance();
%>

<html>
<head>
<script src='http://code.jquery.com/jquery-1.7.1.min.js'></script>

<script src="jsFiles/seeReaders.js"></script>

<title><%=languageResources.getResource(Constants.SEE_ALL_READERS)%>
</title>
</head>
<div id="menu"></div>
<body>

<h2 id="infoMessage" style="display:none"><%=languageResources.getResource(Constants.REGISTERED_READERS_MESSAGE)%>
</h2>

<h2 id="missingReadersMessage" style="display:none">
<%=languageResources.getResource(Constants.NOT_READERS)%>
</h2>

<table id="showReaderName" style="display:none">
       <tr>
		<th><%=languageResources.getResource(Constants.READER_NAME_LABEL)%></th>
		</tr>
</table>
<ul id="showAllReaders">
</ul>



</body>
</html>
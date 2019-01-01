<%@page import="org.eclipse.jdt.internal.compiler.ast.ThisReference"%>
<%@page import="main.java.view.ResourseManager"%>
<%@page import="main.java.Constants"%>
<%@page import="main.java.*"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
	ResourseManager languageResources = new ResourseManager();
	session.setAttribute("languageResources", languageResources);
%>


<html>
<head>
<meta charset="ISO-8859-1">
<!--  <link href="<c:url value="myCss.css" />" rel="stylesheet"> -->
<meta name="viewport" content="width=device-width, initial-scale=1">


</head>
<body>
	<h3>CityLibrary</h3>

	<form action="${pageContext.request.contextPath}/WebViewServlet"
		method="post" class="WebViewServlet">
		<button type="submit" name="button1"> <b><%=languageResources.getResource(Constants.SEE_ALL_BOOKS)%></b> </button>
		<button type="submit" name="button3"><%=languageResources.getResource(Constants.SEE_AVAILABLE_BOOKS)%></button>
		<button type="submit" name="button4"><%=languageResources.getResource(Constants.RETURN_BOOK)%></button>
		<button type="submit" name="button5"><%=languageResources.getResource(Constants.REGISTER_READER)%></button>
		<button type="submit" name="button6"><%=languageResources.getResource(Constants.ADD_BOOK)%></button>
		<button type="submit" name="button"><%=languageResources.getResource(Constants.GIVE_BOOK_TO_READER)%></button>
	</form>
</body>
</html>
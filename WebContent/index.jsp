<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@page import="main.java.*"%>

<html>
<head>
<meta charset="ISO-8859-1">

</head>
<body>
	<h1>CityLibrary</h1>

	<form  action="${pageContext.request.contextPath}/WebViewServlet"  method="post" class="/WebViewServlet">
		<input type="submit" value="Show all books sorted by title"
			name="button1" /> <input type="submit"
			value="Show all books sorted by author" name="button2" /> <input
			type="submit" value="Show available books" name="button3" /> <input
			type="submit" value="Return book"> <input type="submit"
			value="Add book"> <input type="submit"
			value="Register reader"> <input type="submit"
			value="See all readers"> <input type="submit"
			value="Show books of reader">

	</form>




</body>
</html>
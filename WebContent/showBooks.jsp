<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
 <link href="<c:url value="myCss.css" />" rel="stylesheet">

<title>Insert title here</title>
</head>
<body>

<form>

 </br></br> </br>

<c:forEach var="sortedBooks" items="${requestScope.booksSortedByTitle}">
      <li>
     <c:out value="${sortedBooks.title}"> </c:out> 
     <c:out value="${sortedBooks.author}"> </c:out> </br> 
     </li>
     
</c:forEach>

</form>
</body>
</html>
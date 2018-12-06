<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<c:set var="language" scope="session" value=${param.language}></c:set> 
<c:set var="language" value="${not empty param.language ? param.language :
 }" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resources.languageResources"/>

<html lang=${language}>
<head>
<meta charset="ISO-8859-1">
   
</head>
<body>
 <h1>CityLibrary</h1>
<form method="doGet" class="LocaleServlet" >
<a href="LocaleServlet?language=bg">
    <img src="images/bulgarflag.gif" style="width:30px;height:23px;">
</a>
<a href="LocaleServlet?language=en">
    <img src="images/en_flag_imagelarge.jpg" style="width:30px;height:23px;">
    
</a>

<button method="doPost" class="LocaleServlet$addBook">



</form>
</body>
</html>
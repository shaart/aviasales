<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="language" value="${not empty param.language ? param.language :
    not empty language ?  language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="com.epam.aviasales.bundles.global" var="lang"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <title><fmt:message key="manage.title" bundle="${lang}"/></title>
</head>
<body>
<%@include file="layout/header.jsp" %>
<div class="container">
    <div class="container">
        <a href="/manage/flights">
            <div class="text">Flights</div>
            <img class="img-responsive" src="resources/manage/flights.png" width="300" height="auto">
        </a>
    </div>
    <div class="container">
        <a href="/manage/airplanes">
            <div class="text">Airplanes</div>
            <img class="img-responsive" src="resources/manage/airplanes.png"  width="300" height="auto">
        </a>
    </div>
    <div class="container">
        <a href="/manage/airports">
            <div class="text">Airports</div>
            <img class="img-responsive" src="resources/manage/airports.png"  width="300" height="auto">
        </a>
    </div>
    <div class="container">
        <a href="/manage/users">
            <div class="text">Users</div>
            <img class="img-responsive" src="resources/manage/users.png"  width="300" height="auto">
        </a>
    </div>
</div>
<%@include file="layout/footer.jsp" %>
</body>
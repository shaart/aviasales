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
    <div class="row">
        <div class="col-sm-6 col-md-4">
            <a href="/manage/flights">
                <div class="text"><fmt:message key="manage.flights.title" bundle="${lang}"/></div>
                <img class="img-responsive" src="resources/manage/flights.png" width="300"
                     height="auto">
            </a>
        </div>
        <div class="col-sm-6 col-md-4">
            <a href="/manage/airplanes">
                <div class="text"><fmt:message key="manage.airplanes.title" bundle="${lang}"/></div>
                <img class="img-responsive" src="resources/manage/airplanes.png" width="300"
                     height="auto">
            </a>
        </div>
        <div class="col-sm-6 col-md-4">
            <a href="/manage/airports">
                <div class="text"><fmt:message key="manage.airports.title" bundle="${lang}"/></div>
                <img class="img-responsive" src="resources/manage/airports.png" width="300"
                     height="auto">
            </a>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-6 col-md-4">
            <a href="/manage/accounts">
                <div class="text"><fmt:message key="manage.accounts.title" bundle="${lang}"/></div>
                <img class="img-responsive" src="resources/manage/accounts.png" width="300"
                     height="auto">
            </a>
        </div>
        <div class="col-sm-6 col-md-4">
            <a href="/manage/personals">
                <div class="text"><fmt:message key="manage.personaldata.title" bundle="${lang}"/></div>
                <img class="img-responsive" src="resources/manage/personals.png" width="300"
                     height="auto">
            </a>
        </div>
        <div class="col-sm-6 col-md-4">
            <a href="/manage/tickets">
                <div class="text"><fmt:message key="manage.tickets.title" bundle="${lang}"/></div>
                <img class="img-responsive" src="resources/manage/tickets.png" width="300"
                     height="auto">
            </a>
        </div>
    </div>
</div>
<%@include file="layout/footer.jsp" %>
</body>
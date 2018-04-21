<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="language" value="${not empty param.language ? param.language :
    not empty language ?  language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="com.epam.aviasales.bundles.global" var="lang"/>
<c:if test="${account == null || (account.role != 'ADMIN' && account.role != 'MANAGER')}">
    <% response.sendError(404); %>
</c:if>
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
                <img class="img-responsive" src="resources/manage/flights.png"
                     height="auto">
                <div align="center" class="text"><fmt:message key="manage.flights.title"
                                                              bundle="${lang}"/></div>
            </a>
        </div>
        <div class="col-sm-6 col-md-4">
            <a href="/manage/tickets">
                <img class="img-responsive" src="resources/manage/tickets.png"
                     height="auto">
                <div align="center" class="text"><fmt:message key="manage.tickets.title"
                                                              bundle="${lang}"/></div>
            </a>
        </div>
        <div class="col-sm-6 col-md-4">
            <a href="/manage/personals">
                <img class="img-responsive" src="resources/manage/personals.png"
                     height="auto">
                <div align="center" class="text"><fmt:message key="manage.personaldata.title"
                                                              bundle="${lang}"/></div>
            </a>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-6 col-md-4">
            <a href="/manage/airplanes">
                <img class="img-responsive" src="resources/manage/airplanes.png"
                     height="auto">
                <div align="center" class="text"><fmt:message key="manage.airplanes.title"
                                                              bundle="${lang}"/></div>
            </a>
        </div>
        <div class="col-sm-6 col-md-4">
            <a href="/manage/airports">
                <img class="img-responsive" src="resources/manage/airports.png"
                     height="auto">
                <div align="center" class="text"><fmt:message key="manage.airports.title"
                                                              bundle="${lang}"/></div>
            </a>
        </div>
        <c:if test="${account.role == 'ADMIN'}">
            <div class="col-sm-6 col-md-4">
                <a href="/manage/accounts">
                    <img class="img-responsive" src="resources/manage/accounts.png"
                         height="auto">
                    <div align="center" class="text"><fmt:message key="manage.accounts.title"
                                                                  bundle="${lang}"/></div>
                </a>
            </div>
        </c:if>
    </div>
</div>
<%@include file="layout/footer.jsp" %>
</body>
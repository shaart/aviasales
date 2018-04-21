<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://aviasales.epam.com/jsp/util/functions" prefix="juf" %>
<c:set var="language" value="${not empty param.language ? param.language :
    not empty language ?  language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="com.epam.aviasales.bundles.global" var="lang"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <title><fmt:message key="error.title" bundle="${lang}"/></title>
</head>
<body>
<%@include file="layout/header.jsp" %>
<div class="container">
    <h2>Error!</h2>
    <div class="alert alert-danger">
        <p><c:out value="${error}" escapeXml="false"/></p>
    </div>
    <a href="${previousPage}" class="btn btn-primary">
        <span class="glyphicon glyphicon-chevron-left"></span>
        <fmt:message key="error.label.go.back" bundle="${lang}"/>
    </a>
</div>
<%@include file="layout/footer.jsp" %>
</body>
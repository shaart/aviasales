<%--
  Created by IntelliJ IDEA.
  User: vikto
  Date: 4/12/2018
  Time: 8:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ?
    param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="com.epam.aviasales.bundles.global" var="lang"/>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>


<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
    </select>
</form>

<div class="container">

    <form class="form-signin" action="/login" method="post" style="width:250px">
        <h2 class="form-signin-heading"><fmt:message key="log_in" bundle="${lang}"/></h2>
        <label for="inputLogin" class="sr-only"><fmt:message key="login.label.login" bundle="${lang}"/></label>
        <input type="login" name="inputLogin" id="inputLogin" class="form-control" placeholder=<fmt:message key="username" bundle="${lang}"/> required autofocus>
        <label for="inputPassword" class="sr-only"></label>
        <input type="password" name="inputPassword" id="inputPassword" class="form-control" placeholder=<fmt:message key="password" bundle="${lang}"/> required>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="log_in" bundle="${lang}"/></button>
    </form>
    <c:if test = "${errorMessage != null}">
        <p>
            <font color="red"><fmt:message key="${errorMessage}" bundle="${lang}"/></font>
        </p>
    </c:if>

</div>

</body>
</html>
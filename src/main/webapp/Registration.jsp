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

<%@include file="layout/header.jsp" %>
<%--<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
    </select>
</form>--%>

<div class="container">

    <form class="form-signin" action="/register" method="post" style="width:500px">
        <h2 class="form-signin-heading"><fmt:message key="register.label.signup"
                                                     bundle="${lang}"/></h2>

        <div class="form-group">
            <label for="inputLogin" class="control-label"><fmt:message key="register.label.login"
                                                                       bundle="${lang}"/></label>
            <input type="login" name="inputLogin" id="inputLogin" class="form-control" placeholder=
            <fmt:message key="register.label.login" bundle="${lang}"/> required autofocus>
        </div>

        <div class="form-group">
            <label for="inputName" class="control-label"><fmt:message key="register.label.fullname"
                                                                      bundle="${lang}"/></label>
            <input type="text" name="inputName" id="inputName" class="form-control" placeholder=
            <fmt:message key="register.label.fullname" bundle="${lang}"/> required>
        </div>

        <div class="form-group">
            <label for="inputPassword" class="control-label"><fmt:message key="password"
                                                                          bundle="${lang}"/></label>
            <input type="password" name="inputPassword" data-minlength="6" id="inputPassword"
                   class="form-control" placeholder=
                   <fmt:message key="password" bundle="${lang}"/> required>
        </div>

        <div class="form-group">
            <label for="inputPassword" class="control-label"><fmt:message
                    key="register.label.passwordConfirm" bundle="${lang}"/></label>
            <input type="password" name="inputPasswordConfirm" id="inputPasswordConfirm"
                   class="form-control" placeholder=
                   <fmt:message key="register.label.passwordConfirm" bundle="${lang}"/>  required>
        </div>

        <div class="form-group">
            <label for="inputEmail" class="control-label"><fmt:message key="register.label.email"
                                                                       bundle="${lang}"/></label>
            <input type="email" name="inputEmail" id="inputEmail" class="form-control" placeholder=
            <fmt:message key="register.label.email" bundle="${lang}"/> required>
        </div>

        <div class="form-group">
            <label for="inputPhone" class="control-label"><fmt:message key="register.label.phone"
                                                                       bundle="${lang}"/></label>
            <input type="text" class="form-control" name="inputPhone"
                   id="inputPhone" placeholder=
                   <fmt:message key="register.label.phone" bundle="${lang}"/> required>
        </div>
        <div class="form-group">
            <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message
                    key="register.label.signup" bundle="${lang}"/></button>
        </div>
    </form>

    <c:if test="${errorMessage != null}">
        <p>
            <font color="red"><fmt:message key="${errorMessage}" bundle="${lang}"/></font>
        </p>
    </c:if>
</div>
<%@include file="layout/footer.jsp" %>
</body>
</html>
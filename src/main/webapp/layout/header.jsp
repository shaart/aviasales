<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ?
  language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="com.epam.aviasales.bundles.global" var="lang"/>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/webjars/bootstrap/4.0.0-2/css/bootstrap.min.css">
</head>

<div class="container font-weight-normal">
    <header class="blog-header py-2">
        <div class="row flex-nowrap ">
            <div class="col-4 pt-1 justify-content-start" align="left">
                <form class="nav-link active">
                    <input type="image" src="resources/header/russian.png" value="ru"
                           name="language" onclick="submit()">
                    <input type="image" src="resources/header/english.png" value="en"
                           name="language" onclick="submit()">
                </form>
            </div>
            <div class="col-4 pt-2 align-items-center">
                <h3 class="masthead-brand">Avialsales</h3>
            </div>
            <div class="col-4 pt-4 d-flex align-items-center">
                <a class="btn btn-sm btn-outline-secondary" href="/"><fmt:message
                        key="home" bundle="${lang}"/></a>
                    <c:choose>
                        <c:when test="${account == null}">
                            <a class="btn btn-sm btn-outline-secondary" href="/register"><fmt:message
                                    key="sign_up" bundle="${lang}"/></a>
                            <a class="btn btn-sm btn-outline-secondary" href="/login"><fmt:message
                                    key="sign_in" bundle="${lang}"/></a>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${account.role != 'USER'}">
                                <a class="btn btn-sm btn-outline-secondary" href="/manage"><fmt:message
                                        key="manage.title" bundle="${lang}"/></a>
                            </c:if>
                            <a class="btn btn-sm btn-outline-success" href="/profile"><fmt:message
                                    key="profile" bundle="${lang}"/></a>
                            <a class="btn btn-sm btn-outline-danger"
                               href="/singout"><fmt:message key="sign_out" bundle="${lang}"/></a>
                        </c:otherwise>
                    </c:choose>
            </div>

        </div>
    </header>
</div>

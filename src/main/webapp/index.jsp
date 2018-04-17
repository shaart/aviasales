<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ?
    param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="com.epam.aviasales.bundles.global" var="lang"/>
<!DOCTYPE html>
<html lang="${language}">

<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>aviasales</title>
    <style>
        #image {
            background: url("pages/main/header.jpg") no-repeat;
            background-size: 100% 100%;
        }

        #tickets {
            background-color: azure;
            overflow: scroll; /* Добавляем полосы прокрутки */
            width: 100%; /* Ширина блока */
            height: 300px; /* Высота блока */
            padding: 10px; /* Поля вокруг текста */
            border: dashed 1px whitesmoke; /* Параметры рамки */
        }
    </style>
</head>
<header id="image">
    <form>
        <label>
            <select name="language" onchange="submit()">
                <option value="en" ${language==
                        'en' ? 'selected' : ''}>English
                </option>
                <option value="ru" ${language==
                        'ru' ? 'selected' : ''}>Русский
                </option>
            </select>
        </label>
    </form>

    <h1 align="center" style="color: wheat">Aviasales!</h1>
    <hr/>
</header>

<body bgcolor="#f0ffff">

<form action="/flights" method="post">
    <div align="right">
        <label>
            <fmt:message key="from" bundle="${lang}"/>
            <select name="flight_from" size="1">
                <c:forEach var="flight" items="${airports}">
                    <c:choose>
                        <c:when test="${flight.id == from}">
                            <option selected value="${flight.id}">${flight.name}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${flight.id}">${flight.name}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
        </label>
        <label>
            <fmt:message key="to" bundle="${lang}"/>
            <select name="flight_to" size="1">
                <c:forEach var="flight" items="${airports}">
                    <c:choose>
                        <c:when test="${flight.id == to}">
                            <option selected value="${flight.id}">${flight.name}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${flight.id}">${flight.name}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
        </label>
    </div>
    <div align="right">
        <label>
            <fmt:message key="departure" bundle="${lang}"/>
        </label>
        <label>
            <input type="date" name="departure" value=${date == null ? currentDate : date}>
        </label><br>
        <label>
            <input type="submit" value="<fmt:message key="find" bundle="${lang}"/>"/>
        </label>
    </div>
</form>

<c:if test="${flights.size() >= 1}">
    <h4><fmt:message key="flight.label.available_flights" bundle="${lang}"/>:</h4>
    <form action="/ticket" method="post">
        <div id="tickets">
            <c:forEach var="flight" items="${flights}">
                <label style="background-color: cadetblue; border-radius: 5px">
                    <input name="selected_flight" type="radio" value="${flight.id}" checked/> ${flight}
                    <h5><fmt:message key="flight.label.economy_class" bundle="${lang}"/>
                        <fmt:formatNumber value="${flight.baseTicketPrice}"
                                          minFractionDigits="2" maxFractionDigits="2"/> €,
                        <fmt:message key="flight.label.free_seats"
                                     bundle="${lang}"/> ${flight.freeSeatEconomy}</h5>
                    <h5><fmt:message key="flight.label.business_class" bundle="${lang}"/>
                        <fmt:formatNumber value="${flight.baseTicketPrice*1.4}"
                                          minFractionDigits="2" maxFractionDigits="2"/> €,
                        <fmt:message key="flight.label.free_seats"
                                     bundle="${lang}"/> ${flight.freeSeatEconomy}</h5>
                </label>
            </c:forEach>
        </div>
        <br>
        <c:choose>
            <c:when test="${account == null}">
                <h4 style="color: red"><fmt:message key="registrate.warning" bundle="${lang}"/></h4>
            </c:when>
            <c:otherwise>
                <div align="right">
                    <input type="submit" value="<fmt:message key="select" bundle="${lang}"/>">
                </div>
            </c:otherwise>
        </c:choose>
    </form>
    <h4><c:if test="${error != null}"> ${error}</c:if></h4>
</c:if>

<footer>
    <br>
    <div align="center">
        <a href="/"><fmt:message key="home" bundle="${lang}"/></a>
    </div>
</footer>

<script type="text/javascript" src="/webjars/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>
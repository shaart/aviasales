<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ?
    param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<c:set var="MULTIPLIER_PRICE_FOR_BUSINESS_TICKETS" value="1.4"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="com.epam.aviasales.bundles.global" var="lang"/>
<!DOCTYPE html>
<html lang="${language}">

<head>
    <title>aviasales</title>
</head>

<%@include file="layout/header.jsp" %>

<body>
<form action="/flights" method="post">
    <div align="right">
        <label>
            <fmt:message key="from" bundle="${lang}"/>
            <select name="flight_from" size="1" required>
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
            <select name="flight_to" size="1" required>
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
            <input type="date" name="departure" value=${date.equals("") ? currentDate : date}>
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
                    <input name="selected_flight" type="radio" value="${flight.id}"/> ${flight}
                    <h5><fmt:message key="flight.label.economy_class" bundle="${lang}"/>
                        <fmt:formatNumber value="${flight.baseTicketPrice}"
                                          minFractionDigits="2" maxFractionDigits="2"/> €,
                        <fmt:message key="flight.label.free_seats"
                                     bundle="${lang}"/> ${flight.freeSeatEconomy}</h5>
                    <h5><fmt:message key="flight.label.business_class" bundle="${lang}"/>
                        <fmt:formatNumber
                                value="${flight.baseTicketPrice * MULTIPLIER_PRICE_FOR_BUSINESS_TICKETS}"
                                minFractionDigits="2" maxFractionDigits="2"/> €,
                        <fmt:message key="flight.label.free_seats"
                                     bundle="${lang}"/> ${flight.freeSeatBusiness}</h5>
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

</body>

<%@include file="layout/footer.jsp" %>

</html>
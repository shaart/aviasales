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
<%@include file="../layout/header.jsp" %>
<div class="container">
    <table class="table-bordered">
        <thead>
        <tr>
            <th><fmt:message key="flight.label.id" bundle="${lang}"/></th>
            <th><fmt:message key="flight.label.from" bundle="${lang}"/></th>
            <th><fmt:message key="flight.label.to" bundle="${lang}"/></th>
            <th><fmt:message key="flight.label.airplane" bundle="${lang}"/></th>
            <th><fmt:message key="flight.label.departure" bundle="${lang}"/></th>
            <th><fmt:message key="flight.label.arrival" bundle="${lang}"/></th>
            <th><fmt:message key="flight.label.base_ticket_price" bundle="${lang}"/></th>
            <th><fmt:message key="flight.label.extra_baggage_price" bundle="${lang}"/></th>
            <th><fmt:message key="flight.label.free_seat_economy" bundle="${lang}"/></th>
            <th><fmt:message key="flight.label.free_seat_business" bundle="${lang}"/></th>
        </tr>
        </thead>
        <tbody>
        <tr>

        </tr>
        </tbody>
    </table>
    <ul>
    <c:forEach var="flight" items="${flights}">
        <li>${flight.airplane.name}</li>
    </c:forEach>
    </ul>
</div>
</div>
<%@include file="../layout/footer.jsp" %>
</body>
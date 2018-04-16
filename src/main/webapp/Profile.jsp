<%@ page import="java.time.LocalDateTime" %><%--
  Created by IntelliJ IDEA.
  User: vikto
  Date: 4/12/2018
  Time: 10:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="/WEB-INF/functions.tld" prefix="f" %>
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


<h2>
    Name : ${account.name}<br>
    LoginName : ${account.login}<br>
    Email : ${account.email}<br>
    Phone : ${account.phone}
</h2>
<c:choose>
    <c:when test="${noTickets == false}">
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col"><fmt:message key="profile.label.name" bundle="${lang}"/></th>
                <th scope="col"><fmt:message key="profile.label.passport" bundle="${lang}"/></th>
                <th scope="col"><fmt:message key="profile.label.dob" bundle="${lang}"/></th>
                <th scope="col"><fmt:message key="profile.label.airportFrom" bundle="${lang}"/></th>
                <th scope="col"><fmt:message key="profile.label.airportTo" bundle="${lang}"/></th>
                <th scope="col"><fmt:message key="profile.label.departureTime"
                                             bundle="${lang}"/></th>
                <th scope="col"><fmt:message key="profile.label.arrivalTime" bundle="${lang}"/></th>
                <th scope="col"><fmt:message key="profile.label.extraBaggagePrice"
                                             bundle="${lang}"/></th>
                <th scope="col"><fmt:message key="profile.label.class" bundle="${lang}"/></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="ticket" items="${tickets}">
                <tr>
                    <form action="/profile" method="post">
                        <td>${ticket.personalData.name}</td>
                        <td>${ticket.personalData.passport} </td>
                        <td>${ticket.personalData.dateOfBirth}</td>
                        <td>${ticket.flight.fromAirport.name}</td>
                        <td>${ticket.flight.toAirport.name}</td>
                        <td>${ticket.flight.departureTime}</td>
                        <td>${ticket.flight.arrivalTime}</td>
                        <td>${ticket.flight.extraBaggagePrice}</td>
                        <td>
                            <c:choose>
                                <c:when test="${ticket.isBusiness == true}"><fmt:message
                                        key="profile.label.business"
                                        bundle="${lang}"/></c:when>
                                <c:otherwise><fmt:message key="profile.label.economy"
                                                          bundle="${lang}"/></c:otherwise>
                            </c:choose>
                        </td>

                        <td>
                            <c:if test="${f:isBefore(ticket.flight.departureTime)}">
                                <button type="submit" class="btn btn-primary btn-sm"><fmt:message
                                        key="profile.label.return"
                                        bundle="${lang}"/>
                                </button>
                            </c:if>
                        </td>
                        <input type="hidden" name="ticketId" value="${ticket.id}">
                    </form>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise><fmt:message key="profile.message.noTicket" bundle="${lang}"/></c:otherwise>
</c:choose>
</body>
</html>

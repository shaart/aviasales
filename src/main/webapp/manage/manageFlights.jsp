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
    <title><fmt:message key="manage.title" bundle="${lang}"/></title>
    <%--<script type="text/javascript" href="js/tableSearch.js"></script>--%>
    <script type="text/javascript">
      function searchAtTable() {
        // Declare variables
        var input, filter, table, tr, td, i;
        input = document.getElementById("seekFor");
        filter = input.value.toUpperCase();
        table = document.getElementById("dataTable");
        tr = table.getElementsByTagName("tr");
        var rows = tr.getElementsByClassName("lookatme");
        for (i = 0; i < tr.length; i++) {
          td = tr[i].getElementsByTagName("td");
          if (td) {
            if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
              tr[i].style.display = "";
            } else {
              tr[i].style.display = "none";
            }
          }
        }
      }
    </script>
</head>
<body>
<%@include file="../layout/header.jsp" %>
<div class="container" style="width: 100%">
    <span class="glyphicon glyphicon-search"></span>
    <input id="seekFor" type="text" onkeyup="searchAtTable()"
           placeholder="<fmt:message key="flight.label.search" bundle="${lang}"/>"
           title="<fmt:message key="flight.label.search.title" bundle="${lang}"/>">
    <table id="dataTable" class="table-bordered">
        <thead>
        <tr>
            <th class="text-center" style="width: 3%;">
                <fmt:message key="flight.label.id" bundle="${lang}"/></th>
            <th class="text-center" style="width: 7%;">
                <fmt:message key="flight.label.from" bundle="${lang}"/></th>
            <th class="text-center" style="width: 7%;">
                <fmt:message key="flight.label.to" bundle="${lang}"/></th>
            <th class="text-center" style="width: 7%;">
                <fmt:message key="flight.label.airplane" bundle="${lang}"/></th>
            <th class="text-center" style="width: 7%;">
                <fmt:message key="flight.label.departure" bundle="${lang}"/></th>
            <th class="text-center" style="width: 7%;">
                <fmt:message key="flight.label.arrival" bundle="${lang}"/></th>
            <th class="text-center" style="width: 5%;">
                <fmt:message key="flight.label.base_ticket_price" bundle="${lang}"/></th>
            <th class="text-center" style="width: 5%;">
                <fmt:message key="flight.label.extra_baggage_price" bundle="${lang}"/></th>
            <th class="text-center" style="width: 3%;">
                <fmt:message key="flight.label.free_seat_economy" bundle="${lang}"/></th>
            <th class="text-center" style="width: 3%;">
                <fmt:message key="flight.label.free_seat_business" bundle="${lang}"/></th>
            <th class="text-center" style="width: 10%;">
                <fmt:message key="flight.label.control" bundle="${lang}"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="flight" items="${flights}">
            <tr>
                <form action="/manage/flights" method="post">
                    <td><input readonly type="text" class="form-control" width="10" name="id"
                               value="${flight.id}"></td>
                    <td><input type="text" class="form-control" name="fromAirport"
                               value="${flight.fromAirport.name}"></td>
                    <td><input type="text" class="form-control" name="toAirport"
                               value="${flight.toAirport.name}"></td>
                    <td><input type="text" class="form-control" name="airplane"
                               value="${flight.airplane.name}"></td>
                    <td><input class="form-control" name="departureTime"
                               value="${juf:getLocalizedDateTime(flight.departureTime, language)}">
                    <td>
                        <input type="text" class="form-control"
                               name="arrivalTime"
                               value="${juf:getLocalizedDateTime(flight.arrivalTime, language)}">
                    </td>
                    <td><input type="number" class="form-control" name="baseTicketPrice"
                               value="${flight.baseTicketPrice}"></td>
                    <td><input type="number" class="form-control" name="extraBaggagePrice"
                               value="${flight.extraBaggagePrice}"></td>
                    <td><input type="number" class="form-control" name="freeSeatEconomy"
                               value="${flight.freeSeatEconomy}"></td>
                    <td><input type="number" class="form-control" name="freeSeatBusiness"
                               value="${flight.freeSeatBusiness}"></td>
                    <td>
                        <div class="btn-group btn-group-justified">
                            <div class="btn-group">
                                <input type="submit" class="btn btn-primary" name="actionSave"
                                       value="<fmt:message key="flight.label.control.save" bundle="${lang}"/>">
                            </div>
                            <div class="btn-group">
                                <input type="submit" class="btn btn-primary" name="actionDelete"
                                       value="<fmt:message key="flight.label.control.delete" bundle="${lang}"/>">
                            </div>
                        </div>
                    </td>
                </form>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@include file="../layout/footer.jsp" %>
</body>
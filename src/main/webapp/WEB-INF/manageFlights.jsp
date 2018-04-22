<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://aviasales.epam.com/jsp/util/functions" prefix="juf" %>
<c:set var="language" value="${not empty param.language ? param.language :
    not empty language ?  language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="com.epam.aviasales.bundles.global" var="lang"/>
<c:if test="${account == null || (account.role != 'ADMIN' && account.role != 'MANAGER')}">
    <% response.sendError(403); %>
</c:if>
<c:set var="COLUMNS_FIRST_NUM" value="0"/>
<c:set var="COLUMNS_COUNT" value="10"/>
<c:set var="page" value="${page == null || page < 1 ? 1 : page}"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <title><fmt:message key="manage.flights.title" bundle="${lang}"/></title>
</head>
<script type="text/javascript">
  var COLUMNS_START_FROM = ${COLUMNS_FIRST_NUM};
  var COLUMNS_COUNT = ${COLUMNS_COUNT};
  // tr[0] - is header
  // tr[1] - is filtering-row
  var FIRST_DATA_ROW_INDEX = 2;

  function clearFilterFields() {
    for (var i = COLUMNS_START_FROM; i < COLUMNS_COUNT; i++) {
      document.getElementById("seekAtColumn" + i).value = "";
    }
    searchAtTable();
  }

  function clearSearchFields() {
    var searchingRow = document.getElementById("searching-row");
    if (searchingRow) {
      var inputs = searchingRow.getElementsByTagName("input");
      for (var i = 0; i < inputs.length; i++) {
        var input = inputs[i];
        if (input && input.type !== "button" && input.type !== "submit") {
          input.value = "";
        }
      }
      var selects = searchingRow.getElementsByTagName("select");
      for (var i = 0; i < selects.length; i++) {
        var select = selects[i];
        if (select) {
          select.selectedIndex = 0;
        }
      }
    }
  }

  function searchAtTable() {
    var table, tr, td, tds, i, j, matches;
    var filterValues = [COLUMNS_COUNT];
    for (i = COLUMNS_START_FROM; i < COLUMNS_COUNT; i++) {
      filterValues[i] = document.getElementById("seekAtColumn" + i).value.toUpperCase();
    }
    table = document.getElementById("dataTable");
    tr = table.getElementsByTagName("tr");

    for (i = FIRST_DATA_ROW_INDEX; i < tr.length; i++) {
      tds = tr[i].getElementsByTagName("td");
      matches = true;
      for (j = 0; j < COLUMNS_COUNT; j++) {
        td = tds[j];
        if (td) {
          var dataCellInputValue;
          var childInput = td.getElementsByTagName("input")[0];
          if (childInput) {
            dataCellInputValue = childInput.value.toUpperCase();
          } else {
            var childSelect = td.getElementsByTagName("select")[0];
            if (childSelect) {
              dataCellInputValue = childSelect.options[childSelect.selectedIndex].value.toUpperCase();
            }
          }
          if (dataCellInputValue.indexOf(filterValues[j]) < 0) {
            matches = false;
            break;
          }
        }
      }
      if (matches) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }
  }
</script>
<body>
<%@include file="layout/header.jsp" %>
<%@include file="layout/manageNavigation.jsp" %>
<c:if test="${not empty error}">
    <div class="alert alert-danger">
            ${error}
    </div>
</c:if>
<div class="container">
    <h2><fmt:message key="flight.header.search" bundle="${lang}"/></h2>
    <table id="searchTable">
        <thead>
        <tr>
            <th class="text-center" style="width: 5%; min-width: 70px;">
                <fmt:message key="flight.label.id" bundle="${lang}"/></th>
            <th class="text-center" style="width: 20%; min-width: 110px;">
                <fmt:message key="flight.label.from" bundle="${lang}"/></th>
            <th class="text-center" style="width: 20%; min-width: 110px;">
                <fmt:message key="flight.label.to" bundle="${lang}"/></th>
            <th class="text-center" style="width: 20%; min-width: 120px;">
                <fmt:message key="flight.label.airplane" bundle="${lang}"/></th>
            <th class="text-center" style="width: 5%; max-width: 240px;">
                <fmt:message key="flight.label.departure" bundle="${lang}"/></th>
            <th class="text-center" style="width: 5%; max-width: 240px;">
                <fmt:message key="flight.label.arrival" bundle="${lang}"/></th>
            <th class="text-center" style="width: 10%; min-width: 80px;">
                <fmt:message key="flight.label.base_ticket_price" bundle="${lang}"/></th>
            <th class="text-center" style="width: 10%; min-width: 75px;">
                <fmt:message key="flight.label.extra_baggage_price" bundle="${lang}"/></th>
            <th class="text-center" style="width: 5%; min-width: 60px;">
                <fmt:message key="flight.label.free_seat_economy" bundle="${lang}"/></th>
            <th class="text-center" style="width: 5%; min-width: 60px;">
                <fmt:message key="flight.label.free_seat_business" bundle="${lang}"/></th>
            <th class="text-center" style="width: 20%; min-width: 170px;">
                <fmt:message key="flight.label.control" bundle="${lang}"/></th>
        </tr>
        </thead>
        <tbody>
        <tr id="searching-row">
            <form id="search-form" action="/manage/flights" method="get">
                <td><input type="text" class="form-control" width="10" name="id"
                           value="${id}"
                           placeholder="<fmt:message key="flight.label.id" bundle="${lang}"/>">
                </td>
                <td>
                    <select class="form-control" name="fromAirport" size="1">
                        <c:choose>
                            <c:when test="${not empty fromAirport}">
                                <option selected value="">
                                    <fmt:message key="flight.label.choose" bundle="${lang}"/>
                                </option>
                            </c:when>
                            <c:otherwise>
                                <option value="">
                                    <fmt:message key="flight.label.choose" bundle="${lang}"/>
                                </option>
                            </c:otherwise>
                        </c:choose>
                        <c:forEach var="airport" items="${airports}">
                            <c:choose>
                                <c:when test="${airport.name.equals(fromAirport)}">
                                    <option selected
                                            value="${airport.name}">${airport.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${airport.name}">${airport.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <select class="form-control" data-live-search="true" name="toAirport"
                            size="1">
                        <c:choose>
                            <c:when test="${not empty toAirport}">
                                <option selected value="">
                                    <fmt:message key="flight.label.choose" bundle="${lang}"/>
                                </option>
                            </c:when>
                            <c:otherwise>
                                <option value="">
                                    <fmt:message key="flight.label.choose" bundle="${lang}"/>
                                </option>
                            </c:otherwise>
                        </c:choose>
                        <c:forEach var="airport" items="${airports}">
                            <c:choose>
                                <c:when test="${airport.name.equals(toAirport)}">
                                    <option selected
                                            value="${airport.name}">${airport.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${airport.name}">${airport.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <select class="form-control" data-live-search="true" name="airplane"
                            size="1">
                        <c:choose>
                            <c:when test="${not empty airplane}">
                                <option selected value="">
                                    <fmt:message key="flight.label.choose" bundle="${lang}"/>
                                </option>
                            </c:when>
                            <c:otherwise>
                                <option value="">
                                    <fmt:message key="flight.label.choose" bundle="${lang}"/>
                                </option>
                            </c:otherwise>
                        </c:choose>
                        <c:forEach var="curAirplane" items="${airplanes}">
                            <c:choose>
                                <c:when test="${curAirplane.name.equals(airplane)}">
                                    <option selected
                                            value="${curAirplane.name}">${curAirplane.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${curAirplane.name}">${curAirplane.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
                <td><input type="datetime-local" class="form-control" name="departureTime"
                           value="${departureTime}"
                           placeholder="<fmt:message key="flight.label.departure" bundle="${lang}"/>"
                           pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}">
                <td>
                    <input type="datetime-local" class="form-control" name="arrivalTime"
                           value="${arrivalTime}"
                           placeholder="<fmt:message key="flight.label.arrival" bundle="${lang}"/>"
                           pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}">
                </td>
                <td><input type="number" class="form-control" name="baseTicketPrice"
                           value="${baseTicketPrice}"
                           placeholder="<fmt:message key="flight.label.base_ticket_price" bundle="${lang}"/>">
                </td>
                <td><input type="number" class="form-control" name="extraBaggagePrice"
                           value="${extraBaggagePrice}"
                           placeholder="<fmt:message key="flight.label.extra_baggage_price" bundle="${lang}"/>">
                </td>
                <td><input type="number" class="form-control" name="freeSeatEconomy"
                           value="${freeSeatEconomy}"
                           placeholder="<fmt:message key="flight.label.free_seat_economy" bundle="${lang}"/>">
                </td>
                <td><input type="number" class="form-control" name="freeSeatBusiness"
                           value="${freeSeatBusiness}"
                           placeholder="<fmt:message key="flight.label.free_seat_business" bundle="${lang}"/>">
                </td>
                <td>
                    <div class="btn-group btn-group-justified">
                        <div class="btn-group">
                            <input id="search-button" type="submit" class="btn btn-primary"
                                   value="<fmt:message key="flight.label.search" bundle="${lang}"/>">
                        </div>
                        <div class="btn-group">
                            <input class="btn btn-primary" type="button"
                                   onclick="clearSearchFields()"
                                   value="<fmt:message key="flight.label.filter.clear" bundle="${lang}"/>"/>
                        </div>
                    </div>
                </td>
            </form>
        </tr>
        </tbody>
    </table>
    <br>
    <h2><fmt:message key="flight.header.add" bundle="${lang}"/></h2>
    <table id="addTable">
        <thead>
        <tr>
            <th class="text-center" style="width: 5%; min-width: 70px;">
                <fmt:message key="flight.label.id" bundle="${lang}"/></th>
            <th class="text-center" style="width: 20%; min-width: 110px;">
                <fmt:message key="flight.label.from" bundle="${lang}"/></th>
            <th class="text-center" style="width: 20%; min-width: 110px;">
                <fmt:message key="flight.label.to" bundle="${lang}"/></th>
            <th class="text-center" style="width: 20%; min-width: 120px;">
                <fmt:message key="flight.label.airplane" bundle="${lang}"/></th>
            <th class="text-center" style="width: 5%; max-width: 240px;">
                <fmt:message key="flight.label.departure" bundle="${lang}"/></th>
            <th class="text-center" style="width: 5%; max-width: 240px;">
                <fmt:message key="flight.label.arrival" bundle="${lang}"/></th>
            <th class="text-center" style="width: 10%; min-width: 80px;">
                <fmt:message key="flight.label.base_ticket_price" bundle="${lang}"/></th>
            <th class="text-center" style="width: 10%; min-width: 75px;">
                <fmt:message key="flight.label.extra_baggage_price" bundle="${lang}"/></th>
            <th class="text-center" style="width: 5%; min-width: 60px;">
                <fmt:message key="flight.label.free_seat_economy" bundle="${lang}"/></th>
            <th class="text-center" style="width: 5%; min-width: 60px;">
                <fmt:message key="flight.label.free_seat_business" bundle="${lang}"/></th>
            <th class="text-center" style="width: 20%; min-width: 170px;">
                <fmt:message key="flight.label.control" bundle="${lang}"/></th>
        </tr>
        </thead>
        <tbody>
        <tr id="adding-row">
            <form id="addForm" action="/manage/flights" method="post">
                <td><input readonly type="text" disabled class="form-control" width="10" name="id"
                           value="-"
                           placeholder="<fmt:message key="flight.label.id" bundle="${lang}"/>">
                </td>
                <td>
                    <select required oninvalid='this.setCustomValidity("<fmt:message
                            key="page.error.field.is.required" bundle="${lang}"/>")'
                            oninput="setCustomValidity('')" class="form-control" name="fromAirport"
                            size="1">
                        <c:choose>
                            <c:when test="${not empty fromAirport}">
                                <option selected value="">
                                    <fmt:message key="flight.label.choose" bundle="${lang}"/>
                                </option>
                            </c:when>
                            <c:otherwise>
                                <option value="">
                                    <fmt:message key="flight.label.choose" bundle="${lang}"/>
                                </option>
                            </c:otherwise>
                        </c:choose>
                        <c:forEach var="airport" items="${airports}">
                            <c:choose>
                                <c:when test="${airport.name.equals(fromAirport)}">
                                    <option selected
                                            value="${airport.name}">${airport.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${airport.name}">${airport.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <select required oninvalid='this.setCustomValidity("<fmt:message
                            key="page.error.field.is.required" bundle="${lang}"/>")'
                            oninput="setCustomValidity('')" class="form-control"
                            data-live-search="true" name="toAirport"
                            size="1">
                        <c:choose>
                            <c:when test="${not empty toAirport}">
                                <option selected value="">
                                    <fmt:message key="flight.label.choose" bundle="${lang}"/>
                                </option>
                            </c:when>
                            <c:otherwise>
                                <option value="">
                                    <fmt:message key="flight.label.choose" bundle="${lang}"/>
                                </option>
                            </c:otherwise>
                        </c:choose>
                        <c:forEach var="airport" items="${airports}">
                            <c:choose>
                                <c:when test="${airport.name.equals(toAirport)}">
                                    <option selected
                                            value="${airport.name}">${airport.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${airport.name}">${airport.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <select required oninvalid='this.setCustomValidity("<fmt:message
                            key="page.error.field.is.required" bundle="${lang}"/>")'
                            oninput="setCustomValidity('')" class="form-control"
                            data-live-search="true" name="airplane"
                            size="1">
                        <c:choose>
                            <c:when test="${not empty airplane}">
                                <option selected value="">
                                    <fmt:message key="flight.label.choose" bundle="${lang}"/>
                                </option>
                            </c:when>
                            <c:otherwise>
                                <option value="">
                                    <fmt:message key="flight.label.choose" bundle="${lang}"/>
                                </option>
                            </c:otherwise>
                        </c:choose>
                        <c:forEach var="curAirplane" items="${airplanes}">
                            <c:choose>
                                <c:when test="${curAirplane.name.equals(airplane)}">
                                    <option selected
                                            value="${curAirplane.name}">${curAirplane.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${curAirplane.name}">${curAirplane.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
                <td><input type="datetime-local" class="form-control" name="departureTime" value=""
                           placeholder="<fmt:message key="flight.label.departure" bundle="${lang}"/>"
                           pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}" required>
                <td>
                    <input type="datetime-local" class="form-control" name="arrivalTime" value=""
                           placeholder="<fmt:message key="flight.label.arrival" bundle="${lang}"/>"
                           pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}" required>
                </td>
                <td><input required oninvalid='this.setCustomValidity("<fmt:message
                        key="page.error.field.is.required" bundle="${lang}"/>")'
                           oninput="setCustomValidity('')" type="number" class="form-control"
                           name="baseTicketPrice"
                           value=""
                           placeholder="<fmt:message key="flight.label.base_ticket_price" bundle="${lang}"/>">
                </td>
                <td><input required oninvalid='this.setCustomValidity("<fmt:message
                        key="page.error.field.is.required" bundle="${lang}"/>")'
                           oninput="setCustomValidity('')" type="number" class="form-control"
                           name="extraBaggagePrice"
                           value=""
                           placeholder="<fmt:message key="flight.label.extra_baggage_price" bundle="${lang}"/>">
                </td>
                <td><input required oninvalid='this.setCustomValidity("<fmt:message
                        key="page.error.field.is.required" bundle="${lang}"/>")'
                           oninput="setCustomValidity('')" type="number" class="form-control"
                           name="freeSeatEconomy"
                           value=""
                           placeholder="<fmt:message key="flight.label.free_seat_economy" bundle="${lang}"/>">
                </td>
                <td><input required oninvalid='this.setCustomValidity("<fmt:message
                        key="page.error.field.is.required" bundle="${lang}"/>")'
                           oninput="setCustomValidity('')" type="number" class="form-control"
                           name="freeSeatBusiness"
                           value=""
                           placeholder="<fmt:message key="flight.label.free_seat_business" bundle="${lang}"/>">
                </td>
                <td>
                    <div class="btn-group btn-group-justified">
                        <div class="btn-group">
                            <input id="add-button" type="submit" class="btn btn-primary"
                                   name="actionAdd"
                                   value="<fmt:message key="flight.label.control.add" bundle="${lang}"/>">
                        </div>
                    </div>
                </td>
            </form>
        </tr>
        </tbody>
    </table>
    <br>
    <h2><fmt:message key="flight.header.result" bundle="${lang}"/></h2>
    <c:choose>
        <c:when test="${flights == null || flights.size() == 0}">
            <h3><fmt:message key="page.result.no.results" bundle="${lang}"/></h3>
        </c:when>
        <c:otherwise>
            <div class="text-center">
                <nav aria-label="page navigation">
                    <ul class="pagination">
                        <c:if test="${page > 1}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="${prevPageURL}">
                                    <fmt:message key="page.previous" bundle="${lang}"/>
                                </a>
                            </li>
                            <li class="page-item">
                                <a class="page-link" href="${prevPageURL}">
                                        ${page-1}
                                </a>
                            </li>
                        </c:if>
                        <li class="page-item">
                            <a class="page-link" href="${currPageURL}">
                                    ${page}
                            </a>
                        </li>
                        <li class=" page-item">
                            <a class="page-link" href="${nextPageURL}">
                                    ${page+1}
                            </a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="${nextPageURL}">
                                <fmt:message key="page.next" bundle="${lang}"/>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
            <table id="dataTable" class="table-responsive">
                <thead>
                <tr>
                    <th class="text-center" style="width: 5%; min-width: 70px;">
                        <fmt:message key="flight.label.id" bundle="${lang}"/></th>
                    <th class="text-center" style="width: 20%; min-width: 110px;">
                        <fmt:message key="flight.label.from" bundle="${lang}"/></th>
                    <th class="text-center" style="width: 20%; min-width: 110px;">
                        <fmt:message key="flight.label.to" bundle="${lang}"/></th>
                    <th class="text-center" style="width: 20%; min-width: 120px;">
                        <fmt:message key="flight.label.airplane" bundle="${lang}"/></th>
                    <th class="text-center" style="width: 5%; max-width: 240px;">
                        <fmt:message key="flight.label.departure" bundle="${lang}"/></th>
                    <th class="text-center" style="width: 5%; max-width: 240px;">
                        <fmt:message key="flight.label.arrival" bundle="${lang}"/></th>
                    <th class="text-center" style="width: 10%; min-width: 80px;">
                        <fmt:message key="flight.label.base_ticket_price" bundle="${lang}"/></th>
                    <th class="text-center" style="width: 10%; min-width: 75px;">
                        <fmt:message key="flight.label.extra_baggage_price" bundle="${lang}"/></th>
                    <th class="text-center" style="width: 5%; min-width: 60px;">
                        <fmt:message key="flight.label.free_seat_economy" bundle="${lang}"/></th>
                    <th class="text-center" style="width: 5%; min-width: 60px;">
                        <fmt:message key="flight.label.free_seat_business" bundle="${lang}"/></th>
                    <th class="text-center" style="width: 20%; min-width: 170px;">
                        <fmt:message key="flight.label.control" bundle="${lang}"/></th>
                </tr>
                </thead>
                <tbody>
                <tr id="filtering-row">
                    <c:forEach begin="${COLUMNS_FIRST_NUM}" end="${COLUMNS_COUNT - 1}"
                               var="counter">
                        <td>
                            <div class="form-group has-feedback">
                                <i class="form-control-feedback glyphicon glyphicon-search"></i>
                                <input id="seekAtColumn${counter}" class="form-control" type="text"
                                       onkeyup="searchAtTable()"
                                       placeholder="<fmt:message key="flight.label.filter" bundle="${lang}"/>"
                                       title="<fmt:message key="flight.label.filter.title" bundle="${lang}"/>">
                            </div>
                        </td>
                    </c:forEach>
                    <td>
                        <div class="btn-group btn-group-justified">
                            <div class="btn-group">
                                <input class="btn btn-primary" type="button"
                                       onclick="clearFilterFields()"
                                       value="<fmt:message key="flight.label.filter.clear" bundle="${lang}"/>"/>
                            </div>
                        </div>
                    </td>
                </tr>
                </tr>
                <c:forEach var="flight" items="${flights}">
                    <tr>
                        <form action="/manage/flights" method="post">
                            <td><input readonly type="text" class="form-control" width="10"
                                       name="id"
                                       value="${flight.id}"></td>
                            <td>
                                <select class="form-control" name="fromAirport" size="1">
                                    <c:forEach var="airport" items="${airports}">
                                        <c:choose>
                                            <c:when test="${airport.name == flight.fromAirport.name}">
                                                <option selected
                                                        value="${airport.name}">${airport.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${airport.name}">${airport.name}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>
                                <select class="form-control" data-live-search="true"
                                        name="toAirport"
                                        size="1">
                                    <c:forEach var="airport" items="${airports}">
                                        <c:choose>
                                            <c:when test="${airport.name == flight.toAirport.name}">
                                                <option selected
                                                        value="${airport.name}">${airport.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${airport.name}">${airport.name}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>
                                <select class="form-control" data-live-search="true" name="airplane"
                                        size="1">
                                    <c:forEach var="airplane" items="${airplanes}">
                                        <c:choose>
                                            <c:when test="${airplane.name == flight.airplane.name}">
                                                <option selected
                                                        value="${airplane.name}">${airplane.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${airplane.name}">${airplane.name}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </td>
                            <td><fmt:parseDate value="${ flight.departureTime }"
                                               pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                                               type="both"/>
                                <input type="datetime-local" class="form-control"
                                       name="departureTime"
                                       value="<fmt:formatDate pattern="yyyy-MM-dd'T'HH:mm" value="${ parsedDateTime }"/>"
                                       pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}"
                                       required>
                                    <%--value="${juf:getLocalizedDateTime(flight.departureTime, language)}">--%>
                            </td>
                            <td><fmt:parseDate value="${ flight.arrivalTime }"
                                               pattern="yyyy-MM-dd'T'HH:mm"
                                               var="parsedDateTime" type="both"/>
                                <input type="datetime-local" class="form-control" name="arrivalTime"
                                       value="<fmt:formatDate pattern="yyyy-MM-dd'T'HH:mm" value="${ parsedDateTime }"/>"
                                       pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}"
                                       required>
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
                                        <input type="submit" class="btn btn-primary"
                                               name="actionSave"
                                               value="<fmt:message key="flight.label.control.save" bundle="${lang}"/>">
                                    </div>
                                    <div class="btn-group">
                                        <input type="submit" class="btn btn-primary"
                                               name="actionDelete"
                                               value="<fmt:message key="flight.label.control.delete" bundle="${lang}"/>">
                                    </div>
                                </div>
                            </td>
                        </form>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="text-center">
                <nav aria-label="page navigation">
                    <ul class="pagination">
                        <c:if test="${page > 1}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="${prevPageURL}">
                                    <fmt:message key="page.previous" bundle="${lang}"/>
                                </a>
                            </li>
                            <li class="page-item">
                                <a class="page-link" href="${prevPageURL}">
                                        ${page-1}
                                </a>
                            </li>
                        </c:if>
                        <li class="page-item">
                            <a class="page-link" href="${currPageURL}">
                                    ${page}
                            </a>
                        </li>
                        <li class=" page-item">
                            <a class="page-link" href="${nextPageURL}">
                                    ${page+1}
                            </a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="${nextPageURL}">
                                <fmt:message key="page.next" bundle="${lang}"/>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </c:otherwise>
    </c:choose>
</div>
<%@include file="layout/footer.jsp" %>
</body>
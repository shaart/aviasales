<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://aviasales.epam.com/jsp/util/functions" prefix="juf" %>
<c:set var="language" value="${not empty param.language ? param.language :
    not empty language ?  language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="com.epam.aviasales.bundles.global" var="lang"/>
<c:set var="COLUMNS_FIRST_NUM" value="0"/>
<c:set var="COLUMNS_COUNT" value="10"/>
<c:set var="page" value="${page == null || page < 1 ? 1 : page}"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <title><fmt:message key="manage.title" bundle="${lang}"/></title>
    <%--<script type="text/javascript" href="js/tableSearch.js"></script>--%>
</head>
<script type="text/javascript">
  var COLUMNS_START_FROM = ${COLUMNS_FIRST_NUM};
  var COLUMNS_COUNT = ${COLUMNS_COUNT};
  // tr[0] - is header
  // tr[1] - is searching-row
  // tr[2] - is adding-row
  var FIRST_DATA_ROW_INDEX = 3;

  function clearSearchFields() {
    for (var i = COLUMNS_START_FROM; i < COLUMNS_COUNT; i++) {
      document.getElementById("seekAtColumn" + i).value = "";
    }
    searchAtTable();
  }

  function searchAtTable() {
    // Declare variables
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
            // console.log("td.children[0]: " + childInput);
            dataCellInputValue = childInput.value.toUpperCase();
            // console.log("td.children[0].value: " + dataCellInputValue);
            // console.log("seek for: " + filterValues[j]);
            // console.log("indexOf: " + dataCellInputValue.indexOf(filterValues[j]));
            // console.log("matches ? " + (dataCellInputValue.indexOf(filterValues[j]) >= 0));
            // console.log("not matches ? " + (dataCellInputValue.indexOf(filterValues[j]) < 0));
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
        // console.log("-> DISPLAY");
      } else {
        tr[i].style.display = "none";
        // console.log("-> HIDE");
      }
    }
  }
</script>
<body>
<%@include file="../layout/header.jsp" %>
<div class="container" style="width: 100%">
    <div style="">
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <c:if test="${page > 1}">
                    <li class="page-item">
                        <a class="page-link" href="?page=${page - 1}">
                            <fmt:message key="page.previous" bundle="${lang}"/>
                        </a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="?page=${page - 1}">
                                ${page-1}
                        </a>
                    </li>
                </c:if>
                <li class="page-item">
                    <a class="page-link" href="?page=${page}">${page}</a>
                </li>
                <li class="page-item">
                    <a class="page-link" href="?page=${page + 1}">
                        ${page+1}
                    </a>
                </li>
                <li class="page-item">
                    <a class="page-link" href="?page=${page + 1}">
                        <fmt:message key="page.next" bundle="${lang}"/>
                    </a>
                </li>
            </ul>
        </nav>
    </div>
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
        <tr id="searching-row">
            <c:forEach begin="${COLUMNS_FIRST_NUM}" end="${COLUMNS_COUNT - 1}" var="counter">
                <td>
                    <div class="form-group has-feedback">
                        <i class="form-control-feedback glyphicon glyphicon-search"></i>
                        <input id="seekAtColumn${counter}" class="form-control" type="text"
                               onkeyup="searchAtTable()"
                               placeholder="<fmt:message key="flight.label.search" bundle="${lang}"/>"
                               title="<fmt:message key="flight.label.search.title" bundle="${lang}"/>">
                    </div>
                </td>
            </c:forEach>
            <td>
                <div class="btn-group btn-group-justified">
                    <div class="btn-group">
                        <input class="btn btn-primary" type="button" onclick="clearSearchFields()"
                               value="<fmt:message key="flight.label.search.clear" bundle="${lang}"/>"/>
                    </div>
                </div>
            </td>
        </tr>
        <tr id="adding-row">
            <form action="/manage/flights" method="post">
                <td><input readonly type="text" disabled class="form-control" width="10" name="id"
                           value="-"
                           placeholder="<fmt:message key="flight.label.id" bundle="${lang}"/>">
                </td>


                <%--

                + SEARCH AT DATABASE

                --%>


                <td><input type="text" class="form-control" name="fromAirport" value=""
                           placeholder="<fmt:message key="flight.label.from" bundle="${lang}"/>"
                           required>
                </td>
                <td><input type="text" class="form-control" name="toAirport" value=""
                           placeholder="<fmt:message key="flight.label.to" bundle="${lang}"/>"
                           required>
                </td>
                <td><input type="text" class="form-control" name="airplane" value=""
                           placeholder="<fmt:message key="flight.label.airplane" bundle="${lang}"/>"
                           required>
                </td>
                <td><input type="datetime-local" class="form-control" name="departureTime" value=""
                           placeholder="<fmt:message key="flight.label.departure" bundle="${lang}"/>"
                           pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}" required>
                <td>
                    <input type="datetime-local" class="form-control" name="arrivalTime" value=""
                           placeholder="<fmt:message key="flight.label.arrival" bundle="${lang}"/>"
                           pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}" required>
                </td>
                <td><input type="number" class="form-control" name="baseTicketPrice" value=""
                           placeholder="<fmt:message key="flight.label.base_ticket_price" bundle="${lang}"/>">
                </td>
                <td><input type="number" class="form-control" name="extraBaggagePrice" value=""
                           placeholder="<fmt:message key="flight.label.extra_baggage_price" bundle="${lang}"/>">
                </td>
                <td><input type="number" class="form-control" name="freeSeatEconomy" value=""
                           placeholder="<fmt:message key="flight.label.free_seat_economy" bundle="${lang}"/>">
                </td>
                <td><input type="number" class="form-control" name="freeSeatBusiness" value=""
                           placeholder="<fmt:message key="flight.label.free_seat_business" bundle="${lang}"/>">
                </td>
                <td>
                    <div class="btn-group btn-group-justified">
                        <div class="btn-group">
                            <input type="submit" class="btn btn-primary" name="actionAdd"
                                   value="<fmt:message key="flight.label.control.add" bundle="${lang}"/>">
                        </div>
                    </div>
                </td>
            </form>
        </tr>
        </tr>
        <c:forEach var="flight" items="${flights}">
            <tr>
                <form action="/manage/flights" method="post">
                    <td><input readonly type="text" class="form-control" width="10" name="id"
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
                        <select class="form-control" data-live-search="true" name="toAirport"
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
                        <input type="datetime-local" class="form-control" name="departureTime"
                               value="<fmt:formatDate pattern="yyyy-MM-dd'T'HH:mm" value="${ parsedDateTime }"/>"
                               pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}" required>
                            <%--value="${juf:getLocalizedDateTime(flight.departureTime, language)}">--%>
                    </td>
                    <td><fmt:parseDate value="${ flight.arrivalTime }"
                                       pattern="yyyy-MM-dd'T'HH:mm"
                                       var="parsedDateTime" type="both"/>
                        <input type="datetime-local" class="form-control" name="arrivalTime"
                               value="<fmt:formatDate pattern="yyyy-MM-dd'T'HH:mm" value="${ parsedDateTime }"/>"
                               pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}" required>
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
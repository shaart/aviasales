<c:set var="COLUMNS_FIRST_NUM" value="0"/>
<c:set var="COLUMNS_COUNT" value="6"/>
<c:set var="page" value="${page == null || page < 1 ? 1 : page}"/>
<%@include file="layout/header.jspf" %>
    <title><fmt:message key="manage.tickets.title" bundle="${lang}"/></title>
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
<%@include file="layout/manageNavigation.jsp" %>
<div class="container">
    <h2><fmt:message key="page.header.search" bundle="${lang}"/></h2>
    <table id="searchTable" class="table-bordered">
        <thead>
        <tr>
            <th class="text-center" style="width: 5%;">
                <fmt:message key="ticket.label.id" bundle="${lang}"/></th>
            <th class="text-center" style="width: 13%;">
                <fmt:message key="ticket.label.personalData.passport" bundle="${lang}"/></th>
            <th class="text-center" style="width: 25%;">
                <fmt:message key="ticket.label.flight" bundle="${lang}"/></th>
            <th class="text-center" style="width: 15%;">
                <fmt:message key="ticket.label.account.name" bundle="${lang}"/></th>
            <th class="text-center" style="width: 7%;">
                <fmt:message key="ticket.label.price" bundle="${lang}"/></th>
            <th class="text-center" style="width: 10%;">
                <fmt:message key="ticket.label.type" bundle="${lang}"/></th>
            <th class="text-center" style="width: 10%; min-width: 150px;">
                <fmt:message key="page.label.control" bundle="${lang}"/></th>
        </tr>
        </thead>
        <tbody>
        <tr id="searching-row">
            <form id="search-form" action="/manage/tickets" method="get">
                <td><input type="text" class="form-control" width="10" name="id"
                           value="${id}"
                           placeholder="<fmt:message key="ticket.label.id" bundle="${lang}"/>">
                </td>
                <td>
                    <select class="form-control" name="personalDataPassport">
                        <option value="">
                            <fmt:message key="page.label.choose" bundle="${lang}"/>
                        </option>
                        <c:forEach var="personalData" items="${personalDatas}">
                            <option ${personalData.passport.equals(personalDataPassport) ? "selected" : ""}
                                    value="${personalData.passport}">${personalData.passport}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <select class="form-control" name="flightId">
                        <option value="">
                            <fmt:message key="page.label.choose" bundle="${lang}"/>
                        </option>
                        <c:forEach var="flight" items="${flights}">
                            <option ${flight.id == flightId? "selected" : ""}
                                    value="${flight.id}">${flight}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <select class="form-control" name="accountName">
                        <option value=""><fmt:message key="page.label.choose"
                                                      bundle="${lang}"/></option>
                        <c:forEach var="account" items="${accounts}">
                            <option ${account.login.equals(accountName) ? "selected" : ""}
                                    value="${account.login}">${account.login}</option>
                        </c:forEach>
                    </select>
                </td>
                <td><input type="number" class="form-control" name="price"
                           value="${price}"
                           placeholder="<fmt:message key="ticket.label.price" bundle="${lang}"/>">
                </td>
                <td>
                    <select name="isBusiness" class="form-control">
                        <option ${empty isBusiness ? "selected" : ""} value=""><fmt:message
                                key="ticket.label.type.all" bundle="${lang}"/></option>
                        <option ${not empty isBusiness && isBusiness ? "selected" : ""}
                                value="true"><fmt:message
                                key="ticket.label.type.business" bundle="${lang}"/></option>
                        <option ${not empty isBusiness && !isBusiness ? "selected" : ""}
                                value="false"><fmt:message
                                key="ticket.label.type.economy" bundle="${lang}"/></option>
                    </select>
                </td>
                <td>
                    <div class="btn-group btn-group-justified">
                        <div class="btn-group">
                            <input id="search-button" type="submit" class="btn btn-primary"
                                   value="<fmt:message key="page.label.search" bundle="${lang}"/>">
                        </div>
                        <div class="btn-group">
                            <input class="btn btn-primary" type="button"
                                   onclick="clearSearchFields()"
                                   value="<fmt:message key="page.label.filter.clear" bundle="${lang}"/>"/>
                        </div>
                    </div>
                </td>
            </form>
        </tr>
        </tbody>
    </table>
    <br>
    <h2><fmt:message key="page.header.add" bundle="${lang}"/></h2>
    <table id="addTable" class="table-bordered">
        <thead>
        <tr>
            <th class="text-center" style="width: 5%;">
                <fmt:message key="ticket.label.id" bundle="${lang}"/></th>
            <th class="text-center" style="width: 13%;">
                <fmt:message key="ticket.label.personalData.passport" bundle="${lang}"/></th>
            <th class="text-center" style="width: 25%;">
                <fmt:message key="ticket.label.flight" bundle="${lang}"/></th>
            <th class="text-center" style="width: 15%;">
                <fmt:message key="ticket.label.account.name" bundle="${lang}"/></th>
            <th class="text-center" style="width: 7%;">
                <fmt:message key="ticket.label.price" bundle="${lang}"/></th>
            <th class="text-center" style="width: 10%;">
                <fmt:message key="ticket.label.type" bundle="${lang}"/></th>
            <th class="text-center" style="width: 10%; min-width: 150px;">
                <fmt:message key="page.label.control" bundle="${lang}"/></th>
        </tr>
        </thead>
        <tbody>
        <tr id="adding-row">
            <form id="addForm" action="/manage/tickets" method="post">
                <td><input readonly type="text" disabled class="form-control" width="10" name="id"
                           value="-"
                           placeholder="<fmt:message key="ticket.label.id" bundle="${lang}"/>">
                </td>
                </td>
                <td>
                    <select required class="form-control" name="personalDataPassport">
                        <option value="">
                            <fmt:message key="page.label.choose" bundle="${lang}"/>
                        </option>
                        <c:forEach var="personalData" items="${personalDatas}">
                            <option value="${personalData.passport}">${personalData.passport}</option>
                        </c:forEach>
                    </select>
                </td>

                <td>
                    <select required class="form-control" name="flightId">
                        <option value="">
                            <fmt:message key="page.label.choose" bundle="${lang}"/>
                        </option>
                        <c:forEach var="flight" items="${flights}">
                            <option value="${flight.id}">${flight}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <select required class="form-control" name="accountName">
                        <option value="">
                            <fmt:message key="page.label.choose" bundle="${lang}"/>
                        </option>
                        <c:forEach var="account" items="${accounts}">
                            <option value="${account.login}">${account.login}</option>
                        </c:forEach>
                    </select>
                </td>
                <td><input required type="number" class="form-control" name="price"
                           value=""
                           placeholder="<fmt:message key="ticket.label.price" bundle="${lang}"/>">
                </td>
                <td>
                    <select required name="isBusiness" class="form-control">
                        <option value="">
                            <fmt:message key="page.label.choose" bundle="${lang}"/>
                        </option>
                        <option value="true">
                            <fmt:message key="ticket.label.type.business" bundle="${lang}"/>
                        </option>
                        <option value="false">
                            <fmt:message key="ticket.label.type.economy" bundle="${lang}"/>
                        </option>
                    </select>
                </td>
                <td>
                    <div class="btn-group btn-group-justified">
                        <div class="btn-group">
                            <input id="add-button" type="submit" class="btn btn-primary"
                                   name="actionAdd"
                                   value="<fmt:message key="page.label.control.add" bundle="${lang}"/>">
                        </div>
                    </div>
                </td>
            </form>
        </tr>
        </tbody>
    </table>
    <br>
    <h2><fmt:message key="page.header.result" bundle="${lang}"/></h2>
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
    <table id="dataTable" class="table-bordered">
        <thead>
        <tr>
            <th class="text-center" style="width: 5%;">
                <fmt:message key="ticket.label.id" bundle="${lang}"/></th>
            <th class="text-center" style="width: 13%;">
                <fmt:message key="ticket.label.personalData.passport" bundle="${lang}"/></th>
            <th class="text-center" style="width: 25%;">
                <fmt:message key="ticket.label.flight" bundle="${lang}"/></th>
            <th class="text-center" style="width: 15%;">
                <fmt:message key="ticket.label.account.name" bundle="${lang}"/></th>
            <th class="text-center" style="width: 7%;">
                <fmt:message key="ticket.label.price" bundle="${lang}"/></th>
            <th class="text-center" style="width: 10%;">
                <fmt:message key="ticket.label.type" bundle="${lang}"/></th>
            <th class="text-center" style="width: 10%; min-width: 150px;">
                <fmt:message key="page.label.control" bundle="${lang}"/></th>
        </tr>
        </thead>
        <tbody>
        <tr id="filtering-row">
            <c:forEach begin="${COLUMNS_FIRST_NUM}" end="${COLUMNS_COUNT - 1}" var="counter">
                <td>
                    <div class="form-group has-feedback">
                        <i class="form-control-feedback glyphicon glyphicon-search"></i>
                        <input id="seekAtColumn${counter}" class="form-control" type="text"
                               onkeyup="searchAtTable()"
                               placeholder="<fmt:message key="page.label.filter" bundle="${lang}"/>"
                               title="<fmt:message key="page.label.filter.title" bundle="${lang}"/>">
                    </div>
                </td>
            </c:forEach>
            <td>
                <div class="btn-group btn-group-justified">
                    <div class="btn-group">
                        <input class="btn btn-primary" type="button" onclick="clearFilterFields()"
                               value="<fmt:message key="page.label.filter.clear" bundle="${lang}"/>"/>
                    </div>
                </div>
            </td>
        </tr>
        </tr>
        <c:forEach var="ticket" items="${tickets}">
            <tr>
                <form action="/manage/tickets" method="post">
                    <td><input readonly type="text" class="form-control" width="10"
                               name="id"
                               value="${ticket.id}"
                               placeholder="<fmt:message key="ticket.label.id" bundle="${lang}"/>">
                    </td>
                    <td><input readonly type="text" class="form-control"
                               name="personalData.passport"
                               value="${ticket.personalData.passport}"
                               placeholder="<fmt:message key="ticket.label.personalData.passport" bundle="${lang}"/>">
                    </td>
                    <td><input readonly type="text" class="form-control" width="10" name="flight"
                               value="${ticket.flight}"
                               placeholder="<fmt:message key="ticket.label.flight" bundle="${lang}"/>">
                    </td>
                    <td><input readonly type="text" class="form-control" name="account.name"
                               value="${ticket.account.name} (${ticket.account.login})"
                               placeholder="<fmt:message key="ticket.label.account.name" bundle="${lang}"/>">
                    </td>
                    <td><input readonly type="number" class="form-control" name="price"
                               value="${ticket.price}"
                               placeholder="<fmt:message key="ticket.label.price" bundle="${lang}"/>">
                    </td>
                    <c:choose>
                        <c:when test="${ticket.isBusiness}">
                            <td><input readonly type="text" class="form-control" name="isBusiness"
                                       value="<fmt:message key="ticket.label.type.business" bundle="${lang}"/>"
                                       placeholder="<fmt:message key="ticket.label.isBusiness" bundle="${lang}"/>">
                            </td>
                        </c:when>
                        <c:when test="${!ticket.isBusiness}">
                            <td><input readonly type="text" class="form-control" name="isBusiness"
                                       value="<fmt:message key="ticket.label.type.economy" bundle="${lang}"/>"
                                       placeholder="<fmt:message key="ticket.label.isBusiness" bundle="${lang}"/>">
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td><input readonly type="text" class="form-control" name="isBusiness"
                                       value="<fmt:message key="ticket.label.type.unknown" bundle="${lang}"/>"
                                       placeholder="<fmt:message key="ticket.label.isBusiness" bundle="${lang}"/>">
                            </td>
                        </c:otherwise>
                    </c:choose>
                    <td>
                        <div class="btn-group btn-group-justified">
                            <div class="btn-group">
                                <input type="submit" class="btn btn-primary" name="actionDelete"
                                       value="<fmt:message key="page.label.control.delete" bundle="${lang}"/>">
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
</div>
<%@include file="layout/footer.jspf" %>
</body>
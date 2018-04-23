<%@include file="layout/header.jspf" %>
<title><fmt:message key="manage.accounts.title" bundle="${lang}"/></title>
</head>
<c:if test="${account == null || (account.role != 'ADMIN')}">
    <% response.sendError(403); %>
</c:if>
<c:set var="COLUMNS_FIRST_NUM" value="0"/>
<c:set var="COLUMNS_COUNT" value="6"/>
<c:set var="page" value="${page == null || page < 1 ? 1 : page}"/>
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
<%@include file="layout/manageNavigation.jspf" %>
<div class="container">
    <h2><fmt:message key="page.header.search" bundle="${lang}"/></h2>
    <table id="searchTable" class="table-bordered">
        <thead>
        <tr>
            <th class="text-center" style="width: 3%;">
                <fmt:message key="account.label.id" bundle="${lang}"/></th>
            <th class="text-center" style="width: 13%;">
                <fmt:message key="account.label.role" bundle="${lang}"/></th>
            <th class="text-center" style="width: 20%;">
                <fmt:message key="account.label.name" bundle="${lang}"/></th>
            <th class="text-center" style="width: 13%;">
                <fmt:message key="account.label.login" bundle="${lang}"/></th>
            <th class="text-center" style="width: 20%;">
                <fmt:message key="account.label.email" bundle="${lang}"/></th>
            <th class="text-center" style="width: 13%;">
                <fmt:message key="account.label.phone" bundle="${lang}"/></th>
            <th class="text-center" style="width: 15%; min-width: 170px">
                <fmt:message key="page.label.control" bundle="${lang}"/></th>
        </tr>
        </thead>
        <tbody>
        <tr id="searching-row">
            <form id="search-form" action="/manage/accounts" method="get">
                <td><input type="text" class="form-control" width="10" name="id"
                           value="${id}"
                           placeholder="<fmt:message key="account.label.id" bundle="${lang}"/>">
                </td>
                <td>
                    <select class="form-control" name="role">
                        <option value="">
                            <fmt:message key="page.label.choose" bundle="${lang}"/>
                        </option>
                        <c:forEach var="cRole" items="${roles}">
                            <option ${cRole.equals(role) ? "selected" : ""}
                                    value="${cRole}">${cRole}</option>
                        </c:forEach>
                    </select>
                </td>
                <td><input type="text" class="form-control" name="name" value="${name}"
                           placeholder="<fmt:message key="account.label.name" bundle="${lang}"/>">
                </td>
                <td><input type="text" class="form-control" name="login" value="${login}"
                           placeholder="<fmt:message key="account.label.login" bundle="${lang}"/>">
                </td>
                <td><input type="email" class="form-control" name="email" value="${email}"
                           placeholder="<fmt:message key="account.label.email" bundle="${lang}"/>">
                </td>
                <td><input type="text" class="form-control" name="phone" value="${phone}"
                           placeholder="<fmt:message key="account.label.phone" bundle="${lang}"/>">
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
            <th class="text-center" style="width: 13%;">
                <fmt:message key="account.label.role" bundle="${lang}"/></th>
            <th class="text-center" style="width: 20%;">
                <fmt:message key="account.label.name" bundle="${lang}"/></th>
            <th class="text-center" style="width: 13%;">
                <fmt:message key="account.label.login" bundle="${lang}"/></th>
            <th class="text-center" style="width: 13%;">
                <fmt:message key="account.label.password" bundle="${lang}"/></th>
            <th class="text-center" style="width: 20%;">
                <fmt:message key="account.label.email" bundle="${lang}"/></th>
            <th class="text-center" style="width: 13%;">
                <fmt:message key="account.label.phone" bundle="${lang}"/></th>
            <th class="text-center" style="width: 15%; min-width: 170px">
                <fmt:message key="page.label.control" bundle="${lang}"/></th>
        </tr>
        </thead>
        <tbody>
        <tr id="adding-row">
            <form id="addForm" action="/manage/accounts" method="post">
                <td>
                    <select required oninvalid='this.setCustomValidity("<fmt:message
                            key="page.error.field.is.required" bundle="${lang}"/>")'
                            oninput="setCustomValidity('')" class="form-control" name="role">
                        <option value=""><fmt:message key="page.label.choose"
                                                      bundle="${lang}"/></option>
                        <c:forEach var="role" items="${roles}">
                            <option value="${role}">${role}</option>
                        </c:forEach>
                    </select>
                </td>
                <td><input required
                           oninvalid='this.setCustomValidity("<fmt:message
                                   key="page.error.field.is.required"
                                   bundle="${lang}"/>")'
                           oninput="setCustomValidity('')" type="text" class="form-control"
                           name="name"
                           value=""
                           pattern="[A-Za-z\u0400-\u04FF]{1,20}\s[A-Za-z\u0400-\u04FF]{1,20}\s?[A-Za-z\u0400-\u04FF]{0,20}"
                           placeholder="<fmt:message key="account.label.name" bundle="${lang}"/>">
                </td>
                <td><input required oninvalid='this.setCustomValidity("<fmt:message
                        key="page.error.field.is.required" bundle="${lang}"/>")'
                           oninput="setCustomValidity('')" type="text" class="form-control"
                           name="login"
                           value=""
                           pattern="[_@a-zA-Z0-9.]{1,50}"
                           placeholder="<fmt:message key="account.label.login" bundle="${lang}"/>">
                </td>
                <td><input required oninvalid='this.setCustomValidity("<fmt:message
                        key="page.error.field.is.required" bundle="${lang}"/>")'
                           oninput="setCustomValidity('')" type="password" class="form-control"
                           name="password"
                           value=""
                           minlength="6"
                           placeholder="<fmt:message key="account.label.password" bundle="${lang}"/>">
                </td>
                <td><input required oninvalid='this.setCustomValidity("<fmt:message
                        key="page.error.field.is.required" bundle="${lang}"/>")'
                           oninput="setCustomValidity('')" type="email" class="form-control"
                           name="email"
                           value=""
                           pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"
                           placeholder="<fmt:message key="account.label.email" bundle="${lang}"/>">
                </td>
                <td><input required oninvalid='this.setCustomValidity("<fmt:message
                        key="page.error.field.is.required" bundle="${lang}"/>")'
                           oninput="setCustomValidity('')" type="text" class="form-control"
                           name="phone"
                           value=""
                           pattern="[\+]?[0-9][(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}"
                           placeholder="<fmt:message key="account.label.phone" bundle="${lang}"/>">
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
    <c:choose>
        <c:when test="${accounts == null || accounts.size() == 0}">
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
            <table id="dataTable" class="table-bordered">
                <thead>
                <tr>
                    <th class="text-center" style="width: 3%;">
                        <fmt:message key="account.label.id" bundle="${lang}"/></th>
                    <th class="text-center" style="width: 13%;">
                        <fmt:message key="account.label.role" bundle="${lang}"/></th>
                    <th class="text-center" style="width: 20%;">
                        <fmt:message key="account.label.name" bundle="${lang}"/></th>
                    <th class="text-center" style="width: 13%;">
                        <fmt:message key="account.label.login" bundle="${lang}"/></th>
                    <th class="text-center" style="width: 20%;">
                        <fmt:message key="account.label.email" bundle="${lang}"/></th>
                    <th class="text-center" style="width: 13%;">
                        <fmt:message key="account.label.phone" bundle="${lang}"/></th>
                    <th class="text-center" style="width: 15%; min-width: 170px">
                        <fmt:message key="page.label.control" bundle="${lang}"/></th>
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
                                       placeholder="<fmt:message key="page.label.filter" bundle="${lang}"/>"
                                       title="<fmt:message key="page.label.filter.title" bundle="${lang}"/>">
                            </div>
                        </td>
                    </c:forEach>
                    <td>
                        <div class="btn-group btn-group-justified">
                            <div class="btn-group">
                                <input class="btn btn-primary" type="button"
                                       onclick="clearFilterFields()"
                                       value="<fmt:message key="page.label.filter.clear" bundle="${lang}"/>"/>
                            </div>
                        </div>
                    </td>
                </tr>
                </tr>
                <c:forEach var="account" items="${accounts}">
                    <tr>
                        <form action="/manage/accounts" method="post">
                            <td><input readonly type="text" class="form-control" width="10"
                                       name="id"
                                       value="${account.id}"></td>
                            <td>
                                <select required oninvalid='this.setCustomValidity("<fmt:message
                                        key="page.error.field.is.required" bundle="${lang}"/>")'
                                        oninput="setCustomValidity('')" class="form-control"
                                        name="role">
                                    <option value="">
                                        <fmt:message key="page.label.choose" bundle="${lang}"/>
                                    </option>
                                    <c:forEach var="cRole" items="${roles}">
                                        <option ${account.role.name().equals(cRole) ? "selected" : ""}
                                                value="${cRole}">${cRole}
                                        </option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td><input required oninvalid='this.setCustomValidity("<fmt:message
                                    key="page.error.field.is.required" bundle="${lang}"/>")'
                                       oninput="setCustomValidity('')" type="text"
                                       class="form-control"
                                       name="name"
                                       value="${account.name}"
                                       pattern="[A-Za-z\u0400-\u04FF]{1,20}\s[A-Za-z\u0400-\u04FF]{1,20}\s?[A-Za-z\u0400-\u04FF]{0,20}"
                                       placeholder="<fmt:message key="account.label.name" bundle="${lang}"/>">
                            </td>
                            <td><input required oninvalid='this.setCustomValidity("<fmt:message
                                    key="page.error.field.is.required" bundle="${lang}"/>")'
                                       oninput="setCustomValidity('')" type="text"
                                       class="form-control"
                                       name="login"
                                       value="${account.login}"
                                       pattern="[_@a-zA-Z0-9.]{1,50}"
                                       placeholder="<fmt:message key="account.label.login" bundle="${lang}"/>">
                            </td>
                            <td><input required oninvalid='this.setCustomValidity("<fmt:message
                                    key="page.error.field.is.required" bundle="${lang}"/>")'
                                       oninput="setCustomValidity('')" type="email"
                                       class="form-control"
                                       name="email"
                                       value="${account.email}"
                                       pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"
                                       placeholder="<fmt:message key="account.label.email" bundle="${lang}"/>">
                            </td>
                            <td><input required oninvalid='this.setCustomValidity("<fmt:message
                                    key="page.error.field.is.required" bundle="${lang}"/>")'
                                       oninput="setCustomValidity('')" type="text"
                                       class="form-control"
                                       name="phone"
                                       value="${account.phone}"
                                       pattern="[\+]?[0-9][(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}"
                                       placeholder="<fmt:message key="account.label.phone" bundle="${lang}"/>">
                            </td>
                            <td>
                                <div class="btn-group btn-group-justified">
                                    <div class="btn-group">
                                        <input type="submit" class="btn btn-primary"
                                               name="actionSave"
                                               value="<fmt:message key="page.label.control.save" bundle="${lang}"/>">
                                    </div>
                                    <div class="btn-group">
                                        <input type="submit" class="btn btn-primary"
                                               name="actionDelete"
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
        </c:otherwise>
    </c:choose>
</div>
<%@include file="layout/footer.jspf" %>
</body>
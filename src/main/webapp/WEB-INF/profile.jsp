<%@include file="layout/header.jspf" %>
<title><fmt:message
        key="profile.title"
        bundle="${lang}"/></title>
</head>
<body>
<div class="container">
    <table class="table table-borderless">
        <thead>
        <tr>
            <th scope="col" style="width: 20px"></th>
            <th scope="col" style="width: 20px"></th>
            <th scope="col" style="width: 20px"></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th scope="row"><fmt:message key="profile.label.name" bundle="${lang}"/></th>
            <td>${account.name}</td>
            <td>
                <form action="/profile" method="post">
                    <button type="submit" class="btn btn-primary btn-sm"
                            name="changePasswordButton">
                        <fmt:message
                                key="profile.label.changePassword"
                                bundle="${lang}"/>
                    </button>
                </form>
            </td>
        </tr>
        <tr>
            <th scope="row"><fmt:message key="login.label.login" bundle="${lang}"/></th>
            <td>${account.login}</td>
            <td>
                <form action="/profile" method="post">
                    <button type="submit" class="btn btn-primary btn-sm" name="editButton">
                        <fmt:message
                                key="profile.label.edit"
                                bundle="${lang}"/>
                    </button>
                </form>
            </td>
        </tr>
        <tr>
            <th scope="row"><fmt:message key="profile.label.email" bundle="${lang}"/></th>
            <td>${account.email}</td>

        </tr>
        <tr>
            <th scope="row"><fmt:message key="profile.label.phone" bundle="${lang}"/></th>
            <td>${account.phone}</td>

        </tr>
        </tbody>
    </table>
    <form action="/profile" method="post" data-toggle="validator" role="form">
        <div id="modalEditAccount" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button"
                                data-dismiss="modal">x
                        </button>
                        <h4 class="modal-title"><fmt:message
                                key="profile.label.edit"
                                bundle="${lang}"/></h4>
                    </div>
                    <div class="modal-body">
                        <table class="table table-borderless">
                            <thead>
                            <tr>
                                <th scope="col" style="width: 50px"></th>
                                <th scope="col" style="width: 300px"></th>
                                <th scope="col" style="width: 50px"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <th scope="row"><fmt:message key="profile.label.name"
                                                             bundle="${lang}"/></th>
                                <td>
                                    <div class="form-group">
                                        <input type="text" name="inputName" id="inputName"
                                               pattern="[A-Za-z\u0400-\u04FF]{1,20}\s[A-Za-z\u0400-\u04FF]{1,20}\s?[A-Za-z\u0400-\u04FF]{0,20}"
                                               data-error="<fmt:message key="register.error.wrong_input"
                    bundle="${lang}"/>"
                                               class="form-control"
                                               value=
                                                       "${account.name}" required>
                                        <div class="help-block with-errors"></div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row"><fmt:message key="login.label.login"
                                                             bundle="${lang}"/></th>
                                <td>
                                    <div class="form-group">
                                        <input type="login" name="inputLogin" id="inputLogin"
                                               pattern="[_@\.a-zA-z0-9]{1,50}"
                                               data-minlength="1" data-minlength="50"
                                               data-minlength-error="<fmt:message key="input.error.minlegth1"
                    bundle="${lang}"/>" data-maxlength-error="<fmt:message key="input.error.maxlegth50"
                    bundle="${lang}"/>" data-error="<fmt:message key="register.error.wrong_input"
                    bundle="${lang}"/>"
                                               class="form-control"
                                               value=
                                                       "${account.login}"
                                               autofocus required>
                                        <div class="help-block with-errors"></div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row"><fmt:message key="profile.label.email"
                                                             bundle="${lang}"/></th>
                                <td>
                                    <div class="form-group">
                                        <input type="email" name="inputEmail" id="inputEmail"
                                               data-minlength="6"
                                               data-maxlength="50" class="form-control"
                                               data-minlength-error="<fmt:message key="input.error.minlegth6"
                    bundle="${lang}"/>" data-maxlength-error="<fmt:message key="input.error.maxlegth50"
                    bundle="${lang}"/>"
                                               data-error="<fmt:message key="register.error.wrong_input"
                           bundle="${lang}"/>"
                                               class="form-control"
                                               value=
                                                       "${account.email}" required>
                                        <div class="help-block with-errors"></div>
                                    </div>

                                </td>
                            </tr>
                            <tr>
                                <th scope="row"><fmt:message key="profile.label.phone"
                                                             bundle="${lang}"/></th>
                                <td>
                                    <div class="form-group">
                                        <input type="text" class="form-control" name="inputPhone"
                                               id="inputPhone"
                                               pattern="[\+]?[0-9][(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}"
                                               data-error="<fmt:message key="register.error.wrong_input"
                           bundle="${lang}"/>"
                                               value=
                                                       "${account.phone}" required>
                                        <div class="help-block with-errors"></div>
                                    </div>

                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <c:if test="${changeAccountError != null}">
                        <p>
                            <font color="red"><fmt:message
                                    key="${changeAccountError}"
                                    bundle="${lang}"/></font>
                        </p>
                    </c:if>

                    <div class="modal-footer">

                        <button class="btn btn-default" type="submit" name="saveButton">
                            <fmt:message
                                    key="register.label.save"
                                    bundle="${lang}"/></button>

                        <button class="btn btn-default"
                                type="button" data-dismiss="modal">
                            <fmt:message key="profile.label.close"
                                         bundle="${lang}"/></button>
                    </div>
                </div>
            </div>
        </div>
    </form>

    <hr>
    <div id="profileTab" class="container">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#tickets_tab" data-toggle="tab"><fmt:message
                    key="profile.label.ticket"
                    bundle="${lang}"/></a></li>
            <li><a href="#personal_data_tab" data-toggle="tab"><fmt:message
                    key="profile.label.personal_data"
                    bundle="${lang}"/></a></li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane active" id="tickets_tab">
                <c:choose>
                    <c:when test="${tickets != null}">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th scope="col"><fmt:message key="profile.label.name"
                                                             bundle="${lang}"/></th>
                                <th scope="col"><fmt:message key="profile.label.passport"
                                                             bundle="${lang}"/></th>
                                <th scope="col"><fmt:message key="profile.label.dob"
                                                             bundle="${lang}"/></th>
                                <th scope="col"><fmt:message key="profile.label.airportFrom"
                                                             bundle="${lang}"/></th>
                                <th scope="col"><fmt:message key="profile.label.airportTo"
                                                             bundle="${lang}"/></th>
                                <th scope="col"><fmt:message key="profile.label.departureTime"
                                                             bundle="${lang}"/></th>
                                <th scope="col"><fmt:message key="profile.label.arrivalTime"
                                                             bundle="${lang}"/></th>
                                <th scope="col"><fmt:message key="profile.label.extraBaggagePrice"
                                                             bundle="${lang}"/></th>
                                <th scope="col"><fmt:message key="profile.label.class"
                                                             bundle="${lang}"/></th>
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
                                        <td><fmt:parseDate value="${ticket.flight.departureTime}"
                                                           pattern="yyyy-MM-dd'T'HH:mm"
                                                           var="parsedDateTime" type="both"/>

                                            <label class="form-control"
                                                   name="departureTime"
                                                   style="border:0"
                                                   pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}"
                                                   required><fmt:formatDate
                                                    pattern="yyyy-MM-dd' 'HH:mm"
                                                    value="${parsedDateTime}"/></label></td>


                                        <td><fmt:parseDate value="${ticket.flight.arrivalTime}"
                                                           pattern="yyyy-MM-dd'T'HH:mm"
                                                           var="parsedDateTime" type="both"/>

                                            <label class="form-control"
                                                   name="departureTime"
                                                   style="border:0"
                                                   pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}"
                                                   required><fmt:formatDate
                                                    pattern="yyyy-MM-dd' 'HH:mm"
                                                    value="${parsedDateTime}"/></label></td>
                                        <td>${ticket.flight.extraBaggagePrice} &#8364;</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${ticket.isBusiness == true}"><fmt:message
                                                        key="profile.label.business"
                                                        bundle="${lang}"/></c:when>
                                                <c:otherwise><fmt:message
                                                        key="profile.label.economy"
                                                        bundle="${lang}"/></c:otherwise>
                                            </c:choose>
                                        </td>

                                        <td>
                                            <c:if test="${f:isBefore(ticket.flight.departureTime)}">
                                                <button type="submit" class="btn btn-primary btn-sm"
                                                        name="returnButton"><fmt:message
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
                    <c:otherwise><fmt:message key="profile.message.noTicket"
                                              bundle="${lang}"/></c:otherwise>
                </c:choose>
            </div>
            <div class="tab-pane" id="personal_data_tab">
                <c:choose>
                    <c:when test="${personalDatas != null}">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th scope="col"><fmt:message key="profile.label.name"
                                                             bundle="${lang}"/></th>
                                <th scope="col"><fmt:message key="profile.label.passport"
                                                             bundle="${lang}"/></th>
                                <th scope="col"><fmt:message key="profile.label.dob"
                                                             bundle="${lang}"/></th>
                                <th scope="col"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="personalData" items="${personalDatas}">
                                <tr>
                                    <form action="/profile" method="post">
                                        <td>${personalData.name}</td>
                                        <td>${personalData.passport} </td>
                                        <td>${personalData.dateOfBirth}</td>
                                        <input type="hidden" name="personalDataId"
                                               value="${personalData.id}">
                                        <td>
                                            <button class="btn btn-primary btn-sm" type="submit"
                                                    name="editPersonalDataButton"
                                                    id="editPersonalDataButton"><fmt:message
                                                    key="profile.label.edit"
                                                    bundle="${lang}"/></button>
                                        </td>
                                    </form>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise><fmt:message key="profile.message.no_personal_data"
                                              bundle="${lang}"/></c:otherwise>
                </c:choose>
            </div>
            <form action="/profile" method="post" data-toggle="validator" role="form">
                <div id="modalEditPersonalData" class="modal fade" role="dialog">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button class="close" type="button"
                                        data-dismiss="modal">x
                                </button>
                                <h4 class="modal-title"><fmt:message
                                        key="profile.label.edit"
                                        bundle="${lang}"/></h4>
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    <label for="inputPersonalDataName"
                                           class="control-label"><fmt:message
                                            key="register.label.fullname"
                                            bundle="${lang}"/></label>
                                    <input type="text" class="form-control"
                                           name="inputPersonalDataName"
                                           id="inputPersonalDataName"
                                           data-error="<fmt:message key="register.error.wrong_input"
                    bundle="${lang}"/>"
                                           pattern="[A-Za-z\u0400-\u04FF]{1,20}\s[A-Za-z\u0400-\u04FF]{1,20}\s?[A-Za-z\u0400-\u04FF]{0,20}"
                                           value=
                                                   "${modalPersonalData.name}"
                                           required>
                                    <div class="help-block with-errors"></div>
                                </div>

                                <div class="form-group">
                                    <label for="inputPersonalDataPassport"
                                           class="control-label"><fmt:message
                                            key="profile.label.passport"
                                            bundle="${lang}"/></label>
                                    <input type="text" class="form-control"
                                           name="inputPersonalDataPassport"
                                           data-error="<fmt:message key="register.error.wrong_input"
                    bundle="${lang}"/>"
                                           pattern="[A-Za-z0-9\s\-]{6,20}"
                                           id="inputPersonalDataPassport"
                                           value=
                                                   "${modalPersonalData.passport}"
                                           required>
                                    <input type="hidden" name="inputPersonalDataId"
                                           value="${modalPersonalData.id}">
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label for="inputPersonalDataDOB"
                                           class="control-label"><fmt:message
                                            key="profile.label.dob"
                                            bundle="${lang}"/></label>
                                    <input type="date" class="form-control"
                                           name="inputPersonalDataDOB"
                                           id="inputPersonalDataDOB"
                                           value=
                                                   "${modalPersonalData.dateOfBirth}"
                                           required></div>
                            </div>
                            <c:if test="${changePersonalDataError != null}">
                                <p>
                                    <font color="red"><fmt:message
                                            key="${changePersonalDataError}"
                                            bundle="${lang}"/></font>
                                </p>
                            </c:if>

                            <div class="modal-footer">

                                <button class="btn btn-default" type="submit"
                                        name="savePersonalDataButton"
                                        id="savePersonalDataButton">
                                    <fmt:message
                                            key="register.label.save"
                                            bundle="${lang}"/></button>

                                <button class="btn btn-default"
                                        type="button" data-dismiss="modal">
                                    <fmt:message key="profile.label.close"
                                                 bundle="${lang}"/></button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
            <form action="/profile" method="post" data-toggle="validator" role="form">
                <div id="modalChangePassword" class="modal fade" role="dialog">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button class="close" type="button"
                                        data-dismiss="modal">x
                                </button>
                                <h4 class="modal-title"><fmt:message
                                        key="profile.label.edit"
                                        bundle="${lang}"/></h4>
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    <label for="inputOldPassword"
                                           class="control-label"><fmt:message
                                            key="profile.label.current_password"
                                            bundle="${lang}"/></label>
                                    <input type="password" class="form-control"
                                           name="inputOldPassword"
                                           data-minlength="6" data-maxlength="50"
                                           data-minlength-error="<fmt:message key="input.error.minlegth6"
                    bundle="${lang}"/>" data-maxlength-error="<fmt:message key="input.error.maxlegth50"
                    bundle="${lang}"/>"
                                           data-error="<fmt:message key="register.error.wrong_input"
                                   bundle="${lang}"/>"
                                           id="inputOldPassword"
                                           required>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label for="inputNewPassword"
                                           class="control-label"><fmt:message
                                            key="profile.label.new_password"
                                            bundle="${lang}"/></label>
                                    <input type="password" class="form-control"
                                           name="inputNewPassword"
                                           id="inputNewPassword"
                                           data-minlength="6" data-maxlength="50"
                                           data-minlength-error="<fmt:message key="input.error.minlegth6"
                    bundle="${lang}"/>" data-maxlength-error="<fmt:message key="input.error.maxlegth50"
                    bundle="${lang}"/>"
                                           data-error="<fmt:message key="register.error.wrong_input"
                                   bundle="${lang}"/>"
                                           required>
                                    <div class="help-block with-errors"></div></div>
                                    <div class="form-group">
                                        <label for="inputConfirmNewPassword"
                                               class="control-label"><fmt:message
                                                key="profile.label.confirm_new_password"
                                                bundle="${lang}"/></label>
                                        <input type="password" class="form-control"
                                               name="inputConfirmNewPassword"
                                               id="inputConfirmNewPassword"
                                               data-minlength="6" data-maxlength="50"
                                               data-match="#inputNewPassword" data-match-error="<fmt:message key="register.error.not_match"
                            bundle="${lang}"/>"
                                               data-minlength-error="<fmt:message key="input.error.minlegth6"
                    bundle="${lang}"/>" data-maxlength-error="<fmt:message key="input.error.maxlegth50"
                    bundle="${lang}"/>"
                                               data-error="<fmt:message key="register.error.wrong_input"
                                   bundle="${lang}"/>"
                                               required>
                                        <div class="help-block with-errors"></div>
                                    </div></div>
                                    <c:if test="${changePasswordError != null}">
                                        <p>
                                            <font color="red"><fmt:message
                                                    key="${changePasswordError}"
                                                    bundle="${lang}"/></font>
                                        </p>
                                    </c:if>

                                    <div class="modal-footer">

                                        <button class="btn btn-sm" type="submit"
                                                name="savePassword">
                                            <fmt:message
                                                    key="register.label.save"
                                                    bundle="${lang}"/></button>

                                        <button class="btn btn-default"
                                                type="button" data-dismiss="modal">
                                            <fmt:message key="profile.label.close"
                                                         bundle="${lang}"/></button>
                                    </div>
                                </div>
                            </div>
                        </div>
            </form>
            <c:if test="${autoOpenEditPersonalData == true}">
                <script type="text/javascript">
                  $('#profileTab a[href="#personal_data_tab"]').tab('show');
                  $("#modalEditPersonalData").modal()
                </script>
            </c:if>
            <c:if test="${autoOpenEditAccount == true}">
                <script type="text/javascript">
                  $("#modalEditAccount").modal()
                </script>
            </c:if>
            <c:if test="${autoChangePassword == true}">
                <script type="text/javascript">
                  $("#modalChangePassword").modal()
                </script>
            </c:if>
        </div>
    </div>
</div>
<%@include file="layout/footer.jspf" %>
</body>
</html>
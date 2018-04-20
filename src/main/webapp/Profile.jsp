<%@ page import="java.time.LocalDateTime" %>
<%@ page import="com.epam.aviasales.domain.PersonalData" %>
<%--
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
    <link rel="stylesheet" href="/css/aviasales.css">

</head>
<body>

<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/aviasales.js"></script>

<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
    </select>
</form>
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
        <th scope="row"><fmt:message key="profile.label.name" bundle="${lang}"/></th>
        <td>${account.name}</td>
    </tr>
    <tr>
        <th scope="row"><fmt:message key="login.label.login" bundle="${lang}"/></th>
        <td>${account.login}</td>
    </tr>
    <tr>
        <th scope="row"><fmt:message key="profile.label.email" bundle="${lang}"/></th>
        <td>${account.email}</td>
        <td>
            <form action="/profile" method="post">
                <button type="submit" class="btn btn-primary btn-sm" name="changePasswordButton">
                    <fmt:message
                            key="profile.label.changePassword"
                            bundle="${lang}"/>
                </button>
            </form>
        </td>
    </tr>
    <tr>
        <th scope="row"><fmt:message key="profile.label.phone" bundle="${lang}"/></th>
        <td>${account.phone}</td>
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
    </tbody>
</table>
<form action="/profile" method="post">
    <div id="modalEditAccount" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button class="close" type="button"
                            data-dismiss="modal">×
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
                                <input type="text" name="inputName" id="inputName"
                                       class="form-control"
                                       value=
                                               "${account.name}" required>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row"><fmt:message key="login.label.login"
                                                         bundle="${lang}"/></th>
                            <td>

                                <input type="login" name="inputLogin" id="inputLogin"
                                       class="form-control"
                                       value=
                                               "${account.login}"
                                       autofocus required>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row"><fmt:message key="profile.label.email"
                                                         bundle="${lang}"/></th>
                            <td>
                                <input type="email" name="inputEmail" id="inputEmail"
                                       class="form-control"
                                       value=
                                               "${account.email}" required>

                            </td>
                        </tr>
                        <tr>
                            <th scope="row"><fmt:message key="profile.label.phone"
                                                         bundle="${lang}"/></th>
                            <td>
                                <input type="text" class="form-control" name="inputPhone"
                                       id="inputPhone" value=
                                               "${account.phone}" required>

                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <c:if test="${errorMessage != null}">
                    <p>
                        <font color="red"><fmt:message
                                key="${errorMessage}"
                                bundle="${lang}"/></font>
                    </p>
                </c:if>

                <div class="modal-footer">

                    <button class="btn btn-sm" type="submit" name="saveButton">
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
<c:if test="${errorMessage != null}">
    <p>
        <font color="red"><fmt:message key="${errorMessage}" bundle="${lang}"/></font>
    </p>
</c:if>

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
        <form action="/profile" method="post">
            <div id="modalEditPersonalData" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button class="close" type="button"
                                    data-dismiss="modal">×
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
                                       value=
                                               "${modalPersonalData.name}"
                                       required></div>
                            <div class="form-group">
                                <label for="inputPersonalDataPassport"
                                       class="control-label"><fmt:message
                                        key="profile.label.passport"
                                        bundle="${lang}"/></label>
                                <input type="text" class="form-control"
                                       name="inputPersonalDataPassport"
                                       id="inputPersonalDataPassport"
                                       value=
                                               "${modalPersonalData.passport}"
                                       required>
                                <input type="hidden" name="inputPersonalDataId"
                                       value="${modalPersonalData.id}">
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
                        <c:if test="${errorMessage2 != null}">
                            <p>
                                <font color="red"><fmt:message
                                        key="${errorMessage2}"
                                        bundle="${lang}"/></font>
                            </p>
                        </c:if>

                        <div class="modal-footer">

                            <button class="btn btn-sm" type="submit">
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
        <form action="/profile" method="post">
            <div id="modalChangePassword" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button class="close" type="button"
                                    data-dismiss="modal">×
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
                                       id="inputOldPassword"
                                       required></div>
                            <div class="form-group">
                                <label for="inputNewPassword"
                                       class="control-label"><fmt:message
                                        key="profile.label.new_password"
                                        bundle="${lang}"/></label>
                                <input type="password" class="form-control"
                                       name="inputNewPassword"
                                       id="inputNewPassword"
                                       required></div>
                            <div class="form-group">
                                <label for="inputConfirmNewPassword"
                                       class="control-label"><fmt:message
                                        key="profile.label.confirm_new_password"
                                        bundle="${lang}"/></label>
                                <input type="password" class="form-control"
                                       name="inputConfirmNewPassword"
                                       id="inputConfirmNewPassword"
                                       required></div>
                        </div>
                        <c:if test="${errorMessage3 != null}">
                            <p>
                                <font color="red"><fmt:message
                                        key="${errorMessage3}"
                                        bundle="${lang}"/></font>
                            </p>
                        </c:if>

                        <div class="modal-footer">

                            <button class="btn btn-sm" type="submit" name="savePassword">
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
</body>
</html>
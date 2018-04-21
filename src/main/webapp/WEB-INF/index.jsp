
<%@include file="layout/header.jspf" %>
    <title>aviasales</title>
</head>

<body>
<div class="tab-content" style="margin: auto; background: skyblue; padding: 20px">
    <form action="/flights" method="post" class="container-fluid colorlib-form">
        <div class="row" align="right">
            <div class="col-sm-3">
                <div class="form-group">
                    <label><fmt:message key="from" bundle="${lang}"/></label>
                    <div class="form-field">
                        <select name="flight_from" size="1" class="form-control">
                            <c:forEach var="flight" items="${airports}">
                                <c:choose>
                                    <c:when test="${flight.id == from}">
                                        <option value="${flight.id}">${flight.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${flight.id}">${flight.name}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="form-group">
                    <label><fmt:message key="to" bundle="${lang}"/></label>
                    <div class="form-field">
                        <select name="flight_to" size="1" class="form-control">
                            <c:forEach var="flight" items="${airports}">
                                <c:choose>
                                    <c:when test="${flight.id == to}">
                                        <option selected
                                                value="${flight.id}">${flight.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${flight.id}">${flight.name}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="form-group">
                    <label><fmt:message key="departure" bundle="${lang}"/></label>
                    <div class="form-field">
                        <input type="date" class="form-control" name="date"
                               value=${date.equals("") ? currentDate : date}>
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <input type="submit" value="<fmt:message key="find" bundle="${lang}"/>"
                       class="btn btn-primary btn-block">
            </div>
        </div>
    </form>
</div>


<c:if test="${error != null}">
    <label style="color: red"><fmt:message key="${error}" bundle="${lang}"/>!</label>
</c:if>

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
            <%----%>
            <%----%>
            <%----%>
            <%----%>
            <%----%>
        <c:choose>
            <c:when test="${account == null}">
                <label style="color: red"><fmt:message key="registrate.warning"
                                                       bundle="${lang}"/>.</label>
            </c:when>
            <c:otherwise>
                <div class="col-md-2">
                    <input type="submit" value="<fmt:message key="select" bundle="${lang}"/>"
                           class="btn btn-primary btn-block">
                </div>
            </c:otherwise>
        </c:choose>
            <%----%>
            <%----%>
            <%----%>
            <%----%>
            <%----%>
    </form>
    <h5><c:if test="${error != null}"> ${error}</c:if></h5>
</c:if>
<%@include file="layout/footer.jspf" %>


</body>
</html>
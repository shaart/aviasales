<%@include file="layout/header.jspf" %>
<c:set var="MULTIPLIER_PRICE_FOR_BUSINESS_TICKETS" value="${multiplier * 1.4}"/>
<c:set var="MULTIPLIER" value="${multiplier}"/>
<title><fmt:message key="title.ticket" bundle="${lang}"/></title>
</head>

<body>
<div class="container">
    <h4><fmt:message key="ticket.description" bundle="${lang}"/>:</h4>
    <div class="flight-info">
        <div>
            <p><fmt:message key="ticket.flight.id" bundle="${lang}"/>: ${flight.id}</p>
            <p><fmt:message key="from" bundle="${lang}"/>: ${flight.fromAirport.name}</p>
            <p><fmt:message key="to" bundle="${lang}"/>: ${flight.toAirport.name}</p>
            <p><fmt:message key="ticket.airplane.name"
                            bundle="${lang}"/>: ${flight.airplane.name}</p>
            <fmt:parseDate value="${ flight.departureTime }"
                           pattern="yyyy-MM-dd'T'HH:mm"
                           var="parsedDateTime" type="both"/>
            <p><fmt:message key="ticket.departure.time" bundle="${lang}"/>: <fmt:formatDate
                    pattern="yyyy-MM-dd HH:mm" value="${ parsedDateTime }"/></p>
            <fmt:parseDate value="${ flight.arrivalTime }"
                           pattern="yyyy-MM-dd'T'HH:mm"
                           var="parsedDateTime" type="both"/>
            <p><fmt:message key="ticket.arrival.time" bundle="${lang}"/>: <fmt:formatDate
                    pattern="yyyy-MM-dd HH:mm" value="${ parsedDateTime }"/></p>
            <p><fmt:message key="ticket.seats.free.economy"
                            bundle="${lang}"/>: ${flight.freeSeatEconomy}</p>
            <p><fmt:message key="ticket.seats.free.business"
                            bundle="${lang}"/>: ${flight.freeSeatBusiness}</p>
            <p><fmt:message key="ticket.baggage.price" bundle="${lang}"/>
                (1 <fmt:message key="ticket.baggage.kg"
                                bundle="${lang}"/>): ${flight.extraBaggagePrice}
                &#8364;</p>
        </div>
    </div>

    <div>

    </div>
    <h4><fmt:message key="ticket.personal_data.type" bundle="${lang}"/>:</h4>
    <div>
        <form action="/ticket" method="post">
            <div class="personal-data">
                <table>
                    <tr>
                        <th>
                            <fmt:message key="personal_data.first_name" bundle="${lang}"/>
                        </th>
                        <th>
                            <input class="form-group form-control" type="text" name="first_name">
                        </th>
                    </tr>
                    <tr>
                        <th>
                            <fmt:message key="personal_data.last_name" bundle="${lang}"/>
                        </th>
                        <th>
                            <input class="form-group form-control" type="text" name="last_name">
                        </th>
                    </tr>
                    <tr>
                        <th>
                            <fmt:message key="personal_data.passport" bundle="${lang}"/>
                        </th>
                        <th>
                            <input class="form-group form-control" type="text" name="passport">
                        </th>
                    </tr>
                    <tr>
                        <th>
                            <fmt:message key="personal_data.birthday" bundle="${lang}"/>
                        </th>
                        <th>
                            <input class="form-group form-control" type="date" name="birthday">
                        </th>
                    </tr>
                </table>
                <c:if test="${flight.freeSeatEconomy > 0}">
                    <input name="isBusiness" type="radio" value="false"/>
                    <fmt:message key="flight.label.economy_class" bundle="${lang}"/>
                    <fmt:formatNumber value="${(flight.baseTicketPrice * MULTIPLIER)/100}"
                                      minFractionDigits="2"
                                      maxFractionDigits="2"/> &#8364;
                    (<fmt:message key="ticket.baggage.weight" bundle="${lang}"/> 8 <fmt:message
                        key="ticket.baggage.kg" bundle="${lang}"/>) <br>
                </c:if>
                <c:if test="${flight.freeSeatBusiness > 0}">
                    <input name="isBusiness" type="radio" value="true"/>
                    <fmt:message key="flight.label.business_class" bundle="${lang}"/>
                    <fmt:formatNumber
                            value="${(flight.baseTicketPrice * MULTIPLIER_PRICE_FOR_BUSINESS_TICKETS)/100}"
                            minFractionDigits="2"
                            maxFractionDigits="2"/> &#8364;
                    (<fmt:message key="ticket.baggage.weight" bundle="${lang}"/> 20 <fmt:message
                        key="ticket.baggage.kg" bundle="${lang}"/>) <br>
                </c:if>
            </div>
            <c:if test="${error != null}">
            <label class="error"><fmt:message key="${error}" bundle="${lang}"/>!</label>
            </c:if>
            <br>
            <c:if test="${flight.freeSeatBusiness > 0 || flight.freeSeatEconomy > 0}">
            <div class="col-sm-9"></div>
            <div class="s-btn col-sm-3">
                <input type="submit"
                       value="<fmt:message key="buy" bundle="${lang}"/>"
                       class="btn btn-primary btn-block">
            </div>
            </c:if>
    </div>
</div>
</form>
</body>

<%@include file="layout/footer.jspf" %>

</html>
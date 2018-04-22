<%@include file="layout/header.jspf" %>
    <title>aviasales</title>
</head>

<body>
<h4><fmt:message key="ticket.description" bundle="${lang}"/>:</h4>

<div id="dataOfFlight" style="background-color: gainsboro">
    <p><fmt:message key="ticket.flight.id" bundle="${lang}"/>: ${flight.id}</p>
    <p><fmt:message key="from" bundle="${lang}"/>: ${flight.fromAirport.name}</p>
    <p><fmt:message key="to" bundle="${lang}"/>: ${flight.toAirport.name}</p>
    <p><fmt:message key="ticket.airplane.name" bundle="${lang}"/>: ${flight.airplane.name}</p>
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
    <p><fmt:message key="ticket.seats.free.economy" bundle="${lang}"/>: ${flight.freeSeatEconomy}</p>
    <p><fmt:message key="ticket.seats.free.business" bundle="${lang}"/>: ${flight.freeSeatBusiness}</p>
    <p><fmt:message key="ticket.baggage.price" bundle="${lang}"/>
        (1 <fmt:message key="ticket.baggage.kg" bundle="${lang}"/>): ${flight.extraBaggagePrice} &#8364;</p>
</div>

<h4><fmt:message key="ticket.personal_data.type" bundle="${lang}"/>:</h4>

<form action="/ticket" method="post">
    <div id="personalData" style="background-color: wheat">
        <table>
            <tr>
                <th>
                    <fmt:message key="personal_data.first_name" bundle="${lang}"/>
                </th>
                <th>
                    <input type="text" name="first_name">
                </th>
            </tr>
            <tr>
                <th>
                    <fmt:message key="personal_data.last_name" bundle="${lang}"/>
                </th>
                <th>
                    <input type="text" name="last_name">
                </th>
            </tr>
            <tr>
                <th>
                    <fmt:message key="personal_data.passport" bundle="${lang}"/>
                </th>
                <th>
                    <input type="text" name="passport">
                </th>
            </tr>
            <tr>
                <th>
                    <fmt:message key="personal_data.birthday" bundle="${lang}"/>
                </th>
                <th>
                    <input type="date" name="birthday">
                </th>
            </tr>
        </table>
        <c:if test="${flight.freeSeatEconomy > 0}">
            <input name="isBusiness" type="radio" value="false"/>
            <fmt:message key="flight.label.economy_class" bundle="${lang}"/>
            <fmt:formatNumber value="${flight.baseTicketPrice}" minFractionDigits="2" maxFractionDigits="2"/> &#8364;
            (<fmt:message key="ticket.baggage.weight" bundle="${lang}"/> 8 <fmt:message key="ticket.baggage.kg" bundle="${lang}"/>) <br>
        </c:if>
        <c:if test="${flight.freeSeatBusiness > 0}">
            <input name="isBusiness" type="radio" value="true"/>
            <fmt:message key="flight.label.business_class" bundle="${lang}"/>
            <fmt:formatNumber value="${flight.baseTicketPrice*1.4}" minFractionDigits="2" maxFractionDigits="2"/> &#8364;
            (<fmt:message key="ticket.baggage.weight" bundle="${lang}"/> 20 <fmt:message key="ticket.baggage.kg" bundle="${lang}"/>) <br>
        </c:if>
        <c:if test="${error != null}">
            <h5 style="color: red"><fmt:message key="${error}" bundle="${lang}"/>!</h5>
        </c:if>
    </div>
    <c:if test="${flight.freeSeatBusiness > 0 || flight.freeSeatEconomy > 0}">
        <div align="right">
            <input type="submit" value="<fmt:message key="buy" bundle="${lang}"/>">
        </div>
    </c:if>
</form>
</body>

<%@include file="layout/footer.jspf" %>

</html>
<%@include file="layout/header.jspf" %>
<title>aviasales</title>
</head>

<body>

<h4 align="center"><fmt:message key="buy.successfully" bundle="${lang}"/></h4>
<hr/>

<div class="container">
    <h4><fmt:message key="ticket.description" bundle="${lang}"/>:</h4>
    <div class="flight-info">
        <p><fmt:message key="ticket.flight.id" bundle="${lang}"/>: ${ticket.flight.id}</p>
        <p><fmt:message key="from" bundle="${lang}"/>: ${ticket.flight.fromAirport.name}</p>
        <p><fmt:message key="to" bundle="${lang}"/>: ${ticket.flight.toAirport.name}</p>
        <p><fmt:message key="ticket.airplane.name"
                        bundle="${lang}"/>: ${ticket.flight.airplane.name}</p>
        <fmt:parseDate value="${ticket.flight.departureTime }"
                       pattern="yyyy-MM-dd'T'HH:mm"
                       var="parsedDateTime" type="both"/>
        <p><fmt:message key="ticket.departure.time" bundle="${lang}"/>: <fmt:formatDate
                pattern="yyyy-MM-dd HH:mm" value="${ parsedDateTime }"/></p>
        <fmt:parseDate value="${ticket.flight.arrivalTime }"
                       pattern="yyyy-MM-dd'T'HH:mm"
                       var="parsedDateTime" type="both"/>
        <p><fmt:message key="ticket.arrival.time" bundle="${lang}"/>: <fmt:formatDate
                pattern="yyyy-MM-dd HH:mm" value="${ parsedDateTime }"/></p>
        <c:if test="${!ticket.isBusiness}">
            <p><fmt:message key="ticket.class" bundle="${lang}"/>: <fmt:message
                    key="ticket.economy" bundle="${lang}"/></p>
        </c:if>
        <c:if test="${ticket.isBusiness}">
            <p><fmt:message key="ticket.class" bundle="${lang}"/>: <fmt:message
                    key="ticket.business"
                    bundle="${lang}"/></p>
        </c:if>
        <p><fmt:message key="personal_data.full_name"
                        bundle="${lang}"/>: ${ticket.personalData.name}</p>
        <p><fmt:message key="personal_data.passport"
                        bundle="${lang}"/>: ${ticket.personalData.passport}</p>
        <p><fmt:message key="personal_data.birthday"
                        bundle="${lang}"/>: ${ticket.personalData.dateOfBirth}</p>
        <p><fmt:message key="ticket.price" bundle="${lang}"/>:
            <fmt:formatNumber value="${ticket.price/100}" minFractionDigits="2"
                              maxFractionDigits="2"/>
            &#8364;</p>
    </div>
</div>
</body>

<%@include file="layout/footer.jspf" %>

</html>

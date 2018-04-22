<%@include file="layout/header.jspf" %>
    <title><fmt:message key="manage.title" bundle="${lang}"/></title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4">
            <a href="/manage/flights">
                <div class="text"><fmt:message key="manage.flights.title" bundle="${lang}"/></div>
                <img class="img-responsive" src="resources/manage/flights.png" width="300"
                     height="auto">
            </a>
        </div>
        <div class="col-sm-6 col-md-4">
            <a href="/manage/airplanes">
                <div class="text"><fmt:message key="manage.airplanes.title" bundle="${lang}"/></div>
                <img class="img-responsive" src="resources/manage/airplanes.png" width="300"
                     height="auto">
            </a>
        </div>
        <div class="col-sm-6 col-md-4">
            <a href="/manage/airports">
                <div class="text"><fmt:message key="manage.airports.title" bundle="${lang}"/></div>
                <img class="img-responsive" src="resources/manage/airports.png" width="300"
                     height="auto">
            </a>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-6 col-md-4">
            <a href="/manage/accounts">
                <div class="text"><fmt:message key="manage.accounts.title" bundle="${lang}"/></div>
                <img class="img-responsive" src="resources/manage/accounts.png" width="300"
                     height="auto">
            </a>
        </div>
        <div class="col-sm-6 col-md-4">
            <a href="/manage/personals">
                <div class="text"><fmt:message key="manage.personaldata.title" bundle="${lang}"/></div>
                <img class="img-responsive" src="resources/manage/personals.png" width="300"
                     height="auto">
            </a>
        </div>
        <div class="col-sm-6 col-md-4">
            <a href="/manage/tickets">
                <div class="text"><fmt:message key="manage.tickets.title" bundle="${lang}"/></div>
                <img class="img-responsive" src="resources/manage/tickets.png" width="300"
                     height="auto">
            </a>
        </div>
    </div>
</div>
<%@include file="layout/footer.jspf" %>
</body>
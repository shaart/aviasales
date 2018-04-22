<%@include file="layout/header.jspf" %>
    <title><fmt:message key="error.title" bundle="${lang}"/></title>
</head>
<body>
<div class="container">
    <h2><fmt:message key="error.title" bundle="${lang}"/></h2>
    <div class="alert alert-danger">
        <p><c:out value="${error}" escapeXml="false"/></p>
    </div>
    <a href="${previousPage}" class="btn btn-primary">
        <span class="glyphicon glyphicon-chevron-left"></span>
        <fmt:message key="error.label.go.back" bundle="${lang}"/>
    </a>
</div>
<%@include file="layout/footer.jspf" %>
</body>
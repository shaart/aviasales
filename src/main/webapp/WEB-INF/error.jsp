<%@include file="layout/header.jspf" %>
    <title><fmt:message key="error.title" bundle="${lang}"/></title>
</head>
<body>
<h1>Error</h1>
<pre>
    <p><c:out value="${error}" escapeXml="false"/></p>
</pre>
<%@include file="layout/footer.jspf" %>
</body>
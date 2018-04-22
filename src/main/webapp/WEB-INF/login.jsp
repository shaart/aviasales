<%@include file="layout/header.jspf" %>
</head>
<body>

<div class="container">

    <form class="form-signin" action="/login" method="post" style="width:250px">
        <h2 class="form-signin-heading"><fmt:message key="log_in" bundle="${lang}"/></h2>
        <div class="form-group">
            <label for="inputLogin" class="sr-only"><fmt:message key="login.label.login"
                                                                 bundle="${lang}"/></label>
            <input type="login" name="inputLogin" id="inputLogin" class="form-control" placeholder=
            <fmt:message key="username" bundle="${lang}"/> required autofocus>
        </div>
        <div class="form-group">
            <label for="inputPassword" class="sr-only"></label>
            <input type="password" name="inputPassword" id="inputPassword" class="form-control"
                   placeholder=
                   <fmt:message key="password" bundle="${lang}"/> required>
        </div>
        <div class="form-group">
            <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="log_in"
                                                                                        bundle="${lang}"/></button>
        </div>
    </form>
    <c:if test="${errorMessage != null}">
        <p>
            <font color="red"><fmt:message key="${errorMessage}" bundle="${lang}"/></font>
        </p>
    </c:if>
</div>
<%@include file="layout/footer.jspf" %>
</body>
</html>
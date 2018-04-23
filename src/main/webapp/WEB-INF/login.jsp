<%@include file="layout/header.jspf" %>
</head>
<body>

<div class="container" style="text-align: center;">

    <form data-toggle="validator" style="width:300px; display: inline-block" role="form" class="form-signin" action="/login" method="post"
          style="width:250px">
        <h2 class="form-signin-heading"><fmt:message key="log_in" bundle="${lang}"/></h2>
        <div class="form-group">
            <input type="login" name="inputLogin" pattern="[_@a-zA-z0-9\.]{1,50}" data-minlength="1" data-maxlength="50" id="inputLogin"
                   class="form-control" data-minlength-error="<fmt:message key="input.error.minlegth1"
                    bundle="${lang}"/>" data-maxlength-error="<fmt:message key="input.error.maxlegth50"
                    bundle="${lang}"/>" data-error="<fmt:message key="register.error.wrong_input"
                    bundle="${lang}"/>" placeholder=
                   <fmt:message key="register.label.login" bundle="${lang}"/> required>
            <div class="help-block with-errors"  style="margin-top: 0px; margin-bottom: 0px"></div>
        </div>
        <div class="form-group">
            <input type="password" data-minlength="6" data-maxlength="50"
                   class="form-control" data-minlength-error="<fmt:message key="input.error.minlegth6"
                    bundle="${lang}"/>" data-maxlength-error="<fmt:message key="input.error.maxlegth50"
                    bundle="${lang}"/>" id="inputPassword" name="inputPassword"
                   data-error="<fmt:message key="register.error.wrong_input"
                                   bundle="${lang}"/>" placeholder=
                   <fmt:message
                           key="password" bundle="${lang}"/> required>
            <div class="help-block with-errors" style="margin-top: 0px; margin-bottom: 0px"></div>
        </div>
        <div class="form-group">
            <button class="btn btn-primary btn-block" type="submit"><fmt:message key="log_in"
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
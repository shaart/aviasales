<%@include file="layout/header.jspf" %>
</head>
<body>
<div class="container" style=" width:500px; text-align: center">

    <form id="register_form" data-toggle="validator" role="form" class="form-signin"
          action="/register" method="post">
        <h2 class="form-signin-heading"><fmt:message key="register.label.signup"
                                                     bundle="${lang}"/></h2>

        <div class="form-group">
            <label for="inputLogin" class="control-label"><fmt:message key="register.label.login"
                                                                       bundle="${lang}"/></label>
            <input type="login" name="inputLogin" pattern="[_@\.a-zA-z0-9]{1,50}" id="inputLogin"
                   class="form-control" data-minlength="1" data-minlength="50"
                   data-minlength-error="<fmt:message key="input.error.minlegth1"
                    bundle="${lang}"/>" data-maxlength-error="<fmt:message key="input.error.maxlegth50"
                    bundle="${lang}"/>" data-error="<fmt:message key="register.error.wrong_input"
                    bundle="${lang}"/>" placeholder=
                   <fmt:message key="register.label.login" bundle="${lang}"/> autofocus required>
            <div class="help-block with-errors"></div>
        </div>

        <div class="form-group">
            <label for="inputName" class="control-label"><fmt:message key="register.label.fullname"
                                                                      bundle="${lang}"/></label>
            <input type="text" name="inputName" id="inputName"
                   pattern="[A-Za-z\u0400-\u04FF]{1,20}\s[A-Za-z\u0400-\u04FF]{1,20}\s?[A-Za-z\u0400-\u04FF]{0,20}"
                   class="form-control" data-error="<fmt:message key="register.error.wrong_input"
                    bundle="${lang}"/>" placeholder=
                   <fmt:message key="register.label.fullname" bundle="${lang}"/> required>
            <div class="help-block with-errors"></div>
        </div>

        <div class="form-group">
            <label for="inputPassword" class="control-label"><fmt:message key="password"
                                                                          bundle="${lang}"/></label>
            <div class="form-inline row">
                <div class="form-group col-md-6">
                    <input type="password" data-minlength="6" data-maxlength="50"
                           data-minlength-error="<fmt:message key="input.error.minlegth6"
                    bundle="${lang}"/>" data-maxlength-error="<fmt:message key="input.error.maxlegth50"
                    bundle="${lang}"/>"
                           class="form-control" id="inputPassword" name="inputPassword"
                           data-error="<fmt:message key="register.error.wrong_input"
                                   bundle="${lang}"/>" placeholder=
                           <fmt:message
                                   key="password" bundle="${lang}"/> required>
                    <div class="help-block with-errors"></div>
                </div>
                <div class="form-group col-md-6">
                    <input type="password" name="inputPasswordConfirm" data-minlength="6"
                           data-maxlength="50" class="form-control" id="inputPasswordConfirm"
                           data-minlength-error="<fmt:message key="input.error.minlegth6"
                    bundle="${lang}"/>" data-maxlength-error="<fmt:message key="input.error.maxlegth50"
                    bundle="${lang}"/>" data-error="<fmt:message key="register.error.wrong_input"
                            bundle="${lang}"/>"
                           data-match="#inputPassword" data-match-error="<fmt:message key="register.error.not_match"
                            bundle="${lang}"/>"
                           placeholder=
                           <fmt:message
                                   key="register.label.passwordConfirm" bundle="${lang}"/> required>
                    <div class="help-block with-errors"></div>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="inputEmail" class="control-label"><fmt:message key="register.label.email"
                                                                       bundle="${lang}"/></label>
            <input type="email" name="inputEmail" id="inputEmail" data-minlength="6"
                   data-maxlength="50" class="form-control"
                   data-minlength-error="<fmt:message key="input.error.minlegth6"
                    bundle="${lang}"/>" data-maxlength-error="<fmt:message key="input.error.maxlegth50"
                    bundle="${lang}"/>"
                   data-error="<fmt:message key="register.error.wrong_input"
                           bundle="${lang}"/>" placeholder=
                           "<fmt:message key="register.label.email" bundle="${lang}"/>" required>
            <div class="help-block with-errors"></div>
        </div>

        <div class="form-group">
            <label for="inputPhone" class="control-label"><fmt:message key="register.label.phone"
                                                                       bundle="${lang}"/></label>
            <input type="tel" class="form-control" name="inputPhone"
                   pattern="[\+]?[0-9][(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}"
                   id="inputPhone"
                   data-error="<fmt:message key="register.error.wrong_input"
                           bundle="${lang}"/>"
                   placeholder=
                   <fmt:message key="register.label.phone" bundle="${lang}"/> required>
            <div class="help-block with-errors"></div>
        </div>
        <div class="form-group">
            <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message
                    key="register.label.signup" bundle="${lang}"/></button>
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
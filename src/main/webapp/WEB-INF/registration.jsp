<%@include file="layout/header.jspf" %>
</head>
<body>
<div class="container">

    <form id="register_form" class="form-signin" action="/register" method="post" style="width:500px">
        <h2 class="form-signin-heading"><fmt:message key="register.label.signup"
                                                     bundle="${lang}"/></h2>

        <div class="form-group">
            <label for="inputLogin" class="control-label"><fmt:message key="register.label.login"
                                                                       bundle="${lang}"/></label>
            <input type="login" name="inputLogin" id="inputLogin" class="form-control" placeholder=
            <fmt:message key="register.label.login" bundle="${lang}"/> autofocus>
        </div>

        <div class="form-group">
            <label for="inputName" class="control-label"><fmt:message key="register.label.fullname"
                                                                      bundle="${lang}"/></label>
            <input type="text" name="inputName" id="inputName" class="form-control" placeholder=
            <fmt:message key="register.label.fullname" bundle="${lang}"/> required>
        </div>

        <div class="form-group">
            <label for="inputPassword" class="control-label"><fmt:message key="password"
                                                                          bundle="${lang}"/></label>
            <input type="password" name="inputPassword" id="inputPassword"
                   class="form-control" placeholder=
                   <fmt:message key="password" bundle="${lang}"/>>
        </div>

        <div class="form-group">
            <label for="inputPassword" class="control-label"><fmt:message
                    key="register.label.passwordConfirm" bundle="${lang}"/></label>
            <input type="password" name="inputPasswordConfirm" id="inputPasswordConfirm"
                   class="form-control" placeholder=
                   <fmt:message key="register.label.passwordConfirm" bundle="${lang}"/> >
        </div>

        <div class="form-group">
            <label for="inputEmail" class="control-label"><fmt:message key="register.label.email"
                                                                       bundle="${lang}"/></label>
            <input type="email" name="inputEmail" id="inputEmail" class="form-control" placeholder=
            <fmt:message key="register.label.email" bundle="${lang}"/>>
        </div>

        <div class="form-group">
            <label for="inputPhone" class="control-label"><fmt:message key="register.label.phone"
                                                                       bundle="${lang}"/></label>
            <input type="text" class="form-control" name="inputPhone"
                   id="inputPhone" placeholder=
                   <fmt:message key="register.label.phone" bundle="${lang}"/> required>
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

    <%--<script>
      var password = document.getElementById("inputPassword")
          , confirm_password = document.getElementById("inputPasswordConfirm");
      function validatePassword(){
        if(password.value != confirm_password.value) {
          confirm_password.setCustomValidity("<fmt:message key="profile.error.different_passwords" bundle="${lang}"/>");
        } else {
          confirm_password.setCustomValidity('');
        }
      }
      password.onchange = validatePassword;
      confirm_password.onkeyup = validatePassword;
    </script>--%>
</div>
<%@include file="layout/footer.jspf" %>
</body>
</html>
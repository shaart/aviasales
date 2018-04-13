<%--
  Created by IntelliJ IDEA.
  User: vikto
  Date: 4/12/2018
  Time: 8:52 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>

<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<div class="container">

    <form class="form-signin" action="/login" method="post" style="width:250px">
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="inputLogin" class="sr-only">Email address</label>
        <input type="email" name="inputLogin" id="inputLogin" class="form-control" placeholder="Login" required
               autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" name="inputPassword" id="inputPassword" class="form-control" placeholder="Password"
               required>
        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me"> Remember me
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>
    <p>
        <font color="red">${errorMessage}</font>
    </p>

</div>

</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ?
    param.language : not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="com.epam.aviasales.bundles.global" var="lang"/>
<!DOCTYPE html>
<html lang="${language}">
<body>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
    </select>
</form>
<h1>Welcome to ${pageName}!</h1>
<form action="login" method="post">
    <br><br>
    <label for="username"><fmt:message key="username" bundle="${lang}"/></label>
    <input id="username" type="text" name="username"><br>
    <label for="password"><fmt:message key="password" bundle="${lang}"/></label>
    <input id="password" type="password" name="password"><br>
    <input type="submit" value="<fmt:message key="log_in" bundle="${lang}"/>"/>
    <br><br>
</form>
</body>
</html>
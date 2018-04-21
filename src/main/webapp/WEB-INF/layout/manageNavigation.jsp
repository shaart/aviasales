    <%@ page
            contentType="text/html;charset=UTF-8"
            pageEncoding="UTF-8"
            language="java" %>
        <%@ taglib
                prefix="c"
                uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib
                prefix="fmt"
                uri="http://java.sun.com/jsp/jstl/fmt" %>
        <c:set
                var="language"
                value="${not empty param.language ? param.language : not empty language ?
  language : pageContext.request.locale}"
                scope="session"/>
        <fmt:setLocale value="${language}"/>
        <fmt:setBundle
                basename="com.epam.aviasales.bundles.global"
                var="lang"/>
        <div class="container">
        <a href="/manage" class="btn btn-primary">
            <span class="glyphicon glyphicon-chevron-left"></span>
            <fmt:message key="label.manage.back" bundle="${lang}"/>
        </a>
        </div>
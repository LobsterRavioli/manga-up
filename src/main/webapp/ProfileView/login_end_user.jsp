<%--
  Created by IntelliJ IDEA.
  User: tommasosorrentino
  Date: 12/01/23
  Time: 18:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Login</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css_s/form_style.css">
    </head>

    <body>
        <p>${error_message}</p>
        <form method="post" action="${pageContext.request.contextPath}/LoginEndUserServlet">
            <fieldset>
                <legend>Please, log-in to continue</legend>
                <input type="text" name="email"  placeholder="E-mail" required="required" />
                <input type="password" name="password"  placeholder="password" required="required" />
                <input type="submit" id="sub" value="Login"/>
            </fieldset>

        </form>
    </body>
</html>

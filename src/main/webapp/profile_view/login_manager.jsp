<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <link rel="stylesheet" href="#">
    </head>

<body>

    <h1>LOG-IN</h1>

    <form action="${pageContext.request.contextPath}/LoginManager" method="POST">
        <label for="username">Username:</label><br>
        <input type="text" placeholder="username" id="username" name="username" required/><br>
        <label for="password">Password:</label><br>
        <input type="password" placeholder="password" id="password" name="password" required/><br><br><br>
        <label for="U_M">User manager:</label>
        <input type="radio" id="U_M" name="roleName" value="USER_MANAGER" checked="checked"/>
        <label for="O_M">Order manager:</label>
        <input type="radio" id="O_M" name="roleName" value="ORDER_MANAGER"/>
        <label for="C_M">Catalog manager:</label>
        <input type="radio" id="C_M" name="roleName" value="CATALOG_MANAGER"/><br>
        <input type="submit" id="sub" value="Login"/>

        <%
           String errMessage = (String)request.getAttribute("error");
           if(errMessage != null) {
        %>
            <p style='color: red;'><%=errMessage %></p>
        <% } %>

    </form>
</body>
</html>

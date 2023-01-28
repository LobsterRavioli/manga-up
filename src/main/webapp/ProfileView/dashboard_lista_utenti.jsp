<%--
  Created by IntelliJ IDEA.
  User: tommasosorrentino
  Date: 09/01/23
  Time: 21:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Dashboard lista utenti</title>
</head>
<body>

    <c:forEach items="${users}" var="user">
        <p>${user.username} </p>
        <c:forEach items="${user.roles}" var="role">
            <p> ${role}</p>
        </c:forEach>
        <form action="${pageContext.request.contextPath}/UserDeleteServlet" method="post">
            <input type="hidden" name="username" value=${user.username} />
            <input type="submit" value="elimina" name="act" id="box_button" class="accept">
        </form>
    </c:forEach>

</body>
</html>

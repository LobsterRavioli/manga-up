<%--
  Created by IntelliJ IDEA.
  User: tommasosorrentino
  Date: 09/01/23
  Time: 21:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Home Page User Manager</title>
</head>
<body>

    <jsp:include page="/header_manager_homepage.jsp"/>

    <a href="${pageContext.request.contextPath}/UserListServlet">Lista utenti</a>
    <a href="creazione_utente.jsp">Crea nuovo utente</a>

    <hr>

</body>
</html>

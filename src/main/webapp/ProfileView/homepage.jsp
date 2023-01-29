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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css_s/homeManagerStyle.css">
</head>
<body>

    <jsp:include page="/header_manager_homepage.jsp"/>

    <div class="home">
    <nav id=items>
        <div>
            <a href="${pageContext.request.contextPath}/UserListServlet">Lista utenti<br><br></a>
            <a href="creazione_utente.jsp">Crea nuovo utente<br><br></a>
        </div>
    </nav>
    </div>
</body>
</html>

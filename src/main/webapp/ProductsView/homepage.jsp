<%--
  Created by IntelliJ IDEA.
  User: tommasosorrentino
  Date: 09/01/23
  Time: 23:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Homepage</title>
</head>
<body>

<c:if test="${sessionScope.user != null}">

    <h1>Benvenuto ${sessionScope.user.name}!</h1>
    <a href="${pageContext.request.contextPath}/profile_view/dashboard_utente.jsp">Dash-board utente</a>
    <a href="${pageContext.request.contextPath}/LogoutEndUserServlet">Servizio di logout</a>

</c:if>

<c:if test="${sessionScope.user == null}">

    <a href="${pageContext.request.contextPath}/profile_view/login_end_user.jsp">Servizio di accesso</a>
    <a href="${pageContext.request.contextPath}/profile_view/registrazione_utente.jsp">Servizio di registrazione</a>

</c:if>





</body>
</html>

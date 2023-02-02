<%--
  Created by IntelliJ IDEA.
  User: tommasosorrentino
  Date: 10/01/23
  Time: 21:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>DashBoard</title>
</head>
<body>
<%@ include file="/header.jsp" %>
<ul>
    <li><a href="${pageContext.request.contextPath}/AddressDashboardServlet">Lista indirizzi</a></li>
    <li><a href="${pageContext.request.contextPath}/CreditCardDashboardServlet">Lista carte di pagamento</a></li>
    <li><a href="${pageContext.request.contextPath}/OrderListServlet">Storico degli ordini</a></li>
</ul>

</body>
</html>

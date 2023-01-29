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

<a href="${pageContext.request.contextPath}/AddressServletDashboardServlet">Lista indirizzi</a>
<a href="${pageContext.request.contextPath}/CreditCardDashboardServlet">Lista carte di pagamento</a>
<a href="${pageContext.request.contextPath}/">Lista carte di pagamento</a>

</body>
</html>

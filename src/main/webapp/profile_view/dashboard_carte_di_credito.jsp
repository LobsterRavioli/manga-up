<%--
  Created by IntelliJ IDEA.
  User: tommasosorrentino
  Date: 09/01/23
  Time: 21:24
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<c:forEach items="${cards}" var="card">
    <p>${card.cardNumber}, ${card.cvv}, ${card.cardHolder.name}, ${card.cardHolder.surname}</p><br/>
</c:forEach>

</body>
</html>

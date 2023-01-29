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
    <title>Indirizzi</title>
</head>
<body>



<c:if test="${addresses == null}">
    <p> Non hai nessun indirizzo registrane uno.</p>
</c:if>

<c:forEach items="${addresses}" var="address">
    <p>Paese: ${address.country}, Città: ${address.city}, Via: ${address.street}, postal code: ${address.postalCode}, phoneNumber ${address.phoneNumber}, Regione: ${address.region} </p> <br/>

<form action="${pageContext.request.contextPath}/AddressDeleteServlet" method="post">

    <input type="hidden" name="address_id" value=${address.id}/>
    <input type="submit" value="elimina" name="act" id="box_button" class="accept">
</form>


</c:forEach>



</body>
</html>

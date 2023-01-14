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

<a href="${pageContext.request.contextPath}/profile_view/form_carta_credito.jsp">Servizio di aggiunta di carta di credito.</a>

<c:if test="${empty cards}">
    <p> Non hai nessuna carta di credito. </p>
</c:if>

<c:forEach items="${cards}" var="card">
    <p>Numero Carta: ${card.cardNumber}, Proprietario: ${card.cardHolder}, Data di scadenza:${card.expirementDate} </p><br/>
<form action="/CreditCardDeleteServlet">
    <input type="hidden" name="credit_card_id" value=${card.id}/>
    <input type="submit" value="deny" name="act" id="box_button" class="accept">
</form>

</c:forEach>
</body>
</html>

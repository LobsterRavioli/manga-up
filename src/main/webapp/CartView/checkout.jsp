<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="User.AccountService.CreditCard" %>
<%@ page import="User.AccountService.Address" %>
<%@ page import="User.AccountService.AddressDAO" %>
<%--
  Created by IntelliJ IDEA.
  User: Francesco Monzillo
  Date: 29/01/2023
  Time: 21:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Checkout</title>
</head>
<body>

<%@ include file="/header.jsp" %>

    <form id="checkoutForm" method="post" action="/checkoutServlet">
        <c:forEach items="${addresses}" var="address">
            <input type="radio" form="checkoutForm" id="address${address.id}" name="address" value="${address.id}">
            <label for="address${address.id}">Paese: ${address.country}, Città: ${address.city}, Via: ${address.street}, postal code: ${address.postalCode}, phoneNumber ${address.phoneNumber}, Regione: ${address.region} </label>
        </c:forEach>


        <c:forEach items="${cards}" var="card">
            <label for="carta${card.id}">Numero Carta: ${card.cardNumber}, Proprietario: ${card.cardHolder}, Data di scadenza: ${card.expirementDate} </label><br/>
            <input type="radio" form="checkoutForm" id="carta${address.id}" name="card" value="${card.id}">
        </c:forEach>


        <div>
            <table id="productVisualizer" class="productVisualizer">
                <tr>
                    <th class="fontMinus">Nome</th>
                    <th class="fontMinus">Costo Unitario</th>
                    <th class="fontMinus">Quantità</th>
                </tr>
                <%Cart carrello = (Cart) request.getSession(false).getAttribute("cart");%>
                <%int totalProducts=0;%>
                <%for (Map.Entry <Manga, Integer> set : carrello.getProdotti().entrySet()){
                        Manga currManga = set.getKey();
                        int currQuantity = set.getValue();
                        totalProducts = totalProducts + set.getValue();%>
                <tr id="productInspector" class="productInspector">
                    <td><%=currManga.getName()%></td>
                    <td><%=currManga.getPrice()%></td>
                    <td><%=currQuantity%></td>
                </tr>
                <%}%>
            </table>
            <p>Quantità totale di prodotti nel carrello: <%=totalProducts%></p>
        </div>

        <button type="submit">Checkout</button>
    </form>
</body>
</html>

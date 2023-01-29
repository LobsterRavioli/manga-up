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

<%ArrayList<CreditCard> listaCarte = (ArrayList<CreditCard>) request.getAttribute("carte");
  ArrayList<Address> listaAddresses = (ArrayList<Address>) request.getAttribute("indirizzi");
  /*  for(int i=0;i<listaCarte.size();i++)
        System.out.println(listaCarte.get(i));*/
    listaCarte.set(1,new CreditCard());
    listaAddresses.set(1,new Address());
%>

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




<%int i=0;%>
<form id ="checkoutForm" action="" method="post">
    <div>
  <%for(;i<listaCarte.size();i++){
    CreditCard currentCard = (CreditCard) listaCarte.get(i);%>
        <input type="radio" form="checkoutForm" id="carta<%=currentCard.getId()%>" name="carte" value="<%=currentCard.getId()%>">
      <%}%>
    </div>
    <%for(;i<listaAddresses.size();i++){
        Address currentAddress = (Address) listaAddresses.get(i);%>
    <input type="radio" form="checkoutForm" id="indirizzo<%=currentAddress.getId()%>" name="indirizzo" value="<%=currentAddress.getId()%>">
    <%}%>
</form>
</body>
</html>

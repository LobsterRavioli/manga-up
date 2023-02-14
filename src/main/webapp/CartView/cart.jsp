<%--
  Created by IntelliJ IDEA.
  User: Francesco Monzillo
  Date: 16/01/2023
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="ISO-8859-1" import="java.util.ArrayList"%>
<%@ page import="Cart.CheckoutService.CartDAO" %>
<%@ page import="Cart.CheckoutService.Cart" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="Merchandising.MerchandiseService.Manga" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="ISO-8859-1">
  <title>Cart</title>
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css_s/cart.css">
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
</head>

<style>
  .cart-elements-count{
    top: 12px;
    right: 10px !important;
  }
</style>
<body>
<script>
  function add(x){
    var quantityDiv=document.getElementById(x);
    quantityDiv.innerHtml=(parseInt(quantityDiv.innerHtml)+1).toString();
  }

  function controlValue(input){
    if(parseInt(input.value)>parseInt(input.max)){
      input.value = input.max;
      alert("Quantità massima per questo prodotto: "+input.max);
    }
  }

</script>

<body>
<%@ include file="/header.jsp" %>
<%if(request.getParameter("agg")!=null){%>
  <p id="corrUpdate" style="text-align: center;">Il carrello è stato aggiornato con successo</p>
<%}%>

<%if(request.getParameter("prob")!=null){%>
      <p id="removedProduct">Errore relativo al prodotto selezionato: Prodotto non esistente</p>
<%}%>

<%if(request.getParameter("qtity")!=null){%>
<p id="qtity_error">Non sono presenti abbastanza unità del prodotto per la quantità selezionata</p>
<%}%>

<%if(request.getAttribute("error")!=null){%>
<p id="excessiveQuantity"><%=request.getAttribute("error")%></p>
<%}%>

<div class="cart_section">
  <div class="container-fluid" id="container-fluid">
    <div class="row">
      <div class="col-lg-10 offset-lg-1">
        <div class="cart_container">
          <div class="cart_title">Carrello</div>

          <%if(request.getAttribute("errorCard")!=null){%>
              <p><%=request.getAttribute("errorCard")%></p>
          <%}%>
          <%if(request.getAttribute("errorAddress")!=null){%>
            <p><%=request.getAttribute("errorCard")%></p>
          <%}%>
              <%Cart cart = (Cart) session.getAttribute("cart");
                if(cart.getProdotti().size()>0){
                  int i=0;
                  int j;
                  String previous="";
                  double sum=0;
                  for (Map.Entry<Manga,Integer> set : cart.getProdotti().entrySet()) {
                    i++;
                    Manga m = set.getKey();
                    //currentObj=new Manga("isbn","brand","","","",0,null,0,"name","description",3.3,0.0,0.0,0.0,"collections",-1, Product.ProductState.NEW,"interior","");;%>
              <div>
                <div class="cart_items" id="cart_items">
                  <ul class="cart_list" id="cart_list">
                <li class="cart_item clearfix">
                  <div class="cart_item_info d-flex flex-md-row flex-column justify-content-between">
                    <div class="cart_item_image childflex" style="width: 25%;height: 32%;display: flex;justify-content: center;">
                      <img src="${pageContext.request.contextPath}/images/products/<%=m.getImagePath()%>" style="height: 94px;object-fit: contain;max-height: 69%;"></img></div>
                    <div class="cart_item_name cart_info_col childflex">
                      <div class="cart_item_title">Nome</div>
                      <div class="cart_item_text"><%=m.getName()%></div>
                    </div>
                    <div class="cart_item_quantity cart_info_col childflex">
                      <div class="cart_item_title">Quantita'</div>
                      <form action="">
                        <div class="cart_item_text" id="<%=m.getId() %>">
                          <input type="number" form="updateProd" value="<%=set.getValue()%>" min="0" max="<%=m.getQuantity()%>" oninput="controlValue(this);" title="Quantità massima inseribile:<%=m.getQuantity()%>" aria-label="Search" class="form-control" id="countProducts<%=m.getId()%>" style="width: 100px">
                          <button class="btn btn-primary btn-md my-0 p waves-effect waves-light" type="button" onclick="updateElement(<%=m.getId()%>)">Change quantity
                          <i class="fas fa-shopping-cart ml-1"></i>
                        </button>
                        </div>
                      </form>
                    </div>
                    <div class="cart_item_price cart_info_col childflex">
                      <div class="cart_item_title">Prezzo</div>
                      <div class="cart_item_text">
                        <p class="item_Quant3"><%= String.format("%.2f", m.getPrice()).replace(',', '.') %> &euro;</p>
                        <div class="cart_item_text">
                          <button class="item_Quant3" onclick="removeElement(<%=m.getId()%>)">Rimuovi dal carrello</button>
                          <!--<p class="item_Quant4"> &euro;</p>
                         <p class="to_be_hidden">26.59</p>-->
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
                <% sum=sum+(set.getValue()*m.getPrice());%>
              </div>
            </ul>
          </div>
          <%}%>
          <div id="totalContainer">
            <div class="order_total">
              <div class="order_total_content text-md-right">
                <div class="order_total_title">Totale:</div>

                <div class="order_total_amount" id="order_total_amount">
                  <p class="item_Quant3"><%= String.format("%.2f",sum).replace(',', '.') %></p>
                  <p class="item_Quant4"> &euro;</p>
                </div>
              </div>
            </div>
            <div class="cart_buttons">
              <button type="button" onclick="redirect('${pageContext.request.contextPath}/checkoutPreparationServlet')" class="button cart_button_checkout">Proceed to Checkout</button> </div>
          </div>

    <form id="updateProd"></form>

        </div>
      </div>
      </div>
        </ul>
    </div>
    <%}else{ %>
    <p class="f-text" id="f-text">Non hai inserito alcun elemento nel tuo carrello</p>
    <%}%>
  </div>
</div>
</body>
<script>
  function redirect(string){
    window.location.replace("http://localhost:8080"+string);
  }

  function updateElement(y,input){
    console.log(y);
    let stringToQuery = "#countProducts"+y
    console.log(stringToQuery)
    let x = $(stringToQuery).attr('value');
    let max = $(stringToQuery).attr('max');
    redirect("${pageContext.request.contextPath}/cartUpdateItemServlet?add=true&maxQ="+encodeURIComponent(max)+"&quantity="+encodeURIComponent(x)+"&id="+encodeURIComponent(y));
  }

  function removeElement(y){
    console.log(y);
    let stringToQuery = "#countProducts"+y
    console.log(stringToQuery)
    let x = $(stringToQuery).attr('value');
    let max = $(stringToQuery).attr('max');
    redirect("${pageContext.request.contextPath}/cartUpdateItemServlet?add=true&maxQ="+encodeURIComponent(max)+"&quantity="+encodeURIComponent("0")+"&id="+encodeURIComponent(y));

  }

</script>
</body>
</html>

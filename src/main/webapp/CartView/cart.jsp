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

</script>

<body>
<%@ include file="/header.jsp" %>
<div class="cart_section">
  <div class="container-fluid" id="container-fluid">
    <div class="row">
      <div class="col-lg-10 offset-lg-1">
        <div class="cart_container">
          <div class="cart_title">Carrello</div>

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
                          <input type="number" value="<%=set.getValue()%>" min="0" max="<%=m.getQuantity()%>" aria-label="Search" class="form-control" id="countProducts<%=m.getId()%>" style="width: 100px">
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
                        <!--<p class="item_Quant4"> &euro;</p>
                       <p class="to_be_hidden"><%= String.format("%.2f", m.getPrice()).replace(',', '.') %></p>-->
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
            <div class="cart_buttons"> <button type="button" onclick="redirect()" class="button cart_button_clear">Continue Shopping</button> <button type="button" class="button cart_button_checkout">Proceed to Checkout</button> </div>
          </div>


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

  function updateElement(y){
    console.log(y);
    let stringToQuery = "#countProducts"+y
    console.log(stringToQuery)
    let x = $(stringToQuery).attr('value');
    let max = $(stringToQuery).attr('max');
    redirect("${pageContext.request.contextPath}/cartUpdateItemServlet?add=true&maxQ="+encodeURIComponent(max)+"&quantity="+encodeURIComponent(x)+"&id="+encodeURIComponent(y));
  }

  function increase(x){
    let y = x.parentElement.firstElementChild;
    let calc=parseInt(y.innerHTML);
    calc=calc+1;
    y.innerHTML=""+calc;
    let priceDiv = x.parentElement.parentElement.nextElementSibling.firstElementChild.nextElementSibling.firstElementChild;
    let pr=parseFloat(priceDiv.nextElementSibling.nextElementSibling.innerHTML.replace(',','.'));
    let priceConst=priceDiv.nextElementSibling.nextElementSibling;
    if(calc==1){
      let z = document.getElementById("order_total_amount");
      let m=parseFloat(z.firstElementChild.innerHTML).toFixed(2);
      pr=pr.toFixed(2);
      m=Number(m)+Number(parseFloat(priceConst.innerHTML).toFixed(2));
      z.innerHTML= ""+m.toFixed(2);
      let xhrObj=new XMLHttpRequest();
      let TreeName=x.parentElement.parentElement.previousElementSibling.firstElementChild.nextElementSibling;
      xhrObj.open("GET","/ArborVitae/shoppingCartServlet?treeName="+TreeName.innerHTML,true);
      xhrObj.send();
      return;
    }
    pr = parseFloat(priceDiv.innerHTML.replace(',','.'));
    pr= pr.toFixed(2);
    let z = document.getElementById("order_total_amount");
    let m = parseFloat(z.firstElementChild.innerHTML).toFixed(2);
    m=Number(m) + Number(parseFloat(priceConst.innerHTML).toFixed(2));
    m=m.toFixed(2);
    z.firstElementChild.innerHTML= ""+m;
    let xhrObj=new XMLHttpRequest();
    let TreeName=x.parentElement.parentElement.previousElementSibling.firstElementChild.nextElementSibling;
    xhrObj.open("GET","/ArborVitae/shoppingCartServlet?treeName="+encodeURIComponent(TreeName.innerHTML),true);
    xhrObj.send();
    let g = document.querySelector("#count");
    let listCount=g.parentElement;
    g.innerHTML=""+(parseInt(g.innerHTML)+1);

  }

  function decrease(x){
    let y = x.parentElement.firstElementChild;
    let calc=parseInt(y.innerHTML);
    if(calc==0) return;
    calc=calc-1;
    y.innerHTML=""+calc;
    let priceDiv=x.parentElement.parentElement.nextElementSibling.firstElementChild.nextElementSibling.firstElementChild;
    let pr=parseFloat(priceDiv.innerHTML);
    let priceConst=priceDiv.nextElementSibling.nextElementSibling;
    pr=pr.toFixed(2);
    let z = document.getElementById("order_total_amount");
    let m=parseFloat(z.firstElementChild.innerHTML).toFixed(2);
    m=m-parseFloat(priceConst.innerHTML).toFixed(2);
    m=m.toFixed(2);
    z.firstElementChild.innerHTML= ""+m;
    let xhrObj=new XMLHttpRequest();
    let TreeName=x.parentElement.parentElement.previousElementSibling.firstElementChild.nextElementSibling;
    xhrObj.open("GET","/ArborVitae/shoppingCartServlet?tRemove="+encodeURIComponent(TreeName.innerHTML),true);
    xhrObj.send();
    if(calc==0){
      let listElement = x.parentElement.parentElement.parentElement.parentElement.parentElement;
      while((listElement.firstElementChild)!=null){
        listElement.removeChild(listElement.firstElementChild);
      }
      let ul=listElement.parentElement;
      listElement.parentElement.removeChild(listElement);
      let firstDivChild=ul.firstElementChild;
      if(firstDivChild==(ul.lastElementChild)){
        let elem=document.getElementById("hor_line");
        if(elem!=null){
          elem.parentElement.removeChild(elem);
        }
      }


    }
    let g = document.querySelector("#count");
    let listCount=y.parentElement;
    if((g.innerHTML)=='1'){
      let totalDiv=document.getElementById("totalContainer");
      while((totalDiv.firstElementChild)!=null){
        totalDiv.removeChild(totalDiv.firstElementChild);
      }
      totalDiv.parentElement.removeChild(totalDiv);
      g.parentElement.removeChild(g);
      listCount.parentElement.removeChild(listCount);
      let f_textEl=document.createElement("p");
      f_textEl.setAttribute("id","f-text");
      f_textEl.classList.add("f-text");
      f_textEl.innerHTML="Non hai inserito alcun elemento nel tuo carrello";
      let contFl=document.getElementById("cart_list");
      contFl.insertBefore(f_textEl,null);
      return;
    }else{
      g.innerHTML=""+(parseInt(g.innerHTML)-1);
    }

  }

</script>
</body>
</html>

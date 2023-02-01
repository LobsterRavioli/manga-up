<!--
  Created by IntelliJ IDEA.
  User: Francesco Monzillo
  Date: 12/01/2023
  Time: 21:30
  To change this template use File | Settings | File Templates.
--%>-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Merchandising.MerchandiseService.*" %>
<html>
<head>
  <%@ include file="/header.jsp" %>
  <Title><%Manga m = (Manga) request.getAttribute("prod");%>
                <%=m.getName()%>

  </Title>
  <base target="_parent">
  <!--<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&amp;display=swap">
  <link rel="stylesheet" href="https://mdbootstrap.com/api/snippets/static/download/MDB-Pro_4.20.0/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://mdbootstrap.com/api/snippets/static/download/MDB-Pro_4.20.0/css/mdb.min.css">
  <link rel="stylesheet" href="https://mdbootstrap.com/wp-content/themes/mdbootstrap4/docs-app/css/compiled-addons-4.20.0.min.css">
  <link rel="stylesheet" type="text/css" href="https://mdbootstrap.com/wp-content/themes/mdbootstrap4/css/mdb-plugins-gathered.min.css">-->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css_s/product.css" type="text/css">
  <style>/* Footer color for sake of consistency with Navbar */
  .page-footer {
    background-color: #929FBA; }
  </style>
  <!--<style type="text/css">/* Chart.js */
  @-webkit-keyframes chartjs-render-animation{
      from{opacity:0.99}
    to{opacity:1}
  }@keyframes chartjs-render-animation{
       from{opacity:0.99}
       to{opacity:1}
     }
  .chartjs-render-monitor{
    -webkit-animation:chartjs-render-animation 0.001s;animation:chartjs-render-animation 0.001s;
  }
  </style>-->
</head>
<body style="font-family: 'Nunito Sans', sans-serif !important;"><!-- Navbar -->

<!-- Navbar -->

<!--Main layout-->
<div class="contxxx">

      <div class="mt-5 pt-4">
  <div class="container dark-grey-text mt-5">

    <!--Grid row-->
    <div class="row wow fadeIn" style="flex-wrap: unset!important;">

      <!--Grid column-->
      <div class="col-md-6 mb-4" style="padding: 4% 0 0 5%;">

        <img src="${pageContext.request.contextPath}/images/products/<%=m.getImagePath()%>" class="img-fluid" alt="">

      </div>
      <!--Grid column-->

      <!--Grid column-->
      <div class="col-md-6 mb-4">

        <!--Content-->
        <div class="p-4">

          <div class="mb-3">
              <%if(m.getState()== Merchandising.MerchandiseService.ProductState.NEW){%>
                <span class="statesss"><%="Nuovo"%></span>
              <%}else{%>
                <span class="statesss"><%="Usato"%></span>
              <%}%>
          </div>

          <p class="lead">
          <p class="lead font-weight-bold">Prezzo</p>

          <p class="lead">
            <span>€<%= String.format("%.2f", m.getPrice()).replace(',', '.') %></span>
          </p>

          <p class="thin font-weight-bold">Publisher</p>

          <p><%=m.getPublisher()%></p>

          <p class="thin font-weight-bold">Collezione</p>

          <p style="margin-bottom: 2rem;"><%=m.getCollection().getName()%></p>

          <form class="d-flex justify-content-left">
            <!-- Default input -->
            <input type="number" value="1" min="1" max="<%=m.getQuantity()%>" aria-label="Search" class="form-control" id="countProducts" style="width: 100px">
            <button class="btn btn-primary btn-md my-0 p waves-effect waves-light" type="button" onclick="addElement()">
              Add to cart
              <i class="fas fa-shopping-cart ml-1"></i>
            </button>

          </form>
          <%if(request.getAttribute("error")!=null){%>
            <div class="" type="submit" style="font-size: 15px;background-color: darksalmon;color: darkred;">
              Errore con la quantità inserita precedentemente...Il prodotto è stato modificato
            </div>
          <%}else{
          }%>
        </div>


      </div>
      <!--Grid column-->

    </div>
    <!--Grid row-->

    <hr>

    <!--Grid row-->
    <div class="row d-flex justify-content-center wow fadeIn" style="display: block !important;">

      <!--Grid column-->
      <div class="text-center">

        <div style="display: flex;justify-content: center">
          <h4 class="my-4 h4">Additional information</h4>
        </div>

      </div>

      <div class="addInfs">
        <ul class="inL">
          <li class=mLeft>Description
            <p class="none_center"><%=m.getDescription()%></p>
          </li>
          <li class="mLeft">ISBN
            <p class="none_center"><%=m.getIsbn()%></p>
          </li>
          <li class="mLeft">Data di immissione
            <p class="none_center"><%=m.getExitDate()%></p>
          </li>
          <li class="mLeft">Numero di pagine
            <p class="none_center"><%=m.getPages()%></p>
          </li>
          <li class="mLeft">Colori
            <p class="none_center"><%=m.getInterior()%></p>
          </li>
          <li class="mLeft">Dimensioni
            <p class="none_center">Altezza: <%=m.getHeight()%></p>
            <p class="none_center">Larghezza: <%=m.getLength()%></p>
            <p class="none_center">Peso: <%=m.getLength()%></p>
          </li>
          <li class="mLeft">Lingua
            <p class="none_center"><%=m.getLanguage()%></p>
          </li>
          <li class="mLeft">Volume
            <p class="none_center"><%=m.getVolume()%></p>
          </li>
        </ul>
      </div>
      <!--Grid column-->

    </div>
    <!--Grid row-->

    <!--Grid row-->
    <div class="row wow fadeIn">

      <!--Grid column-->
      <!--<div class="col-lg-4 col-md-12 mb-4">

        <img src="https://mdbootstrap.com/img/Photos/Horizontal/E-commerce/Products/11.jpg" class="img-fluid" alt="">

      </div>

      <div class="col-lg-4 col-md-6 mb-4">

        <img src="https://mdbootstrap.com/img/Photos/Horizontal/E-commerce/Products/12.jpg" class="img-fluid" alt="">

      </div>

      <div class="col-lg-4 col-md-6 mb-4">

        <img src="https://mdbootstrap.com/img/Photos/Horizontal/E-commerce/Products/13.jpg" class="img-fluid" alt="">

      </div>-->
      <!--Grid column-->

    </div>
    <!--Grid row-->

  </div>
<!--Main layout-->
</div>
<!--/.Footer-->>">
      }<div class="hiddendiv common"></div></div>
<script>
  function addElement() {
    let req = new XMLHttpRequest();
    let x = document.querySelector("#count");
    let c = document.querySelector("#countProducts");
    console.log(c);
    console.log(x);
    req.onreadystatechange = function () {
      if (this.readyState == 4 && this.status == 200) {
        window.location.replace(window.location.href + "&success=tr");
      } else if (this.readyState == 4 && (this.status == 201)) {
        window.location.replace("http://localhost:8080/${pageContext.request.contextPath}/ProfileView/login_end_user.jsp");
        return;
      } else if (this.readyState == 4 && (this.status == 202)) {
        window.location.replace("http://localhost:8080/${pageContext.request.contextPath}/catalogServlet?productsSupply=1");
        return;
      } else if (this.readyState == 4 && (this.status == 203)) {
        window.location.replace(window.location.href + "&err=tr");
        return;
      }
    }
        req.open("GET", "http://localhost:8080/manga_up_war/cartAddServlet?maxQ="+encodeURIComponent('<%=m.getQuantity()%>')+"&quantity=" + encodeURIComponent(c.value) +"&id="+ encodeURIComponent('<%=m.getId()%>'), true);
        req.send();
  }

</script>

</body></html>

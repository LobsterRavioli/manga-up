<%@ page import="Merchandising.MerchandiseService.beans.Manga" %>
<%@ page import="Merchandising.MerchandiseService.beans.Product" %><%--
  Created by IntelliJ IDEA.
  User: Francesco Monzillo
  Date: 12/01/2023
  Time: 21:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@ include file="/header.jsp" %>
  <Title><%Object ob = request.getAttribute("prod");
            if (ob instanceof Manga){
                Manga m = (Manga) ob;%>
                <%=m.getName()%>
            <%}else{
                Product p = (Product) ob;%>
                <%=p.getName()%>
            <%}%>

  </Title>
  <base target="_parent">
  <script type="text/javascript" src="/js/cartInsertion.js"></script>
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&amp;display=swap">
  <link rel="stylesheet" href="https://mdbootstrap.com/api/snippets/static/download/MDB-Pro_4.20.0/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://mdbootstrap.com/api/snippets/static/download/MDB-Pro_4.20.0/css/mdb.min.css">
  <link rel="stylesheet" href="https://mdbootstrap.com/wp-content/themes/mdbootstrap4/docs-app/css/compiled-addons-4.20.0.min.css">
  <link rel="stylesheet" type="text/css" href="https://mdbootstrap.com/wp-content/themes/mdbootstrap4/css/mdb-plugins-gathered.min.css">
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
  <%
    if (ob instanceof Manga){%>
      <%Manga m = (Manga) ob;%>
      <div class="mt-5 pt-4">
  <div class="container dark-grey-text mt-5">

    <!--Grid row-->
    <div class="row wow fadeIn">

      <!--Grid column-->
      <div class="col-md-6 mb-4">

        <img src="https://mdbootstrap.com/img/Photos/Horizontal/E-commerce/Products/14.jpg" class="img-fluid" alt="">

      </div>
      <!--Grid column-->

      <!--Grid column-->
      <div class="col-md-6 mb-4">

        <!--Content-->
        <div class="p-4">

          <div class="mb-3">
              <%if(m.getState()== Product.ProductState.NEW){%>
                <span class="statesss"><%="Nuovo"%></span>
              <%}else{%>
                <span class="statesss"><%="Usato"%></span>
              <%}%>
          </div>

          <p class="lead">
          <p class="lead font-weight-bold">Prezzo</p>

          <p class="lead">
            <span>€<%=m.getPrice()%></span>
          </p>

          <p class="thin font-weight-bold">Brand</p>

          <p><%=m.getBrand()%></p>

          <p class="thin font-weight-bold">Collezione</p>

          <p style="margin-bottom: 2rem;"><%=m.getCollections()%></p>

          <form class="d-flex justify-content-left">
            <!-- Default input -->
            <input type="number" value="1" min="1" max="<%=m.getQuantity()%>" aria-label="Search" class="form-control" style="width: 100px">
            <button class="btn btn-primary btn-md my-0 p waves-effect waves-light" type="submit" onclick="addItem()">Add to cart
              <i class="fas fa-shopping-cart ml-1"></i>
            </button>

          </form>

        </div>
        <!--Content-->

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
</main>
<!--Main layout-->
</div>
    <%}else{%>
      <%Product p = (Product) ob;%>
  <div class="mt-5 pt-4">
    <div class="container dark-grey-text mt-5">

      <!--Grid row-->
      <div class="row wow fadeIn">

        <!--Grid column-->
        <div class="col-md-6 mb-4">

          <img src="https://mdbootstrap.com/img/Photos/Horizontal/E-commerce/Products/14.jpg" class="img-fluid" alt="">

        </div>
        <!--Grid column-->

        <!--Grid column-->
        <div class="col-md-6 mb-4">

          <!--Content-->
          <div class="p-4">

            <div class="mb-3">
              <%if(p.getState()== Product.ProductState.NEW){%>
              <span class="statesss"><%="Nuovo"%></span>
              <%}else{%>
              <span class="statesss"><%="Usato"%></span>
              <%}%>
            </div>

            <p class="lead">
            <p class="lead font-weight-bold">Prezzo</p>

            <p class="lead">
              <span>€<%=p.getPrice()%></span>
            </p>

            <p class="thin font-weight-bold">Collezione</p>

            <p style="margin-bottom: 2rem;"><%=p.getProducer()%></p>


            <form class="d-flex justify-content-left">
              <!-- Default input -->
              <input type="number" value="1" min="1" max="<%=p.getQuantity()%>" aria-label="Search" class="form-control" style="width: 100px">
              <button class="btn btn-primary btn-md my-0 p waves-effect waves-light" type="submit" onclick="addItem()">Add to cart
                <i class="fas fa-shopping-cart ml-1"></i>
              </button>

            </form>

          </div>
          <!--Content-->

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
              <p class="none_center"><%=p.getDescription()%></p>
            </li>
            <li class="mLeft">Tipo di Prodotto
              <p class="none_center"><%=p.getType_of_product()%></p>
            </li>
            </li>
            <li class="mLeft">Dimensioni
              <p class="none_center">Altezza: <%=p.getHeight()%></p>
              <p class="none_center">Larghezza: <%=p.getLength()%></p>
              <p class="none_center">Peso: <%=p.getLength()%></p>
            </li>
            </li>
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
    </main>
    <!--Main layout-->
  </div>
    <%}%>

<!--Footer--><!--
<footer class="page-footer text-center font-small mt-4 wow fadeIn">


  <div class="pt-4">
    <a class="btn btn-outline-white waves-effect waves-light" href="https://mdbootstrap.com/docs/jquery/getting-started/download/" target="_blank" role="button">Download MDB
      <i class="fas fa-download ml-2"></i>
    </a>
    <a class="btn btn-outline-white waves-effect waves-light" href="https://mdbootstrap.com/education/bootstrap/" target="_blank" role="button">Start
      free tutorial
      <i class="fas fa-graduation-cap ml-2"></i>
    </a>
  </div>


  <hr class="my-4">

  <div class="pb-4">
    <a href="https://www.facebook.com/mdbootstrap" target="_blank">
      <i class="fab fa-facebook-f mr-3"></i>
    </a>

    <a href="https://twitter.com/MDBootstrap" target="_blank">
      <i class="fab fa-twitter mr-3"></i>
    </a>

    <a href="https://www.youtube.com/watch?v=7MUISDJ5ZZ4" target="_blank">
      <i class="fab fa-youtube mr-3"></i>
    </a>

    <a href="https://plus.google.com/u/0/b/107863090883699620484" target="_blank">
      <i class="fab fa-google-plus-g mr-3"></i>
    </a>

    <a href="https://dribbble.com/mdbootstrap" target="_blank">
      <i class="fab fa-dribbble mr-3"></i>
    </a>

    <a href="https://pinterest.com/mdbootstrap" target="_blank">
      <i class="fab fa-pinterest mr-3"></i>
    </a>

    <a href="https://github.com/mdbootstrap/bootstrap-material-design" target="_blank">
      <i class="fab fa-github mr-3"></i>
    </a>

    <a href="http://codepen.io/mdbootstrap/" target="_blank">
      <i class="fab fa-codepen mr-3"></i>
    </a>
  </div>

  <div class="footer-copyright py-3">
    © 2018 Copyright:
    <a href="https://mdbootstrap.com/education/bootstrap/" target="_blank"> MDBootstrap.com </a>
  </div>


</footer>-->
<!--/.Footer--><script type="text/javascript" src="https://mdbootstrap.com/api/snippets/static/download/MDB-Pro_4.20.0/js/jquery.min.js"></script><script type="text/javascript" src="https://mdbootstrap.com/api/snippets/static/download/MDB-Pro_4.20.0/js/popper.min.js"></script><script type="text/javascript" src="https://mdbootstrap.com/api/snippets/static/download/MDB-Pro_4.20.0/js/bootstrap.min.js"></script><script type="text/javascript" src="https://mdbootstrap.com/api/snippets/static/download/MDB-Pro_4.20.0/js/mdb.min.js"></script><script type="text/javascript" src="https://mdbootstrap.com/wp-content/themes/mdbootstrap4/docs-app/js/bundles/4.20.0/compiled-addons.min.js"></script><script type="text/javascript" src="https://mdbootstrap.com/wp-content/themes/mdbootstrap4/js/plugins/mdb-plugins-gathered.min.js"></script><script type="text/javascript">{    new WOW().init();
      }</script><div class="hiddendiv common"></div></div></body></html>

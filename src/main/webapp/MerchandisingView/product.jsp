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
  <Title>ProdottioX</Title>
  <base target="_parent">
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
  <%Object ob = request.getAttribute("prod");
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
            <a href="">
              <span class="">Category 2</span>
            </a>
            <a href="">
              <span class="">New</span>
            </a>
            <a href="">
              <span class="">Bestseller</span>
            </a>
          </div>

          <p class="lead">
            <span>€<%=m.getPrice()%></span>
          </p>

          <p class="lead font-weight-bold">Description</p>

          <p><%=m.getDescription()%></p>

          <form class="d-flex justify-content-left">
            <!-- Default input -->
            <input type="number" value="1" aria-label="Search" class="form-control" style="width: 100px">
            <button class="btn btn-primary btn-md my-0 p waves-effect waves-light" type="submit">Add to cart
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
    <div class="row d-flex justify-content-center wow fadeIn">

      <!--Grid column-->
      <div class="col-md-6 text-center">

        <h4 class="my-4 h4">Additional information</h4>

        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Natus suscipit modi sapiente illo soluta odit
              voluptates,
      quibusdam officia. Neque quibusdam quas a quis porro? Molestias illo neque eum in laborum.</p>

      </div>
      <!--Grid column-->

    </div>
    <!--Grid row-->

    <!--Grid row-->
    <div class="row wow fadeIn">

      <!--Grid column-->
      <div class="col-lg-4 col-md-12 mb-4">

        <img src="https://mdbootstrap.com/img/Photos/Horizontal/E-commerce/Products/11.jpg" class="img-fluid" alt="">

      </div>
      <!--Grid column-->

      <!--Grid column-->
      <div class="col-lg-4 col-md-6 mb-4">

        <img src="https://mdbootstrap.com/img/Photos/Horizontal/E-commerce/Products/12.jpg" class="img-fluid" alt="">

      </div>
      <!--Grid column-->

      <!--Grid column-->
      <div class="col-lg-4 col-md-6 mb-4">

        <img src="https://mdbootstrap.com/img/Photos/Horizontal/E-commerce/Products/13.jpg" class="img-fluid" alt="">

      </div>
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
            <a href="">
              <span class="">Category 2</span>
            </a>
            <a href="">
              <span class="">New</span>
            </a>
            <a href="">
              <span class="">Bestseller</span>
            </a>
          </div>

          <p class="lead">
              <span class="mr-1">
                <del>$200</del>
              </span>
            <span>$100</span>
          </p>

          <p class="lead font-weight-bold">Description</p>

          <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Et dolor suscipit libero eos atque quia ipsa
      sint voluptatibus!
              Beatae sit assumenda asperiores iure at maxime atque repellendus maiores quia sapiente.</p>

          <form class="d-flex justify-content-left">
            <!-- Default input -->
            <input type="number" value="1" aria-label="Search" class="form-control" style="width: 100px">
            <button class="btn btn-primary btn-md my-0 p waves-effect waves-light" type="submit">Add to cart
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
    <div class="row d-flex justify-content-center wow fadeIn">

      <!--Grid column-->
      <div class="col-md-6 text-center">

        <h4 class="my-4 h4">Additional information</h4>

        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Natus suscipit modi sapiente illo soluta odit
              voluptates,
      quibusdam officia. Neque quibusdam quas a quis porro? Molestias illo neque eum in laborum.</p>

      </div>
      <!--Grid column-->

    </div>
    <!--Grid row-->

    <!--Grid row-->
    <div class="row wow fadeIn">

      <!--Grid column-->
      <div class="col-lg-4 col-md-12 mb-4">

        <img src="https://mdbootstrap.com/img/Photos/Horizontal/E-commerce/Products/11.jpg" class="img-fluid" alt="">

      </div>
      <!--Grid column-->

      <!--Grid column-->
      <!--<div class="col-lg-4 col-md-6 mb-4">

        <img src="https://mdbootstrap.com/img/Photos/Horizontal/E-commerce/Products/12.jpg" class="img-fluid" alt="">

      </div>



      <div class="col-lg-4 col-md-6 mb-4">

        <img src="https://mdbootstrap.com/img/Photos/Horizontal/E-commerce/Products/13.jpg" class="img-fluid" alt="">

      </div>


    </div>-->
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

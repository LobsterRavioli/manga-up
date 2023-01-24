<%@ page import="Merchandising.MerchandiseService.CollectionDAO" %>
<%@ page import="Merchandising.MerchandiseService.Collection" %>
<%@ page import="javax.xml.crypto.Data" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Francesco Monzillo
  Date: 04/01/2023
  Time: 21:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zxx"><head>
  <meta charset="UTF-8">
  <meta name="description" content="Male_Fashion Template">
  <meta name="keywords" content="Male_Fashion, unica, creative, html">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">

  <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@300;400;600;700;800;900&amp;display=swap" rel="stylesheet">

  <!--<link rel="stylesheet" href="https://preview.colorlib.com/theme/malefashion/css/bootstrap.min.css" type="text/css">
  <link rel="stylesheet" href="https://preview.colorlib.com/theme/malefashion/css/font-awesome.min.css" type="text/css">
  <link rel="stylesheet" href="https://preview.colorlib.com/theme/malefashion/css/elegant-icons.css" type="text/css">
  <link rel="stylesheet" href="https://preview.colorlib.com/theme/malefashion/css/magnific-popup.css" type="text/css">
  <link rel="stylesheet" href="https://preview.colorlib.com/theme/malefashion/css/nice-select.css" type="text/css">
  <link rel="stylesheet" href="https://preview.colorlib.com/theme/malefashion/css/owl.carousel.min.css" type="text/css">
  <link rel="stylesheet" href="https://preview.colorlib.com/theme/malefashion/css/slicknav.min.css" type="text/css">
  <link rel="stylesheet" href="https://preview.colorlib.com/theme/malefashion/css/style.css" type="text/css">-->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css_s/header.css" type="text/css">
  <script type="text/javascript" async="" src="https://www.google-analytics.com/analytics.js" nonce="dd669f0a-de3f-4a33-889d-a0b3125d17b3"></script><script defer="" referrerpolicy="origin" src="/cdn-cgi/zaraz/s.js?z=JTdCJTIyZXhlY3V0ZWQlMjIlM0ElNUIlNUQlMkMlMjJ0JTIyJTNBJTIyTWFsZS1GYXNoaW9uJTIwJTdDJTIwVGVtcGxhdGUlMjIlMkMlMjJ4JTIyJTNBMC4wNjUwMTQ5NTkzNDIxMDAxMSUyQyUyMnclMjIlM0ExNTM2JTJDJTIyaCUyMiUzQTg2NCUyQyUyMmolMjIlM0E3MjIlMkMlMjJlJTIyJTNBMTUzNiUyQyUyMmwlMjIlM0ElMjJodHRwcyUzQSUyRiUyRnByZXZpZXcuY29sb3JsaWIuY29tJTJGdGhlbWUlMkZtYWxlZmFzaGlvbiUyRiUyMiUyQyUyMnIlMjIlM0ElMjIlMjIlMkMlMjJrJTIyJTNBMjQlMkMlMjJuJTIyJTNBJTIyVVRGLTglMjIlMkMlMjJvJTIyJTNBLTYwJTJDJTIycSUyMiUzQSU1QiU3QiUyMm0lMjIlM0ElMjJzZXQlMjIlMkMlMjJhJTIyJTNBJTVCJTIyMCUyMiUyQyUyMlUlMjIlMkMlN0IlMjJzY29wZSUyMiUzQSUyMnBhZ2UlMjIlN0QlNUQlN0QlMkMlN0IlMjJtJTIyJTNBJTIyc2V0JTIyJTJDJTIyYSUyMiUzQSU1QiUyMjElMjIlMkMlMjJBJTIyJTJDJTdCJTIyc2NvcGUlMjIlM0ElMjJwYWdlJTIyJTdEJTVEJTdEJTJDJTdCJTIybSUyMiUzQSUyMnNldCUyMiUyQyUyMmElMjIlM0ElNUIlMjIyJTIyJTJDJTIyLSUyMiUyQyU3QiUyMnNjb3BlJTIyJTNBJTIycGFnZSUyMiU3RCU1RCU3RCUyQyU3QiUyMm0lMjIlM0ElMjJzZXQlMjIlMkMlMjJhJTIyJTNBJTVCJTIyMyUyMiUyQyUyMjIlMjIlMkMlN0IlMjJzY29wZSUyMiUzQSUyMnBhZ2UlMjIlN0QlNUQlN0QlMkMlN0IlMjJtJTIyJTNBJTIyc2V0JTIyJTJDJTIyYSUyMiUzQSU1QiUyMjQlMjIlMkMlMjIzJTIyJTJDJTdCJTIyc2NvcGUlMjIlM0ElMjJwYWdlJTIyJTdEJTVEJTdEJTJDJTdCJTIybSUyMiUzQSUyMnNldCUyMiUyQyUyMmElMjIlM0ElNUIlMjI1JTIyJTJDJTIyNSUyMiUyQyU3QiUyMnNjb3BlJTIyJTNBJTIycGFnZSUyMiU3RCU1RCU3RCUyQyU3QiUyMm0lMjIlM0ElMjJzZXQlMjIlMkMlMjJhJTIyJTNBJTVCJTIyNiUyMiUyQyUyMjglMjIlMkMlN0IlMjJzY29wZSUyMiUzQSUyMnBhZ2UlMjIlN0QlNUQlN0QlMkMlN0IlMjJtJTIyJTNBJTIyc2V0JTIyJTJDJTIyYSUyMiUzQSU1QiUyMjclMjIlMkMlMjIxJTIyJTJDJTdCJTIyc2NvcGUlMjIlM0ElMjJwYWdlJTIyJTdEJTVEJTdEJTJDJTdCJTIybSUyMiUzQSUyMnNldCUyMiUyQyUyMmElMjIlM0ElNUIlMjI4JTIyJTJDJTIyNSUyMiUyQyU3QiUyMnNjb3BlJTIyJTNBJTIycGFnZSUyMiU3RCU1RCU3RCUyQyU3QiUyMm0lMjIlM0ElMjJzZXQlMjIlMkMlMjJhJTIyJTNBJTVCJTIyOSUyMiUyQyUyMjYlMjIlMkMlN0IlMjJzY29wZSUyMiUzQSUyMnBhZ2UlMjIlN0QlNUQlN0QlMkMlN0IlMjJtJTIyJTNBJTIyc2V0JTIyJTJDJTIyYSUyMiUzQSU1QiUyMjEwJTIyJTJDJTIyOCUyMiUyQyU3QiUyMnNjb3BlJTIyJTNBJTIycGFnZSUyMiU3RCU1RCU3RCUyQyU3QiUyMm0lMjIlM0ElMjJzZXQlMjIlMkMlMjJhJTIyJTNBJTVCJTIyMTElMjIlMkMlMjItJTIyJTJDJTdCJTIyc2NvcGUlMjIlM0ElMjJwYWdlJTIyJTdEJTVEJTdEJTJDJTdCJTIybSUyMiUzQSUyMnNldCUyMiUyQyUyMmElMjIlM0ElNUIlMjIxMiUyMiUyQyUyMjElMjIlMkMlN0IlMjJzY29wZSUyMiUzQSUyMnBhZ2UlMjIlN0QlNUQlN0QlMkMlN0IlMjJtJTIyJTNBJTIyc2V0JTIyJTJDJTIyYSUyMiUzQSU1QiUyMjEzJTIyJTJDJTIyMyUyMiUyQyU3QiUyMnNjb3BlJTIyJTNBJTIycGFnZSUyMiU3RCU1RCU3RCU1RCU3RA=="></script><script nonce="dd669f0a-de3f-4a33-889d-a0b3125d17b3">(function(w,d){!function(eK,eL,eM,eN){eK.zarazData=eK.zarazData||{};eK.zarazData.executed=[];eK.zaraz={deferred:[],listeners:[]};eK.zaraz.q=[];eK.zaraz._f=function(eO){return function(){var eP=Array.prototype.slice.call(arguments);eK.zaraz.q.push({m:eO,a:eP})}};for(const eQ of["track","set","debug"])eK.zaraz[eQ]=eK.zaraz._f(eQ);eK.zaraz.init=()=>{var eR=eL.getElementsByTagName(eN)[0],eS=eL.createElement(eN),eT=eL.getElementsByTagName("title")[0];eT&&(eK.zarazData.t=eL.getElementsByTagName("title")[0].text);eK.zarazData.x=Math.random();eK.zarazData.w=eK.screen.width;eK.zarazData.h=eK.screen.height;eK.zarazData.j=eK.innerHeight;eK.zarazData.e=eK.innerWidth;eK.zarazData.l=eK.location.href;eK.zarazData.r=eL.referrer;eK.zarazData.k=eK.screen.colorDepth;eK.zarazData.n=eL.characterSet;eK.zarazData.o=(new Date).getTimezoneOffset();if(eK.dataLayer)for(const eX of Object.entries(Object.entries(dataLayer).reduce(((eY,eZ)=>({...eY[1],...eZ[1]})))))zaraz.set(eX[0],eX[1],{scope:"page"});eK.zarazData.q=[];for(;eK.zaraz.q.length;){const e_=eK.zaraz.q.shift();eK.zarazData.q.push(e_)}eS.defer=!0;for(const fa of[localStorage,sessionStorage])Object.keys(fa||{}).filter((fc=>fc.startsWith("_zaraz_"))).forEach((fb=>{try{eK.zarazData["z_"+fb.slice(7)]=JSON.parse(fa.getItem(fb))}catch{eK.zarazData["z_"+fb.slice(7)]=fa.getItem(fb)}}));eS.referrerPolicy="origin";eS.src="/cdn-cgi/zaraz/s.js?z="+btoa(encodeURIComponent(JSON.stringify(eK.zarazData)));eR.parentNode.insertBefore(eS,eR)};["complete","interactive"].includes(eL.readyState)?zaraz.init():eK.addEventListener("DOMContentLoaded",zaraz.init)}(w,d,0,"script");})(window,document);</script></head>
<body>


<header class="header">
  <div class="header__top">
    <div class="container">
      <div class="row widdd">
        <div class="col-lg-6 col-md-7">
          <div class="header__top__left">
          </div>
        </div>
        <div class="col-lg-6 col-md-5">
          <div class="header__top__right">
            <div class="header__top__links">
              <a href="${pageContext.request.contextPath}/ProfileView/NoAuthorView/login.jsp">Sign in</a>
              <a href="${pageContext.request.contextPath}/signUp.jsp">Sign up</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="container frat">
    <div class="row">
      <div class="principal">
        <div class="header__logo">
          <a href=""><img src="${pageContext.request.contextPath}/images/logo.png" alt="" class="logoManga"></a>
        </div>

        <nav class="header__menu mobile-menu">
          <ul>
            <li class="active"><a href="${pageContext.request.contextPath}/index.html">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/catalogServlet?productsSupply=1">Catalogo</a></li>
          </ul>
        </nav>

        <div class="header__nav__option">
          <form action="${pageContext.request.contextPath}/productsFilter" method="post" id="filter" style="display: flex;position: relative;right: 7%;">
            <%CollectionDAO c = new CollectionDAO((DataSource) application.getAttribute("DataSource"));
              try{
                ArrayList<Collection> collections = c.retrieveAlL();
                 %><select id="state" form="filter" name="collection">

                <%for(Collection coll: collections){%>
                    <option value="<%=coll.getName()%>"><%=coll.getName()%></option>
                <%}%>
                </select>
              <%}catch (Exception e){

              }%>
            <input type="text" form="filter" placeholder="name" name="name" style="width: 100%;font-size: 76%;">
          </form>
          <button form="filter" class="search-switch" style="border: none; background: none;bottom: 10px;right: 8px;">
            <img src="${pageContext.request.contextPath}/images/search.png" style="width: 1.18rem; " alt="">
          </button>
          <a href="#"><img src="${pageContext.request.contextPath}/images/cart.png" alt=""> <span>0</span></a>
          <div class="price">$0.00</div>
        </div>
      </div>
      <!--<div class="col-lg-6 col-md-6">
        <nav class="header__menu mobile-menu">
          <ul>
            <li class="active"><a href="${pageContext.request.contextPath}/index.html">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/catalogServlet?productsSupply=1&type=Manga">Manga</a></li>
            <li><a href="${pageContext.request.contextPath}/catalogServlet?productsSupply=1&type=">Catalogo</a></li>
          </ul>
        </nav>
      </div>-->
    </div>
    <div class="canvas__open"><i class="fa fa-bars"></i></div>
  </div>
</header>

</body>
</html>
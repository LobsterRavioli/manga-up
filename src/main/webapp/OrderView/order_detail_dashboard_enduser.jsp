<%--
  Created by IntelliJ IDEA.
  User: tommasosorrentino
  Date: 01/02/23
  Time: 20:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<head>
    <meta charset="ISO-8859-1">
    <title>Arbor Vitae</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css_s/ordini.css">
    <style>
        p.info{
            display: inline; !important
        }
    </style>
</head>
<body>


    <%@ include file="/header.jsp" %>

<ul>
    <li>
        Stato ordine:
        <c:if test="${empty order.state}">
            Stato ordine.
        </c:if>
        ${order.state}
    </li>


    <li>
        Prezzo totale:
        <c:if test="${empty order.totalPrice}">
            Prezzo totale.
        </c:if>
        ${order.totalPrice}
    </li>

    <li>
        Data ordine effettuata:
        <c:if test="${empty order.orderDate}">
            Data ordine.
        </c:if>
        ${order.orderDate}
    </li>

    <li>
        Data consegna:
        <c:if test="${empty order.orderDate}">
            Data consegna.
        </c:if>
        ${order.orderDate}
    </li>

    <li>Indirizzo di consegna:
        <c:if test="${empty order.addressEndUserInfo}">
            Indirizzo di consegna dell'ordine.
        </c:if>
        ${order.addressEndUserInfo}
    </li>

    <li>
        Carta di credito:${order.creditCardEndUserInfo}
    </li>

    <li>
        Corriere:
        <c:if test="${empty managed.courierName}">
            Non definito.
        </c:if>
        ${managed.courierName}

    </li>


    <li>
        Tracking number:
        <c:if test="${empty managed.trackNumber}">
            Non definito.
        </c:if>
        ${managed.trackNumber}
    </li>


    <li>
        Data di consegna:
        <c:if test="${empty managed.deliveryDate}">
            Non definito.
        </c:if>
        ${managed.deliveryDate}
    </li>

    <li>
        Data di spedizione:
        <c:if test="${empty managed.shipmentDate}">
            Non definito.
        </c:if>
        ${managed.shipmentDate}
    </li>

</ul>

    <div class="container">
        <section class="h-100 gradient-custom">
            <div class="container py-5 h-100">
                <br><br><br><br>
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-lg-8 col-xl-10">
                        <div class="card" style="border-radius: 10px;">
                            <div class="card-header px-4 py-5">
                                <h5 class="text-muted mb-0">Dettaglio ordine</h5>
                            </div>








                            <div class="card-body p-4">
                                <div class="d-flex justify-content-between align-items-center mb-4">
                                    <p class="lead fw-normal mb-0" style="color: #46dd2c;">Prodotti</p>
                                </div>

                                <div class="card shadow border mb-4">

                                    <div class="card-body">


                                        <div class="row">

                                            <div class="col-md-2">
                                                <img src="${pageContext.request.contextPath}/images/list.png"
                                                     class="img-fluid" alt="name tree" height="40" width="40">
                                            </div>

                                            <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                                <p class="info">Nome prodotto</p>
                                            </div>

                                            <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                                <p class="info">Prezzo unitario</p>
                                            </div>

                                            <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                                <p class="info">Quantità prodotto</p>
                                            </div>

                                            <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                                <p class="info"></p>
                                            </div>

                                        </div>


                                        <c:forEach items="${orderRows}" var="orderRow">

                                            <div class="row">

                                                <div class="col-md-2">
                                                    <img src="${pageContext.request.contextPath}/images/product.png"
                                                         class="img-fluid" alt="name tree" width="40" height="40">
                                                </div>

                                                <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                                    <p class="info">${orderRow.mangaName}</p>
                                                </div>

                                                <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                                    <p class="info">${orderRow.mangaPrice}</p>
                                                </div>


                                                <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                                    <p class="info">${orderRow.quantity}</p>
                                                </div>

                                                <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                                    <p class="info"></p>
                                                </div>

                                                <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                                    <p class="info"></p>
                                                </div>

                                            </div>



                                        </c:forEach>




                                    </div>

                                </div>



                                <hr class="mb-4" style="background-color: #46dd2c ; opacity: 1;">
                            </div>










                        </div>
                    </div>
                </div>
            </div>
        </section>

    </div>

</body>

</html>

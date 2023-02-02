<%--
  Created by IntelliJ IDEA.
  User: tommasosorrentino
  Date: 09/01/23
  Time: 21:24
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<head>
    <meta charset="ISO-8859-1">
    <title>Indirizzi</title>
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
<a href="${pageContext.request.contextPath}/ProfileView/creazione_carta_di_credito.jsp">Servizio di aggiunta di carta di credito.</a>

<c:if test="${cards == null}">
    <p> Non hai nessuna carta di credito. </p>
</c:if>





<div class="container">
    <section class="h-100 gradient-custom">
        <div class="container py-5 h-100">
            <br><br><br><br>
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-lg-8 col-xl-10">
                    <div class="card" style="border-radius: 10px;">
                        <div class="card-header px-4 py-5">
                            <h5 class="text-muted mb-0">I miei ordini</h5>
                        </div>








                        <div class="card-body p-4">
                            <div class="d-flex justify-content-between align-items-center mb-4">
                                <p class="lead fw-normal mb-0" style="color: #46dd2c;">Ricevute</p>
                            </div>

                            <div class="card shadow border mb-4">

                                <div class="card-body">


                                    <div class="row">

                                        <div class="col-md-2">
                                            <img src="${pageContext.request.contextPath}/images/list.png"
                                                 class="img-fluid" alt="name tree" height="40" width="40">
                                        </div>

                                        <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                            <p class="info">Numero carta</p>
                                        </div>

                                        <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                            <p class="info">Data di scadenza</p>
                                        </div>

                                        <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                            <p class="info">Proprietario</p>
                                        </div>

                                        <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                            <p class="info"></p>
                                        </div>

                                        <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                            <p class="info"></p>
                                        </div>

                                    </div>




                                    <c:forEach items="${cards}" var="card">
                                        <div class="row">

                                            <div class="col-md-2">
                                                <img src="${pageContext.request.contextPath}/images/credit-cards.png"
                                                     class="img-fluid" alt="name tree" width="40" height="40">
                                            </div>

                                            <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                                <p class="info">${card.cardNumber}</p>
                                            </div>

                                            <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                                <p class="info">${card.expirementDate}</p>
                                            </div>


                                            <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                                <p class="info">${card.cardHolder}</p>
                                            </div>

                                            <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                                <form action="${pageContext.request.contextPath}/CreditCardDeleteServlet" method="post">
                                                    <input type="hidden" name="credit_card_id" value="${card.id}"/>
                                                    <input type="submit" value="elimina" name="act" id="box_button" class="accept">
                                                </form>
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

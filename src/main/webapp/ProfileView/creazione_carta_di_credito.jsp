<%--
  Created by IntelliJ IDEA.
  User: tommasosorrentino
  Date: 11/01/23
  Time: 21:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Inserimento carta di credito</title>
</head>
<body>
<%@ include file="/header.jsp" %>
<script type = "text/javascript" src="check_formats.js"></script>
<p id="error_credit_card_message">${error_credit_card_message}</p>
    <form action="${pageContext.request.contextPath}/CreditCardCreateServlet" method="POST " id="card_form">
        <div class="container">

            <h1>Inserisci la tua carta di credito</h1>
            <p>Completa il form per poter inserire una carta di credito</p>
            <hr>
            <p class="card_number_error" id="card_number_error" ></p>
            <label for="card_number" ><b>Numero Carta </b></label>
            <input type="text" placeholder="Campo obbligatorio" name="card_number" id="card_number" required>
            <br>
            <p class="expirement_date_error" id="expirement_date_error"></p>
            <p class="expirement_current_date_error" id="expirement_current_date_error"></p>
            <label for="expirement_date" ><b>Data scadenza</b></label>
            <input type="date"  placeholder="Campo obbligatorio" name="expirement_date" id="expirement_date" required>
            <br>

            <p class="cvc_error" id="cvc_error"></p>
            <label for="cvc" ><b>cvc</b></label>
            <input type="password" placeholder="Campo obbligatorio" name="cvc" id="cvc" size="3" required>
            <br>

            <p class="card_holder_error" id="card_holder_error"></p>
            <label for="card_holder"><b>Nome proprietario</b></label>
            <input type="text" placeholder="Campo obbligatorio" name="card_holder" id="card_holder" required>

            <hr>
        </div>
        <button class="fry" type="submit" onclick="check_card_format()">Crea carta di credito</button>
    </form>
</body>
</html>

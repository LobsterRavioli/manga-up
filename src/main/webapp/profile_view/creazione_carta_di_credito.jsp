<%--
  Created by IntelliJ IDEA.
  User: tommasosorrentino
  Date: 11/01/23
  Time: 21:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inserimento carta di credito</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/CreditCardCreateServlet" method="POST">
        <div class="container">
            <h1>Inserisci la tua carta di credito</h1>
            <p>Completa il form per poter inserire una carta di credito</p>
            <hr>
            <label for="country"><b>Numero Carta </b></label>
            <input type="text" placeholder="Campo obbligatorio" name="country" id="country" required>
            <br>

            <label for="date"><b>Data scadenza</b></label>
            <input type="date"  placeholder="Campo obbligatorio" name="date" id="date" required>
            <br>
            <label for="cvc"><b>cvc</b></label>
            <input type="password" placeholder="Campo obbligatorio" name="cvc" id="cvc" size="3" required>
            <br>
            <label for="card_holder_name"><b>Nome proprietario</b></label>
            <input type="text" placeholder="Campo obbligatorio" name="card_holder_name" id="card_holder_name" required>
            <br>
            <label for="card_holder_surname"><b>Cognome proprietario</b></label>
            <input type="text" placeholder="Campo obbligatorio" name="card_holder_surname" id="card_holder_surname" required>
            <br>
            <hr>
        </div>
    </form>
</body>
</html>

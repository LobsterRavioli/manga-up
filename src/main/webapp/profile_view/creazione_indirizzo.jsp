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
    <title>Inserimento indirizzo di spedizione</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/AddressCreateServlet" method="POST">
        <div class="container">
            <h1>Crea il tuo indirizzo</h1>
            <p>Completa il from per poter inserire un nuovo indirizzo</p>
            <hr>
            <label for="country"><b>Nazione</b></label>
            <input type="text" placeholder="Campo obbligatorio" name="country" id="country" required>
            <br>

            <label for="region"><b>Regione</b></label>
            <input type="text"  placeholder="Campo obbligatorio" name="region" id="region" required>
            <br>
            <label for="city"><b>Città</b></label>
            <input type="text" placeholder="Campo obbligatorio" name="city" id="city" required>
            <br>
            <label for="phone_number"><b>Numero di cellualare</b></label>
            <input type="text" placeholder="Campo obbligatorio" name="phone_number" id="phone_number" required>
            <br>
            <label for="postal_code"><b>Codice Postale</b></label>
            <input type="text" placeholder="Campo obbligatorio" name="postal_code" id="postal_code" required>
            <br>
            <hr>
        </div>
        <button class="fry" onclick="check_registration_format()">Register</button>
    </form>
</body>
</html>

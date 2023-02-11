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
<%@ include file="/header.jsp" %>
<script type = "text/javascript" src="check_formats.js"></script>
    <form action="${pageContext.request.contextPath}/AddressCreateServlet" method="POST" id="address_form">
        <div class="container">
            <h1>Crea il tuo indirizzo</h1>
            <p>Completa il from per poter inserire un nuovo indirizzo</p>
            <hr>
            <p class="street_error" id="street_error"></p>
            <label for="street"><b>Via</b></label>
            <input type="text" placeholder="Campo obbligatorio" name="street" id="street" required>
            <br>
            <p class="country_error" id="country_error"></p>
            <label for="country"><b>Nazione</b></label>
            <input type="text" placeholder="Campo obbligatorio" name="country" id="country" required>
            <br>

            <p class="region_error" id="region_error"></p>
            <label for="region"><b>Regione</b></label>
            <input type="text"  placeholder="Campo obbligatorio" name="region" id="region" required>

            <br>
            <p class="city_error" id="city_error"></p>
            <label for="city"><b>Città</b></label>
            <input type="text" placeholder="Campo obbligatorio" name="city" id="city"  required>
            <br>

            <p class="phone_number_address_error" id="phone_number_address_error"></p>
            <label for="phone_number_address"><b>Numero di cellulare indirizzo</b></label>
            <input type="tel" placeholder="Campo obbligatorio" name="phone_number_address"  id="phone_number_address"required>
            <br>

            <p class="postal_code_error" id="postal_code_error"></p>
            <label for="postal_code"><b>Codice Postale</b></label>
            <input type="text" placeholder="Campo obbligatorio" id="postal_code" name="postal_code" required>
            <br>
            <hr>
        </div>
        <button class="fry"  onclick="check_address_format()">Crea indirizzo</button>
    </form>
</body>
</html>

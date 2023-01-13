<%--
  Created by IntelliJ IDEA.
  User: tommasosorrentino
  Date: 12/01/23
  Time: 20:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registrazione</title>
</head>
<body>

<meta http-equiv="pragma" content="no-cache" />
<script type = "text/javascript" src="check_formats.js"></script>
<form action="${pageContext.request.contextPath}/RegistrationServlet" id="registration_form">

    <div class="container">

        <h1>Form di registrazione.</h1>
        <p>Riempi il seguente form per registrarti.</p>
        <hr>
        <p class="email_error"></p>
        <label for="email"><b>Email</b></label>
        <input type="text" placeholder="Inserisci email" name="email" id="email" required>

        <p class="password_error"></p>
        <label for="password"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="psw" id="password" required>


        <label for="psw-repeat"><b>Ripeti la Password</b></label>
        <input type="password" placeholder="Repeat Password" name="psw-repeat" id="psw-repeat" required>

        <p class="name_error"></p>
        <label for="name"><b>Nome</b></label>
        <input type="text" placeholder="Inserisci nome" name="name" id="name" required>

        <p class="surname_error"></p>
        <label for="surname"><b>Cognome</b></label>
        <input type="text" placeholder="Inserisci cognome" name="name" id="surname" required>

        <p class="birth_date_error"></p>
        <label for="birth_date"><b>Data di nascita</b></label>
        <input type="date" placeholder="Inserisci data di nascita" name="birth_date" id="birth_date" required>
        <hr>


        <div class="container">

            <h1>Inserisci la tua carta di credito</h1>
            <p>Completa il form per poter inserire una carta di credito</p>
            <hr>
            <p class="card_number_error"></p>
            <label for="card_number"><b>Numero Carta </b></label>
            <input type="text" placeholder="Campo obbligatorio" name="country" id="card_number" required>
            <br>
            <p class="expirement_date_error"></p>
            <label for="expirement_date"><b>Data scadenza</b></label>
            <input type="date"  placeholder="Campo obbligatorio" name="date" id="expirement_date" required>
            <br>
            <p class="cvv_error"></p>
            <label for="cvc"><b>cvc</b></label>
            <input type="password" placeholder="Campo obbligatorio" name="cvc" id="cvc" size="3" required>
            <br>
            <p class="card_holder_error"></p>
            <label for="card_holder"><b>Nome proprietario</b></label>
            <input type="text" placeholder="Campo obbligatorio" name="card_holder_name" id="card_holder" required>

            <hr>
        </div>

        <div class="container">

            <h1>Crea il tuo indirizzo</h1>
            <p>Completa il from per poter inserire un nuovo indirizzo</p>
            <hr>
            <p class="street_error"></p>
            <label for="street"><b>Via</b></label>
            <input type="text" placeholder="Campo obbligatorio" name="street" id="street" required>
            <br>
            <p class="country_error"></p>
            <label for="country"><b>Nazione</b></label>
            <input type="text" placeholder="Campo obbligatorio" name="country" id="country" required>
            <br>
            <p class="region_error"></p>
            <label for="region"><b>Regione</b></label>
            <input type="text"  placeholder="Campo obbligatorio" name="region" id="region" required>
            <br>
            <p class="city_error"></p>
            <label for="city"><b>Città</b></label>
            <input type="text" placeholder="Campo obbligatorio" name="city" id="city" required>
            <br>

            <p class="phone_number_error"></p>
            <label for="phone_number"><b>Numero di cellulare</b></label>
            <input type="text" placeholder="Campo obbligatorio" name="phone_number" id="phone_number" required>
            <br>
            <p class="postal_code_error"></p>
            <label for="postal_code"><b>Codice Postale</b></label>
            <input type="text" placeholder="Campo obbligatorio" name="postal_code" id="postal_code" required>
            <br>
            <hr>
        </div>

    </div>
    <br>
</form>

<button class="fry" onclick="check_format()">Register</button>

</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: tommasosorrentino
  Date: 12/01/23
  Time: 20:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
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

        <p id="error_credit_card_message">${error_credit_card_message}</p>
        <br>
        <p id="error_email_message">${error_email_message}</p>
        <hr>
        <p class="email_error" id="email_error"></p>
        <label for="email"><b>Email</b></label>
        <input type="text" placeholder="Inserisci email" name="email" id="email" required>

        <p class="password_error" id="password_error"></p>
        <label for="password"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="password" id="password" required>

        <p class="psw-repeat_error" id="password_repeat_error"></p>
        <label for="psw-repeat"><b>Ripeti la Password</b></label>
        <input type="password" placeholder="Repeat Password" name="psw-repeat" id="psw-repeat" required>

        <p class="name_error" id="name_error"></p>
        <label for="name"><b>Nome</b></label>
        <input type="text" placeholder="Inserisci nome" name="name" id="name" required>

        <p class="surname_error" id="surname_error"></p>
        <label for="surname"><b>Cognome</b></label>
        <input type="text" placeholder="Inserisci cognome" name="surname" id="surname" required>

        <p class="phone_number_error" id="phone_number_error"></p>
        <label for="phone_number"><b>Numero di cellulare</b></label>
        <input type="tel" placeholder="Campo obbligatorio" name="phone_number" id="phone_number" required>
        <br>
        <p class="birth_date_error" id="birth_date_error"></p>
        <label for="birth_date" ><b>Data di nascita</b></label>
        <input type="date" placeholder="Inserisci data di nascita" name="birth_date" id="birth_date" required>
        <hr>


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

    </div>
    <br>
    <button class="fry" type="submit" onclick="check_registration_format()">Register</button>
</form>



</body>
</html>


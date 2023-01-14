function check_registration_format(){

    var email_format = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    var name_format = /^[a-zA-Z]+$/;
    var surname_format = /^[a-zA-Z]+$/;
    var password_format = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;
    var birth_date_format = /^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d$/;
    var card_number_format = /^[0-9]{16}$/;
    var cvc_format = /^[0-9]{3}$/;
    var card_holder_format = /^[a-zA-Z]+$/;
    var expirement_date_format = /^(0[1-9]|1[012])[- /.](19|20)\d\d$/;
    var country_format = /^[a-zA-Z]+$/;
    var region_format = /^[a-zA-Z]+$/;
    var city_format = /^[a-zA-Z]+$/;
    var street_format = /^[a-zA-Z]+$/;
    var phone_number_format = /^[0-9]{10}$/;
    var postal_code_format = /^[0-9]{5}$/;

    createdEl = document.createTextNode("Please add your username");


    var email = document.getElementById("email").value;
    var name = document.getElementById("name").value;
    var surname = document.getElementById("surname").value;
    var password = document.getElementById("password").value;
    var psw_repeat = document.getElementById("psw-repeat").value;
    var birth_date = document.getElementById("psw-repeat").value;


    var card_number = document.getElementById("card_number").value;
    var cvc = document.getElementById("cvc").value;
    var card_holder = document.getElementById("card_holder").value;
    var expirement_date = document.getElementById("expirement_date").value;


    var country = document.getElementById("country").value;
    var region = document.getElementById("region").value;
    var city = document.getElementById("city").value;
    var street = document.getElementById("street").value;
    var phone_number = document.getElementById("phone_number").value;
    var postal_code = document.getElementById("postal_code").value;

    if(!email_format.test(email)) {
        document.querySelector(".email_error").innerHTML = "Please enter a username";
        document.querySelector(".email_error").style.display = "block";
        event.preventDefault();

    }

    if(!name_format.test(name)) {
        document.querySelector(".name_error").innerHTML = "Please enter a name";
        document.querySelector(".name_error").style.display = "block";
        event.preventDefault();

    }

    if(!surname_format.test(surname)) {
        document.querySelector(".surname_error").innerHTML = "Please enter a surname";
        document.querySelector(".surname_error").style.display = "block";
        event.preventDefault();

    }

    if(!password_format.test(password)) {
        document.querySelector(".password_error").innerHTML = "Please enter a password";
        document.querySelector(".password_error").style.display = "block";
        event.preventDefault();

    }

    if(!birth_date_format.test(birth_date)) {
        document.querySelector(".birth_date_error").innerHTML = "Inserire una data di nascita valida";
        document.querySelector(".birth_date_error").style.display = "block";
        event.preventDefault();

    }

    if(!card_number_format.test(card_number)) {
        document.querySelector(".card_number_error").innerHTML = "Inserire una carta di credito valida";
        document.querySelector(".card_number_error").style.display = "block";
        event.preventDefault();

    }

    if(!cvc_format.test(cvc)) {
        document.querySelector(".cvc_error").innerHTML = "Inserire un cvc valido";
        document.querySelector(".cvc_error").style.display = "block";
        event.preventDefault();
        return false;
    }

    if(!card_holder_format.test(card_holder)) {
        document.querySelector(".card_holder_error").innerHTML = "Inserire un nome valido";
        document.querySelector(".card_holder_error").style.display = "block";
        event.preventDefault();

    }


    if(!expirement_date_format.test(expirement_date)) {
        document.querySelector(".expirement_date_error").innerHTML = "Inserure una data di scadenza valida";
        document.querySelector(".expirement_date_error").style.display = "block";
        event.preventDefault();

    }

    if(!country_format.test(country)) {
        document.querySelector(".country_error").innerHTML = "Inserire un paese valido";
        document.querySelector(".country_error").style.display = "block";
        event.preventDefault();

    }

    if(!region_format.test(region)) {
        document.querySelector(".region_error").innerHTML = "Inserire una regione valida";
        document.querySelector(".region_error").style.display = "block";
        event.preventDefault();

    }

    if(!city_format.test(city)) {
        document.querySelector(".city_error").innerHTML = "Inserire una città valida";
        document.querySelector(".city_error").style.display = "block";
        event.preventDefault();

    }

    if(!street_format.test(street)) {
        document.querySelector(".street_error").innerHTML = "Inserire una via valida";
        document.querySelector(".street_error").style.display = "block";
        event.preventDefault();

    }

    if(!phone_number_format.test(phone_number)) {
        document.querySelector(".phone_number_error").innerHTML = "Inserire un numero di telefono valido";
        document.querySelector(".phone_number_error").style.display = "block";
        event.preventDefault();

    }

    if(!postal_code_format.test(postal_code)) {
        document.querySelector(".postal_code_error").innerHTML = "Inserire un codice postale valido";
        document.querySelector(".postal_code_error").style.display = "block";
        event.preventDefault();

    }

    if (postal_code_format.test(postal_code) && phone_number_format.test(phone_number) && street_format.test(street) && city_format.test(city) && region_format.test(region) && country_format.test(country) && expirement_date_format.test(expirement_date) && card_holder_format.test(card_holder) && cvc_format.test(cvc) && card_number_format.test(card_number) && birth_date_format.test(birth_date) && password_format.test(password) && surname_format.test(surname) && name_format.test(name) && email_format.test(email)) {
        document.getElementById("registration_form").submit();
    }

    return false;

}

function check_address_format(){

    if(!country_format.test(country)) {
        document.querySelector(".country_error").innerHTML = "Inserire un paese valido";
        document.querySelector(".country_error").style.display = "block";
        event.preventDefault();

    }

    if(!region_format.test(region)) {
        document.querySelector(".region_error").innerHTML = "Inserire una regione valida";
        document.querySelector(".region_error").style.display = "block";
        event.preventDefault();

    }

    if(!city_format.test(city)) {
        document.querySelector(".city_error").innerHTML = "Inserire una città valida";
        document.querySelector(".city_error").style.display = "block";
        event.preventDefault();

    }

    if(!street_format.test(street)) {
        document.querySelector(".street_error").innerHTML = "Inserire una via valida";
        document.querySelector(".street_error").style.display = "block";
        event.preventDefault();

    }

    if(!phone_number_format.test(phone_number)) {
        document.querySelector(".phone_number_error").innerHTML = "Inserire un numero di telefono valido";
        document.querySelector(".phone_number_error").style.display = "block";
        event.preventDefault();

    }

    if(!postal_code_format.test(postal_code)) {
        document.querySelector(".postal_code_error").innerHTML = "Inserire un codice postale valido";
        document.querySelector(".postal_code_error").style.display = "block";
        event.preventDefault();

    }

    if (postal_code_format.test(postal_code) && phone_number_format.test(phone_number) && street_format.test(street) && city_format.test(city) && region_format.test(region) && country_format.test(country)) {
        document.getElementById("address_form").submit();
    }

    return false;

}

function check_card_format(){

        if(!card_number_format.test(card_number)) {
            document.querySelector(".card_number_error").innerHTML = "Inserire una carta di credito valida";
            document.querySelector(".card_number_error").style.display = "block";
            event.preventDefault();

        }

        if(!cvc_format.test(cvc)) {
            document.querySelector(".cvc_error").innerHTML = "Inserire un cvc valido";
            document.querySelector(".cvc_error").style.display = "block";
            event.preventDefault();
            return false;
        }

        if(!card_holder_format.test(card_holder)) {
            document.querySelector(".card_holder_error").innerHTML = "Inserire un nome valido";
            document.querySelector(".card_holder_error").style.display = "block";
            event.preventDefault();

        }

}
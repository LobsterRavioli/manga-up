function check_registration_format(){

    let email_format = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    let name_format = /^[a-zA-Z]+$/;
    let surname_format = /^[a-zA-Z]+$/;
    let password_format = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
    let birth_date_format = /^\d{4}-\d{2}-\d{2}$/;

    let card_number_format = /^[0-9]{13,16}$/;
    let cvc_format = /^[0-9]{3,5}$/;
    let card_holder_format = /^[a-zA-Z]{1,40}$/;
    let expirement_date_format = /^\d{4}-\d{2}-\d{2}$/;

    let country_format = /^[a-zA-Z]{1,56}$/;
    let region_format = /^[a-zA-Z]{1,30}$/;
    let city_format = /^[a-zA-Z]{1,163}$/;
    let street_format = /^[a-zA-Z]{1,44}$/;
    let phone_number_format = /^[0-9]{13,15}$/;
    let postal_code_format = /^[0-9]{5}$/;


    let email = document.getElementById("email").value;
    let name = document.getElementById("name").value;
    let surname = document.getElementById("surname").value;
    let password = document.getElementById("password").value;
    let psw_repeat = document.getElementById("psw-repeat").value;
    let birth_date = document.getElementById("birth_date").value;
    let phone_number = document.getElementById("phone_number").value;


    let card_number = document.getElementById("card_number").value;
    let cvc = document.getElementById("cvc").value;
    let card_holder = document.getElementById("card_holder").value;
    let expirement_date = document.getElementById("expirement_date").value;
    let phone_number_address = document.getElementById("phone_number_address").value;



    let country = document.getElementById("country").value;
    let region = document.getElementById("region").value;
    let city = document.getElementById("city").value;
    let street = document.getElementById("street").value;

    let postal_code = document.getElementById("postal_code").value;


    if(!email_format.test(email)) {
        document.querySelector(".email_error").innerHTML = "Inserire una email valida";
        document.querySelector(".email_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".email_error").innerHTML = "";
        document.querySelector(".email_error").style.display = "block";
        event.preventDefault();
    }


    if(!name_format.test(name)) {
        document.querySelector(".name_error").innerHTML = "Inserire un nome valido";
        document.querySelector(".name_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".name_error").innerHTML = "";
        document.querySelector(".name_error").style.display = "block";
        event.preventDefault();
    }


    if(!surname_format.test(surname)) {
        document.querySelector(".surname_error").innerHTML = "Inserire un cognome valido";
        document.querySelector(".surname_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".surname_error").innerHTML = "";
        document.querySelector(".surname_error").style.display = "block";
        event.preventDefault();
    }

    if(!password_format.test(password)) {
        document.querySelector(".password_error").innerHTML = "Inserire una password valida";
        document.querySelector(".password_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".password_error").innerHTML = "";
        document.querySelector(".password_error").style.display = "block";
        event.preventDefault();
    }



    if(password !== psw_repeat) {
        document.querySelector(".psw-repeat_error").innerHTML = "Le password non coincidono";
        document.querySelector(".psw-repeat_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".psw-repeat_error").innerHTML = "";
        document.querySelector(".psw-repeat_error").style.display = "block";
        event.preventDefault();
    }

    if(!birth_date_format.test(birth_date)) {
        document.querySelector(".birth_date_error").innerHTML = "Inserire una data di nascita valida";
        document.querySelector(".birth_date_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".birth_date_error").innerHTML = "";
        document.querySelector(".birth_date_error").style.display = "block";
        event.preventDefault();

    }

    if(!card_number_format.test(card_number)) {
        document.querySelector(".card_number_error").innerHTML = "Inserire una carta di credito valida";
        document.querySelector(".card_number_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".card_number_error").innerHTML = "";
        document.querySelector(".card_number_error").style.display = "block";
        event.preventDefault();

    }

    if(!cvc_format.test(cvc)) {
        document.querySelector(".cvc_error").innerHTML = "Inserire un cvc valido";
        document.querySelector(".cvc_error").style.display = "block";
        event.preventDefault();
    }
    else {
        document.querySelector(".cvc_error").innerHTML = "";
        document.querySelector(".cvc_error").style.display = "block";
        event.preventDefault();
    }

    if(!card_holder_format.test(card_holder)) {
        document.querySelector(".card_holder_error").innerHTML = "Inserire un nome valido";
        document.querySelector(".card_holder_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".card_holder_error").innerHTML = "";
        document.querySelector(".card_holder_error").style.display = "block";
        event.preventDefault();
    }


    if(!expirement_date_format.test(expirement_date)) {
        document.querySelector(".expirement_date_error").innerHTML = "Inserire una data di scadenza valida";
        document.querySelector(".expirement_date_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".expirement_date_error").innerHTML = "";
        document.querySelector(".expirement_date_error").style.display = "block";
        event.preventDefault();
    }

    if(!country_format.test(country)) {
        document.querySelector(".country_error").innerHTML = "Inserire un paese valido";
        document.querySelector(".country_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".country_error").innerHTML = "";
        document.querySelector(".country_error").style.display = "block";
        event.preventDefault();
    }

    if(!region_format.test(region)) {
        document.querySelector(".region_error").innerHTML = "Inserire una regione valida";
        document.querySelector(".region_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".region_error").innerHTML = "";
        document.querySelector(".region_error").style.display = "block";
        event.preventDefault();
    }

    if(!city_format.test(city)) {
        document.querySelector(".city_error").innerHTML = "Inserire una città valida";
        document.querySelector(".city_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".city_error").innerHTML = "";
        document.querySelector(".city_error").style.display = "block";
        event.preventDefault();
    }

    if(!street_format.test(street)) {
        document.querySelector(".street_error").innerHTML = "Inserire una via valida";
        document.querySelector(".street_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".street_error").innerHTML = "";
        document.querySelector(".street_error").style.display = "block";
        event.preventDefault();
    }

    if(!phone_number_format.test(phone_number_address)) {
        document.querySelector(".phone_number_error").innerHTML = "Inserire un numero di telefono valido";
        document.querySelector(".phone_number_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".phone_number_error").innerHTML = "";
        document.querySelector(".phone_number_error").style.display = "block";
        event.preventDefault();
    }

    if(!postal_code_format.test(postal_code)) {
        document.querySelector(".postal_code_error").innerHTML = "Inserire un codice postale valido";
        document.querySelector(".postal_code_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".postal_code_error").innerHTML = "";
        document.querySelector(".postal_code_error").style.display = "block";
        event.preventDefault();
    }

    if(!phone_number_format.test(phone_number)) {
        document.querySelector(".phone_number_error").innerHTML = "Inserire un numero valido";
        document.querySelector(".phone_number_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".phone_number_card_error").innerHTML = "";
        document.querySelector(".phone_number_card_error").style.display = "block";
        event.preventDefault();
    }



    if (postal_code_format.test(postal_code) && phone_number_format.test(phone_number)
        && street_format.test(street) && city_format.test(city) && region_format.test(region)
        && country_format.test(country) && expirement_date_format.test(expirement_date)
        && card_holder_format.test(card_holder) && cvc_format.test(cvc) && card_number_format.test(card_number)
        && birth_date_format.test(birth_date) && password_format.test(password) && surname_format.test(surname)
        && name_format.test(name) && email_format.test(email) && phone_number_format.test(phone_number_address) && (password == psw_repeat)){
        document.getElementById("registration_form").submit();
    }

    return false;

}

function check_address_format(){

    let country_format = /^[a-zA-Z]{1,56}$/;
    let region_format = /^[a-zA-Z]{1,30}$/;
    let city_format = /^[a-zA-Z]{1,163}$/;
    let street_format = /^[a-zA-Z]{1,44}$/;
    let phone_number_format = /^[0-9]{13,15}$/;
    let postal_code_format = /^[0-9]{5}$/;


    let country = document.getElementById("country").value;
    let region = document.getElementById("region").value;
    let city = document.getElementById("city").value;
    let street = document.getElementById("street").value;
    let phone_number = document.getElementById("phone_number").value;
    let postal_code = document.getElementById("postal_code").value;


    if(!country_format.test(country)) {
        document.querySelector(".country_error").innerHTML = "Inserire un paese valido";
        document.querySelector(".country_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".country_error").innerHTML = "";
        document.querySelector(".country_error").style.display = "block";
        event.preventDefault();
    }

    if(!region_format.test(region)) {
        document.querySelector(".region_error").innerHTML = "Inserire una regione valida";
        document.querySelector(".region_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".region_error").innerHTML = "";
        document.querySelector(".region_error").style.display = "block";
        event.preventDefault();
    }

    if(!city_format.test(city)) {
        document.querySelector(".city_error").innerHTML = "Inserire una città valida";
        document.querySelector(".city_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".city_error").innerHTML = "";
        document.querySelector(".city_error").style.display = "block";
        event.preventDefault();
    }

    if(!street_format.test(street)) {
        document.querySelector(".street_error").innerHTML = "Inserire una via valida";
        document.querySelector(".street_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".street_error").innerHTML = "";
        document.querySelector(".street_error").style.display = "block";
        event.preventDefault();
    }

    if(!phone_number_format.test(phone_number)) {
        document.querySelector(".phone_number_error").innerHTML = "Inserire un numero di telefono valido";
        document.querySelector(".phone_number_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".phone_number_error").innerHTML = "";
        document.querySelector(".phone_number_error").style.display = "block";
        event.preventDefault();
    }

    if(!postal_code_format.test(postal_code)) {
        document.querySelector(".postal_code_error").innerHTML = "Inserire un codice postale valido";
        document.querySelector(".postal_code_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".postal_code_error").innerHTML = "";
        document.querySelector(".postal_code_error").style.display = "block";
        event.preventDefault();
    }


    if (postal_code_format.test(postal_code) && phone_number_format.test(phone_number) && street_format.test(street) && city_format.test(city) && region_format.test(region) && country_format.test(country)) {
        document.getElementById("address_form").submit();
    }

    return false;

}

function check_card_format(){

    let card_number_format = /^[0-9]{13,16}$/;
    let cvc_format = /^[0-9]{3,5}$/;
    let card_holder_format = /^[a-zA-Z]{1,20}$/;
    let expirement_date_format = /^\d{4}-\d{2}-\d{2}$/;


    let card_number = document.getElementById("card_number").value;
    let cvc = document.getElementById("cvc").value;
    let card_holder = document.getElementById("card_holder").value;
    let expirement_date = document.getElementById("expirement_date").value;


    if(!card_number_format.test(card_number)) {
        document.querySelector(".card_number_error").innerHTML = "Inserire una carta di credito valida";
        document.querySelector(".card_number_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".card_number_error").innerHTML = "";
        document.querySelector(".card_number_error").style.display = "block";
        event.preventDefault();

    }

    if(!cvc_format.test(cvc)) {
        document.querySelector(".cvc_error").innerHTML = "Inserire un cvc valido";
        document.querySelector(".cvc_error").style.display = "block";
        event.preventDefault();
    }
    else {
        document.querySelector(".cvc_error").innerHTML = "";
        document.querySelector(".cvc_error").style.display = "block";
        event.preventDefault();
    }

    if(!card_holder_format.test(card_holder)) {
        document.querySelector(".card_holder_error").innerHTML = "Inserire un nome valido";
        document.querySelector(".card_holder_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".card_holder_error").innerHTML = "";
        document.querySelector(".card_holder_error").style.display = "block";
        event.preventDefault();
    }


    if(!expirement_date_format.test(expirement_date)) {
        document.querySelector(".expirement_date_error").innerHTML = "Inserire una data di scadenza valida";
        document.querySelector(".expirement_date_error").style.display = "block";
        event.preventDefault();

    }
    else {
        document.querySelector(".expirement_date_error").innerHTML = "";
        document.querySelector(".expirement_date_error").style.display = "block";
        event.preventDefault();
    }


    if (card_number_format.test(card_number) && cvc_format.test(cvc) && card_holder_format.test(card_holder) && expirement_date_format.test(expirement_date)) {
        document.getElementById("card_form").submit();
    }

    return false;
}
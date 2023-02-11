<%@ page import="Merchandising.MerchandiseService.CollectionDAO" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="Merchandising.MerchandiseService.Genre" %>
<%@ page import="Merchandising.MerchandiseService.GenreDAO" %>
<%@ page import="Merchandising.MerchandiseService.Collection" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Francesco Monzillo
  Date: 24/01/2023
  Time: 12:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Products</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css_s/addProd.css" type="text/css">
</head>
    <body>
        <jsp:include page="/ProductsView/homepage.jsp"/>
        <div class="displayer">
            <div class="toppane"></div>
            <div class="leftpane"></div>

        <form id="addProductForm" action="${pageContext.request.contextPath}/ProductsView/processProductInsertion" method="post" onsubmit="return check_registration_format()" enctype="multipart/form-data">

                <div class="container">

                    <h1>Form per l'inserimento di un prodotto</h1>
                    <p>Riempi i campi del form per l'inserimento del prodotto</p>

                    <p>${error_message}</p>
                    <hr>
                    <p class="name_error" id="name_error"></p>
                    <label for="nome"><b>Nome</b></label>
                    <input type="text" placeholder="Inserisci il nome" name="nome" id="nome" oninput="check_registration_format()" >

                    <p class="publisher_error" id="publisher_error"></p>
                    <label for="editore"><b>Editore</b></label>
                    <input type="text" placeholder="Inserisci l'editore" name="editore" id="editore" oninput="check_registration_format()" >

                    <p class="price_error" id="price_error"></p>
                    <label for="prezzo"><b>Prezzo</b></label>
                    <input type="text" placeholder="Inserisci il prezzo" name="prezzo" id="prezzo" oninput="check_registration_format()" >

                    <p class="weight_error" id="weight_error"></p>
                    <label for="peso"><b>Peso</b></label>
                    <input type="number" placeholder="Inserisci il peso" name="peso" id="peso" min="1" oninput="check_registration_format()" >

                    <p class="height_error" id="height_error"></p>
                    <label for="altezza"><b>Altezza</b></label>
                    <input type="number" placeholder="Inserisci l'altezza" name="altezza" id="altezza" min="1" oninput="check_registration_format()" >
                    <br>
                    <p class="length_error" id="length_error"></p>
                    <label for="larghezza"><b>Larghezza</b></label>
                    <input type="number" placeholder="Inserisci la larghezza" name="larghezza" id="larghezza" min="1" oninput="check_registration_format()" >

                    <p class="state_error" id="state_error"></p>
                    <label for="stato"><b>Stato</b></label>
                    <select id="stato" name="stato">
                        <option value=""></option>
                        <option value="NEW">Nuovo</option>
                        <option value="USED">Usato</option>
                    </select>

                        <p class="description_error" id="description_error"></p>
                        <label for="descrizione"><b>Descrizione</b></label>
                        <textarea rows="4" cols="50" name="descrizione" id="descrizione" oninput="check_registration_format()" placeholder="Inserisci una descrizione del prodotto"></textarea>
                        <br>
                        <p class="isbn_error" id="isbn_error"></p>
                        <label for="isbn"><b>ISBN</b></label>
                        <input type="number"  placeholder="Inserisci l'isbn" name="isbn" id="isbn" oninput="check_registration_format()" >
                        <br>

                        <p class="book_binding_error" id="book_binding_error"></p>
                        <label for="rilegatura"><b>Rilegatura</b></label>
                        <input type="text" placeholder="Inserisci la rilegatura" name="rilegatura" id="rilegatura" oninput="check_registration_format()" >
                        <br>

                        <p class="volume_error" id="volume_error"></p>
                        <label for="volume"><b>Volume</b></label>
                        <input type="text" placeholder="Inserisci il volume" name="volume" id="volume" oninput="check_registration_format()" >

                        <p class="release_date_error" id="release_date_error"></p>
                        <label for="data_uscita"><b>Data di uscita</b></label>
                        <input type="date" placeholder="Inserire la data di uscita" name="data_uscita" oninput="check_registration_format()" id="data_uscita" >
                        <br>
                        <p class="page_number_error" id="page_number_error"></p>
                        <label for="numPagine"><b>Numero di Pagine</b></label>
                        <input type="number" placeholder="Inserire il numero di pagine" name="numPagine" oninput="check_registration_format()" id="numPagine" >
                        <br>
                        <p class="quantity_error" id="quantity_error"></p>
                        <label for="quantity"><b>Quantità da immagazzinare</b></label>
                        <input type="number"  placeholder="Inserire la quantità" name="quantity" id="quantity" oninput="check_registration_format()" min="1" >
                        <br>
                        <p class="interior_error" id="interior_error"></p>
                        <label for="interni"><b>Colore degli Interni</b></label>
                        <input type="text" placeholder="Inserire il colore degli interni" name="interni" oninput="check_registration_format()" id="interni"  >
                        <br>

                        <p class="language_error" id="language_error"></p>
                        <label for="lingua"><b>Lingua</b></label>
                        <input type="text" placeholder="Inserire la lingua" name="lingua" id="lingua"  oninput="check_registration_format()" >
                        <br>

                        <%CollectionDAO c = new CollectionDAO((DataSource) application.getAttribute("DataSource"));
                          GenreDAO g = new GenreDAO((DataSource) application.getAttribute("DataSource"));%>
                        <%
                        try{
                        ArrayList<Collection> collections = c.retrieveAlL();
                        %>
                        <p class="collection_error" id="collection_error"></p>
                        <select id="collezione" name="collection" oninput="check_registration_format()">
                            <option value=""></option>
                            <%for(Collection coll: collections){%>
                            <option value="<%=coll.getName()%>"><%=coll.getName()%></option>
                            <%}%>
                        </select>
                            <%}catch (Exception e){

                        }%>
                        <%
                            try{
                                ArrayList<Genre> collections = g.retrieveAlL();
                        %>
                        <p class="genre_error" id="genre_error"></p>
                        <select id="genere" name="genere" oninput="check_registration_format()">
                            <option value=""></option>
                            <%for(Genre gen: collections){%>
                            <option value="<%=gen.getName()%>"><%=gen.getName()%></option>
                            <%}%>
                        </select>
                        <%}catch (Exception e){

                        }%>

                        <p class="storyMaker_error" id="storyMaker_error"></p>
                        <label for="story_maker"><b>Story Maker</b></label>
                        <input type="text" oninput="check_registration_format()" placeholder="Inserire lo storyMaker" name="story_maker" id="story_maker" >
                        <br>

                        <p class="image_error"></p>
                        <label for="immagine"><b>Immagine</b></label>
                        <input type="file" oninput="check_registration_format()" name="immagine" id="immagine" accept="image/png, image/jpeg" >
                        <br>

                        <hr>


                </div>

                <br>
                <button class="fry" form="addProductForm" type="submit" value="">Inserisci</button>

        </form>
        </div>
    </body>

<script>


    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!
    var yyyy = today.getFullYear();

    if (dd < 10) {
        dd = '0' + dd;
    }

    if (mm < 10) {
        mm = '0' + mm;
    }

    today = yyyy + '-' + mm + '-' + dd;
    document.getElementById("data_uscita").setAttribute("max", today);

    function check_registration_format(){

        let name_format = /^(?![ ]+$)[a-zA-Z 1-9]*$/;
        let publisher_format = /^(?![ ]+$)[a-zA-Z ]*$/;
        let price_format = /^\d+(.\d{1,2})?$/;

        let weight_min_value = 1;
        let height_min_value = 1;
        let lenght_min_value = 1;

        //Controlla stato

        let description_max_lenght = 255;

        let isbn_format = /^[0-9]{13}/;
        let book_binding_format = /^(?![ ]+$)[a-zA-Z ]*$/

        let volume_format = /^(?![ ]+$)[a-zA-Z ]*$/

        //Controlla releaseDate

        let page_number_format = /[0-9]*/; //Same for quantity,weight,lenght,height
        let quantity_min_value = 1;
        let interior_format = /^(?![ ]+$)[a-zA-Z ]*$/;
        let language_format = /^(?![ ]+$)[a-zA-Z ]*$/;


        //Controlla collezione non vuoto
        //Controlla genere non vuoto (trim)


        let story_format = /^(?![ ]+$)[a-zA-Z ]*$/
        //Controllare immagine

        let nome = document.getElementById("nome").value;
        let editore = document.getElementById("editore").value;
        let prezzo = document.getElementById("prezzo").value;
        let peso = document.getElementById("peso").value;
        let altezza = document.getElementById("altezza").value;
        let larghezza = document.getElementById("larghezza").value;

        let stato = document.getElementById("stato").value;
        let descrizione = document.getElementById("descrizione").value;
        let isbn = document.getElementById("isbn").value;
        let rilegatura = document.getElementById("rilegatura").value;
        let volume = document.getElementById("volume").value;

        let data_uscita = document.getElementById("data_uscita").value;
        let numPagine = document.getElementById("numPagine").value;
        let quantity = document.getElementById("quantity").value;
        let interni = document.getElementById("interni").value;

        let lingua = document.getElementById("lingua").value;
        let collezione = document.getElementById("collezione").value;
        let genere = document.getElementById("genere").value;
        let story_maker = document.getElementById("story_maker").value;
        let immagine = document.getElementById("immagine").value;


        let booleano = true;

        if(!name_format.test(nome) || nome.length > 50 || nome.trim().length<1) {
            document.querySelector(".name_error").innerHTML = "Il nome deve rispettare il seguente formato: non vuoto e la lunghezza deve essere minore di 50";
            document.querySelector(".name_error").style.display = "block";
            booleano = false;
        }
        else {
            document.querySelector(".name_error").innerHTML = "";
            document.querySelector(".name_error").style.display = "block";
        }


        if(!publisher_format.test(editore) || editore.length>50 || editore.trim().length<1) {
            document.querySelector(".publisher_error").innerHTML = "Inserire un editore valido : non vuoto e con lunghezza minore di 50 caratteri";
            document.querySelector(".publisher_error").style.display = "block";
            booleano = false;
        }
        else {
            document.querySelector(".publisher_error").innerHTML = "";
            document.querySelector(".publisher_error").style.display = "block";
        }


        if(!price_format.test(prezzo)) {
            document.querySelector(".price_error").innerHTML = "Inserire un prezzo valido ... formato: euro.centesimi";
            document.querySelector(".price_error").style.display = "block";
            booleano = false;
        }
        else {
            document.querySelector(".price_error").innerHTML = "";
            document.querySelector(".price_error").style.display = "block";
        }

        if(!page_number_format.test(peso) || peso<1) {
            document.querySelector(".weight_error").innerHTML = "Inserire un peso valido: non vuoto e maggiore o uguale di 1 cm";
            document.querySelector(".weight_error").style.display = "block";
            booleano = false;
        }
        else {
            document.querySelector(".weight_error").innerHTML = "";
            document.querySelector(".weight_error").style.display = "block";
        }


        if(!page_number_format.test(altezza) || altezza<1) {
            document.querySelector(".height_error").innerHTML = "Inserire un'altezza valida: non vuota e maggiore o uguale di 1 cm";
            document.querySelector(".height_error").style.display = "block";
            booleano = false;
        }
        else {
            document.querySelector(".height_error").innerHTML = "";
            document.querySelector(".height_error").style.display = "block";
        }

        if(!page_number_format.test(larghezza) || larghezza<1) {
            document.querySelector(".length_error").innerHTML = "Inserire una lunghezza valida: non vuota e maggiore o uguale di 1 cm";
            document.querySelector(".length_error").style.display = "block";
            booleano = false;
        }
        else {
            document.querySelector(".length_error").innerHTML = "";
            document.querySelector(".length_error").style.display = "block";
        }

        if(!publisher_format.test(stato) || stato.trim().length<1) {
            document.querySelector(".state_error").innerHTML = "Inserire uno stato valido: non vuoto e con valore 'Nuovo' oppure 'Usato'";
            document.querySelector(".state_error").style.display = "block";
            booleano = false;
        }
        else {
            document.querySelector(".state_error").innerHTML = "";
            document.querySelector(".state_error").style.display = "block";

        }

        if(descrizione.length>255) {
            document.querySelector(".description_error").innerHTML = "La descrizione inserita non può essere più lunga di 255 caratteri";
            document.querySelector(".description_error").style.display = "block";
            booleano = false;
        }
        else {
            document.querySelector(".description_error").innerHTML = "";
            document.querySelector(".description_error").style.display = "block";

        }

        if(!isbn_format.test(isbn) || isbn.length>13) {
            document.querySelector(".isbn_error").innerHTML = "Inserire un isbn valido: non vuoto e maggiore di 13 caratteri";
            document.querySelector(".isbn_error").style.display = "block";
            booleano = false;
        }
        else {
            document.querySelector(".isbn_error").innerHTML = "";
            document.querySelector(".isbn_error").style.display = "block";
        }

        if(!book_binding_format.test(rilegatura) || rilegatura.length>30 || rilegatura.trim().length<1) {
            document.querySelector(".book_binding_error").innerHTML = "Inserire una rilegatura valida: non vuota e con lunghezza minore o uguale di 30 caratteri";
            document.querySelector(".book_binding_error").style.display = "block";
            booleano = false;
        }
        else {
            document.querySelector(".book_binding_error").innerHTML = "";
            document.querySelector(".book_binding_error").style.display = "block";
        }


        if(volume.trim().length<1 || volume.length>20 || volume.trim().length<1) {
            document.querySelector(".volume_error").innerHTML = "Inserire un volume valido: non vuoto e con lunghezza minore o uguale di 20 caratteri ";
            document.querySelector(".volume_error").style.display = "block";
            booleano = false;
        }
        else {
            document.querySelector(".volume_error").innerHTML = "";
            document.querySelector(".volume_error").style.display = "block";
        }

        if(data_uscita === "" || data_uscita>today) {
            document.querySelector(".release_date_error").innerHTML = "Inserire una data valida: non vuota e precedente alla data odierna";
            document.querySelector(".release_date_error").style.display = "block";
            booleano = false;
        }
        else {
            document.querySelector(".release_date_error").innerHTML = "";
            document.querySelector(".release_date_error").style.display = "block";
        }

        if(!page_number_format.test(numPagine) || numPagine<1) {
            document.querySelector(".page_number_error").innerHTML = "Inserire un numero di pagine valido: non vuoto e maggiore o uguale di 1 ";
            document.querySelector(".page_number_error").style.display = "block";
            booleano = false;
        }
        else {
            document.querySelector(".page_number_error").innerHTML = "";
            document.querySelector(".page_number_error").style.display = "block";
        }

        if(!page_number_format.test(quantity) || quantity<1) {
            document.querySelector(".quantity_error").innerHTML = "Inserire a quantità valida: non vuotoa e maggiore o uguale di 1 ";
            document.querySelector(".quantity_error").style.display = "block";
            booleano = false;
        }
        else {
            document.querySelector(".quantity_error").innerHTML = "";
            document.querySelector(".quantity_error").style.display = "block";
        }

        if(!interior_format.test(interni) || interni.length>20 || interni.trim().length<1) {
            document.querySelector(".interior_error").innerHTML = "Inserire un colore degli interni valido: non vuoto e numero di caratteri inferiore o uguale a 20";
            document.querySelector(".interior_error").style.display = "block";
            booleano = false;
        }
        else {
            document.querySelector(".interior_error").innerHTML = "";
            document.querySelector(".interior_error").style.display = "block";
        }

        if(!language_format.test(lingua) || lingua.length>20 || lingua.trim().length<1) {
            document.querySelector(".language_error").innerHTML = "Inserire una lingua valida: non vuota e con lunghezza dei caratteri non maggiore di 20";
            document.querySelector(".language_error").style.display = "block";
            booleano = false;
        }
        else {
            document.querySelector(".language_error").innerHTML = "";
            document.querySelector(".language_error").style.display = "block";
        }

        if(collezione.trim().length<1) {
            document.querySelector(".collection_error").innerHTML = "Inserire una collezione valida: non vuota";
            document.querySelector(".collection_error").style.display = "block";
            document.querySelector(".collection_error").style.color = "red";
            booleano = false;
        }
        else {
            document.querySelector(".collection_error").innerHTML = "";
            document.querySelector(".collection_error").style.display = "block";
        }

        if(genere.trim().length<1) {
            document.querySelector(".genre_error").innerHTML = "Inserire un genere valido: non vuoto";
            document.querySelector(".genre_error").style.display = "block";
            booleano = false;
        }
        else {
            document.querySelector(".genre_error").innerHTML = "";
            document.querySelector(".genre_error").style.display = "block";
        }

        if(!story_format.test(story_maker) || story_maker.length>25 || story_maker.trim().length<1) {
            document.querySelector(".storyMaker_error").innerHTML = "Inserire uno storyMaker valido: non vuoto e con un numero di caratteri non maggiore di 25";
            document.querySelector(".storyMaker_error").style.display = "block";
            booleano = false;
        }
        else {
            document.querySelector(".storyMaker_error").innerHTML = "";
            document.querySelector(".storyMaker_error").style.display = "block";
        }

        /*if(immagine.trim().length<1) {
            document.querySelector(".image_error").innerHTML = "Inserire una immagine";
            document.querySelector(".image_error").style.display = "block";
            booleano = false;
        }
        else {
            document.querySelector(".image_error").innerHTML = "";
            document.querySelector(".image_error").style.display = "block";
        }*/

        console.log(booleano)


        return booleano;

    }


</script>

</html>

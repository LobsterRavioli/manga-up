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

        <form id="addProductForm" action="${pageContext.request.contextPath}/ProductsView/processProductInsertion" method="post" enctype="multipart/form-data">

                <div class="container">

                    <h1>Form per l'inserimento di un prodotto</h1>
                    <p>Riempi i campi del form per l'inserimento del prodotto</p>

                    <p>${error_message}</p>
                    <hr>
                    <p class="name_error"></p>
                    <label for="nome"><b>Nome</b></label>
                    <input type="text" placeholder="Inserisci il nome" name="nome" id="nome"  pattern="^(?![ ]+$)[a-zA-Z ]*$" required>

                    <p class="publisher_error"></p>
                    <label for="editore"><b>Editore</b></label>
                    <input type="text" placeholder="Inserisci l'editore" name="editore" id="editore"  pattern="^(?![ ]+$)[a-zA-Z ]*$" title="Non può avere numeri al suo interno" required>

                    <p class="price_error"></p>
                    <label for="prezzo"><b>Prezzo</b></label>
                    <input type="text" placeholder="Inserisci il prezzo" name="prezzo" id="prezzo"  pattern="[0-9]*[.[0-9]{2}]" title="Rispettare il formato: Euro.Centesimi" required>

                    <p class="weight_error"></p>
                    <label for="peso"><b>Peso</b></label>
                    <input type="number" placeholder="Inserisci il peso" name="peso" id="peso" min="1" required>

                    <p class="height_error"></p>
                    <label for="altezza"><b>Altezza</b></label>
                    <input type="number" placeholder="Inserisci l'altezza" name="altezza" id="altezza" min="1" required>
                    <br>
                    <p class="length_error"></p>
                    <label for="larghezza"><b>Larghezza</b></label>
                    <input type="number" placeholder="Inserisci la larghezza" name="larghezza" id="larghezza" min="1" required>

                    <p class="state_error"></p>
                    <label for="stato"><b>Stato</b></label>
                    <select id="stato" name="stato">
                        <option value="NEW">Nuovo</option>
                        <option value="USED">Usato</option>
                    </select>

                        <p class="description_error"></p>
                        <label for="descrizione"><b>Descrizione</b></label>
                        <textarea rows="4" cols="50" name="descrizione" id="descrizione">Inserisci una descrizione del prodotto
                        </textarea>
                        <br>
                        <p class="isbn_error"></p>
                        <label for="isbn"><b>ISBN</b></label>
                        <input type="text"  placeholder="Inserisci l'isbn" name="isbn" id="isbn" pattern="[0-9]{13}" required>
                        <br>

                        <p class="book_binding_error"></p>
                        <label for="rilegatura"><b>Rilegatura</b></label>
                        <input type="text" placeholder="Inserisci la rilegatura" name="rilegatura" id="rilegatura" pattern="^(?![ ]+$)[a-zA-Z ]*$" required>
                        <br>

                        <p class="volume_error"></p>
                        <label for="volume"><b>Volume</b></label>
                        <input type="text" placeholder="Inserisci il volume" name="volume" id="volume" pattern="^(?![ ]+$)[a-zA-Z ]*$" required>

                        <p class="release_date_error"></p>
                        <label for="data_uscita"><b>Data di uscita</b></label>
                        <input type="date" placeholder="Inserire la data di uscita" name="data_uscita" id="data_uscita" required>
                        <br>
                        <p class="page_number_error"></p>
                        <label for="numPagine"><b>Numero di Pagine</b></label>
                        <input type="text" placeholder="Inserire il numero di pagine" name="numPagine" id="numPagine" pattern="[0-9]*" required>
                        <br>
                        <p class="quantity_error"></p>
                        <label for="quantity"><b>Quantità da immagazzinare</b></label>
                        <input type="text"  placeholder="Inserire la quantità" name="quantity" id="quantity" min="1" pattern="[0-9]*" required>
                        <br>
                        <p class="interior_error"></p>
                        <label for="interni"><b>Colore degli Interni</b></label>
                        <input type="text" placeholder="Inserire il colore degli interni" name="interni" id="interni" pattern="^(?![ ]+$)[a-zA-Z ]*$"  required>
                        <br>

                        <p class="language_error"></p>
                        <label for="lingua"><b>Lingua</b></label>
                        <input type="text" placeholder="Inserire la lingua" name="lingua" id="lingua" pattern="^(?![ ]+$)[a-zA-Z ]*$" required>
                        <br>

                        <%CollectionDAO c = new CollectionDAO((DataSource) application.getAttribute("DataSource"));
                          GenreDAO g = new GenreDAO((DataSource) application.getAttribute("DataSource"));%>
                        <%
                        try{
                        ArrayList<Collection> collections = c.retrieveAlL();
                        %>
                        <p class="collection_error"></p>
                        <select id="state" name="collection">

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
                        <p class="collection_error"></p>
                        <select id="state" name="genere">

                            <%for(Genre gen: collections){%>
                            <option value="<%=gen.getName()%>"><%=gen.getName()%></option>
                            <%}%>
                        </select>
                        <%}catch (Exception e){

                        }%>

                        <p class="storyMaker_error"></p>
                        <label for="story_maker"><b>Story Maker</b></label>
                        <input type="text" placeholder="Inserire lo storyMaker" name="story_maker" id="story_maker" pattern="^(?![ ]+$)[a-zA-Z ]*$" required>
                        <br>

                        <p class="image_error"></p>
                        <label for="immagine"><b>Immagine</b></label>
                        <input type="file" name="immagine" id="immagine" accept="image/png, image/jpeg" required>
                        <br>

                        <hr>


                </div>

                <br>
                <button class="fry" type="submit" onclick="check_registration_format()" value="">Inserisci</button>

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

   /* function checkForm(form)
    {
        return validateInputs();
    }*/


   /* const data = document.getElementById('data_uscita');

    const setError = (element, message) => {

        alert("Il prodotto inserito deve essere già uscito")
    };


    validateInputs = () => {

        var result = false;

        const date = new Date(data.value);
        const currentD = new Date(Date.now());

        if (shipmentD < currentD) {
            setError(date, 'The shipment date is incorrect');
            result = false;
        } else {
            result = true;
        }

        return result;
    }
*/


</script>

</html>

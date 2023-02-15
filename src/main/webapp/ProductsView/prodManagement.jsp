<%@ page import="Merchandising.MerchandiseService.Collection" %>
<%@ page import="Merchandising.MerchandiseService.CollectionDAO" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Merchandising.MerchandiseService.Manga" %><%--
  Created by IntelliJ IDEA.
  User: Francesco Monzillo
  Date: 26/01/2023
  Time: 20:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Products</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css_s/addProd.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css_s/homeManagerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/OrderView/style.css">
</head>
<body>
<jsp:include page="/header_manager_homepage.jsp"/>
<div style="display: table">
    <div style="display:table-row;">
        <div class="home" style="display:table-cell;height: 100vh;position: initial;">
            <nav id="items">
                <div>
                    <a class="select" href="${pageContext.request.contextPath}/ProductsView/addProducts.jsp">Add Product<br><br></a>
                    <a class="select" href="${pageContext.request.contextPath}/ProductsView/prodManagement.jsp">Manage Product<br><br></a>
                </div>
            </nav>
        </div>
        <div style="display:table-cell;">
            <div class="right" style="display: table-cell;position: relative;left: .8%;">
                <form action="${pageContext.request.contextPath}/productsFilter" onsubmit="return validatePricesAndColl()" method="GET" id="filter">
                    <p class="formMainLabel">Filtri:<p>
                    <p class="widthAppropriate formMainLabel">
                        <input type="hidden" value="true" name="UserManager" id="UserManager" form="filter">
                        <label for="name" class="filterinput">Nome:</label><br>
                        <input type="text"  form="filter" id="name" name="name" placeholder="nome prodotto" class="filterinput"><br>

                        <label for="minPrice" class="filterinput">Prezzo di partenza:</label><br>
                        <input type="number" form="filter" value="0" id="minPrice" name="minPrice" placeholder="0"  oninput="validatePricesAndColl()" class="filterinput"><br>

                        <p id="price_error" class="price_error"></p>

                        <label for="maxPrice" class="filterinput">Prezzo Massimo:</label><br>
                        <input type="number" value="0" form="filter" id="maxPrice" name="maxPrice" placeholder="0" oninput="validatePricesAndColl()" class="filterinput"><br><br>

                    <p> Ordina per :</p>
                    <input type="radio" id="soggetto" name="soggetto" value="name" checked>
                    <label for="soggetto">Nome</label><br>
                    <input type="radio" id="prezzo" name="soggetto" value="prezzo">
                    <label for="prezzo">Prezzo</label><br>

                    <hr style="display: inline-block;width: 15.5rem;">

                    <p> Criterio Ordinamento:</p>
                    <input type="radio" id="crescente" name="criteria" value="ASC" checked>
                    <label for="crescente">Crescente</label><br>
                    <input type="radio" id="decrescente" name="criteria" value="DESC">
                    <label for="decrescente">Decrescente</label><br>

                    <hr style="display: inline-block;width: 15.5rem;">

                    <br><label for="collection">Collezione:</label><br>
                    <%CollectionDAO c = new CollectionDAO((DataSource) application.getAttribute("DataSource"));
                        try{
                            ArrayList<Collection> collections = c.retrieveAlL();

                    %>
                    <p id ="coll_error" class="coll_error"></p>
                    <select id="collection" oninput="validatePricesAndColl()"  form="filter" name="collection">
                    <option value=""></option>
                    <%for(Collection coll: collections){%>
                    <option value="<%=coll.getName()%>"><%=coll.getName()%></option>
                    <%}%>
                </select>
                    <%}catch (Exception e){

                    }%>
                    <input type="submit" form="filter" onsubmit="check_registration_format()" value="Submit">
                    </p>
                </form>
            </div>
            <%ArrayList<Manga> lista = (ArrayList<Manga>) request.getAttribute("listaElementi");
                String errore = (String) request.getAttribute("error");
            %>
            <%if(lista!=null){%>
            <div id="results" class="results" style="display:table-cell;">
                <table style="margin: 0 auto;width: 100px;height: 100px;position: relative;top: 10rem;">
                    <tr>
                        <th class="fontMinus">Immagine</th>
                        <th class="fontMinus">Nome</th>
                        <th class="fontMinus">Prezzo</th>
                        <th class="fontMinus">Collezione</th>
                        <th class="fontMinus">Quantità</th>
                        <th class="fontMinus">Rifornisci Prodotto</th>
                        <th class="fontMinus">Rimuovi Prodotto</th>
                    </tr>
                    <%for(int i=0;i<lista.size();i++){
                        Manga m=lista.get(i);%>
                    <tr>
                        <td><img src="${pageContext.request.contextPath}/images/products/<%=m.getImagePath()%>" class="table_imgs"></td>
                        <td><%=m.getName()%></td>
                        <td><%=String.format("%.2f", m.getPrice()).replace(',', '.') %> &euro;</td>
                        <td><%=m.getCollection().getName()%></td>
                        <td><%=m.getQuantity()%></td>
                        <td><!--<a href="#" class="change"></a>--><button name="redirect" onclick="modify('<%=m.getId()%>','<%=m.getName()%>')" type="button" value="true" type="submit">Modifica</button></td>
                        <td><!--<a href="#" class="close"></a>--><button onclick="confirmation('<%=m.getId()%>','<%=m.getName()%>')"> Rimuovi</button></td>
                    </tr>
                    <%}%>
                </table>
            </div>
            <%}else if(request.getAttribute("error")!=null){%>
            <p id="found">Non sono stati trovati elementi conformi ai parametri di ricerca... Riprovare con altri</p>
            <%}else if(request.getAttribute("goodEnding")!=null){
                String ending = (String) request.getAttribute("goodEnding");
                if(ending.equals("true")){%>
            <p id="ending">Prodotto rimosso con successo</p>
            <%}else{%>
            <p id ="ending">C’è stato un errore con il prodotto selezionato, il prodotto non è esistente</p>
            <%}%>
            <%}%>

        </div>
    </div>
</div>


<!--<div style="width: 100%;display: table;height: 100%;">
    <div style="display: table-row;width: 100%;height: 100%;">
        <div class="navbar" style="display:table-cell;">
            <a class="select" href="${pageContext.request.contextPath}/ProductsView/addProducts.jsp">Add Product<br></a>
            <a class="select" href="${pageContext.request.contextPath}/ProductsView/prodManagement.jsp">Manage Product<br></a>
        </div>

    </div>
</div>-->

</body>

<script>

    function validatePricesAndColl(){
        let min = document.getElementById("minPrice").value;
        let max = document.getElementById("maxPrice").value;
        let collection = document.getElementById("collection").value;
        let b = true;

        console.log(max+"\n"+min)

        if(parseInt(max)<parseInt(min)){
            document.querySelector(".price_error").innerHTML = "Range di prezzi non corretto";
            document.querySelector(".price_error").style.display = "block";
            b=false;
        }else{
            document.querySelector(".price_error").innerHTML = "";
            document.querySelector(".price_error").style.display = "block";
        }
        if(collection.trim().length<1){
            document.querySelector(".coll_error").innerHTML = "La collezione selezionata non deve essere vuota";
            document.querySelector(".coll_error").style.display = "block";
            b=false;
        }else{
            document.querySelector(".coll_error").innerHTML = "";
            document.querySelector(".coll_error").style.display = "block";
        }

        return b;
    }

    function redirect(string){
        window.location.replace("http://localhost:8080"+string);
    }

    function confirmation(mangaID,mangaName){
        booleanValue = confirm("Sei sicuro di voler procedere con la rimozione del prodotto di nome '"+mangaName+"'");
        let xhrObj=new XMLHttpRequest();
        xhrObj.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                redirect("${pageContext.request.contextPath}/productsRemoval?goodEnding=true")
            }else if(this.readyState==4 && this.status == 201){
                redirect("${pageContext.request.contextPath}/productsRemoval?goodEnding=false")
            }
        }
        if(booleanValue){
            xhrObj.open("POST","${pageContext.request.contextPath}/productsRemoval?id="+encodeURIComponent(""+mangaID),true);
            xhrObj.send();
        }
    }

    function modify(mangaID,mangaName){
        booleanValue = confirm("Sei sicuro di voler procedere con l'aggiunta di unità per il prodotto '"+mangaName+"'");
        if(booleanValue){
            redirect("${pageContext.request.contextPath}/productsManagement?redirect=true&id="+encodeURIComponent(mangaID))
        }
    }

</script>

</html>


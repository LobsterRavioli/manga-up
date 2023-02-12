<%@ page import="java.util.ArrayList" %>
<%@ page import="Merchandising.MerchandiseService.Manga" %><!--
  Created by IntelliJ IDEA.
  User: Francesco Monzillo
  Date: 08/01/2023
  Time: 21:31
  To change this template use File | Settings | File Templates.
-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/header.jsp" %>
    <title>Catalog</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">-->
    <!--<link rel="stylesheet" href="${pageContext.request.contextPath}/css_s/style.css" type="text/css">-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css_s/catalog.css" type="text/css">
    <!--<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>--
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/catalog.js"></script>-->
    <style>
        ::-webkit-scrollbar {
            width: 8px;
        }
        /* Track */
        ::-webkit-scrollbar-track {
            background: #f1f1f1;
        }

        /* Handle */
        ::-webkit-scrollbar-thumb {
            background: #888;
        }

        /* Handle on hover */
        ::-webkit-scrollbar-thumb:hover {
            background: #555;
        }@import url('https://fonts.googleapis.com/css2?family=Manrope:wght@200&display=swap');html, body{height: 100%}body{display: grid;background: #fff;font-family: 'Manrope', sans-serif}.mydiv{margin-top: 50px;margin-bottom: 50px}.cross{font-size:10px}.padding-0{padding-right: 5px;padding-left: 5px}.img-style{margin-left: -11px;box-shadow: 1px 1px 5px 1px rgba(0, 0, 0, 0.1);border-radius: 5px;max-width: 104% !important}.m-t-20{margin-top: 20px}.bbb_background{background-color: #E0E0E0 !important}.ribbon{width: 150px;height: 150px;overflow: hidden;position: absolute}.ribbon span{position: absolute;display: block;width: 34px;border-radius: 50%;padding: 8px 0;background-color: #3498db;box-shadow: 0 5px 10px rgba(0, 0, 0, .1);color: #fff;font: 100 18px/1 'Lato', sans-serif;text-shadow: 0 1px 1px rgba(0, 0, 0, .2);text-transform: uppercase;text-align: center}.ribbon-top-right{top: -10px;right: -10px}.ribbon-top-right::before, .ribbon-top-right::after{border-top-color: transparent;border-right-color: transparent}.ribbon-top-right::before{top: 0;left: 17px}.ribbon-top-right::after{bottom: 17px;right: 0}.sold_stars i{color: orange}.ribbon-top-right span{right: 17px;top: 17px}div{display: block;position: relative;-webkit-box-sizing: border-box;-moz-box-sizing: border-box;box-sizing: border-box}.bbb_deals_featured{width: 100%}.bbb_deals{width: 100%;margin-right: 7%;padding-top: 80px;padding-left: 25px;padding-right: 25px;padding-bottom: 34px;box-shadow: 1px 1px 5px 1px rgba(0, 0, 0, 0.1);border-radius: 5px;margin-top: 0px}.bbb_deals_title{position: absolute;top: 10px;left: 22px;font-size: 18px;font-weight: 500;color: #000000}.bbb_deals_slider_container{width: 100%}.bbb_deals_item{width: 100% !important}.bbb_deals_image{width: 100%}.bbb_deals_content{margin-top: 33px}.bbb_deals_item_category a{font-size: 14px;font-weight: 400;color: rgba(0, 0, 0, 0.5)}.bbb_deals_item_price_a{font-size: 14px;font-weight: 400;color: rgba(0, 0, 0, 0.6)}.bbb_deals_item_price_a strike{color: red}.bbb_deals_item_name{font-size: 24px;font-weight: 400;color: #000000}.bbb_deals_item_price{font-size: 24px;font-weight: 500;color: #6d6e73}.available{margin-top: 19px}.available_title{font-size: 16px;color: rgba(0, 0, 0, 0.5);font-weight: 400}.available_title span{font-weight: 700}@media only screen and (max-width: 1990px){.bbb_deals{width: 77%;margin-right: 0px}}@media only screen and (max-width: 575px){.bbb_deals{padding-left: 15px;padding-right: 15px}.bbb_deals_title{left: 15px;font-size: 16px}.bbb_deals_slider_nav_container{right: 5px}.bbb_deals_item_name, .bbb_deals_item_price{font-size: 20px}}</style>
</head>
<body classname="snippet-body">
<div class="contxxx">
<div class="toppane"></div>
<div class="leftpane">
<div class="filterForm">
        <!--<p class="formMainLabel">Filtri:<p>
        <p class="widthAppropriate formMainLabel">
            <label for="collections" class="filterinput">Collezione:</label><br>
            <input type="text"  form="filter" id="collections" name="collections" placeholder="nome collezione" class="filterinput"><br>

            <label for="minPrice" class="filterinput">Prezzo di partenza:</label><br>
            <input type="number" form="filter" id="minPrice" name="minPrice" placeholder="0" min="0" class="filterinput"><br>
            <label for="maxPrice" class="filterinput">Prezzo Massimo:</label><br>
            <input type="number" form="filter" id="maxPrice" name="maxPrice" placeholder="0" min="0" class="filterinput"><br><br>

            <select id="state" form="filter" name="state">
                <option value="NEW">Nuovo</option>
                <option value="USED">Usato</option>
            </select>

        <input type="submit" form="filter" value="Submit">
        </p>-->

</div>

</div>
    <% Integer limit=0;%>

    <%if(request.getAttribute("error")!=null){%>
        <p id="prodNonEsistente"><%=request.getAttribute("error")%>></p>
    <%}else{

    }%>

<div class="container mydiv x" id="paginated-list">
    <% ArrayList<Manga>list = (ArrayList<Manga>) request.getAttribute("listaElementi");

    if(list!=null){
        boolean b = false;

       for(int i=0;i<list.size();i++){
           Manga m = null;
           if(i % 3 == 0){
               if(b){%>
                    </div>
               <%}%>
                <%b=true;%>
                    <div class="row fulll" id="idkidk">
            <%}
                m = (Manga) list.get(i);
               %>
                        <div class="col-md-4">
                            <!-- bbb_deals -->
                            <div class="bbb_deals">
                                <div class="bbb_deals_title"><p class="p_title"><%=m.getCollection().getName()%></p></div>
                                <div class="bbb_deals_slider_container">
                                    <div class=" bbb_deals_item">
                                        <div class="bbb_deals_image"><img src="${pageContext.request.contextPath}/images/products/<%=m.getImagePath()%>" alt="" style="height: 180px"></div>

                                        <div class="bbb_deals_content">
                                            <!--<div class="bbb_deals_info_line d-flex flex-row justify-content-start">-->
                                            <div class="bbb_deals_item_category"><a href="#"><%=m.getPublisher()%></a></div>
                                            <!--<div class="bbb_deals_item_price_a ml-auto"><strike>€<%=m.getPrice()%></strike></div>-->
                                        </div>
                                        <!--<div class="bbb_deals_info_line d-flex flex-row justify-content-start">-->
                                        <div class="bbb_deals_item_name"><a class="prodAnchor" href="${pageContext.request.contextPath}/productE_U?prodId=<%=m.getId()%>">
                                            <p class="prodName"> <%=m.getName()%></p>
                                        </a>
                                        </div>

                                    </div>
                                </div>

                            </div>
                            <div class="bbb_deals_item_price ml-auto"><%=String.format("%.2f", m.getPrice()).replace(',', '.') %> &euro;</div>
                            <div class="available">

                                <div class="available_title">Disponibili: <span><%=m.getQuantity()%></span></div>
                                <!--<div class="sold_stars ml-auto"> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star"></i> <i class="fa fa-star"></i> </div>
                            </div>-->
                                <div class="available_bar"><span style="width:17%"></span></div>
                            </div>
                        </div>
           <%}
    }else{%>
                        <p id="problemi"> Non è stato trovato alcun elemento <p>
                                <%}%>

    </div>

    <nav class="pagination-container">
        <button class="pagination-button" id="prev-button" aria-label="Previous page" title="Previous page">
            &lt;
        </button>

        <div id="pagination-numbers">

        </div>

        <button class="pagination-button" id="next-button" aria-label="Next page" title="Next page">
            &gt;
        </button>
    </nav>
        <br class="clear" />

</div>


</div>


<!--<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="#"></script>
<script type="text/javascript" src="#"></script>
<script type="text/javascript" src="#"></script>
<script type="text/javascript"src="#"></script>
<script type="text/javascript">
    var myLink = document.querySelectorAll('a[href="#"]');
    myLink.forEach(function(link){
        link.addEventListener('click', function(e) {
            e.preventDefault();
        });
    });
</script>-->

<script>

    function moreProducts(){
        let xhrObj=new XMLHttpRequest();
        let TreeName=x.parentElement.parentElement.previousElementSibling.firstElementChild.nextElementSibling;
        xhrObj.open("GET","/ArborVitae/catalogServlet?productsSupply="+Math.ceil(currentPage/3),true);
        xhrObj.send();
        return;
    }

    const paginationNumbers = document.getElementById("pagination-numbers");
    const paginatedList = document.getElementById("paginated-list");
    var listItems;
    try{
        listItems = parseInt('<%=limit%>');
    }catch(NullPointerException){
        listItems=0;
    }


    const nextButton = document.getElementById("next-button");
    const prevButton = document.getElementById("prev-button");

    const paginationLimit = 6;
    const prodNumLimit=18;
    const pageCount = Math.ceil(listItems / paginationLimit);
    let currentPage = 1;

    const disableButton = (button) => {
        button.classList.add("disabled");
        button.setAttribute("disabled", true);
    };

    const enableButton = (button) => {
        button.classList.remove("disabled");
        button.removeAttribute("disabled");
    };

    const handlePageButtonsStatus = () => {
        if (currentPage === 1) {
            disableButton(prevButton);
        } else {
            enableButton(prevButton);
        }

        if (pageCount === currentPage) {
            disableButton(nextButton);
        } else {
            enableButton(nextButton);
        }
    };

    const handleActivePageNumber = () => {
        document.querySelectorAll(".pagination-number").forEach((button) => {
            button.classList.remove("active");
            const pageIndex = Number(button.getAttribute("page-index"));
            if (pageIndex == currentPage) {
                button.classList.add("active");
            }
        });
    };

    const appendPageNumber = (index) => {
        const pageNumber = document.createElement("button");
        pageNumber.className = "pagination-number";
        pageNumber.innerHTML = index;
        pageNumber.setAttribute("page-index", index);
        pageNumber.setAttribute("aria-label", "Page " + index);

        paginationNumbers.appendChild(pageNumber);
    };

    const getPaginationNumbers = () => {
        for (let i = 1; i <= pageCount; i++) {
            appendPageNumber(i);
        }
    };

    const setCurrentPage = (pageNum) => {
        currentPage = pageNum;

        handleActivePageNumber();
        handlePageButtonsStatus();

        const prevRange = (pageNum - 1) * paginationLimit;
        const currRange = pageNum * paginationLimit;

        listItems.forEach((item, index) => {
            item.classList.add("hidden");
            if (index >= prevRange && index < currRange) {
                item.classList.remove("hidden");
            }
        });
    };

    window.addEventListener("load", () => {
        getPaginationNumbers();
        setCurrentPage(1);

        prevButton.addEventListener("click", () => {
            setCurrentPage(currentPage - 1);
        });

        nextButton.addEventListener("click", () => {
            setCurrentPage(currentPage + 1);
        });

        document.querySelectorAll(".pagination-number").forEach((button) => {
            const pageIndex = Number(button.getAttribute("page-index"));

            if (pageIndex) {
                button.addEventListener("click", () => {
                    setCurrentPage(pageIndex);
                });
            }
        });
    });


</script>

</body>

</html>

<%--
  Created by IntelliJ IDEA.
  User: Francesco Monzillo
  Date: 30/01/2023
  Time: 20:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Aggiorna quantità Prodotto</title>
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
                <form action="${pageContext.request.contextPath}/productsManagement" method="get" id="managing">

                    <input type="hidden" name="id" value="<%=request.getAttribute("id")%>" form="managing">
                    <label for="quantity">Aggiungi unita:</label>
                    <input id="quantity" name="quantity" type="number" min="1" required form="managing">
                    <button type="submit">Conferma</button>

                </form>
          </div>
    </div>
  </div>

</body>
</html>

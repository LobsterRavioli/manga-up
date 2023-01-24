<%--
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
</head>
    <body>
        <jsp:include page="/ProductsView/homepage.jsp"/>

        <form id="addProductForm" action="<${pageContext.request.contextPath}/addProductServlet" method="post">


        </form>

    </body>
</html>

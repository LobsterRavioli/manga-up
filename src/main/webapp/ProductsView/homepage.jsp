<%--
  Created by IntelliJ IDEA.
  User: tommasosorrentino
  Date: 09/01/23
  Time: 23:00
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: Alessandro
  Date: 09/01/23
  Time: 23:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home Page Catalog Manager</title>
    <link rel="stylesheet" href="../OrderView/style.css">
</head>
<body>

<jsp:include page="/header_manager_homepage.jsp"/>
<script>
    function errore(errore){
        alert(errore)
    }

</script>

<div class="navbar">
    <a class="select" href="${pageContext.request.contextPath}/ProductsView/addProducts.jsp">Add Product<br></a>
    <a class="select" href="${pageContext.request.contextPath}/ProductsView/prodManagement.jsp">Manage Product<br></a>
</div>
<%String errore = (String)request.getAttribute("error");
    if(errore!=null){%>
        <script>
            errore('<%=errore%>');
        </script>
    <%}%>


</body>
</html>
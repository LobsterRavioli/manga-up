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
    <title>Home Page Order Manager</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

    <div class="logo">
        <img src="${pageContext.request.contextPath}/images/logo.png" width="125px">
        <h2>Welcome Francesco</h2>

        <div class="select_role">
        <form action="#">
            <label>Role:
            <select name="usr_role">
                <option value="ord_mng">Order manager</option>
                <option value="clg_mng">Catalog manager</option>
                <option value="usr_mng">User manager</option>
            </select></label>
        </form>
        </div>

        <div class="logout">
            <a href="#">Log-out</a>
        </div>

    </div>

    <div class="navbar">
        <a href="${pageContext.request.contextPath}/orderServlet">Orders<br><br></a>
        <a href="manage_order_list.jsp">Managed Orders<br></a>
    </div>

</body>
</html>

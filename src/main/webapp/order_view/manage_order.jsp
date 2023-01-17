<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%
        String ord_id = request.getParameter("manage"); // ID ordine da gestire

        if(ord_id == null) {
            response.sendRedirect(getServletContext().getContextPath()+"/orderServlet");
            return;
        }
    %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage orders</title>
</head>
<body>

<h2>manage the order having id = <%=ord_id %></h2>
	<form action="${pageContext.request.contextPath}/managedServlet" method="post">
		<input type="hidden" name="action" value="insert">

		<label for="name">Name:</label><br>
		<input name="name" type="text" maxlength="20" required placeholder="enter name"><br>

		<label for="description">Description:</label><br>
		<textarea name="description" maxlength="100" rows="3" required placeholder="enter description"></textarea><br>

		<label for="price">Price:</label><br>
		<input name="price" type="number" min="0" value="0" required><br>

		<label for="quantity">Quantity:</label><br>
		<input name="quantity" type="number" min="1" value="1" required><br>

		<input type="submit" value="Add"><input type="reset" value="Reset">

	</form>
</body>
</html>
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

<h2>Manage the order having id = <%=ord_id %></h2>
	<form action="${pageContext.request.contextPath}/manageServlet" method="post">
		<input type="hidden" name="action" value="insert">

		<input type="hidden" name="orderID" value="<%=ord_id %>">

		<label for="deliveryDate">Delivery date:</label><br>
		<input name="deliveryDate" type="date" required placeholder="enter delivery date"><br>

		<label for="trackingNumber">Tracking number:</label><br>
		<input name="trackingNumber" type="text" maxlength="50" required placeholder="enter tracking number"></textarea><br>

		<label for="courier">Courier name:</label><br>
		<input name="courier" type="text" required placeholder="enter courier name"><br>

		<label for="shipmentDate">Delivery date:</label><br>
        <input name="shipmentDate" type="date" required placeholder="enter shipment date"><br>

		<input type="submit" value="Add"><input type="reset" value="Reset">

	</form>
</body>
</html>
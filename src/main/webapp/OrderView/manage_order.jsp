<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%
        String ord_id = request.getParameter("manage");
        String ord_date = request.getParameter("ord_date");
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
<link rel="stylesheet" href="${pageContext.request.contextPath}/css_s/form_style.css">
<script defer src="${pageContext.request.contextPath}/js/validate.js"></script>
</head>
<body>

	<form id="form" action="${pageContext.request.contextPath}/manageServlet" method="post" onsubmit="return checkForm(this);">
	<fieldset>
	    <h2>Manage the order having id = <%=ord_id %></h2>
		<input type="hidden" name="action" value="insert">

		<input type="hidden" id="orderID" name="orderID" value="<%=ord_id %>">
		<input type="hidden" id="orderDate" name="orderDate" value="<%=ord_date %>">

        <div class="input-control">
		    <label for="deliveryDate">Delivery date:</label><br>
		    <input name="deliveryDate" type="date" id="deliveryDate" required><br>
		    <div class="error"></div>
		</div>

		<label for="trackingNumber">Tracking number:</label><br>
		<input name="trackingNumber" type="text" id="trackingNumber" maxlength="50" required placeholder="enter tracking number"></textarea><br>

		<label for="courier">Courier name:</label><br>
		<input name="courier" type="text" id="courierName" required placeholder="enter courier name"><br>

        <div class="input-control">
		    <label for="shipmentDate">Delivery date:</label><br>
            <input name="shipmentDate" type="date" id="shipmentDate" required><br>
            <div class="error"></div>
        </div>

		<input type="submit" value="Add"><input type="reset" value="Reset">
	</fieldset>
	</form>
</body>
</html>
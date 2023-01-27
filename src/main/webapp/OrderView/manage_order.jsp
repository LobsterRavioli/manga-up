<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>

    <%
        String errorMessage = (String)request.getSession().getAttribute("errorMessage");

        String ord_id = request.getParameter("manage");
        String ord_date = request.getParameter("ord_date");
        Collection<?> couriers = (Collection<?>)request.getSession().getAttribute("couriers");

        if(errorMessage == null) {
            if(ord_id == null) {
                response.sendRedirect(getServletContext().getContextPath()+"/orderServlet");
                return;
            }
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
	<% if(errorMessage == null) { %>
	        <h2>Manage the order having id = <%=ord_id %></h2>
	<% }
	    else {
	%>
	        <h2>Tracking number already present. Enter a different tracking number.</h2>
	<% } %>
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

                <%
                    if(couriers != null) {
                %>
                    <div class="courier-div">
        		    <label for="courier">Courier name:</label><br>
        		    <select name="courier" id="courierName" required><br>
        	    <%
        		        Iterator<?> itc = couriers.iterator();
        		        while(itc.hasNext()) {
        		            String value = itc.next().toString();
        		%>
        		            <option value="<%=value %>" selected><%=value %></option>
        		 <%
        		        }
                    }
                 %>
                </select>
                </div>

                <div class="input-control">
        		    <label for="shipmentDate">Delivery date:</label><br>
                    <input name="shipmentDate" type="date" id="shipmentDate" required><br>
                    <div class="error"></div>
                </div>

        		<input type="submit" value="Add"><input type="reset" value="Reset">
        	</fieldset>
        	</form>
	<%
        if(errorMessage != null) {
    %>
            <p style='color: red;'><%=errorMessage %></p>
     <% } %>

</body>
</html>
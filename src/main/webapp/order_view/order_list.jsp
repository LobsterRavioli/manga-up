<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, Order.DispatchService.beans.Order"%>
<%
    Collection<?> orders = (Collection<?>)request.getAttribute("orders");
    String error = (String)request.getAttribute("error");

    if(orders == null && error == null) {
    	response.sendRedirect("${pageContext.request.contextPath}/orderServlet"); // COME FACCIO A CHIAMARE LA SERVLET ?
   		return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="author" content="Alessandro Carnevale">
    <title>Order list</title>
    <link rel="stylesheet" href="order_style.css">
</head>
<body>
<h2>Order List</h2>

<table>
	<tr>
		<th>Order ID</th>
		<th>End-user ID</th>
		<th>Order date</th>
		<th>Total price</th>
		<th>Order state</th>
		<th>IS_SELECTED</th>
	</tr>
	<%
		if(orders != null && orders.size() > 0) {
			
			Iterator<?> it = orders.iterator();
			while(it.hasNext()) {
				Order bean = (Order)it.next(); // aggiungere la import
	%>
	
			<tr>
				<td>bean.getId()</td>
				<td><%=bean.getEndUserID() %></td>
				<td><%=bean.getOrderDate() %></td>
				<td><%=bean.getTotalPrice() %></td>
				<td><%=bean.getState() %></td>
				<td></td>
			</tr>
	
	<%
			}
		}
		else {
	%>
		<tr>
			<td colspan="6">No orders available</td>
		</tr>
	<%	} %>
</table>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, Order.DispatchService.beans.Order"%>
<%

    Collection<?> orders = (Collection<?>)request.getAttribute("orders");
    String error = (String)request.getAttribute("error");

    if(orders == null && error == null) {
    	response.sendRedirect(getServletContext().getContextPath()+"/orderServlet");
   		return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="author" content="Alessandro Carnevale">
    <title>Order list</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css_s/orderStyle.css">
</head>
<body>

<jsp:include page="/header_manager_homepage.jsp" />

<div class="main_content">
    <h2 id="welcome">List of orders to manage</h2>

    <table>
	    <tr>
		    <th>Order ID <a href="${pageContext.request.contextPath}/orderServlet?sort=ord_id">Sort</a></th>
		    <th>Order date <a href="${pageContext.request.contextPath}/orderServlet?sort=ord_date">Sort</a></th>
		    <th>Order state</th>
		    <th>Total price <a href="${pageContext.request.contextPath}/orderServlet?sort=ord_total_price">Sort</a></th>
		    <th>End-user ID</th>
		    <th>Action</th>
	    </tr>
	    <%
		    if(orders != null && orders.size() > 0) {
			    Iterator<?> it = orders.iterator();
			    while(it.hasNext()) {
				    Order bean = (Order)it.next();
	    %>
	
		    	<tr>
			    	<td><%=bean.getId() %></td>
				    <td><%=bean.getOrderDate() %></td>
				    <td><%=bean.getState() %></td>
				    <td><%=bean.getTotalPrice() %></td>
				    <td><%=bean.getEndUserID() %></td>
				    <td><a href="${pageContext.request.contextPath}/manageServlet?manage=<%=bean.getId()%>">Manage order</a></td>
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
</div>

</body>
</html>
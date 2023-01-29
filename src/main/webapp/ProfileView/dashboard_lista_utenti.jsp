<%--
  Created by IntelliJ IDEA.
  User: tommasosorrentino
  Date: 09/01/23
  Time: 21:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Dashboard lista utenti</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css_s/userTable.css">
</head>
<body>

    <jsp:include page="/header_manager_homepage.jsp"/>

	<table class="extTable">
		<tr class="extTable">
			<th class="extTable">User name</th>
			<th class="extTable">Roles</th>
			<th class="extTable">Action</th>
		</tr>

    	<c:forEach items="${users}" var="user">
    	<tr>
        	<td class="extTable">${user.username}</td>
        	<td class="extTable">
        	<table>
        	<c:forEach items="${user.roles}" var="role">
            	<tr>
            	<td>${role}</td>
        		</tr>
        	</c:forEach>
        	</table>
        	</td>
        	<td class="extTable">
        	<form action="${pageContext.request.contextPath}/UserDeleteServlet" method="post">
            	<input type="hidden" name="username" value=${user.username} />
            	<input type="submit" value="elimina" name="act" id="box_button" class="accept">
        	</form>
        	</td>
        </tr>	
    	</c:forEach>
    
    </table>

</body>
</html>

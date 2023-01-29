<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Home Page Order Manager</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css_s/homeManagerStyle.css">
</head>
<body>

    <jsp:include page="/header_manager_homepage.jsp"/>

	<div class="home">
	<nav id=items>
    	<div>
        	<a href="${pageContext.request.contextPath}/orderServlet" id="orders">Orders<br><br></a>
    	</div>
    </nav>
    </div>
</body>
</html>

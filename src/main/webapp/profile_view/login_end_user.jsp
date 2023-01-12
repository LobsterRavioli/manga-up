<%--
  Created by IntelliJ IDEA.
  User: tommasosorrentino
  Date: 12/01/23
  Time: 18:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <html>
<head>
    <title>Login</title>
</head>

<body>

    <form method="post" action="${pageContext.request.contextPath}/LoginEndUserServlet">
        <input type="text" name="username"  placeholder="username" required="required" />
        <input type="password" name="password"  placeholder="password" required="required" />
        <button type="submit">Login</button>
    </form>

</body>
</html>

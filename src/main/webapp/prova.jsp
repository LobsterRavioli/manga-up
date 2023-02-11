<%--
  Created by IntelliJ IDEA.
  User: tommasosorrentino
  Date: 11/02/23
  Time: 12:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/ServletError" method="post">
        <input type="text" name="test" value="test"/>
        <input type="submit" value="submit"/>
    </form>
</body>
</html>

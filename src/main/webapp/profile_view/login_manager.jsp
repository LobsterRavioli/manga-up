<%--
  Created by IntelliJ IDEA.
  User: tommasosorrentino
  Date: 09/01/23
  Time: 21:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<form name="loginForm" method="POST" action="j_security_check">
    <p>User name: <input type="text" name="j_username" size="20"/></p>
    <p>Password: <input type="password" size="20" name="j_password"/></p>
    <p>  <input type="submit" value="Submit"/></p>
</form>
</html>

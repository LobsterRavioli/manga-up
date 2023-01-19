<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
       String managerName = (String)request.getSession().getAttribute("managerName"); // recupero lo user-name del manager
       String managerRole = (String)request.getSession().getAttribute("roleSelected"); // recupero lo specifico ruolo con il quale ha acceduto

       if(managerName == null || managerName.equals("") || managerRole == null || managerRole.equals("")) {
       	// response.sendRedirect(getServletContext().getContextPath()+"CALL THE LOG-IN SERVLET AND GET THE NAME OF THE MANAGER AND THE ROLE WITH WHICH THEY ARE LOGGED IN");
      	//	return;
       }

       managerName = "Alfonso Maria Giovanni"; // temporaneo, giusto per provare
       managerRole = "ORDER_MANAGER"; // temporaneo, giusto per provare

       if(managerName.length() > 15) {
            managerName = managerName.substring(0,15);
            managerName = managerName.concat(".");
       }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home Page Order Manager</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style_header_manager.css">
</head>
<body>

    <div class="logo">
        <img src="${pageContext.request.contextPath}/images/logo.png" width="125px">
        <h2>Welcome <%=managerName %></h2>

        <div class="select_role">
        <form action="#">
            <label>Role:
            <select name="usr_role">
                <option value="ord_mng">Order manager</option>
                <option value="clg_mng">Catalog manager</option>
                <option value="usr_mng">User manager</option>
            </select></label>
        </form>
        </div>

        <div class="logout">
            <a href="#">Log-out</a>
        </div>

    </div>

</body>
</html>
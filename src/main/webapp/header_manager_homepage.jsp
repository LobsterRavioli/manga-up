<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>

<%
       String managerName = (String)request.getSession().getAttribute("managerName"); // recupero lo user-name del manager

       if(managerName == null) {
            response.sendRedirect(getServletContext().getContextPath()+"/LoginManager");
            return;
       }

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
        <form action="${pageContext.request.contextPath}/switchRole" method="POST">
            <label>Role:
            <select name="usr_role">
            <%
                Map<String, String> roles = new HashMap<String, String>();
                roles.put("USER_MANAGER", "User manager");
                roles.put("ORDER_MANAGER", "Order manager");
                roles.put("CATALOG_MANAGER", "Catalog manager");

                Iterator<String> iterator = roles.keySet().iterator();

                while(iterator.hasNext()) {
                    String roleKey = iterator.next();
                    if(roleKey.equals(managerRole)) {
            %>
                        <option value="<%=roleKey %>" selected><%=roles.get(roleKey) %></option>
            <%
                    }
                    else {
            %>
                        <option value="<%=roleKey %>"><%=roles.get(roleKey) %></option>
            <%
                        }
                }
            %>
            </select></label>
        </form>
        </div>

        <div class="logout">
            <a href="#">Log-out</a>
        </div>

    </div>

</body>
</html>
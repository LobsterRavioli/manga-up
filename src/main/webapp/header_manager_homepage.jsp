<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>

<%
       String managerName = (String)request.getSession().getAttribute("managerName");
       String managerRole = (String)request.getSession().getAttribute("roleSelected");
       Collection<String> otherRoles = (Collection<String>)request.getSession().getAttribute("otherRoles");

       if(managerName==null || managerRole==null) {
            System.out.println(request.getScheme()+":"+request.getServerName()+request.getServerPort()+request.getContextPath());
            %><c:redirect url="${pageContext.request.contextPath}/LoginManager"/><%
       }

       if(managerName.length() > 15) {
            managerName = managerName.substring(0,15);
            managerName = managerName.concat(".");
       }

       String homePage = "";

       if(managerRole.equals("USER_MANAGER"))
            homePage = getServletContext().getContextPath()+"/ProfileView/userManagerHome.jsp";

       else if(managerRole.equals("ORDER_MANAGER"))
            homePage = getServletContext().getContextPath()+"/OrderView/homepage.jsp";

       else if(managerRole.equals("CATALOG_MANAGER"))
            homePage = getServletContext().getContextPath()+"/ProductsView/homepage.jsp";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style_header_manager.css">
</head>
<body>

    <div class="logo">
        <a href="<%=homePage %>"><img src="${pageContext.request.contextPath}/images/logo.png" width="125px"></a>
        <h2>Welcome <%=managerName %></h2>

        <div class="select_role">
        <form action="${pageContext.request.contextPath}/switchRole" method="POST">
            <label>Role:
            <select name="usr_role" onchange="this.form.submit()">
            <%
                Map<String, String> roles = new HashMap<String, String>();

                for(String s : otherRoles)
                {
                    if(s.equals("USER_MANAGER"))
                        roles.put("USER_MANAGER", "User manager");

                    if(s.equals("ORDER_MANAGER"))
                        roles.put("ORDER_MANAGER", "Order manager");

                    if(s.equals("CATALOG_MANAGER"))
                        roles.put("CATALOG_MANAGER", "Catalog manager");
                }

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
            <a href="${pageContext.request.contextPath}/logout">Log-out</a>
        </div>

    </div>

</body>
</html>
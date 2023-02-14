package filters;

import User.ProfileView.CreditCardCreateServlet;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@WebFilter(filterName = "UserManagerFilter")
public class UserManagerFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);
        HttpServletResponse resp = (HttpServletResponse) response;
        String toBeginWithUserManager="/ProfileView";
        String toBeginWithOrderManager="/OrderView";
        String toBeginWithCatalogManager="/ProductsView";

        if(((HttpServletRequest) request).getServletPath().startsWith("/productsFilter") && ((HttpServletRequest) request).getQueryString()==null){
            chain.doFilter(request,response);
            return;
        }

        if (session == null) {
            resp.sendRedirect(req.getContextPath() + "/ProfileView/login_manager.jsp");
            return;
        } else if (session.getAttribute("managerName") == null) {
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/ProfileView/login_manager.jsp");
            return;
        } else {
            Collection<String> ruoli = (Collection<String>) session.getAttribute("otherRoles");
            String path = req.getServletPath();
            if(!ruoli.contains("USER_MANAGER") && path.startsWith(toBeginWithUserManager)){
                session.invalidate();
                resp.sendRedirect(req.getContextPath() + "/ProfileView/login_manager.jsp");
                return;
            }

            if(!ruoli.contains("ORDER_MANAGER") && path.startsWith(toBeginWithOrderManager)){
                session.invalidate();
                resp.sendRedirect(req.getContextPath() + "/ProfileView/login_manager.jsp");
                return;
            }

            if(!ruoli.contains("CATALOG_MANAGER") && path.startsWith(toBeginWithCatalogManager)){
                session.invalidate();
                resp.sendRedirect(req.getContextPath() + "/ProfileView/login_manager.jsp");
                return;
            }
            chain.doFilter(request, response);
            return;
        }
    }
}

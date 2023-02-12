package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "FilterLogin", urlPatterns = { "/ProfileView/dashboard_carte_di_credito.jsp", "/ProfileView/dashboard_end_user.jsp",
        "/ProfileView/dashboard_indirizzi.jsp", "/ProfileView/creazione_carta_di_credito.jsp", "/ProfileView/creazione_indirizzo.jsp"
        ,"/AddressCreateServlet","/visualizeCartServlet","/CreditCardCreateServlet","/CreditCardDashboardServlet","/AddressDeleteServlet","/CreditCardDeleteServlet"/*,"/cartAddServlet"*/,"/cartRemoveServlet","/cartUpdateItemServlet"/*,"/visualizeCartServlet"*/,"/CartView/cart.jsp"})
public class EndUserFilter implements Filter {
    public void init(FilterConfig config) {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest newRequest = (HttpServletRequest) request;
        HttpSession session = newRequest.getSession(false);
        HttpServletResponse resp= (HttpServletResponse) response;
        if(session == null){
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ProfileView/login_end_user.jsp");
            dispatcher.forward(request, response);
            return;
        }else if(session.getAttribute("user") == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ProfileView/login_end_user.jsp");
            dispatcher.forward(request, response);
            return;
        }
        chain.doFilter(request, response);
    }
}

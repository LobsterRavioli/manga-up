package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "FilterLogin", urlPatterns = { "/profile_view/dashboard_carte_di_credito.jsp", "/profile_view/dashboard_utente.jsp",
        "/profile_view/dashboard_indirizzi.jsp", "/profile_view/creazione_carta_di_credito.jsp", "/profile_view/creazione_indirizzo.jsp"
        ,"/AddressCreateServlet","/CreditCardCreateServlet","/AddressDeleteServlet","/CreditCardDeleteServlet"})
public class EndUserFilter implements Filter {
    public void init(FilterConfig config) {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest newRequest = (HttpServletRequest) request;
        HttpSession session = newRequest.getSession();
        if (session.getAttribute("utente") == null) {
            request.setAttribute("error", "necessario il login per procedere");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/profile_view/login_end_user.jsp");
            dispatcher.forward(request, response);
            return;
        }
        chain.doFilter(request, response);
    }
}

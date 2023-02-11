package User.ProfileView;

import User.AccountService.CreditCard;
import User.AccountService.CreditCardDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/CreditCardDeleteServlet")
public class CreditCardDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession(false);

        if (s == null || s.getAttribute("user")==null) {
            response.setStatus(500);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/error_page.jsp"));
            dispatcher.forward(request, response);
            return;
        }

        if (creditCardDAO == null) {
            creditCardDAO = new CreditCardDAO((DataSource)getServletContext().getAttribute("DataSource"));
        }
        int id = Integer.valueOf(request.getParameter("credit_card_id"));
        CreditCard card = new CreditCard();
        card.setId(id);
        try {
            creditCardDAO.delete(card);
        } catch (Exception e) {
            response.setStatus(500);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/error_page.jsp"));
            dispatcher.forward(request, response);
            return;
        }
        response.sendRedirect("CreditCardDashboardServlet");
    }

    public void setCreditCardDAO(CreditCardDAO dao) {
        this.creditCardDAO = dao;
    }
    private CreditCardDAO creditCardDAO;

}

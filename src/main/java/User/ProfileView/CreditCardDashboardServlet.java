package User.ProfileView;

import User.AccountService.CreditCard;
import User.AccountService.CreditCardDAO;
import User.AccountService.EndUser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@WebServlet("/CreditCardDashboardServlet")
public class CreditCardDashboardServlet extends HttpServlet {

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
            creditCardDAO = new CreditCardDAO((DataSource) getServletContext().getAttribute("DataSource"));
        }
        HttpSession session = request.getSession();
        EndUser user = (EndUser) session.getAttribute("user");
        ArrayList<CreditCard> cards = null;
        try {
            cards = (ArrayList<CreditCard>)  creditCardDAO.findAssociatedCards(user);
        }catch (Exception e) {
            response.setStatus(500);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/error_page.jsp"));
            dispatcher.forward(request, response);
            return;
        }

        request.setAttribute("cards", cards);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/ProfileView/dashboard_carte_di_credito.jsp"));
        dispatcher.forward(request, response);
    }

    private CreditCardDAO creditCardDAO;

    public void setCreditCardDAO(CreditCardDAO creditCardDAO) {
        this.creditCardDAO = creditCardDAO;
    }

}

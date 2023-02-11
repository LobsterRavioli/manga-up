package User.ProfileView;


import User.AccountService.CreditCard;
import User.AccountService.CreditCardDAO;
import User.AccountService.EndUser;
import utils.Utils;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/CreditCardCreateServlet")
public class CreditCardCreateServlet extends HttpServlet {

    private CreditCardDAO creditCardDAO;

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
        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        if (creditCardDAO == null) {
            creditCardDAO = new CreditCardDAO(ds);
        }
        HttpSession session = request.getSession();
        EndUser user = (EndUser) session.getAttribute("user");
        CreditCard card = new CreditCard();
        card.setCardHolder(request.getParameter("card_holder"));
        card.setCardNumber(request.getParameter("card_number"));
        card.setExpirementDate(Utils.parseDate(request.getParameter("expirement_date")));
        card.setCvv((request.getParameter("cvc")));

        try {
            card.setEndUser(user);
            creditCardDAO.create(card);
        }
        catch (Exception e) {
            response.setStatus(500);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/error_page.jsp"));
            dispatcher.forward(request, response);
            return;
        }
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/CreditCardDashboardServlet"));
        dispatcher.forward(request, response);
    }

    public void setCreditCardDAO(CreditCardDAO dao) {
        this.creditCardDAO = dao;
    }


}

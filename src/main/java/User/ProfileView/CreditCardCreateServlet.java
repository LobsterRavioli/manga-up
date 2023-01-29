package User.ProfileView;

import User.AccountService.ConcreteCardBuilder;
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


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        CreditCardDAO dao = new CreditCardDAO(ds);
        HttpSession session = request.getSession();
        EndUser user = (EndUser) session.getAttribute("user");
        response.setContentType("text/html");
        CreditCard card = new ConcreteCardBuilder()
                .setCardNumber(request.getParameter("card_number"))
                .setCardHolder(request.getParameter("card_holder"))
                .setExpirationDate(Utils.parseDate(request.getParameter("expirement_date")))
                .setCvv(request.getParameter("cvc"))
                .createCreditCard();

        card.setEndUser(user);
        dao.create(card);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/CreditCardDashboardServlet"));
        dispatcher.forward(request, response);
    }
}

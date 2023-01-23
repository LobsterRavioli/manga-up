package User.ProfileView;

import User.AccountService.ConcreteCardBuilder;
import User.AccountService.CreditCard;
import User.AccountService.CreditCardDAO;
import User.AccountService.EndUser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/CreditCardDashboardServlet")
public class CreditCardDashboardServlet extends HttpServlet {

    DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
    private CreditCardDAO dao = new CreditCardDAO(ds);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        EndUser user = (EndUser) session.getAttribute("user");
        response.setContentType("text/html");
        CreditCard userCard = new ConcreteCardBuilder()
                .setEndUser(user)
                .createCreditCard();
        ArrayList<CreditCard> cards = (ArrayList<CreditCard>) dao.findAllByEnduser(userCard);
        request.setAttribute("cards", cards);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/ProfileView/dashboard_carte_di_credito.jsp"));
        dispatcher.forward(request, response);
    }
}

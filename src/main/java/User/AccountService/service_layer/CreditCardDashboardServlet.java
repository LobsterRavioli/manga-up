package User.AccountService.service_layer;

import User.AccountService.beans.EndUser;
import User.AccountService.beans.CreditCard;
import User.AccountService.dao_layer.interfaces.CreditCardDAO;
import utils.AbstractDAOFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/CreditCardDashboardServlet")
public class CreditCardDashboardServlet extends HttpServlet {

    private AbstractDAOFactory factory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.JDBC);
    private CreditCardDAO dao = factory.getCreditCardDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        EndUser user = (EndUser) session.getAttribute("user");
        response.setContentType("text/html");
        ArrayList<CreditCard> cards = (ArrayList<CreditCard>) dao.find(user);
        request.setAttribute("cards", cards);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/profile_view/dashboard_carte_di_credito.jsp"));
        dispatcher.forward(request, response);
    }
}

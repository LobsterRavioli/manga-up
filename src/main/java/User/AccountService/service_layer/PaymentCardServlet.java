package User.AccountService.service_layer;

import User.AccountService.beans.EndUser;
import User.AccountService.beans.CreditCard;
import User.AccountService.dao_layer.interfaces.PaymentCardDAO;
import utils.AbstractDAOFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/PaymentCardServlet")
public class PaymentCardServlet extends HttpServlet {

    private AbstractDAOFactory factory = AbstractDAOFactory.getDAOFactory("JDBC");
    private PaymentCardDAO dao = factory.getPayamentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        // da prendere dalla sessione
        EndUser user = new EndUser(1, "tommyrock99@hotmail.it","napoli99");

        ArrayList<CreditCard> cards = (ArrayList<CreditCard>) dao.find(user);;
        request.setAttribute("cards", cards);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/profile_view/dashboard_carte_di_credito.jsp"));
        dispatcher.forward(request, response);

    }
}

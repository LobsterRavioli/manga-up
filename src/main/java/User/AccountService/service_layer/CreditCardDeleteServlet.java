package User.AccountService.service_layer;

import User.AccountService.beans.CreditCard;
import User.AccountService.dao_layer.interfaces.CreditCardDAO;
import utils.AbstractDAOFactory;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CreditCardDeleteServlet", value = "/CreditCardDeleteServlet")
public class CreditCardDeleteServlet extends HttpServlet {
    private AbstractDAOFactory factory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.JDBC);
    private CreditCardDAO dao = factory.getCreditCardDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.valueOf(request.getParameter("credit_card_id"));
        CreditCard card = new CreditCard();
        card.setId(id);
        dao.delete(card);
        response.sendRedirect("CreditCardListServlet");
    }
}

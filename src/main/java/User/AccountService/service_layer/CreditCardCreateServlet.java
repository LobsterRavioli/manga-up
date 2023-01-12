package User.AccountService.service_layer;

import User.AccountService.beans.CreditCard;
import User.AccountService.beans.CreditCardBuilder;
import User.AccountService.beans.EndUser;
import User.AccountService.dao_layer.interfaces.CreditCardDAO;
import utils.AbstractDAOFactory;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "CreditCardCreateServlet", value = "/CreditCardCreateServlet")
public class CreditCardCreateServlet extends HttpServlet {

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
        CreditCard creditCard = new CreditCardBuilder()
                .setCardNumber(request.getParameter("card_number"))
                .setCardHolder(request.getParameter("card_holder"))
                .setExpirementDate(new Date(request.getParameter("expiration_date")))
                .setCvv(request.getParameter("cvv"))
                .setEndUser(user)
                .createCreditCard();

        dao.create(creditCard);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/"));
        dispatcher.forward(request, response);
    }
}

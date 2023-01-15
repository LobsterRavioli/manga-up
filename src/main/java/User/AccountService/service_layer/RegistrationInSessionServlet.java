package User.AccountService.service_layer;

import User.AccountService.beans.EndUser;
import User.AccountService.beans.ConcreteEndUserBuilder;
import User.AccountService.dao_layer.interfaces.EndUserDAO;
import utils.AbstractDAOFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegistrationInSessionServlet extends HttpServlet {

    private AbstractDAOFactory factory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.JDBC);
    private EndUserDAO dao = factory.getEndUserDAO();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        EndUser user = new ConcreteEndUserBuilder()
                .setEmail(request.getParameter("username"))
                .setPassword(request.getParameter("password"))
                .createEndUser();



        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/MerchandisingView/homepage.jsp"));
        dispatcher.forward(request, response);

    }

}

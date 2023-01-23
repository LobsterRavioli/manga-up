package User.ProfileView;

import User.AccountService.ConcreteEndUserBuilder;
import User.AccountService.EndUser;
import User.AccountService.EndUserDAO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

public class RegistrationInSessionServlet extends HttpServlet {

    DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
    private EndUserDAO dao = new EndUserDAO(ds);


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

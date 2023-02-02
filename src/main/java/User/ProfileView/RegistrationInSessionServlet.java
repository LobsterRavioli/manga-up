package User.ProfileView;

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



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        EndUserDAO dao = new EndUserDAO(ds);
        response.setContentType("text/html");
        EndUser user = new EndUser();
        user.setEmail(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/MerchandisingView/homepage.jsp"));
        dispatcher.forward(request, response);
    }
}

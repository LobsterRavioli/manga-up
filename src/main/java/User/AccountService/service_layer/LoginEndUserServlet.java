package User.AccountService.service_layer;

import User.AccountService.beans.EndUser;
import User.AccountService.dao_layer.interfaces.EndUserDAO;
import utils.AbstractDAOFactory;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/LoginEndUserServlet")
public class LoginEndUserServlet extends HttpServlet {
    private AbstractDAOFactory factory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.JDBC);
    private EndUserDAO dao = factory.getEndUserDAO();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        String email = request.getParameter("username");
        String password = request.getParameter("password");
        EndUser user = dao.find(email,password);

        if(user != null){
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/RegistrationServlet"));
            dispatcher.forward(request, response);
            return;
        }

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/profile_view/login_end_user.jsp"));
        dispatcher.forward(request, response);

    }
}

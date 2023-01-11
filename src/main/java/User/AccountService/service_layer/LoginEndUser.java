package User.AccountService.service_layer;

import User.AccountService.beans.EndUser;
import User.AccountService.dao_layer.implementations.EndUserDAOImp;
import User.AccountService.dao_layer.interfaces.AddressDAO;
import User.AccountService.dao_layer.interfaces.EndUserDAO;
import utils.AbstractDAOFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/LoginEndUser")
public class LoginEndUser extends HttpServlet {
    private AbstractDAOFactory factory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.JDBC);
    private EndUserDAO dao = factory.getEndUserDAO();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        EndUser user = dao.find(email,password);
        if(user != null){
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/MerchandisingView/homepage.jsp"));
            dispatcher.forward(request, response);
        }
        else{
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/login.jsp"));
            dispatcher.forward(request, response);
        }
    }
}

package User.ProfileView;

import User.AccountService.CreditCard;
import User.AccountService.CreditCardDAO;
import User.AccountService.EndUser;
import User.AccountService.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/UserDeleteServlet")
public class UserDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        UserDAO dao = new UserDAO(ds);
        try {
            String username = request.getParameter("username");
            System.out.println(username + " is being deleted");
            dao.removeUserByUserName(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("UserListServlet");
    }
}

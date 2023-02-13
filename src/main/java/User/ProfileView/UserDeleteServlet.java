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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (userDao == null) {
            userDao = new UserDAO((DataSource)getServletContext().getAttribute("DataSource"));
        }

        try {
            String username = request.getParameter("username");
            userDao.removeUserByUserName(username);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            response.setStatus(500);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/error_page.jsp"));
            dispatcher.forward(request, response);
            return;
        }

        response.sendRedirect("UserListServlet");
    }

    private UserDAO userDao;
    public void setUserDAO(UserDAO dao) {
        this.userDao = dao;
    }

}

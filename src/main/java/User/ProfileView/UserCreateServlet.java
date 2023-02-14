package User.ProfileView;

import User.AccountService.*;
import utils.Utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/UserCreateServlet")
public class UserCreateServlet extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (userDao == null) {
            userDao = new UserDAO((DataSource)getServletContext().getAttribute("DataSource"));
        }
        if (userRoleDAO == null) {
            userRoleDAO = new UserRoleDAO((DataSource)getServletContext().getAttribute("DataSource"));
        }


        User user = new User();
        user.setUsername(req.getParameter("username"));
        user.setPassword(req.getParameter("password"));
        String[] roles = req.getParameterValues("roles");
        try {
            if(userDao.existsUsername(user.getUsername())){
                req.setAttribute("error_message", "Username già esistente");
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(resp.encodeURL("/ProfileView/creazione_utente.jsp"));
                dispatcher.forward(req, resp);
                return;
            }

            userDao.createUser(user);
            userRoleDAO.setRoles(user, roles);

        } catch (SQLException e) {
            resp.setStatus(500);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(resp.encodeURL("/error_page.jsp"));
            dispatcher.forward(req, resp);
            return;
        }

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(resp.encodeURL("/UserListServlet"));
        dispatcher.forward(req, resp);
    }

    public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
        this.userRoleDAO = userRoleDAO;
    }

    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    private UserRoleDAO userRoleDAO;
    private UserDAO userDao;

}

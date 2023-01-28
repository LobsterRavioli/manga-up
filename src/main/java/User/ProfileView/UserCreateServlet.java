package User.ProfileView;

import User.AccountService.*;
import utils.Utils;

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

@WebServlet("/UserCreateServlet")
public class UserCreateServlet extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");

        UserDAO userDao = new UserDAO(ds);
        UserRoleDAO userRoleDAO = new UserRoleDAO(ds);
        User user = new User();
        user.setUsername(req.getParameter("username"));
        user.setPassword(req.getParameter("password"));
        String[] roles = req.getParameterValues("roles");
        try {

            if(userDao.getUserByUsername(user.getUsername()) != null){
                req.setAttribute("error_message", "Username già esistente");
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(resp.encodeURL("/ProfileView/creazione_utente.jsp"));
                dispatcher.forward(req, resp);

                return;
            }

            userDao.createUser(user);
            userRoleDAO.setRoles(user, roles);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(resp.encodeURL("/UserListServlet"));
        dispatcher.forward(req, resp);
        return;
    }

}

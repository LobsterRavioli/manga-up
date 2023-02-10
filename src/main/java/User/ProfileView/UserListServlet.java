package User.ProfileView;

import User.AccountService.User;
import User.AccountService.UserDAO;
import User.AccountService.UserRoleDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

@WebServlet("/UserListServlet")
public class UserListServlet extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        UserDAO userDAO = new UserDAO(ds);
        UserRoleDAO roleDAO = new UserRoleDAO(ds);

        try {
            Collection users = userDAO.getAllUsers(null);
            Iterator<User> usersIt = users.iterator();
            while (usersIt.hasNext()) {
                User user = (User) usersIt.next();
                user.setRoles(roleDAO.getRoles(user));
            }
            req.setAttribute("users", users);

        } catch (SQLException e) {
            req.setAttribute("users", null);
            req.setAttribute("fault", "fault");
        }

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(resp.encodeURL("/ProfileView/dashboard_lista_utenti.jsp"));
        dispatcher.forward(req, resp);

    }

}

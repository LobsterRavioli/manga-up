package User.AccountService.service_layer;

import User.AccountService.beans.User;
import User.AccountService.dao_layer.implementations.UserDAOImp;
import User.AccountService.dao_layer.interfaces.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/switchRole")
public class SwitchRoleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        UserDAO dao = new UserDAOImp(ds);

        String username = (String)request.getSession().getAttribute("managerName");
        String password = (String)request.getSession().getAttribute("password");
        if(username == null || password == null)
        {
            response.sendRedirect(getServletContext().getContextPath()+"/profile_view/login_manager.jsp");
            return;
        }
        else
        {
            String role = request.getParameter("usr_role"); // dalla select

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);

            try {

                if(dao.checkUser(user, role)) // se l'autenticazione va a buon fine
                {
                    HttpSession oldSession = request.getSession(false);
                    if (oldSession != null)
                        oldSession.invalidate(); // invalida la sessione se esiste

                    HttpSession currentSession = request.getSession(); // crea una nuova sessione

                    currentSession.setAttribute("managerName", user.getUsername());
                    currentSession.setAttribute("roleSelected", role);
                    currentSession.setAttribute("password", user.getPassword());
                    currentSession.setAttribute("otherRoles", dao.getRoles(user.getUsername()));

                    currentSession.setMaxInactiveInterval(5 * 60); // 5 minuti di inattività massima, dopo cancella la sessione

                    if (role.equals("USER_MANAGER")) // redirect to user manager homepage
                        response.sendRedirect(getServletContext().getContextPath() + "/profile_view/userManagerHome.jsp");

                    if (role.equals("ORDER_MANAGER"))  // redirect to order manager homepage
                        response.sendRedirect(getServletContext().getContextPath() + "/order_view/homepage.jsp");

                    if (role.equals("CATALOG_MANAGER")) // redirect to catalog manager homepage
                        response.sendRedirect(getServletContext().getContextPath() + "/ProductsView/catalogManagerHome.jsp");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/profile_view/login_manager.jsp").forward(request,response);
            }
        }
    }
}

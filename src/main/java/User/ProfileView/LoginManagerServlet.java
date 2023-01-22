package User.AccountService.service_layer;

import User.AccountService.beans.User;
import User.AccountService.dao_layer.implementations.UserDAOImp;
import User.AccountService.dao_layer.interfaces.UserDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/LoginManager")
public class LoginManagerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        UserDAO dao = new UserDAOImp(ds);

        User manager = new User();

        String login = request.getParameter("username");
        String pwd = request.getParameter("password");
        String roleName = request.getParameter("roleName");

        if(login == null || pwd == null || roleName == null)
            response.sendRedirect(getServletContext().getContextPath()+"/profile_view/login_manager.jsp");
        else
        {
            manager.setUsername(login);
            manager.setPassword(pwd);

            try {

                if(dao.checkUser(manager, roleName)) // se l'autenticazione va a buon fine
                {
                    HttpSession oldSession = request.getSession(false);
                    if(oldSession != null)
                        oldSession.invalidate(); // invalida la sessione se esiste

                    HttpSession currentSession = request.getSession(); // crea una nuova sessione

                    currentSession.setAttribute("managerName", manager.getUsername());
                    currentSession.setAttribute("roleSelected", roleName);
                    currentSession.setAttribute("password", manager.getPassword());
                    currentSession.setAttribute("otherRoles", dao.getRoles(manager.getUsername()));

                    currentSession.setMaxInactiveInterval(5*60); // 5 minuti di inattività massima, dopo cancella la sessione

                    if(roleName.equals("USER_MANAGER")) // redirect to user manager homepage
                        response.sendRedirect(getServletContext().getContextPath()+"/profile_view/userManagerHome.jsp");

                    if(roleName.equals("ORDER_MANAGER"))  // redirect to order manager homepage
                        response.sendRedirect(getServletContext().getContextPath()+"/order_view/homepage.jsp");

                    if(roleName.equals("CATALOG_MANAGER")) // redirect to catalog manager homepage
                        response.sendRedirect(getServletContext().getContextPath()+"/ProductsView/catalogManagerHome.jsp");
                }
                else
                {
                    request.setAttribute("error", "You are not an authorized user!");
                    request.getRequestDispatcher("/profile_view/login_manager.jsp").forward(request,response);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/profile_view/login_manager.jsp").forward(request,response);
            }
        }
    }
}

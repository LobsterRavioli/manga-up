package User.ProfileView;

import User.AccountService.EndUser;
import User.AccountService.EndUserDAO;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/LoginEndUserServlet")
public class LoginEndUserServlet extends HttpServlet {
    DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
    private EndUserDAO dao = new EndUserDAO(ds);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        String email = request.getParameter("username");
        String password = request.getParameter("password");
        EndUser user = dao.login(email,password);

        if(user == null){
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/ProfileView/login_end_user.jsp"));
            dispatcher.forward(request, response);
            return;
        }

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/ProfileView/.jsp"));
        dispatcher.forward(request, response);
    }
}

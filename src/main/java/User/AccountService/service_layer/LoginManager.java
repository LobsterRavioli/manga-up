package User.AccountService.service_layer;

import User.AccountService.dao_layer.implementations.EndUserDAOImp;
import User.AccountService.dao_layer.interfaces.EndUserDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;


@WebServlet("/LoginManager")
public class LoginManager extends HttpServlet {

    EndUserDAO dao;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/index.jsp"));
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        dao = new EndUserDAOImp(ds);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

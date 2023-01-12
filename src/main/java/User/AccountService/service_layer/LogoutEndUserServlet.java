package User.AccountService.service_layer;

import User.AccountService.dao_layer.implementations.EndUserDAOImp;
import User.AccountService.dao_layer.interfaces.EndUserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/LogoutEndUserServlet")
public class LogoutEndUserServlet extends HttpServlet {

    EndUserDAO dao;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/MerchandisingView/home.jsp"));
    }

}
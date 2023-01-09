package User.AccountService.service_layer;

import User.AccountService.dao_layer.implementations.EndUserDAOImp;
import User.AccountService.dao_layer.interfaces.EndUserDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/LoginEndUser")
public class LoginEndUser extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

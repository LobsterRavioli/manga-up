package User.AccountService.service_layer;

import User.AccountService.beans.Address;
import User.AccountService.beans.EndUser;
import User.AccountService.dao_layer.implementations.EndUserDAOImp;
import User.AccountService.dao_layer.interfaces.AddressDAO;
import User.AccountService.dao_layer.interfaces.EndUserDAO;
import utils.AbstractDAOFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@WebServlet("/AddressServlet")
public class AddressServlet extends HttpServlet {

    private AbstractDAOFactory factory = AbstractDAOFactory.getDAOFactory("JDBC");
    private AddressDAO dao = factory.getAddressDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        // da prendere dalla sessione
        EndUser user = new EndUser(1, "tommyrock99@hotmail.it","napoli99");
        dao = factory.getAddressDAO();
        ArrayList addresses = (ArrayList) dao.find(user);
        request.setAttribute("addresses", addresses);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/profile_view/dashboard_indirizzi.jsp"));
        dispatcher.forward(request, response);

    }
}

package User.AccountService.service_layer;

import User.AccountService.beans.Address;
import User.AccountService.beans.ConcreteAddressBuilder;
import User.AccountService.beans.EndUser;
import User.AccountService.dao_layer.interfaces.AddressDAO;
import utils.AbstractDAOFactory;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AddressCreateServlet", value = "/AddressCreateServlet")
public class AddressCreateServlet extends HttpServlet {
    private AbstractDAOFactory factory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.JDBC);
    private AddressDAO dao = factory.getAddressDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        EndUser user = (EndUser) request.getSession().getAttribute("user");
        Address address = new ConcreteAddressBuilder()
                .setStreet(request.getParameter("street"))
                .setCity(request.getParameter("city"))
                .setCountry(request.getParameter("country"))
                .setPostalCode(request.getParameter("postal_code"))
                .setRegion(request.getParameter("region"))
                .setPhoneNumber(request.getParameter("phone_number"))
                .createAddress();

        address.setEndUser(user);
        dao.create(address);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/AddressDashBoardServlet"));
            dispatcher.forward(request, response);
    }
}

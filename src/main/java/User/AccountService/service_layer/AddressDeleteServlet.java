package User.AccountService.service_layer;

import User.AccountService.beans.Address;
import User.AccountService.beans.AddressBuilder;
import User.AccountService.beans.EndUser;
import User.AccountService.dao_layer.interfaces.AddressDAO;
import utils.AbstractDAOFactory;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AddressDeleteServlet", value = "/AddressDeleteServlet")
public class AddressDeleteServlet extends HttpServlet {

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
        Address address = new AddressBuilder()
                .setStreet(request.getParameter("street"))
                .setCity(request.getParameter("city"))
                .setCountry(request.getParameter("country"))
                .setPostalCode(request.getParameter("postal_code"))
                .setRegion(request.getParameter("region"))
                .setPhoneNumber(request.getParameter("phone_number"))
                .setEndUser(user)
                .createAddress();

        dao.findSingleByEnduser(address);
        dao.delete(address);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/AddressDashboardServlet"));
        dispatcher.forward(request, response);
    }
}

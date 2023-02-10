package User.ProfileView;

import User.AccountService.Address;
import User.AccountService.AddressDAO;
import User.AccountService.EndUser;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/AddressCreateServlet")
public class AddressCreateServlet extends HttpServlet {

    private AddressDAO addressDAO;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        if (addressDAO == null) {
            addressDAO = new AddressDAO(ds);
        }
        response.setContentType("text/html");
        EndUser user = (EndUser) request.getSession().getAttribute("user");

        Address address = new Address();
        address.setStreet(request.getParameter("street"));
        address.setCity(request.getParameter("city"));
        address.setCountry(request.getParameter("country"));
        address.setPostalCode(request.getParameter("postal_code"));
        address.setRegion(request.getParameter("region"));
        address.setPhoneNumber(request.getParameter("phone_number"));
        address.setEndUser(user);

        try {
            addressDAO.create(address);
        } catch (Exception e) {
            response.setStatus(499);
            return;
        }

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/AddressDashboardServlet"));
            dispatcher.forward(request, response);
    }

    public void setAddressDAO(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }
}

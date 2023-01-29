package User.ProfileView;

import User.AccountService.Address;
import User.AccountService.AddressDAO;
import User.AccountService.ConcreteAddressBuilder;
import User.AccountService.EndUser;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/AddressCreateServlet")
public class AddressCreateServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        AddressDAO dao = new AddressDAO(ds);
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

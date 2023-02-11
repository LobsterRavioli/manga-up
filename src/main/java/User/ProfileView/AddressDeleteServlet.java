package User.ProfileView;

import User.AccountService.Address;
import User.AccountService.AddressDAO;

import User.AccountService.EndUser;
import utils.DAOException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/AddressDeleteServlet")
public class AddressDeleteServlet extends HttpServlet {

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
        EndUser user = (EndUser) request.getSession().getAttribute("user");
        Address address = new Address();
        address.setId(Integer.parseInt(request.getParameter("address_id")));
        address.setEndUser(user);
        try {
            addressDAO.delete(address);
        } catch (DAOException e) {
            response.setStatus(499);
        }
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/AddressDashboardServlet"));
        dispatcher.forward(request, response);
    }

    public void setAddressDAO(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }


}

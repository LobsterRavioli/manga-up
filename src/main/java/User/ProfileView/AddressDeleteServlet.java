package User.ProfileView;

import User.AccountService.Address;
import User.AccountService.AddressDAO;

import User.AccountService.EndUser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/AddressDeleteServlet")
public class AddressDeleteServlet extends HttpServlet {

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
        Address address = new Address();
        address.setId(Integer.parseInt(request.getParameter("address_id")));
        address.setEndUser(user);
        dao.delete(address);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/AddressDashboardServlet"));
        dispatcher.forward(request, response);
    }
}

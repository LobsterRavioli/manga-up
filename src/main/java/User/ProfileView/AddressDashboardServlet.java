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
import java.util.ArrayList;

@WebServlet("/AddressServletDashboardServlet")
public class AddressDashboardServlet extends HttpServlet {

    DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
    private AddressDAO dao = new AddressDAO(ds);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        EndUser user = (EndUser) session.getAttribute("user");

        Address address = new ConcreteAddressBuilder()
                .setEndUser(user)
                .createAddress();
        ArrayList addresses = (ArrayList) dao.findAllByEnduser(address);
        request.setAttribute("addresses", addresses);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/ProfileView/dashboard_indirizzi.jsp"));
        dispatcher.forward(request, response);
    }
}

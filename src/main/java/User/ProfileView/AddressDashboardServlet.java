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


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        AddressDAO dao = new AddressDAO(ds);
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        EndUser user = (EndUser) session.getAttribute("user");

        ArrayList addresses = (ArrayList) dao.findAssociatedAddresses(user);
        request.setAttribute("addresses", addresses);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/ProfileView/dashboard_indirizzi.jsp"));
        dispatcher.forward(request, response);
    }
}

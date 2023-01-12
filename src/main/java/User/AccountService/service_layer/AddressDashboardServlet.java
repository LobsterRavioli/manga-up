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
import java.util.ArrayList;

@WebServlet("/AddressServletDashboardServlet")
public class AddressDashboardServlet extends HttpServlet {

    private AbstractDAOFactory factory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.JDBC);
    private AddressDAO dao = factory.getAddressDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        EndUser user = (EndUser) session.getAttribute("user");
        dao = factory.getAddressDAO();
        Address address = new AddressBuilder()
                .setEndUser(user)
                .createAddress();
        ArrayList addresses = (ArrayList) dao.findAllByEnduser(address);
        request.setAttribute("addresses", addresses);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/profile_view/dashboard_indirizzi.jsp"));
        dispatcher.forward(request, response);

    }
}
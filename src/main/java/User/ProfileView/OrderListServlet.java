package User.ProfileView;


import Order.DispatchService.Order;
import Order.DispatchService.OrderDAO;
import User.AccountService.EndUser;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

/*@WebServlet(name = "OrderListServlet", value = "/OrderListServlet")
public class OrderListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try {
        EndUser user = (EndUser) request.getSession().getAttribute("user");
        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        OrderDAO dao = new OrderDAO(ds);
        Collection order = dao.retrieveOrdersAssociatedToUsers(user);
        request.setAttribute("order", order);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/ProductsView/endUserHomepage.jsp"));
        dispatcher.forward(request, response);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}*/

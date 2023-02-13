package Order.OrderView;

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

@WebServlet(name = "OrderListServlet", value = "/OrderListServlet")
public class OrderListServlet extends HttpServlet {

    private OrderDAO orderDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EndUser user = (EndUser) request.getSession().getAttribute("user");

        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");

        if(orderDAO == null)
            orderDAO = new OrderDAO(ds);

        try
        {
            Collection order = orderDAO.retrieveOrdersAssociatedToUsers(user);
            request.setAttribute("orders", order);

            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/OrderView/order_dashboard_enduser.jsp"));
            dispatcher.forward(request, response);

        } catch (SQLException e) {

            response.setStatus(400);
            getServletContext().getRequestDispatcher(response.encodeURL("/error_page.jsp")).forward(request, response);
        }
    }
    public void setOrderDAO(OrderDAO orderDAO)
    {
        this.orderDAO = orderDAO;
    }
}

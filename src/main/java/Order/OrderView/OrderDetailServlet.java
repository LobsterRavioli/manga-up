package Order.OrderView;

import Order.DispatchService.*;
import User.AccountService.EndUser;
import User.AccountService.EndUserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "OrderDetailServlet", value = "/OrderDetailServlet")
public class OrderDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        EndUser user = (EndUser) request.getSession().getAttribute("user");
        int orderID = Integer.parseInt(request.getParameter("order_id"));
        Order order = new Order();
        order.setId(orderID);
        OrderRowDAO rowDAO = new OrderRowDAO(ds);
        OrderDAO orderDAO = new OrderDAO(ds);
        ManagedOrderDAO managedOrderDAO = new ManagedOrderDAO(ds);
        try {
            ManagedOrder managed = managedOrderDAO.retrieveByOrder(orderID);
            System.out.println(managed);
            Collection orderRows = rowDAO.retrieveOrderRowAssociatedToOrder(order);
            order = orderDAO.retrieve(orderID);
            request.setAttribute("order", order);
            request.setAttribute("managed", managed);
            request.setAttribute("orderRows", orderRows);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/OrderView/order_detail_dashboard_enduser.jsp"));
            dispatcher.forward(request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/OrderView/order_dashboard_enduser.jsp"));
        dispatcher.forward(request, response);
    }
}

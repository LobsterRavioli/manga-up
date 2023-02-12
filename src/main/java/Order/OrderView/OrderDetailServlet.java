package Order.OrderView;

import Order.DispatchService.*;
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

    private OrderRowDAO rowDAO;
    private OrderDAO orderDAO;
    private ManagedOrderDAO managedOrderDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        // EndUser user = (EndUser) request.getSession().getAttribute("user");
        int orderID = Integer.parseInt(request.getParameter("order_id"));

        Order order = new Order();
        order.setId(orderID);

        if(rowDAO == null)
            rowDAO = new OrderRowDAO(ds);

        if(orderDAO == null)
            orderDAO = new OrderDAO(ds);

        if(managedOrderDAO == null)
            managedOrderDAO = new ManagedOrderDAO(ds);

        try {
            ManagedOrder managed = managedOrderDAO.retrieveByOrder(orderID);

            // System.out.println(managed);

            Collection orderRows = rowDAO.retrieveOrderRowAssociatedToOrder(order);

            order = orderDAO.retrieve(orderID);
            request.setAttribute("order", order);
            request.setAttribute("managed", managed);
            request.setAttribute("orderRows", orderRows);

            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/OrderView/order_detail_dashboard_enduser.jsp"));
            dispatcher.forward(request, response);

            return;

        } catch (Exception e) {

            // e.printStackTrace();
            response.setStatus(400);
            //getServletContext().getRequestDispatcher(response.encodeURL("/error_page.jsp")).forward(request, response);
        }

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/OrderView/order_dashboard_enduser.jsp"));
        dispatcher.forward(request, response);
    }

    public void setRowDAO(OrderRowDAO rowDAO) {
        this.rowDAO = rowDAO;
    }

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public void setManagedOrderDAO(ManagedOrderDAO managedOrderDAO) {
        this.managedOrderDAO = managedOrderDAO;
    }
}

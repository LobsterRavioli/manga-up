package Order.DispatchService.service_layer;

import Order.DispatchService.beans.Order;
import Order.DispatchService.dao_layer.implementations.ManagedOrderDAOImp;
import Order.DispatchService.dao_layer.implementations.OrderDAOImp;
import Order.DispatchService.dao_layer.interfaces.ManagedOrderDAO;
import Order.DispatchService.dao_layer.interfaces.OrderDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "orderServlet", value = "/orderServlet")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        OrderDAO model = new OrderDAOImp(ds);

        String criteria = request.getParameter("sort");

        try
        {
            request.removeAttribute("orders");
            request.setAttribute("orders", model.doRetriveAll(criteria));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
        }

        getServletContext().getRequestDispatcher("/order_view/order_list.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

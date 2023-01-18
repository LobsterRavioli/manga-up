package Order.DispatchService.service_layer;

import Order.DispatchService.beans.Order;
import Order.DispatchService.dao_layer.implementations.ManagedOrderDAOImp;
import Order.DispatchService.dao_layer.implementations.OrderDAOImp;
import Order.DispatchService.dao_layer.interfaces.ManagedOrderDAO;
import Order.DispatchService.dao_layer.interfaces.OrderDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "manageServlet", value = "/manageServlet")
public class ManagedOrderServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String ord_id = request.getParameter("manage");

        if(ord_id != null)
        {
            getServletContext().getRequestDispatcher("/order_view/manage_order.jsp").forward(request, response);
        }
        else
        {
            response.sendRedirect(request.getServletContext().getContextPath()+"/orderServlet");
            return;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

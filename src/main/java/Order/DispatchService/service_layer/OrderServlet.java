package Order.DispatchService.service_layer;

import Order.DispatchService.dao_layer.implementations.OrderDAOImp;
import Order.DispatchService.dao_layer.interfaces.OrderDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/orderServlet")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        OrderDAO model = new OrderDAOImp(ds);

        try
        {
            request.setAttribute("orders", model.doRetriveAll(null));
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

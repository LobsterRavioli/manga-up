package Order.OrderView;

import Order.DispatchService.OrderDAO;

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

        // RECUPERO LO USERNAME DELL'ORDER MANAGER
        String orderManagerName = (String) request.getSession().getAttribute("managerName");

        if(orderManagerName == null)
            response.sendRedirect(getServletContext().getContextPath()+"/LoginManager");

        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        OrderDAO model = new OrderDAO(ds);

        String criteria = request.getParameter("sort");

        try
        {
            request.removeAttribute("orders");
            request.setAttribute("orders", model.doRetrieveAllForSpecificUser(orderManagerName, criteria));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
        }

        getServletContext().getRequestDispatcher("/OrderView/order_list.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

package Order.OrderView;

import Order.DispatchService.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "manageServlet", value = "/manageServlet")
public class ManagedOrderServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        ManagedOrderDAO managedOrderDAO = new ManagedOrderDAO(ds);
        OrderDAO orderDAO = new OrderDAO(ds);
        ToManageDAO toManageDAO = new ToManageDAO(ds);

        String action = request.getParameter("action");
        String ord_id = request.getParameter("manage"); // id dell'ordine da gestire

        if (action == null) {

            if (ord_id != null) {
                getServletContext().getRequestDispatcher("/OrderView/manage_order.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getServletContext().getContextPath() + "/orderServlet");
                return;
            }
        }
        else
        {
            try {
                if (action.equalsIgnoreCase("insert")) {
                    String userName = (String) request.getSession().getAttribute("managerName");
                    int orderId = Integer.parseInt(request.getParameter("orderID"));
                    Date deliveryDate = Date.valueOf(request.getParameter("deliveryDate"));
                    String trackingNumber = request.getParameter("trackingNumber");
                    String courierName = request.getParameter("courier");
                    Date shipmentDate = Date.valueOf(request.getParameter("shipmentDate"));

                    /*
                    * Controllare i dati ricevuti dal form
                    */

                    ManagedOrder managed = new ManagedOrder();

                    managed.setUserName(userName);
                    managed.setId(orderId);
                    managed.setDeliveryDate(deliveryDate);
                    managed.setTrackNumber(trackingNumber);
                    managed.setCourierName(courierName);
                    managed.setShipmentDate(shipmentDate);

                    managed.setState(Order.SENT);
                    orderDAO.update(managed); // modifico lo stato dell'ordine
                    managedOrderDAO.create(managed); // aggiungo l'ordine alla tabbella degli ordini gestiti

                    ToManage toManage = new ToManage();
                    toManage.setUserName(userName);
                    toManage.setOrderId(orderId);
                    toManageDAO.delete(toManage); // elimino l'ordine gestito dalla lista degli ordini da gestire

                    response.sendRedirect(request.getServletContext().getContextPath() + "/OrderView/order_list.jsp");
                    // redirect alla pagina degli ordini da gestire
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

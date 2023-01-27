package Order.OrderView;

import Order.DispatchService.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.HashSet;

@WebServlet(name = "manageServlet", value = "/manageServlet")
public class ManagedOrderServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        ManagedOrderDAO managedOrderDAO = new ManagedOrderDAO(ds);
        OrderDAO orderDAO = new OrderDAO(ds);
        ToManageDAO toManageDAO = new ToManageDAO(ds);

        String action = request.getParameter("action");
        String ord_id = request.getParameter("manage");
        String ord_date = request.getParameter("ord_date");

        HashSet<String> couriers = new HashSet<>();
        couriers.add("BRT");
        couriers.add("DHL");
        couriers.add("SDA");
        couriers.add("GLS");
        couriers.add("UPS");
        couriers.add("TNT");
        couriers.add("Nexive");
        couriers.add("Fercam");
        request.getSession().setAttribute("couriers", couriers);

        if (action == null) {

            if (ord_id != null) {

                request.getSession().setAttribute("s_ordID", ord_id);
                request.getSession().setAttribute("s_ordDate", ord_date);

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

                    ManagedOrder managed = new ManagedOrder();

                    managed.setUserName(userName);
                    managed.setId(orderId);
                    managed.setDeliveryDate(deliveryDate);
                    managed.setTrackNumber(trackingNumber);
                    managed.setCourierName(courierName);
                    managed.setShipmentDate(shipmentDate);

                    managedOrderDAO.create(managed); // aggiungo l'ordine alla tabbella degli ordini gestiti
                    managed.setState(Order.SENT);
                    orderDAO.update(managed); // modifico lo stato dell'ordine

                    ToManage toManage = new ToManage();
                    toManage.setUserName(userName);
                    toManage.setOrderId(orderId);
                    toManageDAO.delete(toManage); // elimino l'ordine gestito dalla lista degli ordini da gestire
                    response.sendRedirect(getServletContext().getContextPath()+"/OrderView/order_list.jsp");
                }
            }
            catch (SQLIntegrityConstraintViolationException e)
            {
                e.printStackTrace();
                HttpSession session = request.getSession();
                session.setAttribute("errorMessage", e.getMessage());
                String id = (String)session.getAttribute("s_ordID");
                String date = (String)session.getAttribute("s_ordDate");
                response.sendRedirect(getServletContext().getContextPath()+"/OrderView/manage_order.jsp?manage="+id+"&ord_date="+date);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

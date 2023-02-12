package Order.OrderView;

import Order.DispatchService.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashSet;

@WebServlet(name = "manageServlet", value = "/manageServlet")
public class ManagedOrderServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;

    private OrderSubmissionFacade orderSubmissionFacade;
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");

        if(orderSubmissionFacade == null)
            orderSubmissionFacade = (OrderSubmissionFacade) this.getServletContext().getAttribute(OrderSubmissionFacade.ORDER_SUBMISSION_FACADE);

        String action = request.getParameter("action");
        String ord_id = request.getParameter("manage");
        String ord_date = request.getParameter("ord_date");

        /*
         * L'insieme di tutti i corrieri disponibili nel sistema (non abbiamo una tabella, quindi li istanzio)
         */
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
                    Date shipmentDate = Date.valueOf(request.getParameter("shipmentDate"));
                    String trackingNumber = request.getParameter("trackingNumber");
                    String courierName = request.getParameter("courier");
                    Date deliveryDate = Date.valueOf(request.getParameter("deliveryDate"));

                    // Costruisco l'ordine gestito prelevando i dati che mi sono arrivati dal form
                    ManagedOrder managed = new ManagedOrder();

                    managed.setUserName(userName);
                    managed.setId(orderId);
                    managed.setShipmentDate(shipmentDate);
                    managed.setTrackNumber(trackingNumber);
                    managed.setCourierName(courierName);
                    managed.setDeliveryDate(deliveryDate);


                    // invoco il facade che utilizza i DAO per mantenere la coerenza delle info nel DB
                    orderSubmissionFacade.executeTask(managed);

                    response.sendRedirect(getServletContext().getContextPath()+"/OrderView/order_list.jsp");
                }
            }
            catch (SQLIntegrityConstraintViolationException e)
            {
                response.setStatus(400);

                // e.printStackTrace();
                HttpSession session = request.getSession();
                session.setAttribute("errorMessage", e.getMessage());
                String id = (String)session.getAttribute("s_ordID");
                String date = (String)session.getAttribute("s_ordDate");
                response.sendRedirect(getServletContext().getContextPath()+"/OrderView/manage_order.jsp?manage="+id+"&ord_date="+date);
            }
            catch (SQLException e) {

                // e.printStackTrace();
                response.setStatus(400);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void setOrderSubmissionFacade(OrderSubmissionFacade orderSubmissionFacade) {

        this.orderSubmissionFacade = orderSubmissionFacade;
    }
}

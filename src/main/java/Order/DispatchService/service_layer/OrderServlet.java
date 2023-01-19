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

@WebServlet(name = "orderServlet", value = "/orderServlet")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // RECUPERO L'ID DELL'ORDER MANAGER
        Integer orderManagerId = (Integer)request.getSession().getAttribute("managerID");

        //CODICE TEMPORANEO TANTO PER TESTARE
        if(orderManagerId == null)
            orderManagerId = 4; // recupero gli ordini del gestore avebte ID = 4
        // CI SERVE UNA QUERY CHE POSSA RECUPERARE GLI ID DEI MANAGER CHE SIANO SOLO GESTORI DEGLI ORDINI

        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        OrderDAO model = new OrderDAOImp(ds);

        String criteria = request.getParameter("sort");

        try
        {
            request.removeAttribute("orders");
            request.setAttribute("orders", model.doRetrieveAllForSpecificUser(orderManagerId, criteria));
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

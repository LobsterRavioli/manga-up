package Order.OrderView;

import Order.DispatchService.OrderRowDAO;
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

@WebServlet(name = "OrderListServlet", value = "/OrderListServlet")
public class OrderDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        EndUser user = (EndUser) request.getSession().getAttribute("user");
        int orderID = Integer.parseInt(request.getParameter("order_id"));


        OrderRowDAO dao = new OrderRowDAO(ds);

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/ProductsView/order_dashboard_enduser.jsp"));
        dispatcher.forward(request, response);
    }
}

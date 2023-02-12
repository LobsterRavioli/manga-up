package unit.servlets;

import Merchandising.MerchandiseService.Collection;
import Order.DispatchService.*;
import Order.OrderView.OrderDetailServlet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.verify;

public class OrderDetailServletTest {

    private OrderDAO orderDAO;
    private OrderRowDAO rowDAO;
    private ManagedOrderDAO managedOrderDAO;

    private HttpServletRequest request;

    private HttpServletResponse response;
    private HttpSession session;

    private OrderDetailServlet spy;

    @BeforeEach
    void setUp() {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession(false)).thenReturn(session);
        spy = Mockito.spy(new OrderDetailServlet());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
    }

    @AfterEach
    void tearDown() {
        request = null;
        response = null;
    }

    @Test
    void fail() throws SQLException, ServletException, IOException {

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/OrderView/order_dashboard_enduser.jsp"))).thenReturn(Mockito.mock(RequestDispatcher.class));

        Mockito.when(request.getParameter("order_id")).thenReturn("1");

        rowDAO = Mockito.mock(OrderRowDAO.class);
        Mockito.doAnswer(invocation -> {

            throw new Exception();

        }).when(rowDAO).retrieveOrderRowAssociatedToOrder(Mockito.any(Order.class));

        spy.setRowDAO(rowDAO);
        spy.doPost(request, response);

        Mockito.verify(response).setStatus(400);
        verify(context).getRequestDispatcher(response.encodeURL("/OrderView/order_dashboard_enduser.jsp"));
    }

    @Test
    void success() throws SQLException, ServletException, IOException {

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/OrderView/order_detail_dashboard_enduser.jsp"))).thenReturn(Mockito.mock(RequestDispatcher.class));

        Mockito.when(request.getParameter("order_id")).thenReturn("1");

        int orderID = Integer.parseInt(request.getParameter("order_id"));

        Order order = Mockito.mock(Order.class);
        order.setId(orderID);

        managedOrderDAO = Mockito.mock(ManagedOrderDAO.class);
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(managedOrderDAO).retrieveByOrder(orderID);


        rowDAO = Mockito.mock(OrderRowDAO.class);
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(rowDAO).retrieveOrderRowAssociatedToOrder(order);


        orderDAO = Mockito.mock(OrderDAO.class);
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(orderDAO).retrieve(orderID);

        spy.setOrderDAO(orderDAO);
        spy.setRowDAO(rowDAO);
        spy.setManagedOrderDAO(managedOrderDAO);

        spy.doPost(request, response);

        verify(context).getRequestDispatcher(response.encodeURL("/OrderView/order_detail_dashboard_enduser.jsp"));
    }
}

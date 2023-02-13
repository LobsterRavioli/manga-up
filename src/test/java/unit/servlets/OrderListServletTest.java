package unit.servlets;

import Order.DispatchService.OrderDAO;
import Order.OrderView.OrderListServlet;
import User.AccountService.EndUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

public class OrderListServletTest {

    private OrderDAO orderDAO;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    private OrderListServlet spy;

    @BeforeEach
    void setUp() {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession(false)).thenReturn(session);
        spy = Mockito.spy(new OrderListServlet());
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
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/error_page.jsp"))).thenReturn(Mockito.mock(RequestDispatcher.class));


        Mockito.when(session.getAttribute("user")).thenReturn(new EndUser());

        orderDAO = Mockito.mock(OrderDAO.class);
        Mockito.doAnswer(invocation -> {

            throw new SQLException();

        }).when(orderDAO).retrieveOrdersAssociatedToUsers(Mockito.any(EndUser.class));

        spy.setOrderDAO(orderDAO);
        spy.doPost(request, response);

        verify(response).setStatus(400);
        verify(context).getRequestDispatcher(response.encodeURL("/error_page.jsp"));
    }

    @Test
    void success() throws SQLException, ServletException, IOException {

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/OrderView/order_dashboard_enduser.jsp"))).thenReturn(Mockito.mock(RequestDispatcher.class));

        Mockito.when(session.getAttribute("user")).thenReturn(new EndUser());

        orderDAO = Mockito.mock(OrderDAO.class);
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(orderDAO).retrieveOrdersAssociatedToUsers(Mockito.any(EndUser.class));

        spy.setOrderDAO(orderDAO);
        spy.doPost(request, response);

        verify(context).getRequestDispatcher(response.encodeURL("/OrderView/order_dashboard_enduser.jsp"));
    }
}


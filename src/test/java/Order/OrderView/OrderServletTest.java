package Order.OrderView;

import Order.DispatchService.OrderDAO;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


class OrderServletTest {

    private OrderDAO orderDAO;

    private ServletContext context;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private OrderServlet spy;

    @BeforeEach
    void setUp() {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession(false)).thenReturn(session);
        spy = Mockito.spy(new OrderServlet());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
        Mockito.when(spy.getServletContext()).thenReturn(context);

    }

    @AfterEach
    void tearDown() {
        request=null;
        response=null;
    }

    @Test
    void fail() throws SQLException, ServletException, IOException {

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/error_page.jsp"))).thenReturn(rD);

        Mockito.when(session.getAttribute("managerName")).thenReturn("Giovanni");
        Mockito.when(request.getAttribute("orders")).thenReturn(null);

        String orderManager = (String) request.getSession().getAttribute("managerName");

        orderDAO = Mockito.mock(OrderDAO.class);
        Mockito.doAnswer(invocation -> {
            throw new SQLException();
        }).when(orderDAO).doRetrieveAllForSpecificUser(orderManager, null);

        spy.setOrderDAO(orderDAO);
        spy.doGet(request, response);

        Mockito.verify(request).setAttribute("error", new SQLException().getMessage());
        Mockito.verify(response).setStatus(400);
        verify(context).getRequestDispatcher(response.encodeURL("/error_page.jsp"));
    }

    @Test
    void success() throws SQLException, ServletException, IOException {

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/error_page.jsp"))).thenReturn(rD);

        Mockito.when(session.getAttribute("managerName")).thenReturn("Giovanni");

        String orderManager = (String) request.getSession().getAttribute("managerName");

        orderDAO = Mockito.mock(OrderDAO.class);
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(orderDAO).doRetrieveAllForSpecificUser(orderManager, null);

        spy.setOrderDAO(orderDAO);
        spy.doGet(request, response);

        verify(context).getRequestDispatcher(response.encodeURL(request.getContextPath() + "/OrderView/order_list.jsp"));
    }
}
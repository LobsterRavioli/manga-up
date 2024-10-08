package unit.servlets;

import Order.DispatchService.ManagedOrder;
import Order.DispatchService.Order;
import Order.DispatchService.OrderDAO;
import Order.DispatchService.OrderSubmissionFacade;
import Order.OrderView.ManagedOrderServlet;
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
import java.sql.SQLIntegrityConstraintViolationException;

class ManagedOrderServletTest {

    private OrderDAO orderDAO;
    private OrderSubmissionFacade orderSubmissionFacade;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private ManagedOrderServlet spy;

    @BeforeEach
    void setUp() {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession(false)).thenReturn(session);
        spy = Mockito.spy(new ManagedOrderServlet());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
        Mockito.when(spy.getServletContext()).thenReturn(context);
    }

    @AfterEach
    void tearDown() {
        request = null;
        response = null;
    }

    @Test
    void failForDuplicateTrackNumber() throws SQLException, ServletException, IOException {

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher("/OrderView/manage_order.jsp")).thenReturn(rD);

        Mockito.when(request.getParameter("action")).thenReturn("insert");

        Mockito.when(session.getAttribute("managerName")).thenReturn(" ");
        Mockito.when(request.getParameter("orderID")).thenReturn("0");
        Mockito.when(request.getParameter("shipmentDate")).thenReturn("2012-03-03");
        Mockito.when(request.getParameter("trackingNumber")).thenReturn(" ");
        Mockito.when(request.getParameter("courier")).thenReturn(" ");
        Mockito.when(request.getParameter("deliveryDate")).thenReturn("2012-05-03");

        orderDAO = Mockito.mock(OrderDAO.class);
        Mockito.doAnswer(invocation -> {

            return new Order();

        }).when(orderDAO).retrieve(Mockito.any(Integer.class));

        spy.setOrderDAO(orderDAO);


        Mockito.when(session.getAttribute("s_ordID")).thenReturn("3");
        Mockito.when(session.getAttribute("s_ordDate")).thenReturn("2012-03-03");


        orderSubmissionFacade = Mockito.mock(OrderSubmissionFacade.class);
        Mockito.doAnswer(invocation -> {

            throw new SQLIntegrityConstraintViolationException();

        }).when(orderSubmissionFacade).executeTask(Mockito.any(ManagedOrder.class));

        spy.setOrderSubmissionFacade(orderSubmissionFacade);
        spy.doGet(request, response);

        Mockito.verify(response).setStatus(400);
        Mockito.verify(session).setAttribute("errorMessage", new SQLIntegrityConstraintViolationException().getMessage());
        Mockito.verify(context).getRequestDispatcher(context.getContextPath()+"/OrderView/manage_order.jsp?manage=3&ord_date=2012-03-03");
    }

    @Test
    void success() throws SQLException, IOException, ServletException {

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher("/OrderView/order_list.jsp")).thenReturn(rD);

        Mockito.when(request.getParameter("action")).thenReturn("insert");

        Mockito.when(session.getAttribute("managerName")).thenReturn(" ");
        Mockito.when(request.getParameter("orderID")).thenReturn("0");
        Mockito.when(request.getParameter("shipmentDate")).thenReturn("2012-03-03");
        Mockito.when(request.getParameter("trackingNumber")).thenReturn(" ");
        Mockito.when(request.getParameter("courier")).thenReturn(" ");
        Mockito.when(request.getParameter("deliveryDate")).thenReturn("2012-05-03");


        orderDAO = Mockito.mock(OrderDAO.class);
        Mockito.doAnswer(invocation -> {

            return new Order();

        }).when(orderDAO).retrieve(Mockito.any(Integer.class));

        spy.setOrderDAO(orderDAO);

        orderSubmissionFacade = Mockito.mock(OrderSubmissionFacade.class);
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(orderSubmissionFacade).executeTask(Mockito.any(ManagedOrder.class));

        spy.setOrderSubmissionFacade(orderSubmissionFacade);
        spy.doGet(request, response);

        Mockito.verify(context).getRequestDispatcher(context.getContextPath()+"/OrderView/order_list.jsp");
    }
}
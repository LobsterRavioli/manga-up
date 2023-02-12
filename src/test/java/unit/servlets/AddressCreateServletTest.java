package unit.servlets;

import Cart.CartView.cartAddServlet;
import Cart.CheckoutService.Cart;
import Cart.CheckoutService.CartDAO;
import Merchandising.MerchandiseService.Manga;
import User.AccountService.Address;
import User.AccountService.AddressDAO;
import User.AccountService.EndUser;
import User.ProfileView.AddressCreateServlet;
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
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AddressCreateServletTest {
    private AddressDAO addressDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    private AddressCreateServlet spy;

    @BeforeEach
    void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession(false)).thenReturn(session);
        spy = Mockito.spy(new AddressCreateServlet());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
        Mockito.when(spy.getServletContext()).thenReturn(context);
    }


    @Test
    void fail() throws ServletException, IOException, SQLException {
        Mockito.when(request.getAttribute("street")).thenReturn("Via Roma");
        Mockito.when(request.getParameter("city")).thenReturn("Roma");
        Mockito.when(request.getParameter("country")).thenReturn("Italia");
        Mockito.when(request.getParameter("postal_code")).thenReturn("80040");
        Mockito.when(request.getParameter("region")).thenReturn("Lazio");
        Mockito.when(request.getParameter("phone_number")).thenReturn("+393662968496");
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/error_page.jsp"))).thenReturn(rD);
        addressDAO = mock(AddressDAO.class);
        Mockito.doAnswer(invocation -> {
            throw new Exception();
        }).when(addressDAO).create(any(Address.class));
        spy.setAddressDAO(addressDAO);
        spy.doPost(request, response);
        verify(response).setStatus(500);
        verify(context).getRequestDispatcher(response.encodeURL("/error_page.jsp"));
    }

    @Test
    void success() throws ServletException, IOException, SQLException {

        Mockito.when(request.getAttribute("street")).thenReturn("Via Roma");
        Mockito.when(request.getParameter("city")).thenReturn("Roma");
        Mockito.when(request.getParameter("country")).thenReturn("Italia");
        Mockito.when(request.getParameter("postal_code")).thenReturn("80040");
        Mockito.when(request.getParameter("region")).thenReturn("Lazio");
        Mockito.when(request.getParameter("phone_number")).thenReturn("+393662968496");
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/AddressDashboardServlet"))).thenReturn(rD);

        addressDAO = mock(AddressDAO.class);
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(addressDAO).create(any(Address.class));

        spy.setAddressDAO(addressDAO);
        spy.doPost(request, response);
        verify(context).getRequestDispatcher(response.encodeURL("/AddressDashboardServlet"));

    }

    @Test
    void SessionInvalid() throws ServletException, IOException, SQLException {
        Mockito.when(request.getSession(false)).thenReturn(null);
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/error_page.jsp"))).thenReturn(rD);
        addressDAO = mock(AddressDAO.class);
        Mockito.doAnswer(invocation -> {
            throw new Exception();
        }).when(addressDAO).create(any(Address.class));
        spy.setAddressDAO(addressDAO);
        spy.doPost(request, response);
        verify(response).setStatus(500);
        verify(context).getRequestDispatcher(response.encodeURL("/error_page.jsp"));
    }

    @Test
    void SessionInvalidParameter() throws ServletException, IOException, SQLException {
        Mockito.when(session.getAttribute("user")).thenReturn(null);
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/error_page.jsp"))).thenReturn(rD);
        addressDAO = mock(AddressDAO.class);
        Mockito.doAnswer(invocation -> {
            throw new Exception();
        }).when(addressDAO).create(any(Address.class));
        spy.setAddressDAO(addressDAO);
        spy.doPost(request, response);
        verify(response).setStatus(500);
        verify(context).getRequestDispatcher(response.encodeURL("/error_page.jsp"));
    }


}
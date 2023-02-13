package unit.servlets;

import User.AccountService.Address;
import User.AccountService.AddressDAO;
import User.ProfileView.AddressCreateServlet;
import User.ProfileView.AddressDeleteServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import utils.DAOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AddressDeleteServletTest {
    private AddressDAO addressDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    private AddressDeleteServlet spy;

    @BeforeEach
    void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession(false)).thenReturn(session);
        spy = Mockito.spy(new AddressDeleteServlet());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
        Mockito.when(spy.getServletContext()).thenReturn(context);
    }


    @Test
    void fail() throws ServletException, IOException {

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/error_page.jsp"))).thenReturn(rD);

        Mockito.when(request.getParameter("address_id")).thenReturn("1");
        addressDAO = mock(AddressDAO.class);
        Mockito.doAnswer(invocation -> {
            throw new DAOException("Error");
        }).when(addressDAO).delete(any(Address.class));
        spy.setAddressDAO(addressDAO);
        spy.doPost(request, response);
        verify(response).setStatus(500);
        verify(context).getRequestDispatcher(response.encodeURL("/error_page.jsp"));
    }

    @Test
    void success() throws ServletException, IOException {
        Mockito.when(request.getParameter("address_id")).thenReturn("1");
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/AddressDashboardServlet"))).thenReturn(rD);
        addressDAO = mock(AddressDAO.class);
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(addressDAO).delete(any(Address.class));

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
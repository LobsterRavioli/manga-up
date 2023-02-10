package unit.servlets;

import User.AccountService.Address;
import User.AccountService.AddressDAO;
import User.AccountService.EndUser;
import User.ProfileView.AddressCreateServlet;
import User.ProfileView.AddressDashboardServlet;
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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AddressDashboardServletTest {

    private AddressDAO addressDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    private AddressDashboardServlet spy;

    @BeforeEach
    void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession(false)).thenReturn(session);
        spy = Mockito.spy(new AddressDashboardServlet());
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
        Mockito.when(session.getAttribute("user")).thenReturn(new EndUser(1));
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/ProfileView/dashboard_indirizzi.jsp"))).thenReturn(rD);
        addressDAO = mock(AddressDAO.class);
        ArrayList<Address> addresses = new ArrayList<Address>();
        Mockito.when(addressDAO.findAssociatedAddresses(any(EndUser.class))).thenThrow(new DAOException("Error"));
        spy.setAddressDAO(addressDAO);
        spy.doPost(request, response);
        verify(request).setAttribute("addresses", null);
        verify(response).setStatus(500);
    }

    @Test
    void success() throws ServletException, IOException {

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(session.getAttribute("user")).thenReturn(new EndUser(1));
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/ProfileView/dashboard_indirizzi.jsp"))).thenReturn(rD);
        addressDAO = mock(AddressDAO.class);
        ArrayList<Address> addresses = new ArrayList<Address>();
        addresses.add(new Address(1, new EndUser(1), "Italia", "Napoli", "Via roma 35", "80040", "+393662968496", "Campania"));
        addresses.add(new Address(1, new EndUser(1), "Italia", "Napoli", "Via filippo 35", "80040", "+393662968496", "Campania"));
        Mockito.when(addressDAO.findAssociatedAddresses(any(EndUser.class))).thenReturn(addresses);
        spy.setAddressDAO(addressDAO);
        spy.doPost(request, response);
        verify(request).setAttribute("addresses", addresses);
        verify(context).getRequestDispatcher(response.encodeURL("/ProfileView/dashboard_indirizzi.jsp"));
    }

}
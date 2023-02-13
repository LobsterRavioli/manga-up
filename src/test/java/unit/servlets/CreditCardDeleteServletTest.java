package unit.servlets;

import User.AccountService.CreditCard;
import User.AccountService.CreditCardDAO;
import User.AccountService.EndUser;
import User.ProfileView.CreditCardDashboardServlet;
import User.ProfileView.CreditCardDeleteServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import utils.Utils;

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
import static org.mockito.Mockito.*;

class CreditCardDeleteServletTest {
    private CreditCardDAO creditCardDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    private CreditCardDeleteServlet spy;

    @BeforeEach
    void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession(false)).thenReturn(session);
        spy = Mockito.spy(new CreditCardDeleteServlet());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
        Mockito.when(spy.getServletContext()).thenReturn(context);
        this.creditCardDAO = mock(CreditCardDAO.class);
    }

    @Test
    void fail() throws ServletException, IOException {
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/error_page.jsp"))).thenReturn(rD);
        Mockito.when(request.getParameter("credit_card_id")).thenReturn("1");
        Mockito.doAnswer(invocation -> {
            throw new Exception();
        }).when(creditCardDAO).delete(any(CreditCard.class));
        spy.setCreditCardDAO(creditCardDAO);
        spy.doPost(request, response);
        verify(response).setStatus(500);
        verify(context).getRequestDispatcher(response.encodeURL("/error_page.jsp"));
    }

    @Test
    void success() throws ServletException, IOException {
        Mockito.when(request.getParameter("credit_card_id")).thenReturn("1");
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/CreditCardDashboardServlet"))).thenReturn(rD);
        creditCardDAO = mock(CreditCardDAO.class);
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(creditCardDAO).delete(any(CreditCard.class));
        spy.setCreditCardDAO(creditCardDAO);
        spy.doPost(request, response);
        verify(response).sendRedirect("CreditCardDashboardServlet");
    }

    @Test
    void SessionInvalid() throws ServletException, IOException, SQLException {
        Mockito.when(request.getSession(false)).thenReturn(null);
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/error_page.jsp"))).thenReturn(rD);
        creditCardDAO = mock(CreditCardDAO.class);
        Mockito.doAnswer(invocation -> {
            throw new Exception();
        }).when(creditCardDAO).create(any(CreditCard.class));
        spy.setCreditCardDAO(creditCardDAO);
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
        creditCardDAO = mock(CreditCardDAO.class);
        Mockito.doAnswer(invocation -> {
            throw new Exception();
        }).when(creditCardDAO).create(any(CreditCard.class));
        spy.setCreditCardDAO(creditCardDAO);
        spy.doPost(request, response);
        verify(response).setStatus(500);
        verify(context).getRequestDispatcher(response.encodeURL("/error_page.jsp"));
    }

}
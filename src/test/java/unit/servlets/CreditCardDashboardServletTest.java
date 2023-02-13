package unit.servlets;

import User.AccountService.CreditCard;
import User.AccountService.CreditCardDAO;
import User.AccountService.EndUser;
import User.ProfileView.CreditCardCreateServlet;
import User.ProfileView.CreditCardDashboardServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import utils.DAOException;
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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreditCardDashboardServletTest {

    private CreditCardDAO creditCardDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    private CreditCardDashboardServlet spy;

    @BeforeEach
    void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession(false)).thenReturn(session);
        spy = Mockito.spy(new CreditCardDashboardServlet());
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
        Mockito.when(spy.getServletContext()).thenReturn(context);
        Mockito.when(session.getAttribute("user")).thenReturn(new EndUser(1));
        creditCardDAO = mock(CreditCardDAO.class);
        Mockito.when(creditCardDAO.findAssociatedCards(any(EndUser.class))).thenThrow(new DAOException("Error"));
        spy.setCreditCardDAO(creditCardDAO);
        spy.doPost(request, response);
        verify(response).setStatus(500);
        verify(context).getRequestDispatcher(response.encodeURL("/error_page.jsp"));
    }

    @Test
    void success() throws ServletException, IOException {
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(session.getAttribute("user")).thenReturn(new EndUser(1));
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/ProfileView/dashboard_carte_di_credito.jsp"))).thenReturn(rD);
        ArrayList<CreditCard> cards = new ArrayList<>();
        cards.add(new CreditCard(1, "123", new EndUser(1), "1111111111111", "80040", Utils.parseDate("2030-01-01")));
        cards.add(new CreditCard(2, "123", new EndUser(1), "1111111111111", "80040", Utils.parseDate("2030-01-01")));
        creditCardDAO = mock(CreditCardDAO.class);

        Mockito.doAnswer(invocation -> {
            return cards;
        }).when(creditCardDAO).findAssociatedCards(any(EndUser.class));

        spy.setCreditCardDAO(creditCardDAO);
        spy.doPost(request, response);
        verify(context).getRequestDispatcher(response.encodeURL("/ProfileView/dashboard_carte_di_credito.jsp"));
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
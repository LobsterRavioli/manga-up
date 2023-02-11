package unit.servlets;

import User.AccountService.CreditCard;
import User.AccountService.CreditCardDAO;
import User.ProfileView.CreditCardCreateServlet;
import User.ProfileView.CreditCardDashboardServlet;
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
        Mockito.when(session.getAttribute("card_holder")).thenReturn("Tommaso Sorrentino");
        Mockito.when(request.getParameter("card_number")).thenReturn("1111111111111");
        Mockito.when(request.getParameter("expirement_date")).thenReturn("2030-01-01");
        Mockito.when(request.getParameter("cvc")).thenReturn("80040");
        creditCardDAO = mock(CreditCardDAO.class);
        Mockito.doAnswer(invocation -> {
            throw new Exception();
        }).when(creditCardDAO).create(any(CreditCard.class));
        spy.setCreditCardDAO(creditCardDAO);
        spy.doPost(request, response);
        verify(response).setStatus(499);
    }

    @Test
    void success() throws ServletException, IOException {
        Mockito.when(session.getAttribute("card_holder")).thenReturn("Tommaso Sorrentino");
        Mockito.when(request.getParameter("card_number")).thenReturn("1111111111111");
        Mockito.when(request.getParameter("expirement_date")).thenReturn("2030-01-01");
        Mockito.when(request.getParameter("cvc")).thenReturn("123");
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/CreditCardDashboardServlet"))).thenReturn(rD);
        creditCardDAO = mock(CreditCardDAO.class);
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(creditCardDAO).create(any(CreditCard.class));

        spy.setCreditCardDAO(creditCardDAO);
        spy.doPost(request, response);
        verify(context).getRequestDispatcher(response.encodeURL("/CreditCardDashboardServlet"));
    }

}
package unit.servlets;

import Cart.CheckoutService.CartDAO;
import User.AccountService.*;
import User.ProfileView.RegistrationServlet;
import User.ProfileView.UserCreateServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class RegistrationServletTest {


    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private CartDAO cartDAO;

    private CreditCardDAO creditCardDAO;
    private UserFacadeImp userFacadeImp;

    private EndUserDAO endUserDAO;
    private RegistrationServlet servlet;


    @BeforeEach
    void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession(false)).thenReturn(session);
        servlet = Mockito.spy(new RegistrationServlet());
        Mockito.when(servlet.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
        Mockito.when(servlet.getServletContext()).thenReturn(context);
        endUserDAO = mock(EndUserDAO.class);
        cartDAO = mock(CartDAO.class);
        creditCardDAO = mock(CreditCardDAO.class);
        servlet.setCartDAO(cartDAO);
        servlet.setEndUserDAO(endUserDAO);
        servlet.setCreditCardDAO(creditCardDAO);
        userFacadeImp = mock(UserFacadeImp.class);

    }




    @Test
    void success() throws Exception {
        when(endUserDAO.existEmail(any(String.class))).thenReturn(false);
        when(creditCardDAO.existsCreditCardNumber(any(String.class))).thenReturn(false);

        when(request.getParameter("name")).thenReturn("Tommaso");
        when(request.getParameter("surname")).thenReturn("Sorrentino");
        when(request.getParameter("email")).thenReturn("tommytock99@hotmai.it");
        when(request.getParameter("phone_number")).thenReturn("+393662968496");
        when(request.getParameter("password")).thenReturn("password1!");
        when(request.getParameter("birth_date")).thenReturn("2000-01-01");

        when(request.getParameter("street")).thenReturn("Via guinceri 35");
        when(request.getParameter("city")).thenReturn("Napoli");
        when(request.getParameter("country")).thenReturn("Region");
        when(request.getParameter("postal_code")).thenReturn("80040");
        when(request.getParameter("region")).thenReturn("Campania");
        when(request.getParameter("phone_number_address")).thenReturn("+393662968496");


        when(request.getParameter("card_number")).thenReturn("1111111111111");
        when(request.getParameter("card_holder")).thenReturn("Tommaso Sorrentino");
        when(request.getParameter("expirement_date")).thenReturn("2030-01-01");
        when(request.getParameter("cvv")).thenReturn("123");
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(userFacadeImp).registration(any(EndUser.class));

        when(servlet.getServletContext().getAttribute(UserFacade.USER_FACADE)).thenReturn(userFacadeImp);

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(servlet.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/AddressDashboardServlet"))).thenReturn(rD);


        servlet.doPost(request, response);
        verify(context).getRequestDispatcher(response.encodeURL("/ProductsView/endUserHomepage.jsp"));

    }


    @Test
    void failByExistingUser() throws Exception {
        when(endUserDAO.existEmail(any(String.class))).thenReturn(true);
        when(creditCardDAO.existsCreditCardNumber(any(String.class))).thenReturn(false);

        when(request.getParameter("name")).thenReturn("Tommaso");
        when(request.getParameter("surname")).thenReturn("Sorrentino");
        when(request.getParameter("email")).thenReturn("tommytock99@hotmai.it");
        when(request.getParameter("phone_number")).thenReturn("+393662968496");
        when(request.getParameter("password")).thenReturn("password1!");
        when(request.getParameter("birth_date")).thenReturn("2000-01-01");

        when(request.getParameter("street")).thenReturn("Via guinceri 35");
        when(request.getParameter("city")).thenReturn("Napoli");
        when(request.getParameter("country")).thenReturn("Region");
        when(request.getParameter("postal_code")).thenReturn("80040");
        when(request.getParameter("region")).thenReturn("Campania");
        when(request.getParameter("phone_number_address")).thenReturn("+393662968496");


        when(request.getParameter("card_number")).thenReturn("1111111111111");
        when(request.getParameter("card_holder")).thenReturn("Tommaso Sorrentino");
        when(request.getParameter("expirement_date")).thenReturn("2030-01-01");
        when(request.getParameter("cvv")).thenReturn("123");
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(userFacadeImp).registration(any(EndUser.class));

        when(servlet.getServletContext().getAttribute(UserFacade.USER_FACADE)).thenReturn(userFacadeImp);

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(servlet.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/AddressDashboardServlet"))).thenReturn(rD);


        servlet.doPost(request, response);
        verify(context).getRequestDispatcher(response.encodeURL("/ProductsView/endUserHomepage.jsp"));
        verify(request).setAttribute("error_email_message", RegistrationServlet.EMAIL_ERROR);
    }

    @Test

    void failByExistCreditCard() throws Exception {
        when(endUserDAO.existEmail(any(String.class))).thenReturn(false);
        when(creditCardDAO.existsCreditCardNumber(any(String.class))).thenReturn(true);

        when(request.getParameter("name")).thenReturn("Tommaso");
        when(request.getParameter("surname")).thenReturn("Sorrentino");
        when(request.getParameter("email")).thenReturn("tommytock99@hotmai.it");
        when(request.getParameter("phone_number")).thenReturn("+393662968496");
        when(request.getParameter("password")).thenReturn("password1!");
        when(request.getParameter("birth_date")).thenReturn("2000-01-01");

        when(request.getParameter("street")).thenReturn("Via guinceri 35");
        when(request.getParameter("city")).thenReturn("Napoli");
        when(request.getParameter("country")).thenReturn("Region");
        when(request.getParameter("postal_code")).thenReturn("80040");
        when(request.getParameter("region")).thenReturn("Campania");
        when(request.getParameter("phone_number_address")).thenReturn("+393662968496");


        when(request.getParameter("card_number")).thenReturn("1111111111111");
        when(request.getParameter("card_holder")).thenReturn("Tommaso Sorrentino");
        when(request.getParameter("expirement_date")).thenReturn("2030-01-01");
        when(request.getParameter("cvv")).thenReturn("123");
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(userFacadeImp).registration(any(EndUser.class));

        when(servlet.getServletContext().getAttribute(UserFacade.USER_FACADE)).thenReturn(userFacadeImp);

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(servlet.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/ProfileView/registrazione_utente.jsp"))).thenReturn(rD);


        servlet.doPost(request, response);
        verify(context).getRequestDispatcher(response.encodeURL("/ProfileView/registrazione_utente.jsp"));
        verify(request).setAttribute("error_credit_card_message", RegistrationServlet.CARD_ERROR);
    }

    @Test
    void failByRegistration() throws Exception {
        when(endUserDAO.existEmail(any(String.class))).thenReturn(false);
        when(creditCardDAO.existsCreditCardNumber(any(String.class))).thenReturn(false);

        when(request.getParameter("name")).thenReturn("Tommaso");
        when(request.getParameter("surname")).thenReturn("Sorrentino");
        when(request.getParameter("email")).thenReturn("tommytock99@hotmai.it");
        when(request.getParameter("phone_number")).thenReturn("+393662968496");
        when(request.getParameter("password")).thenReturn("password1!");
        when(request.getParameter("birth_date")).thenReturn("2000-01-01");

        when(request.getParameter("street")).thenReturn("Via guinceri 35");
        when(request.getParameter("city")).thenReturn("Napoli");
        when(request.getParameter("country")).thenReturn("Region");
        when(request.getParameter("postal_code")).thenReturn("80040");
        when(request.getParameter("region")).thenReturn("Campania");
        when(request.getParameter("phone_number_address")).thenReturn("+393662968496");


        when(request.getParameter("card_number")).thenReturn("1111111111111");
        when(request.getParameter("card_holder")).thenReturn("Tommaso Sorrentino");
        when(request.getParameter("expirement_date")).thenReturn("2030-01-01");
        when(request.getParameter("cvv")).thenReturn("123");
        Mockito.doAnswer(invocation -> {
            throw new Exception();
        }).when(userFacadeImp).registration(any(EndUser.class));

        when(servlet.getServletContext().getAttribute(UserFacade.USER_FACADE)).thenReturn(userFacadeImp);

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(servlet.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/error_page.jsp"))).thenReturn(rD);


        servlet.doPost(request, response);
        verify(context).getRequestDispatcher(response.encodeURL("/error_page.jsp"));
    }

}
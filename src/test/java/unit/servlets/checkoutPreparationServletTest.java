package unit.servlets;

import Cart.CartView.cartUpdateItemServlet;
import Cart.CartView.checkoutPreparationServlet;
import Cart.CheckoutService.CartDAO;
import User.AccountService.*;
import com.beust.ah.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class checkoutPreparationServletTest {

    private checkoutPreparationServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private checkoutPreparationServlet spy;
    private AddressDAO addressDAO;
    private CreditCardDAO creditCardDAO;

    ServletContext context;
    @BeforeEach
    void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession(false)).thenReturn(session);
        spy = Mockito.spy(new checkoutPreparationServlet());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        context = Mockito.mock(ServletContext.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
        Mockito.when(spy.getServletContext()).thenReturn(context);
    }

    @Test
    void nonExistentCreditCardTest() throws Exception{
        Mockito.when(session.getAttribute("user")).thenReturn(new EndUser(3));
        addressDAO = mock(AddressDAO.class);
        creditCardDAO = mock(CreditCardDAO.class);
        Mockito.doAnswer(invocation -> {
            return new ArrayList<CreditCard>();
        }).when(creditCardDAO).findAssociatedCards(any(EndUser.class));

        Mockito.doAnswer(invocation -> {
            ArrayList<Address> add = new ArrayList<Address>();
            add.add(new Address());
            return add;
        }).when(addressDAO).findAssociatedAddresses(any(EndUser.class));


        spy.setDaoADD(addressDAO);
        spy.setDaoCC(creditCardDAO);

        RequestDispatcher rd = mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher("/CartView/cart.jsp")).thenReturn(rd);
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(rd).forward(request,response);


        spy.doPost(request,response);


        verify(request).setAttribute("errorCard","nessuna carta di pagamento associata all'account");
        verify(request,never()).setAttribute("errorAddress","nessun indirizzo associato all'account");
        verify(context,never()).getRequestDispatcher("/CartView/checkout.jsp");
    }

    @Test
    void nonExistentAddressTest() throws Exception{
        Mockito.when(session.getAttribute("user")).thenReturn(new EndUser(3));
        addressDAO = mock(AddressDAO.class);
        creditCardDAO = mock(CreditCardDAO.class);
        Mockito.doAnswer(invocation -> {
            ArrayList<CreditCard> add = new ArrayList<CreditCard>();
            add.add(new CreditCard());
            return add;
        }).when(creditCardDAO).findAssociatedCards(any(EndUser.class));

        Mockito.doAnswer(invocation -> {
            return new ArrayList<Address>();
        }).when(addressDAO).findAssociatedAddresses(any(EndUser.class));


        spy.setDaoADD(addressDAO);
        spy.setDaoCC(creditCardDAO);

        RequestDispatcher rd = mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher("/CartView/cart.jsp")).thenReturn(rd);
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(rd).forward(request,response);


        spy.doPost(request,response);


        verify(request,never()).setAttribute("errorCard","nessuna carta di pagamento associata all'account");
        verify(request).setAttribute("errorAddress","nessun indirizzo associato all'account");
        verify(context,never()).getRequestDispatcher("/CartView/checkout.jsp");
    }

    @Test
    void nonExistentCardAndAddressTest() throws Exception{
        Mockito.when(session.getAttribute("user")).thenReturn(new EndUser(3));
        addressDAO = mock(AddressDAO.class);
        creditCardDAO = mock(CreditCardDAO.class);
        Mockito.doAnswer(invocation -> {
            return new ArrayList<CreditCard>();
        }).when(creditCardDAO).findAssociatedCards(any(EndUser.class));

        Mockito.doAnswer(invocation -> {
            return new ArrayList<Address>();
        }).when(addressDAO).findAssociatedAddresses(any(EndUser.class));


        spy.setDaoADD(addressDAO);
        spy.setDaoCC(creditCardDAO);

        RequestDispatcher rd = mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher("/CartView/cart.jsp")).thenReturn(rd);
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(rd).forward(request,response);


        spy.doPost(request,response);


        verify(request).setAttribute("errorCard","nessuna carta di pagamento associata all'account");
        verify(request).setAttribute("errorAddress","nessun indirizzo associato all'account");
        verify(context,never()).getRequestDispatcher("/CartView/checkout.jsp");
    }


    @Test
    void successfulRedirection() throws Exception{
        Mockito.when(session.getAttribute("user")).thenReturn(new EndUser(3));
        addressDAO = mock(AddressDAO.class);
        creditCardDAO = mock(CreditCardDAO.class);
        Mockito.doAnswer(invocation -> {
            ArrayList<CreditCard> add = new ArrayList<CreditCard>();
            add.add(new CreditCard());
            return add;
        }).when(creditCardDAO).findAssociatedCards(any(EndUser.class));

        Mockito.doAnswer(invocation -> {
            ArrayList<Address> add = new ArrayList<Address>();
            add.add(new Address());
            return add;
        }).when(addressDAO).findAssociatedAddresses(any(EndUser.class));


        spy.setDaoADD(addressDAO);
        spy.setDaoCC(creditCardDAO);

        RequestDispatcher rd = mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher("/CartView/checkout.jsp")).thenReturn(rd);
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(rd).forward(request,response);


        spy.doPost(request,response);


        verify(request,never()).setAttribute("errorCard","nessuna carta di pagamento associata all'account");
        verify(request,never()).setAttribute("errorAddress","nessun indirizzo associato all'account");
        verify(context).getRequestDispatcher("/CartView/checkout.jsp");
    }

}

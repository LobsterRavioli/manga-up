package unit.servlets;


import Cart.CartView.checkoutPreparationServlet;
import Cart.CartView.visualizeCartServlet;
import Cart.CheckoutService.CartDAO;
import Cart.CheckoutService.Cart;
import Merchandising.MerchandiseService.Manga;
import User.AccountService.AddressDAO;
import User.AccountService.CreditCardDAO;
import User.AccountService.EndUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class visualizeCartServletTest {

    private visualizeCartServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private visualizeCartServlet spy;
    private CartDAO cartDAO;

    ServletContext context;
    @BeforeEach
    void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession(false)).thenReturn(session);
        spy = Mockito.spy(new visualizeCartServlet());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        context = Mockito.mock(ServletContext.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
        Mockito.when(spy.getServletContext()).thenReturn(context);
    }


    @Test
    void atLeastOneTest() throws Exception{
        EndUser e = new EndUser(3);
        Mockito.when(session.getAttribute("user")).thenReturn(e);
        cartDAO = mock(CartDAO.class);
        Mockito.doAnswer(invocation -> {
            HashMap<Manga,Integer> hash = new HashMap<Manga,Integer>();
            hash.put(new Manga(20),1);
            return new Cart(hash);
        }).when(cartDAO).retrieveByUser(any(EndUser.class));

        spy.setDao(cartDAO);
        spy.doPost(request,response);
        verify(response).sendRedirect(request.getContextPath()+"/CartView/cart.jsp");


    }

    @Test
    void noProductTest() throws Exception{

        EndUser e = new EndUser(3);
        Mockito.when(session.getAttribute("user")).thenReturn(e);
        cartDAO = mock(CartDAO.class);
        Mockito.doAnswer(invocation -> {
            throw new Exception("nessun Elemento presente nel carrello");
        }).when(cartDAO).retrieveByUser(any(EndUser.class));

        spy.setDao(cartDAO);
        spy.doPost(request,response);

        verify(response).sendRedirect(request.getContextPath()+"/CartView/cart.jsp");

    }




}

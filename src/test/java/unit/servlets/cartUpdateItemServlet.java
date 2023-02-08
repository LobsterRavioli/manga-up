package unit.servlets;

import Cart.CartView.cartAddServlet;
import Cart.CartView.cartUpdateItemServlet;
import Cart.CheckoutService.Cart;
import Cart.CheckoutService.CartDAO;
import Merchandising.MerchandiseService.Manga;
import User.AccountService.EndUser;
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

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class cartUpdateItemServletTest {

    private cartUpdateItemServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private cartUpdateItemServlet spy;
    private CartDAO cartDAO;

    @BeforeEach
    void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession(false)).thenReturn(session);
        spy = Mockito.spy(new cartUpdateItemServlet());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
        Mockito.when(spy.getServletContext()).thenReturn(context);
    }

    @Test
    void success() throws Exception{
        EndUser e_U = new EndUser(3);
        Mockito.when(request.getParameter("add")).thenReturn("false");
        Mockito.when(session.getAttribute("user")).thenReturn(e_U);
        Mockito.when(request.getParameter("quantity")).thenReturn("2");
        Mockito.when(request.getParameter("id")).thenReturn("20");
        Mockito.when(request.getParameter("maxQ")).thenReturn("3");
        Mockito.when(session.getAttribute("cart")).thenReturn(new Cart(new HashMap<Manga, Integer>()));
        cartDAO = mock(CartDAO.class);
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(cartDAO).updateProduct(any(Manga.class),any(int.class),any(EndUser.class));

        spy.setDao(cartDAO);
        spy.doPost(request,response);
        verify(response).setStatus(200);
    }

    @Test
    void incorrectUser() throws Exception{
        EndUser e_U = new EndUser(3);
        Mockito.when(request.getParameter("add")).thenReturn("false");
        Mockito.when(session.getAttribute("user")).thenReturn(e_U);
        Mockito.when(request.getParameter("quantity")).thenReturn("2");
        Mockito.when(request.getParameter("id")).thenReturn("20");
        Mockito.when(request.getParameter("maxQ")).thenReturn("3");
        Mockito.when(session.getAttribute("cart")).thenReturn(new Cart(new HashMap<Manga, Integer>()));
        cartDAO = mock(CartDAO.class);
        Mockito.doAnswer(invocation -> {
            throw new Exception("utente non esistente");
        }).when(cartDAO).updateProduct(any(Manga.class),any(int.class),any(EndUser.class));

        spy.setDao(cartDAO);
        spy.doPost(request,response);
        verify(response).setStatus(201);
    }

    @Test
    void incorrectProcuct() throws Exception{
        EndUser e_U = new EndUser(3);
        Mockito.when(request.getParameter("add")).thenReturn("false");
        Mockito.when(session.getAttribute("user")).thenReturn(e_U);
        Mockito.when(request.getParameter("quantity")).thenReturn("2");
        Mockito.when(request.getParameter("id")).thenReturn("20");
        Mockito.when(request.getParameter("maxQ")).thenReturn("3");
        Mockito.when(session.getAttribute("cart")).thenReturn(new Cart(new HashMap<Manga, Integer>()));
        cartDAO = mock(CartDAO.class);
        Mockito.doAnswer(invocation -> {
            throw new Exception("prodotto non esistente");
        }).when(cartDAO).updateProduct(any(Manga.class),any(int.class),any(EndUser.class));

        spy.setDao(cartDAO);
        spy.doPost(request,response);
        verify(response).setStatus(202);
    }

    @Test
    void incorrectQuantity() throws Exception{
        EndUser e_U = new EndUser(3);
        Mockito.when(request.getParameter("add")).thenReturn("false");
        Mockito.when(session.getAttribute("user")).thenReturn(e_U);
        Mockito.when(request.getParameter("quantity")).thenReturn("2");
        Mockito.when(request.getParameter("id")).thenReturn("20");
        Mockito.when(request.getParameter("maxQ")).thenReturn("3");
        Mockito.when(session.getAttribute("cart")).thenReturn(new Cart(new HashMap<Manga, Integer>()));
        cartDAO = mock(CartDAO.class);
        Mockito.doAnswer(invocation -> {
            throw new Exception("quantità inserita non valida");
        }).when(cartDAO).updateProduct(any(Manga.class),any(int.class),any(EndUser.class));

        spy.setDao(cartDAO);
        spy.doPost(request,response);
        verify(response).setStatus(203);
    }

    @Test
    void removeProduct() throws Exception{
        EndUser e_U = new EndUser(3);
        Mockito.when(request.getParameter("add")).thenReturn("false");
        Mockito.when(session.getAttribute("user")).thenReturn(e_U);
        Mockito.when(request.getParameter("quantity")).thenReturn("0");
        Mockito.when(request.getParameter("id")).thenReturn("20");
        Mockito.when(request.getParameter("maxQ")).thenReturn("3");
        Cart c = mock(Cart.class);
        Mockito.when(session.getAttribute("cart")).thenReturn(c);
        cartDAO = mock(CartDAO.class);
        Mockito.doAnswer(invocation -> {
           return null;
        }).when(cartDAO).removeProduct(any(Manga.class),any(EndUser.class));

        spy.setDao(cartDAO);
        spy.doPost(request,response);
        verify(c).removeFromCart(any(Manga.class));
    }


}

package unit.servlets;

import Cart.CheckoutService.CartDAO;
import User.AccountService.CreditCardDAO;
import User.AccountService.EndUser;
import User.AccountService.EndUserDAO;
import User.ProfileView.CreditCardDeleteServlet;
import User.ProfileView.LoginEndUserServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class LoginEndUserServletTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private LoginEndUserServlet servlet;
    private EndUserDAO endUserDAO;
    private CartDAO cartDAO;

    @BeforeEach
    void setUp() throws Exception {

        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession(false)).thenReturn(session);
        servlet = Mockito.spy(new LoginEndUserServlet());
        Mockito.when(servlet.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
        Mockito.when(servlet.getServletContext()).thenReturn(context);
        this.cartDAO = mock(CartDAO.class);
        this.endUserDAO = mock(EndUserDAO.class);

    }

    @Test
    void loginFail() throws ServletException, IOException {
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(servlet.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/ProfileView/login_end_user.jsp"))).thenReturn(rD);
        String email = "test";
        String password = "test";
        Mockito.when(request.getParameter("email")).thenReturn(email);
        Mockito.when(request.getParameter("password")).thenReturn(password);
        EndUserDAO endUserDAO = mock(EndUserDAO.class);
        Mockito.when(endUserDAO.login(email, password)).thenReturn(new EndUser(1, "Tommaso", "Sorrentino"));
        servlet.setEndUserDAO(endUserDAO);
        servlet.doPost(request, response);
        verify(request, times(1)).setAttribute("error_message", LoginEndUserServlet.ERROR_MESSAGE);
        verify(context).getRequestDispatcher(response.encodeURL("/ProfileView/login_end_user.jsp.jsp"));


    }

    @Test
    void loginPass() throws ServletException, IOException {
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(servlet.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/ProfileView/login_end_user.jsp"))).thenReturn(rD);
        String email = "test";
        String password = "test";
        Mockito.when(request.getParameter("email")).thenReturn(email);
        Mockito.when(request.getParameter("password")).thenReturn(password);
        EndUserDAO endUserDAO = mock(EndUserDAO.class);
        Mockito.when(endUserDAO.login(email, password)).thenReturn(new EndUser(1, "Tommaso", "Sorrentino"));
        servlet.setEndUserDAO(endUserDAO);
        servlet.doPost(request, response);
        verify(context).getRequestDispatcher(response.encodeURL("/ProfileView/login_end_user.jsp"));
    }
    

}
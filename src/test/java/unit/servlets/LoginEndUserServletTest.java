package unit.servlets;

import Cart.CheckoutService.CartDAO;
import User.AccountService.EndUser;
import User.AccountService.EndUserDAO;
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
        Mockito.when(servlet.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
        Mockito.when(servlet.getServletContext()).thenReturn(context);
        servlet = Mockito.spy(new LoginEndUserServlet());

    }

    @Test
    void invalidParameter() throws ServletException, IOException {

        String email = "test";
        String password = "test";
        Mockito.when(request.getParameter("email")).thenReturn(email);
        Mockito.when(request.getParameter("password")).thenReturn(password);
        EndUserDAO endUserDAO = mock(EndUserDAO.class);
        Mockito.when(endUserDAO.login(email, password)).thenReturn(new EndUser(1, "Tommaso", "Sorrentino"));
        servlet.setEndUserDAO(endUserDAO);
        servlet.doPost(request, response);
        verify(request, times(1)).setAttribute("error_message", LoginEndUserServlet.ERROR_MESSAGE);

    }

    private static Stream<Arguments> invalidParameterProvider() throws Exception {
        return Stream.of(
                Arguments.of("Test case email esistente e password non corretta", null, "password"),
                Arguments.of("Test case email non esistente e password non esistente", "", "password"),
                Arguments.of("Test case email non esistente e password esistente", "123", "password1!"),
                Arguments.of("Test case email non esistente e password esistente", "tommyrock99@hotmai.it", "napoli"),
                Arguments.of("Test case email non esistente e password esistente", "tommyrock99@hotmai.it", null),
                Arguments.of("Test case email non esistente e password esistente", "tommyrock99@hotmai.it", "")
        );
    }

    @Test
    void doPost() {
    }
}
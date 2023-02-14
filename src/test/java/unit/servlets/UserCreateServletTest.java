package unit.servlets;

import User.AccountService.*;
import User.ProfileView.UserCreateServlet;
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
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserCreateServletTest {
    private UserDAO userDAO;

    private UserRoleDAO userRole;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    private UserCreateServlet spy;


    @BeforeEach
    void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession(false)).thenReturn(session);
        spy = Mockito.spy(new UserCreateServlet());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
        Mockito.when(spy.getServletContext()).thenReturn(context);
        userDAO = mock(UserDAO.class);
        userRole = mock(UserRoleDAO.class);
    }


    @Test
    void fail() throws ServletException, IOException, SQLException {

        userDAO = mock(UserDAO.class);
        userRole = mock(UserRoleDAO.class);

        Mockito.when(request.getParameter("username")).thenReturn("tommaso1");
        Mockito.when(request.getParameter("password")).thenReturn("password1!");
        String[] roles = new String[] {"USER_MANAGER", "CATALOG_MANAGER"};
        Mockito.when(request.getParameterValues("roles")).thenReturn(roles);
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/AddressDashboardServlet"))).thenReturn(rD);
        Mockito.when(userDAO.existsUsername(any())).thenReturn(true);
        spy.setUserDao(userDAO);
        spy.setUserRoleDAO(userRole);
        spy.doPost(request, response);

        verify(request).setAttribute("error_message", "Username già esistente");
        verify(context).getRequestDispatcher(response.encodeURL("/ProfileView/creazione_utente.jsp"));
    }

    @Test
    void failUserDAO() throws ServletException, IOException, SQLException {

        userDAO = mock(UserDAO.class);
        userRole = mock(UserRoleDAO.class);

        Mockito.when(request.getParameter("username")).thenReturn("tommaso1");
        Mockito.when(request.getParameter("password")).thenReturn("password1!");
        String[] roles = new String[] {"USER_MANAGER", "CATALOG_MANAGER"};
        Mockito.when(request.getParameterValues("roles")).thenReturn(roles);
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/ProfileView/creazione_utente.jsp"))).thenReturn(rD);
        Mockito.when(userDAO.existsUsername(any())).thenReturn(false);
        Mockito.doAnswer(invocation -> {
            throw new SQLException();
        }).when(userDAO).createUser(any(User.class));

        spy.setUserDao(userDAO);
        spy.setUserRoleDAO(userRole);
        spy.doPost(request, response);

        verify(context).getRequestDispatcher(response.encodeURL("/error_page.jsp"));
    }

    @Test
    void failUserRoleDAO() throws ServletException, IOException, SQLException {

        userDAO = mock(UserDAO.class);
        userRole = mock(UserRoleDAO.class);


        Mockito.when(request.getParameter("username")).thenReturn("tommaso1");
        Mockito.when(request.getParameter("password")).thenReturn("password1!");
        String[] roles = new String[] {"USER_MANAGER", "CATALOG_MANAGER"};
        Mockito.when(request.getParameterValues("roles")).thenReturn(roles);
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/error_page.jsp"))).thenReturn(rD);
        Mockito.when(userDAO.existsUsername(any())).thenReturn(false);

        Mockito.doAnswer(invocation -> {
            throw new SQLException();
        }).when(userRole).setRoles(any(User.class), any(String[].class));

        spy.setUserDao(userDAO);
        spy.setUserRoleDAO(userRole);
        spy.doPost(request, response);

        verify(response).setStatus(500);
        verify(context).getRequestDispatcher(response.encodeURL("/error_page.jsp"));
    }

    @Test
    void success() throws ServletException, IOException, SQLException {

        Mockito.when(request.getParameter("username")).thenReturn("tommaso");
        Mockito.when(request.getParameter("password")).thenReturn("password1!");
        String[] roles = new String[] {"USER_MANAGER", "CATALOG_MANAGER"};;
        Mockito.when(request.getParameterValues("roles")).thenReturn(roles);

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/AddressDashboardServlet"))).thenReturn(rD);
        Mockito.when(userDAO.existsUsername(any())).thenReturn(false);
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(userDAO).createUser(any(User.class));
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(userRole).setRoles(any(User.class), any(String[].class));
        spy.setUserDao(userDAO);
        spy.setUserRoleDAO(userRole);

        spy.doPost(request, response);
        verify(context).getRequestDispatcher(response.encodeURL("/UserListServlet"));

    }


}
package unit.servlets;

import Order.DispatchService.ToManageDAO;
import User.AccountService.User;
import User.AccountService.UserDAO;
import User.AccountService.UserRoleDAO;
import User.ProfileView.UserCreateServlet;
import User.ProfileView.UserDeleteServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.meta.When;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserDeleteServletTest {

    private UserDeleteServlet spy;
    private UserDAO userDAO;
    private ToManageDAO toManage;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getSession(false)).thenReturn(session);
        spy = Mockito.spy(new UserDeleteServlet());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
        Mockito.when(spy.getServletContext()).thenReturn(context);
        userDAO = mock(UserDAO.class);
        toManage = mock(ToManageDAO.class);
        spy.setToManageDAO(toManage);
    }
    @Test
    void success() throws ServletException, IOException, SQLException {
        Mockito.when(toManage.hasOrders(any())).thenReturn(false);
        Mockito.when(request.getAttribute("username")).thenReturn("tommaso");
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(userDAO).removeUserByUserName(any(String.class));
        spy.setUserDAO(userDAO);
        spy.doPost(request, response);
        verify(response).sendRedirect("UserListServlet");

    }

    @Test
    void fail() throws SQLException, ServletException, IOException {
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL("/error_page.jsp"))).thenReturn(rD);

        Mockito.when(request.getParameter("username")).thenReturn("tommaso");

        Mockito.doAnswer(invocation -> {
            throw new SQLException();
        }).when(userDAO).removeUserByUserName(any(String.class));
        spy.setUserDAO(userDAO);
        spy.doPost(request, response);
        verify(response).setStatus(500);
        verify(context).getRequestDispatcher(response.encodeURL("/error_page.jsp"));
    }

}
package integration;

import Cart.CheckoutService.CartDAO;
import User.AccountService.EndUser;
import User.AccountService.EndUserDAO;
import User.ProfileView.LoginEndUserServlet;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import javax.naming.Context;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginEndUserIntegrationTest {

    private LoginEndUserServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private LoginEndUserServlet spy;
    private EndUserDAO endUserDAO;
    private CartDAO cartDAO;
    private Context context;

    private static IDatabaseTester tester;

    @BeforeAll
    void setUpAll() throws ClassNotFoundException {
        tester = new JdbcDatabaseTester(org.h2.Driver.class.getName(),
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:schema_db/schema.sql'",
                "prova",
                ""

        );

        // Refresh permette di svuotare la cache dopo un modifica con setDataSet
        // DeleteAll ci svuota il DB mantenendo lo schema
        tester.setSetUpOperation(DatabaseOperation.REFRESH);
        tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
    }

    private static void refreshDataSet(String filename) throws Exception {
        IDataSet initialState = new FlatXmlDataSetBuilder()
                .build(LoginEndUserIntegrationTest.class.getClassLoader().getResourceAsStream(filename));
        tester.setDataSet(initialState);
        tester.onSetup();
    }

    @BeforeEach
    void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        spy = Mockito.spy(new LoginEndUserServlet());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
        Mockito.when(spy.getServletContext()).thenReturn(context);
        DataSource ds = Mockito.mock(DataSource.class);
        refreshDataSet("enduser_dao/init.xml");
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        endUserDAO = new EndUserDAO(ds);
        cartDAO = new CartDAO(ds);

    }

    @Test
    void loginSuccess() throws Exception {
        refreshDataSet("enduser_dao/end_user_dao_exists_email.xml");
        String email = "tommyrock99@hotmail.it";
        String password = "password1!";

        Mockito.when(request.getParameter("email")).thenReturn(email);
        Mockito.when(request.getParameter("password")).thenReturn(password);
        spy.setEndUserDAO(endUserDAO);
        spy.setCartDAO(cartDAO);
        spy.doPost(request, response);
        verify(response).encodeURL("/ProductsView/endUserHomepage.jsp");

    }

    @Test
    void lol(){
        ArrayList<String> lol = new ArrayList<>();
        lol.add("lol");
        lol.add("lol");
        ArrayList<String> lol2 = new ArrayList<>();
        lol2.add("lol");
        lol2.add("lol");
        Assert.assertTrue(lol.equals(lol2));
    }




}

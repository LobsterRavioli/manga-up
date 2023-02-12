package integration;


import Cart.CartView.cartUpdateItemServlet;
import Cart.CartView.checkoutPreparationServlet;
import Cart.CheckoutService.Cart;
import Cart.CheckoutService.CartDAO;
import Merchandising.MerchandiseService.Manga;
import User.AccountService.*;
import org.checkerframework.checker.units.qual.C;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class checkoutPreparationServletIntegrationTest {

    private checkoutPreparationServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private checkoutPreparationServlet spy;
    private AddressDAO addressDAO;
    private CreditCardDAO creditCardDAO;

    private static IDatabaseTester tester;

    ServletContext context;

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
        Mockito.when(request.getSession(false)).thenReturn(session);
        spy = Mockito.spy(new checkoutPreparationServlet());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        DataSource ds = Mockito.mock(DataSource.class);
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        refreshDataSet("Cart/cart_dao/checkoutPrep.xml");
        given(ds.getConnection()).willAnswer(new Answer<Connection>() {
            public Connection answer(InvocationOnMock invocation) {
                try {
                    Connection c = tester.getConnection().getConnection();
                    return c;
                } catch (Exception e) {
                    return null;
                }
            }
        });
        addressDAO = new AddressDAO(ds);
        creditCardDAO = new CreditCardDAO(ds);
    }

    @Test
    void nonExistentCreditCardTest() throws Exception{
        Mockito.when(session.getAttribute("user")).thenReturn(new EndUser(2));

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
        Mockito.when(session.getAttribute("user")).thenReturn(new EndUser(4));

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
        Mockito.when(session.getAttribute("user")).thenReturn(new EndUser(1));

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

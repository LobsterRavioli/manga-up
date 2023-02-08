package integration;

import Cart.CartView.cartAddServlet;
import Cart.CheckoutService.Cart;
import Cart.CheckoutService.CartDAO;
import Merchandising.MerchandiseService.Manga;
import Merchandising.MerchandiseService.MangaDAO;
import Merchandising.ProductsView.processProductInsertion;
import User.AccountService.EndUser;
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

import javax.naming.Context;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class cartAddServletIntegrationTest {

    private cartAddServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private cartAddServlet spy;
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
        Mockito.when(request.getSession(false)).thenReturn(session);
        spy = Mockito.spy(new cartAddServlet());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        DataSource ds = Mockito.mock(DataSource.class);
        refreshDataSet("Merchandising/manga_dao/init.xml");
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        cartDAO = new CartDAO(ds);
    }

    @Test
    void success() throws Exception{
        EndUser e_U = new EndUser(3);
        Mockito.when(session.getAttribute("user")).thenReturn(e_U);
        Mockito.when(request.getParameter("quantity")).thenReturn("2");
        Mockito.when(request.getParameter("id")).thenReturn("5");
        Mockito.when(request.getParameter("maxQ")).thenReturn("3");
        Mockito.when(session.getAttribute("cart")).thenReturn(new Cart(new HashMap<Manga, Integer>()));
        spy.setDao(cartDAO);
        spy.doPost(request,response);
        verify(response).setStatus(200);
        ITable actualTable = tester.getConnection().createDataSet().getTable("Cart");
        Assert.assertTrue(actualTable.getRowCount()==2);
    }

    @Test
    void update() throws Exception{
        EndUser e_U = new EndUser(3);
        Mockito.when(session.getAttribute("user")).thenReturn(e_U);
        Mockito.when(request.getParameter("quantity")).thenReturn("2");
        Mockito.when(request.getParameter("id")).thenReturn("5");
        Mockito.when(request.getParameter("maxQ")).thenReturn("3");
        Mockito.when(session.getAttribute("cart")).thenReturn(new Cart(new HashMap<Manga, Integer>()));
        spy.setDao(cartDAO);
        spy.doPost(request,response);
        verify(response).setStatus(200);
        ITable actualTable = tester.getConnection().createDataSet().getTable("Cart");
        Assert.assertTrue((int)actualTable.getValue(0,"quantity")==2);
    }


    @Test
    void incorrectUser() throws Exception{
        EndUser e_U = new EndUser(2);
        Mockito.when(session.getAttribute("user")).thenReturn(e_U);
        Mockito.when(request.getParameter("quantity")).thenReturn("2");
        Mockito.when(request.getParameter("id")).thenReturn("20");
        Mockito.when(request.getParameter("maxQ")).thenReturn("3");
        Mockito.when(session.getAttribute("cart")).thenReturn(new Cart(new HashMap<Manga, Integer>()));
        spy.setDao(cartDAO);
        spy.doPost(request,response);
        verify(response).setStatus(201);
    }

    @Test
    void incorrectProcuct() throws Exception{
        EndUser e_U = new EndUser(3);
        Mockito.when(session.getAttribute("user")).thenReturn(e_U);
        Mockito.when(request.getParameter("quantity")).thenReturn("2");
        Mockito.when(request.getParameter("id")).thenReturn("18");
        Mockito.when(request.getParameter("maxQ")).thenReturn("3");
        Mockito.when(session.getAttribute("cart")).thenReturn(new Cart(new HashMap<Manga, Integer>()));
        spy.setDao(cartDAO);
        spy.doPost(request,response);
        verify(response).setStatus(202);
    }

    @Test
    void incorrectQuantity() throws Exception{
        EndUser e_U = new EndUser(3);
        Mockito.when(session.getAttribute("user")).thenReturn(e_U);
        Mockito.when(request.getParameter("quantity")).thenReturn("5");
        Mockito.when(request.getParameter("id")).thenReturn("20");
        Mockito.when(request.getParameter("maxQ")).thenReturn("3");
        Mockito.when(session.getAttribute("cart")).thenReturn(new Cart(new HashMap<Manga, Integer>()));
        spy.setDao(cartDAO);
        spy.doPost(request,response);
        verify(response).setStatus(203);
    }

}

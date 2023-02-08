package integration;

import Cart.CartView.cartUpdateItemServlet;
import Cart.CheckoutService.CartDAO;
import Merchandising.MerchandiseService.Manga;
import Merchandising.MerchandiseService.MangaDAO;
import Merchandising.ProductsView.catalogServlet;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class catalogServletIntegrationTest {

    private catalogServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private catalogServlet spy;
    private MangaDAO mangaDAO;
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
        spy = Mockito.spy(new catalogServlet());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        DataSource ds = Mockito.mock(DataSource.class);
        refreshDataSet("Merchandising/manga_dao/init.xml");
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        mangaDAO = new MangaDAO(ds);
    }

    @Test
    void success() throws Exception{
        spy.setDaoM(mangaDAO);
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher("/ProductsView/catalog.jsp")).thenReturn(rD);
        spy.doGet(request, response);
        verify(request,never()).setAttribute("listaElementi",null);
        verify(context).getRequestDispatcher("/ProductsView/catalog.jsp");
    }


    @Test
    void fail()throws Exception{
        mangaDAO.delete(20);
        DataSource ds = Mockito.mock(DataSource.class);
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        mangaDAO = new MangaDAO(ds);
        mangaDAO.delete(5);
        DataSource ds1= Mockito.mock(DataSource.class);
        Mockito.when(ds1.getConnection()).thenReturn(tester.getConnection().getConnection());
        mangaDAO = new MangaDAO(ds1);
        spy.setDaoM(mangaDAO);

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher("/ProductsView/catalog.jsp")).thenReturn(rD);
        spy.doGet(request, response);
        verify(request,never()).setAttribute(eq("listaElementi"),any(ArrayList.class));
        verify(context).getRequestDispatcher("/ProductsView/catalog.jsp");

    }
}

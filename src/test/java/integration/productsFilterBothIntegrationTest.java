package integration;

import Cart.CartView.visualizeCartServlet;
import Cart.CheckoutService.CartDAO;
import Merchandising.MerchandiseService.Manga;
import Merchandising.MerchandiseService.MangaDAO;
import Merchandising.ProductsView.productsFilter;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class productsFilterBothIntegrationTest {

    private productsFilter servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private productsFilter spy;
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
        spy = Mockito.spy(new productsFilter());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        DataSource ds = Mockito.mock(DataSource.class);
        refreshDataSet("Merchandising/manga_dao/init.xml");
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        mangaDAO = new MangaDAO(ds);
    }

    @Test
    void userManagerCollectionFail() throws Exception{
        Mockito.when(request.getParameter("name")).thenReturn("");
        Mockito.when(request.getParameter("collection")).thenReturn("");
        Mockito.when(request.getParameter("UserManager")).thenReturn("true");
        Mockito.when(request.getParameter("minPrice")).thenReturn("0");
        Mockito.when(request.getParameter("maxPrice")).thenReturn("100");
        Mockito.when(request.getParameter("soggetto")).thenReturn("name");
        Mockito.when(request.getParameter("criteria")).thenReturn("ASC");

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher("/ProductsView/prodManagement.jsp")).thenReturn(rD);
        spy.setDaoM(mangaDAO);
        spy.doPost(request, response);

        verify(request).setAttribute("listaElementi",null);
        verify(request).setAttribute(eq("error"),eq("Collezione inserita non esistente nel db"));
        verify(context).getRequestDispatcher("/ProductsView/prodManagement.jsp");

    }

    @Test
    void userManagerSuccess() throws Exception{
        Mockito.when(request.getParameter("name")).thenReturn("");
        Mockito.when(request.getParameter("collection")).thenReturn("Mecha");
        Mockito.when(request.getParameter("UserManager")).thenReturn("true");
        Mockito.when(request.getParameter("minPrice")).thenReturn("0");
        Mockito.when(request.getParameter("maxPrice")).thenReturn("100");
        Mockito.when(request.getParameter("soggetto")).thenReturn("name");
        Mockito.when(request.getParameter("criteria")).thenReturn("ASC");

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher("/ProductsView/prodManagement.jsp")).thenReturn(rD);
        spy.setDaoM(mangaDAO);
        spy.doPost(request, response);

        verify(request).setAttribute(eq("listaElementi"),notNull());
        verify(context).getRequestDispatcher("/ProductsView/prodManagement.jsp");

    }

    @Test
    void userManagerPriceFail() throws Exception{
        Mockito.when(request.getParameter("name")).thenReturn("");
        Mockito.when(request.getParameter("collection")).thenReturn("Mecha");
        Mockito.when(request.getParameter("UserManager")).thenReturn("true");
        Mockito.when(request.getParameter("minPrice")).thenReturn("5");
        Mockito.when(request.getParameter("maxPrice")).thenReturn("1");
        Mockito.when(request.getParameter("soggetto")).thenReturn("name");
        Mockito.when(request.getParameter("criteria")).thenReturn("ASC");

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher("/ProductsView/prodManagement.jsp")).thenReturn(rD);
        spy.setDaoM(mangaDAO);
        spy.doPost(request, response);

        verify(request).setAttribute("listaElementi",null);
        verify(request).setAttribute(eq("error"),eq("range di valori inserito non corretto"));
        verify(context).getRequestDispatcher("/ProductsView/prodManagement.jsp");

    }

    @Test
    void userManagerProductsNotFound() throws Exception{
        Mockito.when(request.getParameter("name")).thenReturn("asasfdfsdf");
        Mockito.when(request.getParameter("collection")).thenReturn("Mecha");
        Mockito.when(request.getParameter("UserManager")).thenReturn("true");
        Mockito.when(request.getParameter("minPrice")).thenReturn("0");
        Mockito.when(request.getParameter("maxPrice")).thenReturn("100");
        Mockito.when(request.getParameter("soggetto")).thenReturn("name");
        Mockito.when(request.getParameter("criteria")).thenReturn("ASC");

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher("/ProductsView/prodManagement.jsp")).thenReturn(rD);
        spy.setDaoM(mangaDAO);
        spy.doPost(request, response);

        verify(request).setAttribute("listaElementi",null);
        verify(request).setAttribute(eq("error"),eq("non sono stati trovati prodotti affini ai filtri di ricerca specificati"));
        verify(context).getRequestDispatcher("/ProductsView/prodManagement.jsp");
    }
}

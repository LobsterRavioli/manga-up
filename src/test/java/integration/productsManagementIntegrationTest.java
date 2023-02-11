package integration;


import Merchandising.MerchandiseService.MangaDAO;
import Merchandising.ProductsView.productsFilter;
import Merchandising.ProductsView.productsManagement;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class productsManagementIntegrationTest {

    private productsManagement servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private productsManagement spy;
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
        spy = Mockito.spy(new productsManagement());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        DataSource ds = Mockito.mock(DataSource.class);
        refreshDataSet("Merchandising/manga_dao/init.xml");
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        mangaDAO = new MangaDAO(ds);
    }

    @Test
    void updateSuccess() throws Exception{
        Mockito.when(request.getParameter("redirect")).thenReturn(null);
        Mockito.when(request.getParameter("id")).thenReturn("5");
        Mockito.when(request.getParameter("quantity")).thenReturn("6");
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(request.getRequestDispatcher("/ProductsView/homepage.jsp")).thenReturn(rD);

        spy.setMa(mangaDAO);
        spy.doPost(request, response);

        verify(request).setAttribute("success","Operazione avvenuta con successo");
        verify(request).getRequestDispatcher("/ProductsView/homepage.jsp");
    }

    @Test
    void updateInvalidId() throws Exception{
        Mockito.when(request.getParameter("redirect")).thenReturn(null);
        Mockito.when(request.getParameter("id")).thenReturn("9");
        Mockito.when(request.getParameter("quantity")).thenReturn("6");
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(request.getRequestDispatcher("/ProductsView/homepage.jsp")).thenReturn(rD);

        spy.setMa(mangaDAO);
        spy.doPost(request, response);

        verify(request).setAttribute(eq("error"),eq("Il prodotto selezionato per l'aggiornamento non è presente nel db'"));
        verify(request).getRequestDispatcher("/ProductsView/homepage.jsp");
    }


    @Test
    void updateInvalidQuantity() throws Exception{
        Mockito.when(request.getParameter("redirect")).thenReturn(null);
        Mockito.when(request.getParameter("id")).thenReturn("5");
        Mockito.when(request.getParameter("quantity")).thenReturn("0");
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(request.getRequestDispatcher("/ProductsView/homepage.jsp")).thenReturn(rD);

        spy.setMa(mangaDAO);
        spy.doPost(request, response);

        verify(request).setAttribute(eq("error"),eq("quantità inserita non valida"));
        verify(request).getRequestDispatcher("/ProductsView/homepage.jsp");
    }

}

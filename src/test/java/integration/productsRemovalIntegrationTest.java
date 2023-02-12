package integration;


import Merchandising.MerchandiseService.MangaDAO;
import Merchandising.ProductsView.productsManagement;
import Merchandising.ProductsView.productsRemoval;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class productsRemovalIntegrationTest {

    private productsRemoval servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private productsRemoval spy;
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
        spy = Mockito.spy(new productsRemoval());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        DataSource ds = Mockito.mock(DataSource.class);
        refreshDataSet("Merchandising/manga_dao/init.xml");
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        mangaDAO = new MangaDAO(ds);
    }


    @Test
    void removeSuccess() throws Exception{
        Mockito.when(request.getParameter("goodEnding")).thenReturn(null);
        Mockito.when(request.getParameter("id")).thenReturn("5");
        spy.setDaoM(mangaDAO);
        spy.doPost(request, response);

        ITable actualTable = tester.getConnection().createDataSet().getTable("MANGA");
        Assert.assertTrue(actualTable.getRowCount()==1);
        verify(response).setStatus(200);
    }

    @Test
    void removeFailed() throws Exception{
        Mockito.when(request.getParameter("goodEnding")).thenReturn(null);
        Mockito.when(request.getParameter("id")).thenReturn("12");
        spy.setDaoM(mangaDAO);
        spy.doPost(request, response);

        ITable actualTable = tester.getConnection().createDataSet().getTable("MANGA");
        Assert.assertTrue(actualTable.getRowCount()==2);
        verify(request).setAttribute("unexistentProd","error");
        verify(response).setStatus(201);
    }

}

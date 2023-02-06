package integration;

import Merchandising.MerchandiseService.MangaDAO;
import Merchandising.MerchandiseService.ProductState;
import Merchandising.ProductsView.processProductInsertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import javax.naming.Context;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class processProductInsertionIntegrationTest {

    private processProductInsertion servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private processProductInsertion spy;
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
        spy = Mockito.spy(new processProductInsertion());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        DataSource ds = Mockito.mock(DataSource.class);
        refreshDataSet("Merchandising/manga_dao/init.xml");
        Mockito.when(ds.getConnection()).thenReturn(tester.getConnection().getConnection());
        mangaDAO = new MangaDAO(ds);

    }


    @Test
    void productSuccessfullyInserted()throws Exception{
        String prodName = "Sanctuary";
        String prodPublisher = "Star Comics";
        String prodPrice = "30.25";
        String prodWeight = "3";
        String prodHeight = "4";
        String prodLength = "5";
        String prodState = "NEW";
        String prodDescription = "BEL MANGA";
        String prodISBN = "1234567891011";
        String prodBinding = "Spilli";
        String prodVolume = "Vol.5";
        String prodDataUscita = "2021-02-03";
        String prodPageNumber = "68";
        String prodQuantity = "9";
        String prodInterior = "blu";
        String prodLanguage = "ESP";
        String prodCollection = "Mecha";
        String prodGenre = "Josei";
        String prodStoryMaker = "SAVIANO";

        // Create path components to save the file
        Part partFile = mock(Part.class);
        Mockito.when(partFile.getInputStream()).thenReturn(new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        });
        Mockito.when(request.getParameter("nome")).thenReturn(prodName);
        Mockito.when(request.getParameter("editore")).thenReturn(prodPublisher);
        Mockito.when(request.getParameter("prezzo")).thenReturn(prodPrice);
        Mockito.when(request.getParameter("peso")).thenReturn(prodWeight);
        Mockito.when(request.getParameter("altezza")).thenReturn(prodHeight);
        Mockito.when(request.getParameter("larghezza")).thenReturn(prodLength);
        Mockito.when(request.getParameter("stato")).thenReturn(prodState);
        Mockito.when(request.getParameter("descrizione")).thenReturn(prodDescription);
        Mockito.when(request.getParameter("isbn")).thenReturn(prodISBN);
        Mockito.when(request.getParameter("rilegatura")).thenReturn(prodBinding);
        Mockito.when(request.getParameter("volume")).thenReturn(prodVolume);
        Mockito.when(request.getParameter("data_uscita")).thenReturn(prodDataUscita);
        Mockito.when(request.getParameter("numPagine")).thenReturn(prodPageNumber);
        Mockito.when(request.getParameter("quantity")).thenReturn(prodQuantity);
        Mockito.when(request.getParameter("interni")).thenReturn(prodInterior);
        Mockito.when(request.getParameter("lingua")).thenReturn(prodLanguage);
        Mockito.when(request.getParameter("collection")).thenReturn(prodCollection);
        Mockito.when(request.getParameter("genere")).thenReturn(prodGenre);
        Mockito.when(request.getParameter("story_maker")).thenReturn(prodStoryMaker);
        Mockito.when(request.getPart("immagine")).thenReturn(partFile);

        spy.setMangaDAO(mangaDAO);

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher("/ProductsView/homepage.jsp")).thenReturn(rD);

        /*Mockito.doAnswer(invocation -> {
            return null;                            //Method to have a mocked execution of a method
        }).when(rD).forward(request,response);*/

        spy.doPost(request, response);
        verify(context.getRequestDispatcher("/ProductsView/homepage.jsp"));
    }

}

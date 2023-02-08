package unit.servlets;


import Cart.CheckoutService.CartDAO;
import Merchandising.MerchandiseService.*;
import Merchandising.ProductsView.processProductInsertion;
import User.AccountService.EndUser;
import User.AccountService.EndUserDAO;
import User.ProfileView.LoginEndUserServlet;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

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
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class processProductInsertionTest {

    private processProductInsertion servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private processProductInsertion spy;
    private MangaDAO mangaDAO;

    @BeforeEach
    void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        spy = Mockito.spy(new processProductInsertion());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
        Mockito.when(spy.getServletContext()).thenReturn(context);
    }






    @Test
    void correctInsertion() throws Exception{
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
        MangaDAO mangaDAO = mock(MangaDAO.class);

        Mockito.doAnswer(invocation -> {
            return null;                            //Method to have a mocked execution of a method
        }).when(mangaDAO).create(any(Manga.class));

        spy.setMangaDAO(mangaDAO);

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher("/ProductsView/homepage.jsp")).thenReturn(rD);

        spy.doPost(request, response);
        verify(context).getRequestDispatcher("/ProductsView/homepage.jsp");
    }


    @ParameterizedTest(name = "{index} - {0} (parametri: {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}, {11}, {12}, {13}, {14}, {15}, {16}, {17}, {18}, {19})")
    @MethodSource("createTestFailProvider")
    void createTestFailInvalidInput(String testName, String isbn, String publisher, String binding, String language, String volume, int pages, Date exitDate, String name, String description, double price, int height, int length, int weight, int quantity, String interior, String imagePath, Collection collection, ProductState state, String storyMaker, Genre genre) throws Exception {
        Part partFile = mock(Part.class);
        Mockito.when(partFile.getInputStream()).thenReturn(new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        });



        Mockito.when(request.getParameter("nome")).thenReturn(name);
        Mockito.when(request.getParameter("editore")).thenReturn(publisher);
        Mockito.when(request.getParameter("prezzo")).thenReturn(""+price);
        Mockito.when(request.getParameter("peso")).thenReturn(""+weight);
        Mockito.when(request.getParameter("altezza")).thenReturn(""+height);
        Mockito.when(request.getParameter("larghezza")).thenReturn(""+length);
        Mockito.when(request.getParameter("stato")).thenReturn(""+state);
        Mockito.when(request.getParameter("descrizione")).thenReturn(description);
        Mockito.when(request.getParameter("isbn")).thenReturn(isbn);
        Mockito.when(request.getParameter("rilegatura")).thenReturn(binding);
        Mockito.when(request.getParameter("volume")).thenReturn(volume);

        if(exitDate==null)
            Mockito.when(request.getParameter("data_uscita")).thenReturn(null);
        else
            Mockito.when(request.getParameter("data_uscita")).thenReturn(""+exitDate);

        Mockito.when(request.getParameter("numPagine")).thenReturn(""+pages);
        Mockito.when(request.getParameter("quantity")).thenReturn(""+quantity);
        Mockito.when(request.getParameter("interni")).thenReturn(interior);
        Mockito.when(request.getParameter("lingua")).thenReturn(language);
        if(collection==null)
            Mockito.when(request.getParameter("collection")).thenReturn(null);
        else
            Mockito.when(request.getParameter("collection")).thenReturn(collection.getName());

        if(genre==null)
            Mockito.when(request.getParameter("collection")).thenReturn(null);
        else
            Mockito.when(request.getParameter("collection")).thenReturn(genre.getName());




        Mockito.when(request.getParameter("story_maker")).thenReturn(storyMaker);
        Mockito.when(request.getPart("immagine")).thenReturn(partFile);
        MangaDAO mangaDAO = mock(MangaDAO.class);
        Mockito.doAnswer(invocation -> {
            throw new Exception("Errore");                            //Method to have a mocked execution of a method
        }).when(mangaDAO).create(any(Manga.class));

        spy.setMangaDAO(mangaDAO);

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher("/ProductsView/homepage.jsp")).thenReturn(rD);

        spy.doPost(request, response);

        verify(request).setAttribute(any(String.class),any(String.class));

    }

    private static Stream<Arguments> createTestFailProvider() {
        /*nome prodotto: “Sanctuary 3”
        publisher: Star Comics
        prezzo: 25.99
        peso:  3
        dimensioni: (altezza: 3, larghezza:5)
        stato: “NEW”
        descrizione: “vuoto”
        ISBN: 9997546123412
        rilegatura: “bordatura”
        volume: “VOL.5”
        data di uscita: 2001-03-05
        numero di pagine: 287
        quantità: 3
        interni: 6
        lingua: “ITA”
        collezione: *presente nel db*
        genere: *presente nel db*
        storyMaker: “Akira Toriyama”*/

        return Stream.of(

                //Controllo ISBN
                Arguments.of("Test case creazione prodotto fallita ISBN non valido", null,"Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita ISBN non valido", "9997546123","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo Editore
                Arguments.of("Test case creazione prodotto fallita Editore non valido", "9997546123412",null,"bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita Editore non valido", "9997546123412","","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita Editore non valido", "9997546123412","Lorem ipsum dolor sit amet, consectetur sodales sed","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo Rilegatura
                Arguments.of("Test case creazione prodotto fallita Rilegatura non valida", "9997546123412","Star Comics",null,"ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita Rilegatura non valida", "9997546123412","Star Comics","","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita Rilegatura non valida", "9997546123412","Star Comics","Lorem ipsum dolor sit amet, consectetur sodales sed","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo lingua
                Arguments.of("Test case creazione prodotto fallita lingua non valida", "9997546123412","Star Comics","bordatura",null,"VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita lingua non valida", "9997546123412","Star Comics","bordatura","","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita lingua non valida", "9997546123412","Star Comics","bordatura","Lorem ipsum dolor sit amet, consectetur sodales sed","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo volume
                Arguments.of("Test case creazione prodotto fallita volume non valido", "9997546123412","Star Comics","bordatura","ITA",null,287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita volume non valido", "9997546123412","Star Comics","bordatura","ITA","",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita volume non valido", "9997546123412","Star Comics","bordatura","ITA","Lorem ipsum dolor sit amet, consectetur sodales sed",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo Numero di pagine
                Arguments.of("Test case creazione prodotto fallita Numero di pagine non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",0,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo Data
                Arguments.of("Test case creazione prodotto fallita Numero di pagine non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,null,"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita Numero di pagine non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2025-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo Nome
                Arguments.of("Test case creazione prodotto fallita Nome non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),null,"",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita Nome non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita Nome non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Lorem ipsum dolor sit amet, consectetur sodales sed","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita Nome non valido ", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo Prezzo
                Arguments.of("Test case creazione prodotto fallita Prezzo non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",0,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo Altezza
                Arguments.of("Test case creazione prodotto fallita Altezza non valida", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,0,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo Lunghezza
                Arguments.of("Test case creazione prodotto fallita Lunghezza non valida", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,0,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo Peso
                Arguments.of("Test case creazione prodotto fallita Peso non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,0,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo Quantità
                Arguments.of("Test case creazione prodotto fallita Quantità non valida", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,0,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo Interni
                Arguments.of("Test case creazione prodotto fallita Interni non validi", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,null,"",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita Interni non validi", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita Interni non validi", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"Lorem ipsum dolor sit amet, consectetur sodales sed","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo collezione
                Arguments.of("Test case creazione prodotto fallita collezione non valida", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",null,ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita collezione non valida", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection(null),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita collezione non valida", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection(""),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita collezione non valida", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Me"),ProductState.NEW,"Akira Toriyama",new Genre("Josei")),
                //Controllo stato
                Arguments.of("Test case creazione prodotto fallita stato non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),null,"Akira Toriyama",new Genre("Josei")),
                //Controllo StoryMaker
                Arguments.of("Test case creazione prodotto fallita storymaker non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,null,new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita storymaker non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"",new Genre("Josei")),
                Arguments.of("Test case creazione prodotto fallita storymaker non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Lorem ipsum dolor sit amet, consectetur sodales sed",new Genre("Josei")),
                //Controllo genere
                Arguments.of("Test case creazione prodotto fallita genere non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",null),
                Arguments.of("Test case creazione prodotto fallita genere non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre(null)),
                Arguments.of("Test case creazione prodotto fallita genere non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("")),
                Arguments.of("Test case creazione prodotto fallita genere non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Jo")),
                //Controllo Descrizione
                Arguments.of("Test case creazione prodotto fallita genere non valido", "9997546123412","Star Comics","bordatura","ITA","VOL.5",287,(java.sql.Date)Date.valueOf("2001-02-05"),"Sanctuary","Lorem ipsum dolor sit amet, consectetur sodales sedLorem ipsum dolor sit amet, consectetur sodales sedLorem ipsum dolor sit amet, consectetur sodales sedLorem ipsum dolor sit amet, consectetur sodales sedLorem ipsum dolor sit amet, consectetur sodales sedLorem ipsum dolor sit amet, consectetur sodales sed",25.99,3,5,3,3,"rosso","",new Collection("Mecha"),ProductState.NEW,"Akira Toriyama",new Genre("Jo"))

        );
    }

}

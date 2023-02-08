package unit.servlets;

import Merchandising.MerchandiseService.Manga;
import Merchandising.MerchandiseService.MangaDAO;
import Merchandising.ProductsView.catalogServlet;
import Merchandising.ProductsView.processProductInsertion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class catalogServletTest {

    private catalogServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private catalogServlet spy;
    private MangaDAO mangaDAO;

    @BeforeEach
    void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        spy = Mockito.spy(new catalogServlet());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
        Mockito.when(spy.getServletContext()).thenReturn(context);
    }

    @Test
    void success() throws Exception{
        MangaDAO mangaDAO = mock(MangaDAO.class);

        ArrayList<Manga> lista = new ArrayList<Manga>();
        lista.add(new Manga(25));
        Mockito.doAnswer(invocation -> {
            return lista;                            //Method to have a mocked execution of a method
        }).when(mangaDAO).retrieveAll();

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
        MangaDAO mangaDAO = mock(MangaDAO.class);

        Mockito.doAnswer(invocation -> {
            throw new Exception("Nessun elemento trovato");   //Method to have a mocked execution of a method
        }).when(mangaDAO).retrieveAll();

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

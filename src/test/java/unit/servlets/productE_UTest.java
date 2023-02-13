package unit.servlets;

import Merchandising.MerchandiseService.Manga;
import Merchandising.MerchandiseService.MangaDAO;
import Merchandising.ProductsView.catalogServlet;
import Merchandising.ProductsView.productE_U;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class productE_UTest {

    private productE_U servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private productE_U spy;
    private MangaDAO mangaDAO;

    @BeforeEach
    void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        spy = Mockito.spy(new productE_U());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(context.getRequestDispatcher(response.encodeURL(""))).thenReturn(Mockito.mock(RequestDispatcher.class));
        Mockito.when(spy.getServletContext()).thenReturn(context);
    }


    @Test
    void success() throws Exception{
        mangaDAO = mock(MangaDAO.class);
        Mockito.when(request.getParameter("prodId")).thenReturn("5");
        Mockito.when(request.getParameter("err")).thenReturn(null);
        Mockito.when(request.getParameter("success")).thenReturn(null);
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        Mockito.when(context.getRequestDispatcher("/ProductsView/product.jsp")).thenReturn(mock(RequestDispatcher.class));
        Mockito.doAnswer(invocation -> {
            return new Manga(5);
        }).when(mangaDAO).retrieveById(5);

        spy.setDaoM(mangaDAO);
        spy.doPost(request,response);

        verify(request).setAttribute(eq("prod"),notNull());
        verify(context).getRequestDispatcher("/ProductsView/product.jsp");
    }


    @Test
    void error() throws Exception{
        mangaDAO = mock(MangaDAO.class);
        Mockito.when(request.getParameter("prodId")).thenReturn("17");
        Mockito.when(request.getParameter("err")).thenReturn(null);
        Mockito.when(request.getParameter("success")).thenReturn(null);
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rd =mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher(any(String.class))).thenReturn(rd);
        Mockito.when(context.getRequestDispatcher(any(String.class))).thenReturn(rd);
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(mangaDAO).retrieveById(17);

        spy.setDaoM(mangaDAO);
        spy.doPost(request,response);

        verify(request).setAttribute("error","Errore relativo al prodotto selezionato: Prodotto non esistente");
        verify(context).getRequestDispatcher("/catalogServlet");
    }

}

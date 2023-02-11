package unit.servlets;

import Merchandising.MerchandiseService.Manga;
import Merchandising.MerchandiseService.MangaDAO;
import Merchandising.ProductsView.catalogServlet;
import Merchandising.ProductsView.productsFilter;
import com.beust.ah.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class productsFilterBothTest {

    private productsFilter servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private productsFilter spy;
    private MangaDAO mangaDAO;

    @BeforeEach
    void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        spy = Mockito.spy(new productsFilter());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        ServletContext context = Mockito.mock(ServletContext.class);
        RequestDispatcher rd = mock(RequestDispatcher.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
    }

    @Test
    void userManagerSuccess() throws Exception{
        mangaDAO=mock(MangaDAO.class);
        Mockito.when(request.getParameter("name")).thenReturn("");
        Mockito.when(request.getParameter("collection")).thenReturn("");
        Mockito.when(request.getParameter("UserManager")).thenReturn("true");
        Mockito.when(request.getParameter("minPrice")).thenReturn("0");
        Mockito.when(request.getParameter("maxPrice")).thenReturn("100");
        Mockito.when(request.getParameter("soggetto")).thenReturn("name");
        Mockito.when(request.getParameter("criteria")).thenReturn("ASC");
        Mockito.doAnswer(invocation -> {
            ArrayList<Manga> lista = new ArrayList<Manga>();
            lista.add(new Manga(20));
            lista.add(new Manga(5));
            return lista;
        }).when(mangaDAO).filterForUsers(any(String.class),any(float.class),any(float.class),any(String.class),any(boolean.class),any(boolean.class));


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
    void userManagerFail() throws Exception{
        mangaDAO=mock(MangaDAO.class);
        Mockito.when(request.getParameter("name")).thenReturn("");
        Mockito.when(request.getParameter("collection")).thenReturn("asdds");
        Mockito.when(request.getParameter("UserManager")).thenReturn("true");
        Mockito.when(request.getParameter("minPrice")).thenReturn("0");
        Mockito.when(request.getParameter("maxPrice")).thenReturn("0");
        Mockito.when(request.getParameter("soggetto")).thenReturn("name");
        Mockito.when(request.getParameter("criteria")).thenReturn("ASC");
        Mockito.doAnswer(invocation -> {
            throw new Exception("Some Message");
        }).when(mangaDAO).filterForUsers(any(String.class),any(float.class),any(float.class),any(String.class),any(boolean.class),any(boolean.class));

        spy.setDaoM(mangaDAO);

        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher("/ProductsView/prodManagement.jsp")).thenReturn(rD);
        spy.doPost(request, response);
        verify(request).setAttribute("listaElementi",null);
        verify(request).setAttribute(eq("error"),any(String.class));
        verify(context).getRequestDispatcher("/ProductsView/prodManagement.jsp");
    }


}

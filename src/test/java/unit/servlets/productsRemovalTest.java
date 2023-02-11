package unit.servlets;

import Merchandising.MerchandiseService.MangaDAO;
import Merchandising.ProductsView.productsManagement;
import Merchandising.ProductsView.productsRemoval;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class productsRemovalTest {

    private productsRemoval servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private productsRemoval spy;
    private MangaDAO mangaDAO;

    @BeforeEach
    void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        spy = Mockito.spy(new productsRemoval());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        ServletContext context = Mockito.mock(ServletContext.class);
        RequestDispatcher rd = mock(RequestDispatcher.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
    }


    @Test
    void redirectEnding() throws Exception{
        mangaDAO=mock(MangaDAO.class);
        Mockito.when(request.getParameter("goodEnding")).thenReturn("true");
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(context.getRequestDispatcher("/ProductsView/prodManagement.jsp")).thenReturn(rD);
        spy.setDaoM(mangaDAO);
        spy.doPost(request, response);

        verify(request).setAttribute("goodEnding","true");
        verify(context).getRequestDispatcher("/ProductsView/prodManagement.jsp");
    }

    @Test
    void removeSuccess() throws Exception{
        mangaDAO=mock(MangaDAO.class);
        Mockito.when(request.getParameter("goodEnding")).thenReturn(null);
        Mockito.when(request.getParameter("id")).thenReturn("5");
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(mangaDAO).delete(5);
        spy.setDaoM(mangaDAO);
        spy.doPost(request, response);

        verify(response).setStatus(200);
    }

    @Test
    void removeFailed() throws Exception{
        mangaDAO=mock(MangaDAO.class);
        Mockito.when(request.getParameter("goodEnding")).thenReturn(null);
        Mockito.when(request.getParameter("id")).thenReturn("12");
        Mockito.doAnswer(invocation -> {
            throw new Exception("Something");
        }).when(mangaDAO).delete(12);
        spy.setDaoM(mangaDAO);
        spy.doPost(request, response);

        verify(request).setAttribute("unexistentProd","error");
        verify(response).setStatus(201);
    }
}

package unit.servlets;

import Merchandising.MerchandiseService.MangaDAO;
import Merchandising.ProductsView.productsFilter;
import Merchandising.ProductsView.productsManagement;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class productsManagementTest {
    private productsManagement servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private productsManagement spy;
    private MangaDAO mangaDAO;

    @BeforeEach
    void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class) ;
        response = Mockito.mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        spy = Mockito.spy(new productsManagement());
        Mockito.when(spy.getServletConfig()).thenReturn(Mockito.mock(ServletConfig.class));
        ServletContext context = Mockito.mock(ServletContext.class);
        RequestDispatcher rd = mock(RequestDispatcher.class);
        Mockito.when(spy.getServletContext()).thenReturn(context);
    }


    @Test
    void redirectToChange() throws Exception{
        mangaDAO=mock(MangaDAO.class);
        Mockito.when(request.getParameter("redirect")).thenReturn("something");
        Mockito.when(request.getParameter("id")).thenReturn("5");

        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(request.getRequestDispatcher("/ProductsView/updateQuantity.jsp")).thenReturn(rD);
        spy.setMa(mangaDAO);
        spy.doPost(request, response);

        verify(request).getRequestDispatcher("/ProductsView/updateQuantity.jsp");
    }

    @Test
    void updateSuccess() throws Exception{
        mangaDAO=mock(MangaDAO.class);
        Mockito.when(request.getParameter("redirect")).thenReturn(null);
        Mockito.when(request.getParameter("id")).thenReturn("5");
        Mockito.when(request.getParameter("quantity")).thenReturn("6");
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(request.getRequestDispatcher("/ProductsView/homepage.jsp")).thenReturn(rD);
        Mockito.doAnswer(invocation -> {
            return null;
        }).when(mangaDAO).updateQuantity(6,5);
        spy.setMa(mangaDAO);
        spy.doPost(request, response);

        verify(request).setAttribute("success","Operazione avvenuta con successo");
        verify(request).getRequestDispatcher("/ProductsView/homepage.jsp");
    }

    @Test
    void updateFail() throws Exception{
        mangaDAO=mock(MangaDAO.class);
        Mockito.when(request.getParameter("redirect")).thenReturn(null);
        Mockito.when(request.getParameter("id")).thenReturn("9");
        Mockito.when(request.getParameter("quantity")).thenReturn("6");
        RequestDispatcher rD = Mockito.mock(RequestDispatcher.class);
        Mockito.when(request.getRequestDispatcher("/ProductsView/homepage.jsp")).thenReturn(rD);
        Mockito.doAnswer(invocation -> {
            throw new Exception("Something");
        }).when(mangaDAO).updateQuantity(6,9);
        spy.setMa(mangaDAO);
        spy.doPost(request, response);

        verify(request).setAttribute(eq("error"),any(String.class));
        verify(request).getRequestDispatcher("/ProductsView/homepage.jsp");
    }
}

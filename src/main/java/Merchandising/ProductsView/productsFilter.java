package Merchandising.ProductsView;

import Merchandising.MerchandiseService.Manga;
import Merchandising.MerchandiseService.MangaDAO;

import javax.faces.application.Application;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "productsFilter", value = "/productsFilter")
public class productsFilter extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");

        MangaDAO daoM = new MangaDAO(ds);
        String name = request.getParameter("name");
        String collections = request.getParameter("collection");
        System.out.println(name+"\n"+collections);
        try{
            ArrayList<Manga> lista = daoM.filterForEndUsers(name,collections);
            request.setAttribute("listaElementi",lista);
            RequestDispatcher rD = getServletContext().getRequestDispatcher("/ProductsView/catalog.jsp");
            rD.forward(request,response);
        }catch (Exception e){
            System.out.println(e.getMessage()+" errore");
            request.setAttribute("listaElementi",null);
            RequestDispatcher rD = getServletContext().getRequestDispatcher("/ProductsView/catalog.jsp");
            rD.forward(request,response);
        }
    }
}

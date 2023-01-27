package Merchandising.ProductsView;

import Merchandising.MerchandiseService.Manga;
import Merchandising.MerchandiseService.MangaDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "catalogServlet", value = "/catalogServlet")
public class catalogServlet extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("productsSupply");
        ArrayList<Manga> list = new ArrayList<Manga>();

        DataSource ds =(DataSource) getServletContext().getAttribute("DataSource");
        MangaDAO daoM = new MangaDAO(ds);
        try{
            list = daoM.retrieveAll();
            request.setAttribute("listaElementi",list);
            RequestDispatcher rD = getServletContext().getRequestDispatcher("/ProductsView/catalog.jsp");
            rD.forward(request,response);
        }catch (Exception e){
            System.out.println(e.getMessage());
            request.setAttribute("listaElementi",null);
            RequestDispatcher rD = getServletContext().getRequestDispatcher("/ProductsView/catalog.jsp");
            rD.forward(request,response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

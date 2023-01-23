package Merchandising.ProductsView;

import Merchandising.MerchandiseService.MangaDAO;
import Merchandising.MerchandiseService.ProductDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "catalogServlet", value = "/catalogServlet")
public class catalogServlet extends HttpServlet {

    DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
    private ProductDAO daoP = new ProductDAO(ds);

    private MangaDAO daoM = new MangaDAO(ds);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("productsSupply");
        String type = request.getParameter("type");
        ArrayList list = new ArrayList();
        if(type.equals("Manga")){
            DataSource ds =(DataSource) getServletContext().getAttribute("DataSource");
            MangaDAO daoM = new MangaDAO(ds);
           // list = daoM.retrieveAll();
            request.setAttribute("listaElementi",list);
            RequestDispatcher rD = getServletContext().getRequestDispatcher("/ProductsView/catalog.jsp");
            rD.forward(request,response);
        }
        else{
            DataSource ds =(DataSource) getServletContext().getAttribute("DataSource");
            ProductDAO daoP = new ProductDAO(ds);
            //list = daoP.retrieveAll();
            //list.addAll(daoM.retrieveAll());
            request.setAttribute("listaElementi",list);
            RequestDispatcher rD = getServletContext().getRequestDispatcher("/ProductsView/catalog.jsp");
            rD.forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

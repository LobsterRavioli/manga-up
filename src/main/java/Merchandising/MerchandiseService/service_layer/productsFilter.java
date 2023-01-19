package Merchandising.MerchandiseService.service_layer;

import Merchandising.MerchandiseService.beans.Product;
import Merchandising.MerchandiseService.dao_layer.implementations.MangaDAOImpl;
import Merchandising.MerchandiseService.dao_layer.implementations.ProductDAOImpl;
import utils.AbstractDAOFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@WebServlet(name = "productsFilter", value = "/productsFilter")
public class productsFilter extends HttpServlet {

    private AbstractDAOFactory factory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.JDBC);
    private ProductDAOImpl daoP = factory.getProductDAO();

    private MangaDAOImpl daoM = factory.getMangaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String collections = request.getParameter("collections");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        String state = request.getParameter("state");

        Product.ProductState ps;
        if(state.equals("NEW"))
            ps= Product.ProductState.NEW;
        else
            ps= Product.ProductState.USED;

        ArrayList lista;

        ArrayList listaP=null;
        try {
            if(minPrice=="")
                minPrice="0";

            if (maxPrice=="")
                maxPrice="0";

            lista = daoM.retrieveByFilters(name, collections, Float.parseFloat(minPrice), Float.parseFloat(maxPrice), ps);
            if(lista==null)
                lista=daoP.retrieveByFilters(name, collections, Float.parseFloat(minPrice), Float.parseFloat(maxPrice), ps);
            else {
                listaP = daoP.retrieveByFilters(name, collections, Float.parseFloat(minPrice), Float.parseFloat(maxPrice), ps);
                if (listaP != null)
                    lista.addAll(listaP);
            }

            if(lista==null)
                lista= new ArrayList();

            request.setAttribute("listaElementi",lista);
            RequestDispatcher rD = getServletContext().getRequestDispatcher("/ProductsView/catalog.jsp");
            rD.forward(request,response);
            return;
        }catch (Exception e){
            System.out.println(e.getMessage()+" errore");
            request.setAttribute("error",e.getMessage());
        }
    }
}

package Merchandising.ProductsView;

import Merchandising.MerchandiseService.Manga;
import Merchandising.MerchandiseService.MangaDAO;


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
        if(request.getParameter("UserManager")!=null){
            try {
                String minPrice = request.getParameter("minPrice");
                String maxPrice = request.getParameter("maxPrice");
                String soggettoOrder = request.getParameter("soggetto");
                String criteriaOrder = request.getParameter("criteria");
                boolean orderSubject;
                boolean orderCriteria;

                if(soggettoOrder.equals("name"))
                    orderSubject = true;
                else
                    orderSubject = false;

                if(criteriaOrder.equals("ASC"))
                    orderCriteria = true;
                else
                    orderCriteria = false;

                ArrayList<Manga> lista = daoM.filterForUsers(name,Float.parseFloat(minPrice),Float.parseFloat(maxPrice),collections,orderSubject,orderCriteria);
                request.setAttribute("listaElementi", lista);
                RequestDispatcher rD = getServletContext().getRequestDispatcher("/ProductsView/prodManagement.jsp");
                rD.forward(request,response);
            }catch (Exception e){
                request.setAttribute("listaElementi",null);
                request.setAttribute("errore","errore");
                RequestDispatcher rD = getServletContext().getRequestDispatcher("/ProductsView/prodManagement.jsp");
                rD.forward(request,response);
                return;
            }
        }
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

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

    MangaDAO daoM;


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<Manga> list = new ArrayList<Manga>();

        if(daoM == null) {
            DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
            daoM = new MangaDAO(ds);
        }

        HttpSession session = request.getSession(true);
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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void setDaoM(MangaDAO m){
        daoM=m;
    }
}

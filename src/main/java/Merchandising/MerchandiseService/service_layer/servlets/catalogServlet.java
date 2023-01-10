package Merchandising.MerchandiseService.service_layer.servlets;

import Merchandising.MerchandiseService.service_layer.implementations.MangaDAOImpl;
import Merchandising.MerchandiseService.service_layer.interfaces.MangaDAO;
import context.MainContext;

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
        String type = request.getParameter("type");
        ArrayList list = new ArrayList();
        if(type.equals("Manga")){
            DataSource ds =(DataSource) getServletContext().getAttribute("DataSource");
            MangaDAOImpl mD = new MangaDAOImpl(ds);
            list = mD.retrieveAll();
            request.setAttribute("listaElementi",list);
            RequestDispatcher rD = getServletContext().getRequestDispatcher("/MerchandisingView/catalog.jsp");
            rD.forward(request,response);
        }else{
            return;
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

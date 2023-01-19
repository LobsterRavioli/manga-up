package Merchandising.MerchandiseService.service_layer;

import Merchandising.MerchandiseService.dao_layer.implementations.MangaDAOImpl;
import Merchandising.MerchandiseService.dao_layer.implementations.ProductDAOImpl;
import User.AccountService.dao_layer.interfaces.AddressDAO;
import utils.AbstractDAOFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "catalogServlet", value = "/catalogServlet")
public class catalogServlet extends HttpServlet {

    private AbstractDAOFactory factory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.JDBC);
    private ProductDAOImpl daoP = factory.getProductDAO();

    private MangaDAOImpl daoM = factory.getMangaDAO();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("productsSupply");
        String type = request.getParameter("type");
        ArrayList list = new ArrayList();
        if(type.equals("Manga")){
            DataSource ds =(DataSource) getServletContext().getAttribute("DataSource");
            MangaDAOImpl daoM = new MangaDAOImpl(ds);
            list = daoM.retrieveAll();
            request.setAttribute("listaElementi",list);
            RequestDispatcher rD = getServletContext().getRequestDispatcher("/ProductsView/catalog.jsp");
            rD.forward(request,response);
        }
        else{
            DataSource ds =(DataSource) getServletContext().getAttribute("DataSource");
            ProductDAOImpl daoP = new ProductDAOImpl(ds);
            list = daoP.retrieveAll();
            list.addAll(daoM.retrieveAll());
            request.setAttribute("listaElementi",list);
            RequestDispatcher rD = getServletContext().getRequestDispatcher("/ProductsView/catalog.jsp");
            rD.forward(request,response);
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

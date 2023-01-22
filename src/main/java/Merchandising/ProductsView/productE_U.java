package Merchandising.MerchandiseService.service_layer;

import Merchandising.MerchandiseService.beans.Manga;
import Merchandising.MerchandiseService.dao_layer.implementations.MangaDAOImpl;
import Merchandising.MerchandiseService.dao_layer.implementations.ProductDAOImpl;
import utils.AbstractDAOFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "productE_U", value = "/productE_U")
public class productE_U extends HttpServlet {

    private AbstractDAOFactory factory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.JDBC);
    private ProductDAOImpl daoP = factory.getProductDAO();

    private MangaDAOImpl daoM = factory.getMangaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("prodId");
        String prodType = request.getParameter("prodType");
        if(prodType.equals("M")){
            Manga m = daoM.retrieveById(Integer.parseInt(id));
            request.setAttribute("prod",m);
            RequestDispatcher rD = getServletContext().getRequestDispatcher("/ProductsView/product.jsp");
            rD.forward(request,response);
        }else{
            Product p = daoP.retrieveById(Integer.parseInt(id));
            request.setAttribute("prod",p);
            RequestDispatcher rD = getServletContext().getRequestDispatcher("/ProductsView/product.jsp");
            rD.forward(request,response);
        }

    }
}

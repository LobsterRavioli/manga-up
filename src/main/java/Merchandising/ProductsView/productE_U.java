package Merchandising.ProductsView;
import Merchandising.MerchandiseService.Manga;
import Merchandising.MerchandiseService.MangaDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(name = "productE_U", value = "/productE_U")
public class productE_U extends HttpServlet {

    DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");

    private MangaDAO daoM = new MangaDAO(ds);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("prodId");

        try{
            Manga m = daoM.retrieveById(Integer.parseInt(id));
            request.setAttribute("prod",m);
            RequestDispatcher rD = getServletContext().getRequestDispatcher("/ProductsView/product.jsp");
            rD.forward(request,response);
        }catch (Exception e){
            request.setAttribute("prod",null);
            RequestDispatcher rD = getServletContext().getRequestDispatcher("/ProductsView/product.jsp");
            rD.forward(request,response);
        }

    }
}

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

    MangaDAO daoM;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("prodId");
        String err = request.getParameter("err");

        if(daoM == null) {
            DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
            daoM = new MangaDAO(ds);
        }
        if(err!=null){
            request.setAttribute("error",true);
        }
        String success = request.getParameter("success");
        if(success!=null){
            request.setAttribute("success",true);
        }

        System.out.println("Recursion :(");

        Manga m = daoM.retrieveById(Integer.parseInt(id));
        if(m==null){
            request.setAttribute("error","Errore relativo al prodotto selezionato: Prodotto non esistente");
            RequestDispatcher rD = getServletContext().getRequestDispatcher("/catalogServlet");
            rD.forward(request,response);
            return;
        }
        request.setAttribute("prod",m);
        RequestDispatcher rD = getServletContext().getRequestDispatcher("/ProductsView/product.jsp");
        rD.forward(request,response);


    }

    public void setDaoM(MangaDAO daoM){
        this.daoM=daoM;
    }
}

package Merchandising.ProductsView;

import Merchandising.MerchandiseService.MangaDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(name = "productsRemoval", value = "/productsRemoval")
public class productsRemoval extends HttpServlet {

    MangaDAO daoM;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(daoM==null) {
            DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
            daoM = new MangaDAO(ds);
        }
        String ending= request.getParameter("goodEnding");
        if(ending!=null && !ending.equals("")){
            request.setAttribute("goodEnding",ending);
            RequestDispatcher rD = getServletContext().getRequestDispatcher("/ProductsView/prodManagement.jsp");
            rD.forward(request,response);
            return;
        }

        String mangaID = request.getParameter("id");

        try{
            daoM.delete(Integer.parseInt(mangaID));
            response.setStatus(200);
            return;
        }catch (Exception e){
            System.out.println(mangaID);
            System.out.println(e.getMessage());
            request.setAttribute("unexistentProd","error");
            response.setStatus(201);
            return;
        }
    }

    public void setDaoM(MangaDAO ma){
        this.daoM=ma;
    }
}

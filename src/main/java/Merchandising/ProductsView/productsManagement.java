package Merchandising.ProductsView;

import Merchandising.MerchandiseService.Manga;
import Merchandising.MerchandiseService.MangaDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(name = "productsManagement", value = "/productsManagement")
public class productsManagement extends HttpServlet {

    MangaDAO ma;
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(ma==null) {
            DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
            ma = new MangaDAO(ds);
        }
        String red = request.getParameter("redirect");
        if(red!=null) {
            String id = request.getParameter("id");
            request.setAttribute("id",id);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ProductsView/updateQuantity.jsp");
            dispatcher.forward(request, response);
            return;
        }else{
            String id = request.getParameter("id");
            String quantity = request.getParameter("quantity");
            int quantità = Integer.parseInt(quantity);
            try{
                System.out.println(quantità+"\n"+id);
                ma.updateQuantity(quantità,Integer.parseInt(id));
                request.setAttribute("success","Operazione avvenuta con successo");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/ProductsView/homepage.jsp");
                dispatcher.forward(request, response);
                return;
            }catch (Exception e){
                System.out.println(e.getMessage());
                request.setAttribute("error",e.getMessage());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/ProductsView/homepage.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }

    }
    public void setMa(MangaDAO ma){
        this.ma=ma;
    }
}

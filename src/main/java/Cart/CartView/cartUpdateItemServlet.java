package Cart.CartView;

import Cart.CheckoutService.CartDAO;
import User.AccountService.EndUser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(name = "cartDecreaseServlet", value = "/cartDecreaseServlet")
public class cartUpdateItemServlet extends HttpServlet {

    DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
    private CartDAO dao = new CartDAO(ds);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EndUser endUser = (EndUser) request.getSession().getAttribute("user");
        if(endUser==null){
            request.setAttribute("error","Utente non presente in sessione");
            response.sendRedirect(getServletContext().getContextPath()+"/loginServlet");
            return;
        }


        String quantity = request.getParameter("quantity");
        String prod_id = request.getParameter("prod");
        String type = request.getParameter("type");
        response.setContentType("text/plain");
/*
        try{
            if(type.equals("M")){
                if(dao.addElement(endUser.getId(),Integer.parseInt(prod_id),Integer.parseInt(quantity), Manga.class) == true) {
                    response.getWriter().write("OK");
                }else{
                    response.getWriter().write("Problem");
                }
            }else{
                if(dao.addElement(endUser.getId(),Integer.parseInt(prod_id),Integer.parseInt(quantity), Product.class)==true){
                    response.getWriter().write("OK");
                }else{
                    response.getWriter().write("Problem");
                }
            }
        }catch (Exception e){
            request.setAttribute("error",e.getMessage());
        }
 */
    }
}

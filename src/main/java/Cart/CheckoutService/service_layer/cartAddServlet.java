package Cart.CheckoutService.service_layer;

import Cart.CheckoutService.dao_layer.interfaces.CartDAO;
import Merchandising.MerchandiseService.beans.Manga;
import User.AccountService.beans.EndUser;
import utils.AbstractDAOFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "cartAddServlet", value = "/cartAddServlet")
public class cartAddServlet extends HttpServlet {
    private AbstractDAOFactory factory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.JDBC);
    private CartDAO dao = factory.getCartDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EndUser endUser = (EndUser) request.getSession().getAttribute("user");
        if(endUser==null){
            request.setAttribute("error","Utente non presente in sessione");

            return;
        }
        String quantity = request.getParameter("quantity");
        String prod_id = request.getParameter("prod");
        String type = request.getParameter("type");
        response.setContentType("text/plain");

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
    }
}

package Cart.CartService.service_layer;

import Cart.CartService.dao_layer.interfaces.CartDAO;
import Merchandising.MerchandiseService.beans.Manga;
import Merchandising.MerchandiseService.beans.Product;
import User.AccountService.beans.EndUser;
import User.AccountService.dao_layer.interfaces.AddressDAO;
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
        String quantity = request.getParameter("quantity");
        String prod_id = request.getParameter("prod");
        String type = request.getParameter("type");

        response.setContentType("text/plain");

        if(type.equals("M")){
            if(dao.addElement(endUser.getId(),Integer.parseInt(prod_id),Integer.parseInt(quantity), Manga.class)==true) {
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
    }
}

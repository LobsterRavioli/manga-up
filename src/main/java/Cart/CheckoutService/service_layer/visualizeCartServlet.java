package Cart.CheckoutService.service_layer;

import Cart.CheckoutService.dao_layer.UserNotAssociatedException;
import Cart.CheckoutService.dao_layer.interfaces.CartDAO;
import User.AccountService.beans.EndUser;

import Cart.CheckoutService.beans.Cart;
import utils.AbstractDAOFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "visualizeCartServlet", value = "/visualizeCartServlet")
public class visualizeCartServlet extends HttpServlet {

    private AbstractDAOFactory factory = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.JDBC);
    private CartDAO dao = factory.getCartDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession();
        EndUser u = (EndUser) request.getSession().getAttribute("user");
        Cart c;
        if(s.getAttribute("cart")==null){
            try {
                c = new Cart(dao.retrieveCart(u.getId()));
            } catch (UserNotAssociatedException e) {
                throw new RuntimeException(e);
            }
            s.setAttribute("cart",c);
        }
        else{
            c = (Cart) s.getAttribute("cart");
        }
        request.setAttribute("cart",c);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/CartView/cart.jsp"));
        dispatcher.forward(request, response);
        return;
    }
}

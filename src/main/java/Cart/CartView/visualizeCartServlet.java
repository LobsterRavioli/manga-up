package Cart.CartView;

import Cart.CheckoutService.Cart;
import Cart.CheckoutService.CartDAO;
import Merchandising.MerchandiseService.Manga;
import User.AccountService.EndUser;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "visualizeCartServlet", value = "/visualizeCartServlet")
public class visualizeCartServlet extends HttpServlet {

    DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
    private CartDAO dao = new CartDAO(ds);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart c = new Cart((HashMap<Manga, Integer>) request.getSession().getAttribute("cart"));
        request.setAttribute("listaProdottiCart",c);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/cart.jsp");
        rd.forward(request,response);
        return;
        /*
        try{
            if(s.getAttribute("cart")==null){
                c = new Cart(dao.retrieveCart(u.getId()));
                s.setAttribute("cart",c);
            }
            else{
                c = (Cart) s.getAttribute("cart");
            }
        request.setAttribute("cart",c);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/CartView/cart.jsp"));
        dispatcher.forward(request, response);
        return;
        }catch (Exception e){
            request.setAttribute("error",e.getMessage());
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/CartView/cart.jsp"));
            dispatcher.forward(request, response);
        }

    }
}

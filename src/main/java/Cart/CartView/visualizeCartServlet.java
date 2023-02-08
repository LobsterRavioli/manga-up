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


    private CartDAO dao;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (dao == null){
            DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
            dao = new CartDAO(ds);
        }


        EndUser user = (EndUser) request.getSession(false).getAttribute("user");
        try{
            request.getSession().setAttribute("cart",new Cart(dao.retrieveByUser(user)));
        }catch (Exception e){
            request.getSession().setAttribute("cart",new Cart(new HashMap<Manga,Integer>()));
        }
        response.sendRedirect(request.getContextPath()+"/CartView/cart.jsp");
        return;
    }

    public void setDao(CartDAO dao) {
        this.dao = dao;
    }
}

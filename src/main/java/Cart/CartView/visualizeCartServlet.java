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


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*Cart c = new Cart((HashMap<Manga, Integer>) request.getSession().getAttribute("cart"));
        request.setAttribute("listaProdottiCart", c);/*
         */

        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        CartDAO dao = new CartDAO(ds);
        EndUser user = new EndUser(1,"francesco","M.1234");
        request.getSession().setAttribute("user",user);
        try{
            request.getSession().setAttribute("cart",new Cart(dao.retrieveByUser(user)));
        }catch (Exception e){
            request.getSession().setAttribute("cart",new Cart(new HashMap<Manga,Integer>()));
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/CartView/cart.jsp");
        rd.forward(request, response);
    }
}

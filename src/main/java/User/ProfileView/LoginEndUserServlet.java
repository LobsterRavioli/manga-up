package User.ProfileView;

import Cart.CheckoutService.Cart;
import Cart.CheckoutService.CartDAO;
import Merchandising.MerchandiseService.Manga;
import User.AccountService.EndUser;
import User.AccountService.EndUserDAO;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/LoginEndUserServlet")
public class LoginEndUserServlet extends HttpServlet {

    private EndUserDAO endUserDAO;

    private CartDAO cartDAO;

    public static final String ERROR_MESSAGE = "Username o password non validi";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (!validate(email, password)){
            request.setAttribute("error_message", ERROR_MESSAGE);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/ProfileView/login_end_user.jsp"));
            dispatcher.forward(request, response);
            return;
        }

        if (endUserDAO == null) {
            DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
            endUserDAO = new EndUserDAO(ds);
        }

        if(cartDAO == null){
            DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
            cartDAO = new CartDAO(ds);
        }

        EndUser user = endUserDAO.login(email,password);
        HttpSession session = request.getSession();

        if(user == null){
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/ProfileView/login_end_user.jsp"));
            request.setAttribute("error_message", ERROR_MESSAGE);
            dispatcher.forward(request, response);
            return;
        }

        session.setAttribute("user",user);
        try{
            Cart c = new Cart(cartDAO.retrieveByUser(user));
            session.setAttribute("cart", c);
        } catch (Exception e){
           session.setAttribute("cart",new Cart(new HashMap<Manga,Integer>()));
        }

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/ProductsView/endUserHomepage.jsp"));
        dispatcher.forward(request, response);
    }

    private static boolean validate(String email, String password) {
        if (email == null || password == null) return false;
        return email.matches(EndUser.EMAIL_REGEX) && password.matches(EndUser.PASSWORD_REGEX);
    }

    public void setEndUserDAO(EndUserDAO dao){
        this.endUserDAO = dao;
    }
    public void setCartDAO(CartDAO dao){
        this.cartDAO = dao;
    }
}

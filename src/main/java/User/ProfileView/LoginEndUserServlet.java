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
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/LoginEndUserServlet")
public class LoginEndUserServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        EndUserDAO dao = new EndUserDAO(ds);
        response.setContentType("text/html");
        String email = request.getParameter("username");
        String password = request.getParameter("password");
        EndUser user = dao.login(email,password);
        HttpSession session = request.getSession();

        if(user == null){
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/ProfileView/login_end_user.jsp"));
            dispatcher.forward(request, response);
            return;
        }

        session.setAttribute("user",user);
        try{

            CartDAO cD = new CartDAO(ds);

            Cart c= new Cart(cD.retrieveByUser(user));

            session.setAttribute("cart",c);

        }catch (Exception e){
           session.setAttribute("cart",new Cart(new HashMap<Manga,Integer>()));
        }

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(response.encodeURL("/ProductsView/endUserHomepage.jsp"));
        dispatcher.forward(request, response);
    }
}

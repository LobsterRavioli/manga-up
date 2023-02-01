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

@WebServlet(name = "cartUpdateItemServlet", value = "/cartUpdateItemServlet")
public class cartUpdateItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        System.out.println("Sono LA UPDATE");
        CartDAO dao = new CartDAO(ds);
        String aggiunta = request.getParameter("add");
        System.out.println(aggiunta);
        HttpSession s = request.getSession(false);
        if (s == null){
            response.setStatus(201);
            return;
        }else if(s.getAttribute("user")==null){
            response.setStatus(201);
            return;
        }
        EndUser endUser = (EndUser) s.getAttribute("user");
        String quantity = request.getParameter("quantity");
        String prod_id = request.getParameter("id");
        String maxQ = request.getParameter("maxQ");
        Manga m =new Manga(Integer.parseInt(prod_id));
        Cart ca = (Cart) s.getAttribute("cart");
        try{
            m.setQuantity(Integer.parseInt(maxQ));
            if(quantity.equals("0")){
                dao.removeProduct(m,endUser);
                ca.removeFromCart(m);
                s.setAttribute("cart",ca);
                response.sendRedirect(request.getContextPath() + "/CartView/cart.jsp?agg=true");
                return;
            }
            dao.updateProduct(m,Integer.parseInt(quantity),endUser);
            ca.updateProdotto(m,Integer.parseInt(quantity));
            if(aggiunta.equals("true")){
                response.sendRedirect(request.getContextPath() + "/CartView/cart.jsp?agg=true");
                return;
            }
            response.setStatus(200);
            response.getWriter().write(quantity);
            return;
            }catch (Exception e){
                System.out.println(e.getMessage());
                if(e.getMessage().equals("utente non esistente")){
                response.setStatus(201);
                }
                else if (e.getMessage().equals("prodotto non esistente")) {
                    response.setStatus(202);

                }else if(e.getMessage().equals("quantità inserita non valida")){
                    response.setStatus(203);
                }
                return;
        }
    }
}

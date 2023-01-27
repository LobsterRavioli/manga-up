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
        try{
            m.setQuantity(Integer.parseInt(maxQ));
            dao.updateProduct(m,Integer.parseInt(quantity),endUser);
            Cart c = (Cart) request.getSession().getAttribute("cart");
            c.updateProdotto(m,Integer.parseInt(quantity));
            if(aggiunta.equals("true")){
                System.out.println("non so perchè");
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/CartView/cart.jsp");
                rd.forward(request, response);
                return;
            }
            response.setStatus(200);
            response.getWriter().write(quantity);
            return;
            }catch (Exception e){
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

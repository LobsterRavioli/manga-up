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
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "cartAddServlet", value = "/cartAddServlet")
public class cartAddServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        CartDAO dao = new CartDAO(ds);
        HttpSession s = request.getSession(false);
        if (s == null){
            response.setStatus(201);
            return;
        }else if(s.getAttribute("user")==null){
            response.setStatus(201);
            return;
        }
        PrintWriter pw;
        EndUser endUser = (EndUser) s.getAttribute("user");
        String quantity = request.getParameter("quantity");
        String prod_id = request.getParameter("id");
        String maxQ = request.getParameter("maxQ");
        System.out.println(request.getQueryString());
        System.out.println(Integer.parseInt(quantity));
        Manga m =new Manga(Integer.parseInt(prod_id));
        Cart c = (Cart) s.getAttribute("cart");
        m.setQuantity(Integer.parseInt(maxQ));
        for (Map.Entry<Manga,Integer> set : c.getProdotti().entrySet()) {
            Manga inCart = set.getKey();
            if (inCart.getId() == m.getId()) {
                try {
                    dao.updateProduct(m, Integer.parseInt(quantity), endUser);
                    pw=response.getWriter();
                    pw.print("Quantità correttamente aggiornata nel carrello");
                    response.setStatus(200);
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
        try{
            m.setQuantity(Integer.parseInt(maxQ));
            dao.addProduct(m,Integer.parseInt(quantity),endUser);
            pw=response.getWriter();
            pw.print("Prodotto correttamente inserito nel carrello");
            response.setStatus(200);
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

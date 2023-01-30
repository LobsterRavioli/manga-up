package Cart.CartView;

import Cart.CheckoutService.CartDAO;
import Merchandising.MerchandiseService.Manga;
import Merchandising.MerchandiseService.MangaDAO;
import Order.DispatchService.Order;
import User.AccountService.CreditCard;
import User.AccountService.EndUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import static java.util.Map.Entry;

@WebServlet(name = "checkoutServlet", value = "/checkoutServlet")
public class checkoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int mangaQuantityFromWarehouse, mangaQuantityFromCart;
            DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
            String addressInfo = req.getParameter("address");
            String creditCardInfo = req.getParameter("creditCard");


            CartDAO cartDAO = new CartDAO(ds);
            MangaDAO mangaDAO = new MangaDAO(ds);
            EndUser user = (EndUser) req.getSession(false).getAttribute("user");
            HashMap<Manga, Integer> map = cartDAO.retrieveByUser(user);

            if(!isAvailableProducts(map)){
                // redirect to error page
            }

            for (Entry<Manga, Integer> entry : map.entrySet()) {
                Manga manga = entry.getKey();
                mangaQuantityFromCart = entry.getValue();
                manga.setQuantity(manga.getQuantity() - mangaQuantityFromCart);
                mangaDAO.updateQuantity(manga);
            }

            for (Entry<Manga, Integer> entry : map.entrySet()) {
                Manga manga = entry.getKey();
                mangaQuantityFromCart = entry.getValue();
                manga.setQuantity(mangaQuantityFromCart);
            }

            // Order order = new Order(user);





            // creare l'ordine e assegnarlo al manager

            resp.sendRedirect("/CartView/checkout.jsp");


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static boolean isAvailableProducts(HashMap<Manga, Integer> map){

        int mangaQuantityFromWarehouse,mangaQuantityFromCart;
        for(Entry<Manga, Integer> entry : map.entrySet()){
            Manga manga = entry.getKey();
            mangaQuantityFromWarehouse = manga.getQuantity();
            mangaQuantityFromCart = entry.getValue();
            if(mangaQuantityFromCart > mangaQuantityFromWarehouse){
                return false;
            }
        }
        return true;
    }






}

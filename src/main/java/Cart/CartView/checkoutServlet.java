package Cart.CartView;

import Cart.CheckoutService.CartDAO;
import Merchandising.MerchandiseService.Manga;
import Merchandising.MerchandiseService.MangaDAO;
import Order.DispatchService.Order;
import Order.DispatchService.OrderSubmissionFacade;
import Order.DispatchService.OrderSubmissionFacadeImp;
import User.AccountService.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
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
            EndUser endUser = (EndUser) req.getSession(false).getAttribute("user");

            int addressId = Integer.parseInt(req.getParameter("address"));
            int creditCardId = Integer.parseInt(req.getParameter("creditCard"));
            CreditCard userCard = new CreditCardDAO(ds).findById(creditCardId);
            Address addressEndUser = new AddressDAO(ds).findById(addressId);
            CartDAO cartDAO = new CartDAO(ds);
            MangaDAO mangaDAO = new MangaDAO(ds);
            HashMap<Manga, Integer> map = cartDAO.retrieveByUser(endUser);

            if(!isAvailableProducts(map)){
                req.setAttribute("error","Quantità prodotti non disponibili");
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/CartView/cart.jsp");
                rd.forward(req, resp);
                return;
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

            Order order = new Order(endUser, addressEndUser, userCard);

            OrderSubmissionFacade facade = (OrderSubmissionFacade) getServletContext().getAttribute(OrderSubmissionFacade.ORDER_SUBMISSION_FACADE);

            facade.createOrder(order, new ArrayList<Manga>(map.keySet()));

            cartDAO.toEmptyCart(order.getEndUser());

            req.getSession(false).removeAttribute("cart");

            RequestDispatcher rd = getServletContext().getRequestDispatcher("/CartView/homepage.jsp");

            rd.forward(req, resp);
            return;

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

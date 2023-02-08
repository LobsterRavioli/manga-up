package Cart.CartView;


import Cart.CheckoutService.Cart;
import Cart.CheckoutService.CartDAO;
import Merchandising.MerchandiseService.Manga;
import Merchandising.MerchandiseService.MangaDAO;
import Order.DispatchService.Order;
import Order.DispatchService.OrderSubmissionFacade;
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
            int creditCardId = Integer.parseInt(req.getParameter("card"));
            CreditCard userCard = new CreditCardDAO(ds).findById(creditCardId);
            if(userCard==null)
                System.out.println("Problema null");

            Address addressEndUser = new AddressDAO(ds).findById(addressId);
            CartDAO cartDAO = new CartDAO(ds);
            MangaDAO mangaDAO = new MangaDAO(ds);
            HashMap<Manga, Integer> map = cartDAO.retrieveByUser(endUser);

            if(!isAvailableProducts(map)){
                cartDAO.toEmptyCart(endUser);
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
            UserFacade facadeUser = (UserFacade) getServletContext().getAttribute(UserFacade.USER_FACADE);

            facade.createOrder(order, new ArrayList<Manga>(map.keySet()),facadeUser.managerEngagement());

            cartDAO.toEmptyCart(order.getEndUser());

            req.getSession(false).setAttribute("cart",new Cart(new HashMap<Manga,Integer>()));

            RequestDispatcher rd = getServletContext().getRequestDispatcher("/OrderDetailServlet?order_id="+order.getId()); //Andrebbe rimandato a order_detail_listServlet

            rd.forward(req, resp);
            return;

        } catch (Exception e) {
            System.out.println(e.getMessage());
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

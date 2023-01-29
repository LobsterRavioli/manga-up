package Cart.CartView;

import Cart.CheckoutService.CartDAO;
import Merchandising.MerchandiseService.MangaDAO;
import Order.DispatchService.OrderDAO;
import Order.DispatchService.OrderRow;
import Order.DispatchService.OrderRowDAO;
import User.AccountService.EndUserDAO;

import javax.sql.DataSource;

public class CartServicesImp {

    private CartDAO cartDAO;
    private EndUserDAO endUserDAO;
    private MangaDAO mangaDAO;
    private OrderDAO orderDAO;
    private OrderRowDAO orderRowDAO;

    public CartServicesImp(DataSource ds){
        cartDAO = new CartDAO(ds);
        endUserDAO = new EndUserDAO(ds);
        mangaDAO = new MangaDAO(ds);
        orderDAO = new OrderDAO(ds);
        orderRowDAO = new OrderRowDAO(ds);
    }


    public void checkout(){

    }

}

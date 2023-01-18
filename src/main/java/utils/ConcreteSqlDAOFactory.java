package utils;

import Cart.CheckoutService.dao_layer.implementations.CartDAOImpl;
import Cart.CheckoutService.dao_layer.interfaces.CartDAO;
import Merchandising.MerchandiseService.dao_layer.implementations.AutoreDAOImpl;
import Merchandising.MerchandiseService.dao_layer.implementations.MangaDAOImpl;
import Merchandising.MerchandiseService.dao_layer.implementations.ProductDAOImpl;
import Order.DispatchService.dao_layer.implementations.OrderDAOImp;
import Order.DispatchService.dao_layer.interfaces.OrderDAO;
import User.AccountService.dao_layer.implementations.AddressDAOImp;
import User.AccountService.dao_layer.implementations.CreditCardDAOImp;
import User.AccountService.dao_layer.implementations.EndUserDAOImp;
import User.AccountService.dao_layer.implementations.UserRoleDAOImp;
import User.AccountService.dao_layer.interfaces.AddressDAO;
import User.AccountService.dao_layer.interfaces.CreditCardDAO;
import User.AccountService.dao_layer.interfaces.EndUserDAO;
import User.AccountService.dao_layer.interfaces.UserRoleDAO;

import javax.sql.DataSource;

public class ConcreteSqlDAOFactory extends AbstractDAOFactory {
    DataSource ds;

    ConcreteSqlDAOFactory(DataSource ds){
        this.ds = ds;
    }


    @Override
    public AddressDAO getAddressDAO() {
        return new AddressDAOImp(ds);
    }

    @Override
    public CreditCardDAO getCreditCardDAO() { return new CreditCardDAOImp(ds); }

    @Override
    public EndUserDAO getEndUserDAO() {
       return new EndUserDAOImp(ds);
    }

    @Override
    public UserRoleDAO getUserRoleDAO() {
        return new UserRoleDAOImp(ds);
    }

    @Override
    public OrderDAO getOrderDAO() {
        return new OrderDAOImp(ds);
    }

    public MangaDAOImpl getMangaDAO() {
        return new MangaDAOImpl(ds);
    }
    public ProductDAOImpl getProductDAO() {
        return new ProductDAOImpl(ds);
    }
    public AutoreDAOImpl getAutoreDAO() {
        return new AutoreDAOImpl(ds);
    }

    public CartDAO getCartDAO() {
        return new CartDAOImpl(ds);
    }
}

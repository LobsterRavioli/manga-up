package utils;

import Cart.CheckoutService.dao_layer.interfaces.CartDAO;
import Merchandising.MerchandiseService.dao_layer.implementations.MangaDAOImpl;
import Merchandising.MerchandiseService.dao_layer.implementations.ProductDAOImpl;
import Order.DispatchService.dao_layer.interfaces.OrderDAO;
import User.AccountService.dao_layer.interfaces.AddressDAO;
import User.AccountService.dao_layer.interfaces.CreditCardDAO;
import User.AccountService.dao_layer.interfaces.EndUserDAO;
import User.AccountService.dao_layer.interfaces.UserRoleDAO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class AbstractDAOFactory {

    public static final String JDBC = "JDBC";
    public abstract AddressDAO getAddressDAO();

    public abstract CreditCardDAO getCreditCardDAO();

    public abstract EndUserDAO getEndUserDAO();

    public abstract MangaDAOImpl getMangaDAO();

    public abstract ProductDAOImpl getProductDAO();

    public abstract AutoreDAOImpl getAutoreDAO();
    public abstract UserRoleDAO getUserRoleDAO();
    public abstract OrderDAO getOrderDAO();

    public abstract CartDAO getCartDAO();

    public static AbstractDAOFactory getDAOFactory(String factory){


        switch (factory) {
            case "JDBC":
                try {
                    Context initCtx = new InitialContext();
                    Context envCtx = (Context) initCtx.lookup("java:comp/env");
                    DataSource dataSource = (DataSource) envCtx.lookup("jdbc/manga-up");
                    return new ConcreteSqlDAOFactory(dataSource);
                } catch (NamingException e) {
                    throw new DAOException(
                            "DataSource '" + "jdbc/manga-up" + "' is missing in JNDI.", e);
                }

            default:
                return null;
        }
    }

}

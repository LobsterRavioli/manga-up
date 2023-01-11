package utils;

import Order.DispatchService.dao_layer.implementations.OrderDAOImp;
import Order.DispatchService.dao_layer.interfaces.OrderDAO;
import User.AccountService.dao_layer.implementations.AddressDAOImp;
import User.AccountService.dao_layer.implementations.EndUserDAOImp;
import User.AccountService.dao_layer.implementations.CreditCardDAOImp;
import User.AccountService.dao_layer.implementations.UserRoleDAOImp;
import User.AccountService.dao_layer.interfaces.*;

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
}

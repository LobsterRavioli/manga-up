package utils;

import Order.DispatchService.dao_layer.implementations.OrderDAOImp;
import Order.DispatchService.dao_layer.interfaces.OrderDAO;
import User.AccountService.dao_layer.implementations.AddressDAOImp;
import User.AccountService.dao_layer.implementations.EndUserDAOImp;
import User.AccountService.dao_layer.implementations.PaymentCardDAOImp;
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
    public PaymentCardDAO getPaymentDAO() { return new PaymentCardDAOImp(ds); }

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

package User.AccountService.service_layer;

import User.AccountService.beans.Address;
import User.AccountService.beans.EndUser;
import User.AccountService.beans.PaymentCard;
import User.AccountService.beans.User;
import User.AccountService.dao_layer.implementations.AddressDAOImp;
import User.AccountService.dao_layer.interfaces.*;


import javax.sql.DataSource;
import java.sql.SQLException;

public class AccountServiceImp implements AccountService {


    private EndUserDAO endUserDao;
    private PaymentCardDAO cardDAO;
    private AddressDAO addressDAO;

    public AccountServiceImp(){}

    @Override
    public User login(String username, String password){
        return null;
    }

    @Override
    public void registration(EndUser user){
        if(EndUser.checkConventions(user))
            endUserDao.create(user);
    }

    @Override
    public void addAddress(EndUser user, Address address) {
        return;
    }

    @Override
    public void removeAddress(EndUser user, Address address) {
        return;
    }

    @Override
    public void addPaymentCard(EndUser user, PaymentCard card) {
        return;
    }

    @Override
    public void removePaymentCard(EndUser user, PaymentCard card) {
    }

}

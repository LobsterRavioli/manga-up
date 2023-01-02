package User.AccountService.service_layer;

import User.AccountService.beans.Address;
import User.AccountService.beans.EndUser;
import User.AccountService.beans.PaymentCard;
import User.AccountService.beans.User;
import User.AccountService.dao_layer.interfaces.*;
import com.sun.tools.sjavac.Util;


import java.sql.SQLException;

public class AccountServiceImp implements AccountService {

    private EndUserDAO endUserDao;
    private CatalogManagerUserDAO catalogDAO;
    private OrderManagerUserDAO orderDAO;
    private PaymentCardDAO cardDAO;
    private AddressDAO addressDAO;


    @Override
    public User login(String username, String password){

        User u;
        try {

            if ((u = endUserDao.find(username, password)) != null) {
                return u;
            } else if ((u = catalogDAO.findByUsernameAndPassword(username, password)) != null) {
                return u;
            }
            else {
                u = orderDAO.findByUsernameAndPassword(username, password);
                return u;
            }

        } catch (SQLException e){
            Util.getStackTrace(e);
        }

        return null;
    }

    @Override
    public void registration(EndUser user) {
        endUserDao.create(user);
        return;
    }

    @Override
    public void addAddress(EndUser user, Address address) {
        addressDAO.create(user, address);
        return;
    }

    @Override
    public void removeAddress(EndUser user, Address address) {
        addressDAO.remove(user, address);
        return;
    }

    @Override
    public void addPaymentCard(EndUser user, PaymentCard card) {
        cardDAO.create(user, card);
        return;
    }

    @Override
    public void removePaymentCard(EndUser user, PaymentCard card) {

    }

}

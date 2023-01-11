package User.AccountService.dao_layer.interfaces;

import User.AccountService.beans.EndUser;
import User.AccountService.beans.CreditCard;

import java.util.Collection;

public interface PaymentCardDAO {
    void create(CreditCard card);
    void delete(CreditCard card);
    void update(CreditCard card);
    Collection find(EndUser user);
    boolean existsCvc(String cvc);
}

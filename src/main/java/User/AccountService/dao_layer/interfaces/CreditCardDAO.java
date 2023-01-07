package User.AccountService.dao_layer.interfaces;

import User.AccountService.beans.Address;
import User.AccountService.beans.CreditCard;
import User.AccountService.beans.EndUser;

import java.util.Collection;

public interface CreditCardDAO {
    void create(CreditCard card);
    void delete(CreditCard card);
    void update(CreditCard card);
    public CreditCard find(int id);

    public boolean existsCvc(String cvc);
    Collection list();
    Collection find(EndUser user);

}

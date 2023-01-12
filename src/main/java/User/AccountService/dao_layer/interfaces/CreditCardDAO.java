package User.AccountService.dao_layer.interfaces;

import User.AccountService.beans.CreditCard;

import java.util.Collection;

public interface CreditCardDAO {
    void create(CreditCard card);
    void delete(CreditCard card);
    void update(CreditCard card);
    Collection findAllByEnduser(CreditCard userCard);

    Collection findSingleByEnduser(CreditCard userCard);

    boolean existsCvc(String cvc);

}

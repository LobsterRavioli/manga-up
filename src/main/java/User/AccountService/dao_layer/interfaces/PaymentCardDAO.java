package User.AccountService.dao_layer.interfaces;

import User.AccountService.beans.PaymentCard;

public interface PaymentCardDAO {
    void create(PaymentCard card);
    void delete(PaymentCard card);
    void update(PaymentCard card);
    PaymentCard retrieve(int id);
}

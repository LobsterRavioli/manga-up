package User.AccountService.service_layer;

import User.AccountService.beans.Address;
import User.AccountService.beans.PaymentCard;

public interface AccountService {

    public User login(String username, String password);
    public void registration(EndUser user);
    public void addAddress(EndUser user, Address address);
    public void removeAddress(EndUser user, Address address);
    public void addPaymentCard(EndUser user, PaymentCard card);
    public void removePaymentCard(EndUser user, PaymentCard card);

}

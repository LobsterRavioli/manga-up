package User.AccountService.service_layer;

import User.AccountService.beans.Address;
import User.AccountService.beans.EndUser;
import User.AccountService.beans.PaymentCard;
import User.AccountService.beans.User;

public interface AccountService {

    public User login(String username, String password);
    public void registration(EndUser user); //throws ConventionExceptions;
    public void addAddress(EndUser user, Address address);
    public void removeAddress(EndUser user, Address address);
    public void addPaymentCard(EndUser user, PaymentCard card);
    public void removePaymentCard(EndUser user, PaymentCard card);

}

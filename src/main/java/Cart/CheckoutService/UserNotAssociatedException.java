package Cart.CheckoutService.dao_layer;

import User.AccountService.beans.User;

public class UserNotAssociatedException extends Exception{

    public UserNotAssociatedException(){
        super("L'utente inserito non è presente nel db");
    }

}

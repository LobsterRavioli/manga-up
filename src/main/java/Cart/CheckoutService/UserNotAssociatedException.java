package Cart.CheckoutService;

public class UserNotAssociatedException extends Exception{

    public UserNotAssociatedException(){
        super("L'utente inserito non è presente nel db");
    }

}

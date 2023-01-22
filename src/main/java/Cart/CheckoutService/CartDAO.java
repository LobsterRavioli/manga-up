package Cart.CheckoutService.dao_layer.interfaces;

import Cart.CheckoutService.dao_layer.UserNotAssociatedException;
import Merchandising.MerchandiseService.dao_layer.exceptions.InvalidQuantityException;
import Merchandising.MerchandiseService.dao_layer.exceptions.NonExistentProductException;

import java.util.HashMap;

public interface CartDAO {

    public HashMap<Object,Integer> retrieveCart(int userID)throws UserNotAssociatedException;
    public boolean removeElement(int user_id,int prod_ID,Class type)throws NonExistentProductException,UserNotAssociatedException;
    public boolean addElement(int user_id,int prod_id,int quantity,Class type) throws NonExistentProductException,UserNotAssociatedException,InvalidQuantityException;
    public boolean updateElement(int user_id,int prod_id,int quantity,Class c) throws NonExistentProductException,UserNotAssociatedException,InvalidQuantityException;

}

package Cart.CartService.dao_layer.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

public interface CartDAO {

    public HashMap<Object,Integer> retrieveCart(int userID);

    public boolean removeElement(int user_id,int prod_ID,Class type);

    public boolean addElement(int user_id,int prod_id,int quantity,Class type);

    public boolean updateElement(int user_id,int prod_id,int quantity,Class c);

}

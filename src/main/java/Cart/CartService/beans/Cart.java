package Cart.CartService.beans;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart {

    HashMap<Object,Integer> prodotti;

    public Cart(HashMap<Object,Integer> prod){
        prodotti = prod;
    }

    public void addToCart(Object prod,int quantity){
        prodotti.put(prod,quantity);
    }

    public void removeFromCart(Object prod){
        prodotti.remove(prod);
    }

    public HashMap<Object, Integer> getProdotti() {
        return prodotti;
    }

    public void setProdotti(HashMap<Object, Integer> prodotti) {
        this.prodotti = prodotti;
    }
}

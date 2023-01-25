package Cart.CheckoutService;

import Merchandising.MerchandiseService.Manga;

import java.util.HashMap;

public class Cart {

    HashMap<Manga,Integer> prodotti;

    public Cart(HashMap<Manga,Integer> prod){
        prodotti = prod;
    }

    public void addToCart(Manga prod, int quantity){
        prodotti.put(prod,quantity);
    }

    public void removeFromCart(Manga prod){
        prodotti.remove(prod);
    }

    public HashMap<Manga, Integer> getProdotti() {
        return prodotti;
    }

    public void setProdotti(HashMap<Manga, Integer> prodotti) {
        this.prodotti = prodotti;
    }
}

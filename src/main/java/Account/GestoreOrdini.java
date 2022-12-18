package Account;

import Ordine.Order;

import java.util.ArrayList;

public class GestoreOrdini extends User{

    private final ArrayList<Order> ordiniGestiti;

    public GestoreOrdini(String username, String password,ArrayList<Order>ordiniGestiti) {
        super(username, password);
        this.ordiniGestiti=ordiniGestiti;
    }

    public void addOrder(Order order){
        ordiniGestiti.add(order);
    }

    public ArrayList<Order> getOrdiniGestiti() {
        return ordiniGestiti;
    }

}

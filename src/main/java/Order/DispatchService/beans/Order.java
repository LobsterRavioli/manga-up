package Order.DispatchService.beans;

import java.sql.Date;
import User.AccountService.beans.User;

public class Order {

    public Order()
    {
        ;
    }

    public Order(long id,Date orderDate,Date arrivalDate,State state,User user,Courier courier)
    {
        this.id = id;
        this.orderDate = orderDate;
        this.arrivalDate = arrivalDate;
        this.state = state;
        this.user = user;
        this.courier = courier;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public enum State
    {
        TO_SEND,
        SENT
    }

    private long id;

    //private List<Product> products;
    /*
    * Aggiungere l'attributo "products" qui non credo vada bene...
    * Magari ci serve una tabella che collegli le entità Prodotto e Ordine.
    * */

    private java.sql.Date orderDate,arrivalDate;
    private State state;
    private User user; // Vale la stessa cosa che vale per products (?)
    private Courier courier;
}

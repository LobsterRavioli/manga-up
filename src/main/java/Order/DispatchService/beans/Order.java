package Order.DispatchService.beans;

import Merchandising.MerchandiseService.beans.Product;
import User.AccountService.beans.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Order {

    public Order()
    {
        ;
    }

    public Order(OrderState state, User user, Date orderDate, Date arrivalDate)
    {
        this.state = state;
        this.user = user;
        this.orderDate = orderDate;
        this.arrivalDate = arrivalDate;
    }

    public OrderState getState()
    {
        return state;
    }

    public User getUser()
    {
        return user;
    }

    public Date getOrderDate()
    {
        return orderDate;
    }

    public Date getArrivalDate()
    {
        return arrivalDate;
    }

    public void setState(OrderState state)
    {
        this.state = state;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setOrderDate(Date orderDate)
    {
        this.orderDate = orderDate;
    }

    public void setArrivalDate(Date arrivalDate)
    {
        this.arrivalDate = arrivalDate;
    }

    public void addProduct(Product product)
    {
        products.add(product);
    }

    public void removeProduct(Product product)
    {
        if(products.contains(product))
            products.remove(product);
    }

    public ArrayList<Product> getProducts()
    {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return state == order.state && Objects.equals(user, order.user) && Objects.equals(orderDate, order.orderDate) && Objects.equals(arrivalDate, order.arrivalDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, user, orderDate, arrivalDate);
    }

    public enum OrderState
    {
        TO_SEND,
        SENT
    }

    private OrderState state;
    private User user;
    private Date orderDate, arrivalDate;
    private ArrayList<Product> products;
}

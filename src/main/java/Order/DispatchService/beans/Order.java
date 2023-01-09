package Order.DispatchService.beans;

import java.sql.Date;
import java.util.Objects;

import User.AccountService.beans.User;

public class Order {

    public Order()
    {
        ;
    }

    public Order(long id, Date orderDate, State state, double totalPrice, Courier courier, User user)
    {
        this.id = id;
        this.orderDate = orderDate;
        this.state = state;
        this.totalPrice = totalPrice;
        this.courier = courier;
        this.user = user;
    }

    public long getId()
    {
        return this.id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public Date getOrderDate()
    {
        return this.orderDate;
    }

    public void setOrderDate(Date orderDate)
    {
        this.orderDate = orderDate;
    }

    public State getState()
    {
        return this.state;
    }

    public void setState(State state)
    {
        this.state = state;
    }

    public double getTotalPrice()
    {
        return this.totalPrice;
    }

    public void setTotalPrice(double totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    public long getCourierID()
    {
        return this.courier.getId();
    }

    public void setCourierID(long newCourierID)
    {
        this.courier.setId(newCourierID);
    }

    public String getCourierName()
    {
        return this.courier.getName();
    }

    public void setCourierName(String newCourierName)
    {
        this.courier.setName(newCourierName);
    }

    public long getUserID()
    {
        return this.user.getId();
    }

    public void setUserID(long newUserID)
    {
        this.setUserID(newUserID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Double.compare(order.totalPrice, totalPrice) == 0 && Objects.equals(orderDate, order.orderDate) && state == order.state && Objects.equals(courier, order.courier) && Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderDate, state, totalPrice, courier, user);
    }

    public enum State
    {
        TO_SEND,
        SENT
    }

    private long id;
    private Date orderDate;
    private State state;
    private double totalPrice;
    private Courier courier;
    private User user;
}

package Order.DispatchService.beans;

import java.sql.Date;
import java.util.Objects;

import User.AccountService.beans.EndUser;
import User.AccountService.beans.User;

public class Order {

    public Order()
    {
        ;
    }

    public Order(long id, Date orderDate, State state, double totalPrice, User user, EndUser endUser, String courierName)
    {
        this.id = id;
        this.orderDate = orderDate;
        this.state = state;
        this.totalPrice = totalPrice;
        this.user = user;
        this.endUser = endUser;
        this.courierName = courierName;
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

    public String getCourierName()
    {
        return courierName;
    }

    public void setCourierName(String newCourierName)
    {
        courierName = newCourierName;
    }

    public String getUserName()
    {
        return this.user.getUsername();
    }

    public void setUserName(String newUserName)
    {
        this.setUserName(newUserName);
    }

    public long getEndUserID() {
        return this.endUser.getId();
    }

    public void setEndUserID(int newEndUserID)
    {
        this.endUser.setId(newEndUserID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Double.compare(order.totalPrice, totalPrice) == 0 && Objects.equals(orderDate, order.orderDate) && state == order.state && Objects.equals(user, order.user) && Objects.equals(endUser, order.endUser) && Objects.equals(courierName, order.courierName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderDate, state, totalPrice, user, endUser, courierName);
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
    private User user;

    private EndUser endUser;

    private String courierName;
}

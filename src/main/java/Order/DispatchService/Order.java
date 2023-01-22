package Order.DispatchService.beans;

import java.sql.Date;
import java.util.Objects;

import User.AccountService.beans.EndUser;
import User.AccountService.beans.User;

public class Order {

    public Order()
    {
        //this.user = new User();
        this.endUser = new EndUser();
    }

    public Order(long id, Date orderDate, double totalPrice, EndUser endUser)
    {
        this.id = id;
        this.orderDate = orderDate;
        this.state = Order.TO_SENT;
        this.totalPrice = totalPrice;
        this.endUser = endUser;
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

    public String getState()
    {
        return this.state;
    }

    public void setState(String state)
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

    /*
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
        this.user.setUsername(newUserName);
    }
*/
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
        return id == order.id && Double.compare(order.totalPrice, totalPrice) == 0 && Objects.equals(orderDate, order.orderDate) && Objects.equals(state, order.state) && Objects.equals(endUser, order.endUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderDate, state, totalPrice, endUser);
    }

    private long id;
    private Date orderDate;
    private String state;
    private double totalPrice;
    private EndUser endUser;

    public static final String TO_SENT = "TO_SEND";
    public static final String SENT = "SENT";
}

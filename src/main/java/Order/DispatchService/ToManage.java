package Order.DispatchService;

import User.AccountService.User;

import java.util.Objects;

public class ToManage {

    public ToManage()
    {
        this.user = new User();
        this.order = new Order();
    }

    public ToManage(User user, Order order)
    {
        this.user = user;
        this.order = order;
    }

    public long getUserId()
    {
        return this.user.getId();
    }

    public String getUserName()
    {
        return this.user.getUsername();
    }

    public void setUserName(String newUserName)
    {
        this.user.setUsername(newUserName);
    }

    public void setUserId(int newUserId)
    {
        this.user.setId(newUserId);
    }

    public long getOrderId()
    {
        return this.order.getId();
    }

    public void setOrderId(long newOrderId)
    {
        this.order.setId(newOrderId);
    }

    public boolean validateCreation()
    {
        if(this.user.getUsername() == null || this.user.getUsername().trim().equals("") ||
        this.order.getId() <= 0)
            return false;
        else
            return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToManage toManage = (ToManage) o;
        return Objects.equals(user, toManage.user) && Objects.equals(order, toManage.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, order);
    }

    private User user;
    private Order order;
}

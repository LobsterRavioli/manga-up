package Order.DispatchService;

import java.sql.Date;
import java.util.Objects;

import User.AccountService.Address;
import User.AccountService.CreditCard;
import User.AccountService.EndUser;

public class Order {

    public Order()
    {
        this.endUser = new EndUser();

        this.endUserAddress = new Address();
        this.addressInfo = endUserAddress.toString();

        this.endUserCard = new CreditCard();
        this.creditCardInfo = endUserCard.toString();

        this.state = Order.TO_SENT;
    }

    public Order(EndUser endUser, Address endUserAddress, CreditCard endUserCard) {
        this.endUser = endUser;

        this.endUserAddress = endUserAddress;
        this.addressInfo = endUserAddress.toString();

        this.endUserCard = endUserCard;
        this.creditCardInfo = endUserCard.toString();

        this.state = Order.TO_SENT;
    }

    public Order(long id, Date orderDate, double totalPrice, EndUser endUser, CreditCard card)
    {
        this.id = id;
        this.orderDate = orderDate;
        this.state = Order.TO_SENT;
        this.totalPrice = totalPrice;
        this.endUser = endUser;
        this.endUserCard = card;
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

    public int getEndUserID() {
        return this.endUser.getId();
    }

    public void setEndUserID(int newEndUserID)
    {
        this.endUser.setId(newEndUserID);
    }

    public void setEndUserCard(CreditCard endUserCard)
    {
        this.endUserCard = endUserCard;
    }

    public static String formatInfo(String info)
    {
        String[] splitData = info.split(",");

        for(int i = 0; i < splitData.length; i++)
        {
            splitData[i] = splitData[i].trim();

            if(splitData[i].length() > 50)
            {
                splitData[i] = splitData[i].substring(0,50);
                splitData[i] += ".";
            }
        }

        return String.join(",\n", splitData);
    }

    public void setCreditCardEndUserInfo(String endUserCardInfo)
    {
        this.creditCardInfo = endUserCardInfo;
    }

    public String getCreditCardEndUserInfo()
    {
        return creditCardInfo == null ? this.endUserCard.toString() : creditCardInfo;
    }

    public void setEndUserAddress(Address endUserAddress)
    {
        this.endUserAddress = endUserAddress;
    }
    public void setAddressEndUserInfo(String endUserAddressInfo)
    {
        this.addressInfo = endUserAddressInfo;
    }

    public String getAddressEndUserInfo() {
        return addressInfo == null ? this.endUserAddress.toString() : addressInfo;
    }


    public EndUser getEndUser()
    {
        return this.endUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Double.compare(order.totalPrice, totalPrice) == 0 && Objects.equals(orderDate, order.orderDate) && Objects.equals(state, order.state) && Objects.equals(endUser, order.endUser) && Objects.equals(endUserAddress, order.endUserAddress) && Objects.equals(endUserCard, order.endUserCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderDate, state, totalPrice, endUser, endUserAddress, endUserCard);
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

    public void setCreditCardEndUser(int cardId)
    {
        this.endUserCard.setId(cardId);
    }
*/

    private long id;
    private Date orderDate;
    private String state;
    private double totalPrice;
    private EndUser endUser;

    private Address endUserAddress;
    private String addressInfo;
    private CreditCard endUserCard;
    private String creditCardInfo;

    public static final String TO_SENT = "TO_SEND";
    public static final String SENT = "SENT";

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", state='" + state + '\'' +
                ", totalPrice=" + totalPrice +
                ", endUser=" + endUser +
                ", endUserAddress=" + endUserAddress +
                ", addressInfo='" + addressInfo + '\'' +
                ", endUserCard=" + endUserCard +
                ", creditCardInfo='" + creditCardInfo + '\'' +
                '}';
    }
}

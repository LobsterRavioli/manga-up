package Order.DispatchService;


import User.AccountService.CreditCard;
import User.AccountService.EndUser;
import User.AccountService.User;

import java.sql.Date;
import java.util.Objects;

public class ManagedOrder extends Order {

    public ManagedOrder()
    {
        super();
        this.user = new User();
    }

    public ManagedOrder(long id, Date orderDate, double totalPrice, EndUser endUser, CreditCard card,
                        User user, Date deliveryDate, String trackNumber, String courierName, Date shipmentDate)
    {
        super(id, orderDate, totalPrice, endUser, card);
        this.state = Order.SENT;
        this.user = user;
        this.deliveryDate = deliveryDate;
        this.trackNumber = trackNumber;
        this.courierName = courierName;
        this.shipmentDate = shipmentDate;
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

    public Date getDeliveryDate()
    {
        return this.deliveryDate;
    }

    public void setDeliveryDate(Date newDeliveryDate)
    {
        this.deliveryDate = newDeliveryDate;
    }

    public String getTrackNumber()
    {
        return this.trackNumber;
    }

    public void setTrackNumber(String newTrackNumber)
    {
        this.trackNumber = newTrackNumber;
    }
    public String getCourierName()
    {
        return courierName;
    }

    public void setCourierName(String newCourierName)
    {
        courierName = newCourierName;
    }
    public Date getShipmentDate()
    {
        return this.shipmentDate;
    }

    public void setShipmentDate(Date newShipmentDate)
    {
        this.shipmentDate = newShipmentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ManagedOrder)) return false;
        if (!super.equals(o)) return false;
        ManagedOrder that = (ManagedOrder) o;
        return Objects.equals(user, that.user) && Objects.equals(deliveryDate, that.deliveryDate) && Objects.equals(shipmentDate, that.shipmentDate) && Objects.equals(trackNumber, that.trackNumber) && Objects.equals(state, that.state) && Objects.equals(courierName, that.courierName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, deliveryDate, shipmentDate, trackNumber, state, courierName);
    }

    private User user;
    private Date deliveryDate, shipmentDate;
    private String trackNumber, state, courierName;
}

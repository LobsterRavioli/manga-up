package Order.DispatchService;


import User.AccountService.Address;
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
        this.state = Order.SENT;
    }

    public ManagedOrder(EndUser endUser, Address endUserAddress, CreditCard endUserCard, User user, Date shipmentDate,
                        String trackNumber, String courierName, Date deliveryDate)
    {
        super(endUser, endUserAddress, endUserCard);
        this.state = Order.SENT;
        this.user = user;
        this.shipmentDate = shipmentDate;
        this.trackNumber = trackNumber;
        this.courierName = courierName;
        this.deliveryDate = deliveryDate;
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
        ManagedOrder that = (ManagedOrder) o;
        return user.getUsername().equals(that.user.getUsername()) && shipmentDate.equals(that.shipmentDate) &&
                deliveryDate.equals(that.deliveryDate) && trackNumber.equals(that.trackNumber) &&
                state.equals(that.state) && courierName.equals(that.courierName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, shipmentDate, deliveryDate, trackNumber, state, courierName);
    }

    @Override
    public String toString() {
        return "ManagedOrder{" +
                "user=" + user +
                ", deliveryDate=" + deliveryDate +
                ", shipmentDate=" + shipmentDate +
                ", trackNumber='" + trackNumber + '\'' +
                ", state='" + state + '\'' +
                ", courierName='" + courierName + '\'' +
                '}';
    }

    public boolean validateManagedOrder()
    {
        String managerName = getUserName();
        Date orderDate = this.getOrderDate();

        if(managerName == null || managerName.trim() == "" || shipmentDate == null || deliveryDate == null
                || trackNumber == null || trackNumber.trim() == "" || courierName == null || courierName.trim() == "")
            return false;
        else return true;
    }

    public boolean validateManagedOrderCreation()
    {
        String managerName = getUserName();
        Date orderDate = this.getOrderDate();

        if(!this.validateOrderCreation())
            return false;

        else if(managerName == null || managerName.trim() == "" || shipmentDate == null || shipmentDate.before(orderDate) ||
                deliveryDate == null || deliveryDate.before(orderDate) || deliveryDate.before(shipmentDate) ||
                trackNumber == null || trackNumber.trim() == "" || courierName == null || courierName.trim() == "")
            return false;
        else return true;
    }

    private User user;
    private Date shipmentDate, deliveryDate;
    private String trackNumber, state, courierName;
}

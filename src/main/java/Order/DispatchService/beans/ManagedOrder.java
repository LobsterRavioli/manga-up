package Order.DispatchService.beans;

import User.AccountService.beans.User;

import java.sql.Date;
import java.util.Objects;

public class ManagedOrder extends Order {

    public ManagedOrder()
    {
        super();
    }

    public ManagedOrder(long id,Date orderDate,State state,double totalPrice,Courier courier,User user,
                        Date deliveryDate,String trackNumber,Date shipmentDate)
    {
        super(id, orderDate, state, totalPrice, courier, user);
        this.deliveryDate = deliveryDate;
        this.trackNumber = trackNumber;
        this.shipmentDate = shipmentDate;
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
        return Objects.equals(deliveryDate, that.deliveryDate) && Objects.equals(shipmentDate, that.shipmentDate) && Objects.equals(trackNumber, that.trackNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), deliveryDate, shipmentDate, trackNumber);
    }

    private Date deliveryDate, shipmentDate;
    private String trackNumber;
}

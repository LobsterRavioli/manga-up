package Order.DispatchService.service_layer;

import Order.DispatchService.beans.Order;

import java.util.Date;

/**
 * @author Alessandro
 */
public class DispatchServiceImp implements DispatchService {

    @Override
    public void setOrderState(Order order, Order.OrderState state)
    {
        order.setState(state);
    }

    @Override
    public void setOrderDate(Order order, Date orderDate)
    {
        order.setOrderDate(orderDate);
    }

    @Override
    public void setOrderArrivalDate(Order order, Date arrivalDate)
    {
        order.setArrivalDate(arrivalDate);
    }
}

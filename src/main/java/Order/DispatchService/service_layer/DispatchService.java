package Order.DispatchService.service_layer;

import Order.DispatchService.beans.Order;

import java.util.Date;

/**
 * @author Alessandro
 */
public interface DispatchService {
    void setOrderState(Order order, Order.OrderState state);
    void setOrderDate(Order order, Date orderDate);
    void setOrderArrivalDate(Order order, Date arrivalDate);
}

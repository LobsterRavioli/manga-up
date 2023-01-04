package Order.DispatchService.dao_layer.interfaces;

import Order.DispatchService.beans.Order;

/**
 * @author Alessandro
 */
public interface OrderDAO {

    void create(Order order);

    void delete(Order order);

    void update(Order order);

    Order retrieve(int id);
}

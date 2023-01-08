package Order.DispatchService.dao_layer.interfaces;

import Order.DispatchService.beans.Order;

import java.sql.SQLException;

/**
 * @author Alessandro
 */
public interface OrderDAO {

    void create(Order order) throws SQLException;

    void delete(Order order) throws SQLException;

    void update(Order order) throws SQLException;

    Order retrieve(int id) throws SQLException;
}

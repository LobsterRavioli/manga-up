package Order.DispatchService.dao_layer.interfaces;

import Order.DispatchService.beans.ManagedOrder;

import java.sql.Date;
import java.sql.SQLException;

public interface ManagedOrderDAO {
    void create(ManagedOrder managedOrder) throws SQLException;
    ManagedOrder retrieve(long ordId, String userName) throws SQLException;
    void update(ManagedOrder managedOrder) throws SQLException;
    void delete(ManagedOrder managedOrder) throws SQLException;

    ManagedOrder retrieveByDeliveryDate(Date deliveryDate) throws SQLException;
    ManagedOrder retrieveByTrackNumber(String trackNumber) throws SQLException;
}

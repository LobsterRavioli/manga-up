package Order.DispatchService.dao_layer.interfaces;

import Order.DispatchService.beans.Collection;

/**
 * @author Alessandro
 */
public interface CollectionDAO {

    void create(Collection collection);

    void delete(Collection collection);

    void update(Collection collection);

    Collection retrieve(int id);
}

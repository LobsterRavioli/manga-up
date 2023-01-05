package Merchandising.MerchandiseService.service_layer.interfaces;

import Merchandising.MerchandiseService.beans.Collection;

/**
 * @author Alessandro
 */
public interface CollectionDAO {

    void create(Collection collection);

    void delete(String titolo);

    void update(Collection collection,String titolo);

    Collection retrieve(String titolo);


}

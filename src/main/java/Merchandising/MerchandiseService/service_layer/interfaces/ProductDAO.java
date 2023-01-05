package Merchandising.MerchandiseService.service_layer.interfaces;

import Merchandising.MerchandiseService.beans.Product;

public interface ProductDAO {
    void create(Product p);

    void delete(Product p);

    void update(Product p);

    Product retrieve(int id);


}
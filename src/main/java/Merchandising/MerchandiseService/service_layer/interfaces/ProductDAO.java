package Merchandising.MerchandiseService.service_layer.interfaces;

import Merchandising.MerchandiseService.beans.Product;

import java.util.ArrayList;

public interface ProductDAO {
    void create(Product p);

    void delete(int id);

    void update(Product p);

    Product retrieveById(int id);

    ArrayList<Product> retrieveByName(String name);

    ArrayList<Product> retrieveAll();

    ArrayList<Product> retrieveByPrice(double priceStart,double priceEnd);


}
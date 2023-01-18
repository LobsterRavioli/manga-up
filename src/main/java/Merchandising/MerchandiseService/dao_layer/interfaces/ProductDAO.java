package Merchandising.MerchandiseService.dao_layer.interfaces;

import Merchandising.MerchandiseService.beans.Product;
import Merchandising.MerchandiseService.dao_layer.exceptions.ExceededLengthException;
import Merchandising.MerchandiseService.dao_layer.exceptions.InvalidQuantityException;
import Merchandising.MerchandiseService.dao_layer.exceptions.WrongRangeException;

import java.util.ArrayList;

public interface ProductDAO {
    void create(Product p) throws ExceededLengthException,WrongRangeException, InvalidQuantityException;

    void delete(int id);

    void update(int id,int quantity)throws InvalidQuantityException;

    Product retrieveById(int id);

    ArrayList<Product> retrieveByFilters(String name, String collections, float min_price, float max_price, Product.ProductState ps) throws WrongRangeException;

    ArrayList<Product> retrieveAll();



}
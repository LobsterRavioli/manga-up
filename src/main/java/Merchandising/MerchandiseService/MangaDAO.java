package Merchandising.MerchandiseService;

import Merchandising.MerchandiseService.beans.Manga;
import Merchandising.MerchandiseService.dao_layer.exceptions.*;

import java.util.ArrayList;

public interface MangaDAO {

    void create(Manga p) throws ExceededLengthException, InvalidQuantityException,WrongRangeException, NeededStateException;

    void delete(int id);

    void update(int quantity,int id) throws InvalidQuantityException, UnavailableProductException;

    Manga retrieveById(int id);

    ArrayList<Manga> retrieveAll();

    ArrayList<Manga> retrieveByFilters(String name,String collections,float min_price, float max_price, Product.ProductState ps)throws WrongRangeException;

}

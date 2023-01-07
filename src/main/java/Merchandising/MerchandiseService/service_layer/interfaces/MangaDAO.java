package Merchandising.MerchandiseService.service_layer.interfaces;

import Merchandising.MerchandiseService.beans.Manga;
import Merchandising.MerchandiseService.beans.Product;

import java.util.ArrayList;

public interface MangaDAO {

    void create(Manga p);

    void delete(int id);

    void update(Manga p);

    Manga retrieveById(int id);

    ArrayList<Manga> retrieveByName(String name);

    ArrayList<Manga> retrieveByPrice(double priceStart,double priceEnd);

}

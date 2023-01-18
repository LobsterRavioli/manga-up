package Merchandising.MerchandiseService.dao_layer.interfaces;

import Merchandising.MerchandiseService.beans.Autore;
import Merchandising.MerchandiseService.beans.Genre;

import java.util.ArrayList;

public interface GenreDAO {



    public ArrayList<Genre> retrieveByManga(int id);

    public void delete(String name);

    public void create(Genre g);

    //public boolean orphanControl(int genre_id);

}

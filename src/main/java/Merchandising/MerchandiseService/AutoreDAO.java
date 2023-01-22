package Merchandising.MerchandiseService.dao_layer.interfaces;

import Merchandising.MerchandiseService.beans.Autore;

import java.util.ArrayList;

public interface AutoreDAO {

    public void create(Autore a);

    public void delete(int id);

    public Autore retrieve(int autore_id);

    public ArrayList<Autore> retrieveByManga(int manga_id);

    //public boolean orphanControl(int id);


}

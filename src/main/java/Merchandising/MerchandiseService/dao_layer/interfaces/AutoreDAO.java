package Merchandising.MerchandiseService.dao_layer.interfaces;

import Merchandising.MerchandiseService.beans.Autore;

public interface AutoreDAO {

    public void create(Autore a);

    public void delete(int id);

    public Autore retrieve(int id);


}

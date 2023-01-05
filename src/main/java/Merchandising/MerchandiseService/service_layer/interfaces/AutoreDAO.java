package Merchandising.MerchandiseService.service_layer.interfaces;

import Merchandising.MerchandiseService.beans.Autore;

public interface AutoreDAO {
    void create(Autore a);

    void delete(Autore a);

    void update(Autore a);

    Autore retrieve(int id);


}

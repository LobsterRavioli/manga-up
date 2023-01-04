package Order.DispatchService.dao_layer.interfaces;

import Merchandising.MerchandiseService.beans.Manga;

/**
 * @author Alessandro
 */
public interface MangaDAO {
    void create(Manga manga);

    void delete(Manga manga);

    void update(Manga manga);

    Manga retrieve(int id);
}

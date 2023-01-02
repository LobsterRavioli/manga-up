package User.AccountService.dao_layer.interfaces;

import User.AccountService.beans.CatalogManagerUser;

public interface CatalogManagerUserDAO {
    void create(CatalogManagerUser user);
    void delete(CatalogManagerUser user);
    void update(CatalogManagerUser user);
    CatalogManagerUser retrieve(int id);
    CatalogManagerUser findByUsernameAndPassword(String username, String password);
}

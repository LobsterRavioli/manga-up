package User.AccountService.dao_layer.interfaces;

import Merchandising.MerchandiseService.beans.Collection;

public interface UserRoleDAO {


    public void getRoles(String username);

    public void updateRoles(String username, Collection roles);
}


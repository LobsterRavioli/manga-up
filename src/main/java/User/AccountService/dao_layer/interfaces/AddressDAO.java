package User.AccountService.dao_layer.interfaces;

import User.AccountService.beans.Address;
import User.AccountService.beans.EndUser;

import java.util.Collection;

public interface AddressDAO {
    void create(Address address);
    void delete(Address address);

    public Address find(int id);
    Collection list();

    Collection find(EndUser user);


}

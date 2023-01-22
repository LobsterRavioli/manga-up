package User.AccountService.dao_layer.interfaces;

import User.AccountService.beans.Address;

import java.util.Collection;

public interface AddressDAO {
    void create(Address address);
    void delete(Address address);

    Address find(int id); // findById()

    Collection list();

    Collection findAllByEnduser(Address address);
    Collection findSingleByEnduser(Address address);

    Collection find(Address address);
    


}

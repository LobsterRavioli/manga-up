package User.AccountService.dao_layer.interfaces;

import User.AccountService.beans.Address;

public interface AddressDAO {
    void create(Address address);
    void delete(Address address);
    void update(Address address);
    Address retrieve(int id);
}

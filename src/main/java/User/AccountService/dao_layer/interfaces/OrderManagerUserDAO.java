package User.AccountService.dao_layer.interfaces;

import User.AccountService.beans.OrderManagerUser;

public interface OrderManagerUserDAO {
    void create(OrderManagerUser user);
    void delete(OrderManagerUser user);
    void update(OrderManagerUser user);
    OrderManagerUser retrieve(int id);
    OrderManagerUser findByUsernameAndPassword(String username, String password);
}

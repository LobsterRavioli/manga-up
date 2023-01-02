package User.AccountService.dao_layer.implementations;

import User.AccountService.beans.OrderManagerUser;
import User.AccountService.dao_layer.interfaces.OrderManagerUserDAO;

public class OrderManagerUserDAOImp implements OrderManagerUserDAO {

    private static final String CREATE_QUERY = "";
    private static final String DELETE_QUERY = "";
    private static final String UPDATE_QUERY= "";
    private static final String RETRIEVE_QUERY= "";
    private static final String RETRIEVE_BY_USERNAME_AND_PASSWORD= "";

    @Override
    public void create(OrderManagerUser user) {

    }

    @Override
    public void delete(OrderManagerUser user) {

    }

    @Override
    public void update(OrderManagerUser user) {

    }

    @Override
    public OrderManagerUser retrieve(int id) {
        return null;
    }

    @Override
    public OrderManagerUser findByUsernameAndPassword(String username, String password) {
        return null;
    }
}

package User.AccountService.dao_layer.interfaces;

import User.AccountService.beans.User;

import java.sql.SQLException;
import java.util.Collection;

public interface UserDAO {

    void create(User user) throws SQLException;
    void delete(User user) throws SQLException;

    User getUserById(int id) throws SQLException;

    User checkUsername(String login) throws SQLException;

    // modified
    Collection<User> getAllUsers(String role_name) throws SQLException;
}

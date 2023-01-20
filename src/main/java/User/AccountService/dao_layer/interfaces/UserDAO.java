package User.AccountService.dao_layer.interfaces;

import User.AccountService.beans.User;

import java.sql.SQLException;
import java.util.Collection;

public interface UserDAO {

    void create(User user) throws SQLException;
    void delete(User user) throws SQLException;

    User getUserById(int id) throws SQLException;

    // Trova uno user avente username uguale al parametro login
    User checkUsername(String login) throws SQLException;

    //controlla se un user con una certa login e una certa password può accedere al sistema.
    //se la coppia (login, password) è presente nel DB, allora le credenziali di user sono corrette ed ha accesso al sistema
    boolean checkUser(User user, String roleToLog) throws SQLException;
    Collection<User> getAllUsers(String role_name) throws SQLException;

    Collection<User> getAllBeginnerOrderManagers() throws SQLException;

    // restituisce l'ID del gestore degli ordini al quale commissionare la gestione di un ordine
    int getTargetOrderManagerId() throws SQLException;

    Collection<String> getRole(String username) throws SQLException;
}

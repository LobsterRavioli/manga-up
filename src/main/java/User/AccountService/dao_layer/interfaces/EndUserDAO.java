package User.AccountService.dao_layer.interfaces;

import User.AccountService.beans.EndUser;
import utils.DAOException;


import java.util.Collection;

public interface EndUserDAO {
    void create(EndUser user) throws DAOException;
    void delete(EndUser user) throws DAOException;
    void update(EndUser user) throws DAOException;
    EndUser find(int id) throws DAOException;
    EndUser find(String email, String password) throws DAOException;
    boolean existEmail(String email) throws DAOException;

    Collection list() throws DAOException;
}

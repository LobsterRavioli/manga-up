package User.AccountService.dao_layer.implementations;

import User.AccountService.dao_layer.interfaces.UserRoleDAO;
import utils.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import static utils.DAOUtil.prepareStatement;


public class UserRoleDAOImp implements UserRoleDAO {
    private static String SQL_GET_ROLES = "SELECT * FROM USER_ROLE WHERE username = ?";
    private static String SQL_UPDATE_ROLES = "UPDATE USER_ROLE SET role = ? WHERE username = ?";
    private static String REMOVE_ALL_ROLES = "DELETE FROM USER_ROLE WHERE username = ?";
    @Override
    public void getRoles(String username) {
        Object[] values = {
                username
        };
        try(
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_GET_ROLES, false, values)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating user failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void updateRoles(String username, Collection roles) {


    }
}

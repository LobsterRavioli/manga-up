package User.AccountService.dao_layer.implementations;

import User.AccountService.beans.Address;
import User.AccountService.beans.User;
import User.AccountService.dao_layer.interfaces.UserRoleDAO;
import utils.DAOException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static utils.DAOUtil.prepareStatement;


public class UserRoleDAOImp implements UserRoleDAO {

    private DataSource ds;

    public UserRoleDAOImp(DataSource ds){
        this.ds = ds;
    }

    private static String SQL_GET_ROLES = "SELECT * FROM USER_ROLE WHERE username = ?";

    private static String SQL_INSERT_ROLE = "INSERT INTO USER_ROLE (username, role) VALUES (?, ?)";
    private static String REMOVE_ALL_ROLES = "DELETE FROM USER_ROLE WHERE username = ?";

    @Override
    public Collection getRoles(User user) {
        ArrayList<String> roles = new ArrayList<>();
        Object values[] = {user.getUsername()};
        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_GET_ROLES, false, values);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                roles.add(resultSet.getString("role"));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return roles;
    }

    @Override
    public void setRoles(User user, Collection roles) {
        removeAllRoles(user);
        for (Object role : roles) {
            Object values[] = {user.getUsername(), role};
            try (
                    Connection connection = ds.getConnection();
                    PreparedStatement statement = prepareStatement(connection, SQL_INSERT_ROLE, false, values);
            ) {
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }


    private void removeAllRoles(User user){
        Object values[] = {user.getUsername()};
        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, REMOVE_ALL_ROLES, false, values);
        ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }



    private static User map(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }
}

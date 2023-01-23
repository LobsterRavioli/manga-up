package User.AccountService;

import utils.DAOException;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static utils.DAOUtil.prepareStatement;


public class UserRoleDAO {

    private DataSource ds;

    public UserRoleDAO(DataSource ds){
        this.ds = ds;
    }

    private static String SQL_GET_ROLES = "SELECT * FROM user_roles WHERE user_name = ?";

    private static String SQL_INSERT_ROLE = "INSERT INTO user_roles (user_name, role_name) VALUES (?, ?)";
    private static String REMOVE_ALL_ROLES = "DELETE FROM user_roles WHERE user_name = ?";


    public Collection getRoles(User user) {
        ArrayList<String> roles = new ArrayList<>();
        Object values[] = {user.getUsername()};
        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_GET_ROLES, false, values);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                roles.add(resultSet.getString("role_name"));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return roles;
    }

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

    private void removeAllRoles(User user) {
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
        user.setUsername(resultSet.getString("user_name"));
        user.setPassword(resultSet.getString("role_name"));
        return user;
    }
}

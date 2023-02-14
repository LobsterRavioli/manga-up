package User.AccountService;

import utils.DAOException;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static utils.DAOUtil.prepareStatement;


public class UserRoleDAO {

    private DataSource ds;

    public UserRoleDAO(DataSource ds){
        this.ds = ds;
    }

    private static String SQL_GET_ROLES = "SELECT * FROM USER_ROLES WHERE user_name = ?";

    private static String SQL_INSERT_ROLE = "INSERT INTO USER_ROLES (user_name, role_name) VALUES (?, ?)";
    private static String REMOVE_ALL_ROLES = "DELETE FROM USER_ROLES WHERE user_name = ?";

    public Set<String> getRoles(User user) {

        if (user == null || user.getUsername() == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        HashSet<String> roles = new HashSet<String>();

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

        if (roles.isEmpty()) {
            return null;
        }

        return roles;
    }


    public void setRoles(User user, String [] roles) {
        if (user == null || user.getUsername() == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (roles == null) {
            throw new IllegalArgumentException("Roles cannot be null");
        }
        if (roles.length == 0) {
            throw new IllegalArgumentException("Roles cannot be empty");
        }
        for (String role : roles) {
            if (role == null) {
                throw new IllegalArgumentException("Roles cannot be null");
            }
            if (role.isEmpty()) {
                throw new IllegalArgumentException("Roles cannot be empty");
            }
        }
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

        if(user==null){
            throw new IllegalArgumentException("user cannot be null");
        }

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

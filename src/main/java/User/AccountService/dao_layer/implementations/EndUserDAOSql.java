package User.AccountService.dao_layer.implementations;

import User.AccountService.beans.EndUser;
import User.AccountService.dao_layer.interfaces.EndUserDAO;
import utils.DAOException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static utils.DAOUtil.*;

public class EndUserDAOSql implements EndUserDAO {
    public static final String TABLE = "END_USER";
    private DataSource ds;

    public EndUserDAOSql(DataSource ds){
        this.ds = ds;

    }

    private static final String SQL_INSERT =
            "INSERT INTO END_USER (email, name, surname, password, phone_number, birth_date)\n" +
            "VALUES (?, ?, ?, MD5(?), ?, ?);";
    private static final String SQL_DELETE =
            "DELETE FROM END_USER WHERE email = ? ;";
    private static final String SQL_UPDATE =
            "UPDATE END_USER SET name = ? , surname = ? , email = ? , password = MD5(?) , phone_number = ? WHERE id = ? ;";
    private static final String SQL_FIND_BY_ID =
            "SELECT * FROM END_USER WHERE id = ? ;";
    private static final String SQL_FIND_BY_EMAIL_AND_PASSWORD =
            "SELECT * FROM END_USER WHERE email = ? AND password = MD5(?) ;";


    private static final String SQL_EXIST_EMAIL =
            "SELECT id FROM EndUser WHERE email = ?";

    private static final String SQL_CHANGE_PASSWORD =
            "UPDATE EndUser SET password = MD5(?) WHERE id = ?";

    @Override
    public void create(EndUser user) throws IllegalArgumentException, DAOException {

        if (user.getId() != 0) {
            throw new IllegalArgumentException("User is already created, the user ID is not null.");
        }

        Object[] values = {
                user.getEmail(),
                user.getName(),
                user.getSurname(),
                user.getPassword(),
                user.getPhoneNumber(),
                toSqlDate(user.getBirthdate())
        };

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                } else {
                    throw new DAOException("Creating user failed, no generated key obtained.");
                }
            }
        } catch (SQLException  e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(EndUser user) {
        Object[] values = {
                user.getId()
        };

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting user failed, no rows affected.");
            } else {
                user.setId(0);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(EndUser user) {

        if (user.getId() == 0) {
            throw new IllegalArgumentException("User is not created yet, the user ID is null.");
        }

        Object[] values = {
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                toSqlDate(user.getBirthdate()),
                user.getId()
        };

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean existEmail(String email) throws DAOException {
        Object[] values = {
                email
        };

        boolean exist;

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_EXIST_EMAIL, false, values);
                ResultSet resultSet = statement.executeQuery()
        ) {
            exist = resultSet.next();
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return exist;
    }

    @Override
    public EndUser find(int id) { return find(SQL_FIND_BY_ID, id); }

    @Override
    public EndUser find(String email, String password) {
        return find(SQL_FIND_BY_EMAIL_AND_PASSWORD,email,password);
    }

    @Override
    public void changePassword(EndUser user) throws DAOException {

        if (user.getId() == 0) {
            throw new IllegalArgumentException("User is not created yet, the user ID is null.");
        }

        Object[] values = {
                user.getPassword(),
                user.getId()
        };

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_CHANGE_PASSWORD, false, values)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Changing password failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private EndUser find(String sql, Object... values) throws DAOException {
        EndUser user = null;

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, sql, false, values);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                user = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return user;
    }

    /**
     * Map the current row of the given ResultSet to an User.
     * @param resultSet The ResultSet of which the current row is to be mapped to an User.
     * @return The mapped User from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */

    private static EndUser map(ResultSet resultSet) throws SQLException {
        EndUser user = new EndUser();
        user.setId(resultSet.getInt("id"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setBirthdate(resultSet.getDate("birth_date"));
        return user;
    }
}

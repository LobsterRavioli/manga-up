package User.AccountService;

import utils.DAOException;
import utils.Utils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static utils.DAOUtil.prepareStatement;
import static utils.DAOUtil.toSqlDate;


public class EndUserDAO {
    public static final String TABLE = "END_USER";
    private DataSource ds;

    public EndUserDAO(DataSource ds){
        this.ds = ds;
    }

    private static final String SQL_INSERT =
            "INSERT INTO END_USER (usr_email, usr_name, usr_surname, usr_password, usr_phone_number, usr_birth_date)\n" +
            "VALUES (?, ?, ?, ?, ?, ?);";

    private static final String SQL_DELETE =
            "DELETE FROM END_USER WHERE usr_id = ? ;";

    private static final String SQL_UPDATE =
            "UPDATE END_USER SET usr_name = ? , usr_surname = ? , usr_email = ? , usr_password = ? , usr_phone_number = ? WHERE usr_id = ? ;";

    private static final String SQL_FIND_BY_ID =
            "SELECT * FROM END_USER WHERE usr_id = ? ;";


    private static final String SQL_FIND_BY_EMAIL_AND_PASSWORD =
            "SELECT * FROM END_USER WHERE usr_email = ? AND usr_password = ? ;";

    private static final String SQL_EXIST_EMAIL =
            "SELECT usr_email FROM END_USER WHERE usr_email = ?";

    private static final String SQL_LIST_ORDER_BY_ID = "SELECT * FROM END_USER ORDER BY usr_id;";

    public void create(EndUser user) throws IllegalArgumentException, DAOException {


        if (!EndUser.validate(user)) {
            throw new IllegalArgumentException("Invalid data.");
        }

        Object[] values = {
                user.getEmail(),
                user.getName(),
                user.getSurname(),
                Utils.hash(user.getPassword()),
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

    public boolean existEmail(String email) throws DAOException {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email is null");
        }
        boolean exist;

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_EXIST_EMAIL, false, email);
                ResultSet resultSet = statement.executeQuery()
        ) {
            exist = resultSet.next();
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return exist;
    }

    public EndUser login(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Invalid input");
        }
        return find(SQL_FIND_BY_EMAIL_AND_PASSWORD,email, Utils.hash(password));
    }

    public EndUser findById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid id");
        }
        return find(SQL_FIND_BY_ID, id);
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
        user.setId(resultSet.getInt("usr_id"));
        user.setEmail(resultSet.getString("usr_email"));
        user.setPassword(resultSet.getString("usr_password"));
        user.setPhoneNumber(resultSet.getString("usr_phone_number"));
        user.setName(resultSet.getString("usr_name"));
        user.setSurname(resultSet.getString("usr_surname"));
        user.setBirthdate(resultSet.getDate("usr_birth_date"));
        return user;

    }

    /*
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
        if (user.getId() != 0) {
            throw new IllegalArgumentException("User is already created, the user ID is not null.");
        }
        Object[] values = {
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword(),
                toSqlDate(user.getBirthdate())
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
    public Collection list() throws DAOException {
        List<EndUser> users = new ArrayList<>();

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                users.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return users;
    }
*/
}

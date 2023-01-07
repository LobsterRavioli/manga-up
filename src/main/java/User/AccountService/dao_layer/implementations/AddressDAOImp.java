package User.AccountService.dao_layer.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import User.AccountService.beans.Address;
import User.AccountService.beans.EndUser;
import User.AccountService.dao_layer.interfaces.AddressDAO;
import utils.DAOException;
import static utils.DAOUtil.prepareStatement;

public class AddressDAOImp implements AddressDAO {

    private DataSource ds;

    public AddressDAOImp(DataSource ds){
        this.ds = ds;
    }
    private static final String CREATE_QUERY = "INSERT INTO ADDRESS (street, country, city, street, street_number, postal_code, user_id) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String DELETE_QUERY = "DELETE FROM ADDRESS WHERE id = ?;";

    private static final String SQL_LIST_ORDER_BY_ORDER_ID =
            "SELECT * FROM ADDRESS ORDER BY id";
    private static final String SQL_FIND_BY_ADDRESS_ID = "SELECT * FROM ADDRESS WHERE user_id = ?;";
    private static final String SQL_FIND_BY_USER_ID = "SELECT * FROM ADDRESS WHERE user_id = ?;";
    

    @Override
    public void create(Address address) {
        Object[] values = {
            address.getStreet(),
            address.getCountry(),
            address.getCity(),
            address.getStreet(),
            address.getStreetNumber(),
            address.getPostalCode(),
            address.getEndUser().getId()
    };

    try (
            Connection connection = ds.getConnection();
            PreparedStatement statement = prepareStatement(connection, CREATE_QUERY, true, values)
    ) {
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new DAOException("Creating user failed, no rows affected.");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                address.setId(generatedKeys.getInt(1));
            } else {
                throw new DAOException("Creating user failed, no generated key obtained.");
            }
        }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(Address address) {
        Object[] values = {
            address.getId()
        };

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, DELETE_QUERY, false, values)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting user failed, no rows affected.");
            } else {
                address.setId(0);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Collection list() {
        List<Address> addresses = new ArrayList<>();

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_LIST_ORDER_BY_ORDER_ID);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                addresses.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return addresses;

    }

    @Override
    public Collection find(EndUser user) {
        List<Address> addresses = new ArrayList<>();
        Object[] values = {
                user.getId()
        };
        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_FIND_BY_USER_ID, false, values);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                addresses.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return addresses;
    }

    public Address find(int id) {
        return find(SQL_FIND_BY_ADDRESS_ID);
    }

    private static Address map(ResultSet resultSet) throws SQLException {
        Address address = new Address();
        address.setId(resultSet.getInt("id"));
        address.setStreet(resultSet.getString("street"));
        address.setCountry(resultSet.getString("country"));
        address.setCity(resultSet.getString("city"));
        address.setStreet(resultSet.getString("street"));
        address.setStreetNumber(resultSet.getString("street_number"));
        address.setPostalCode(resultSet.getString("postal_code"));
        EndUser user = new EndUser();
        user.setId(resultSet.getInt("user_id"));
        address.setEndUser(user);

        return address;
    }


    private Address find(String sql, Object... values) throws DAOException {
        Address address = null;

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, sql, false, values);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                address = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return address;
    }
}

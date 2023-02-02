package User.AccountService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;
import utils.DAOException;

import static utils.DAOUtil.prepareStatement;


public class AddressDAO {

    private DataSource ds;

    public AddressDAO(DataSource ds){
        this.ds = ds;
    }
    private static final String CREATE_QUERY = "INSERT INTO address (addr_street, addr_country, addr_city, addr_postal_code, addr_phone_number, addr_region,usr_id) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String DELETE_QUERY = "DELETE FROM address WHERE addr_id = ?;";

    private static final String SQL_LIST_ORDER_BY_ORDER_ID =
            "SELECT * FROM address ORDER BY addr_id";
    private static final String SQL_FIND_BY_ADDRESS_ID = "SELECT * FROM address WHERE usr_id = ?;";
    private static final String SQL_FIND_ALL_BY_ENDUSER = "SELECT * FROM address WHERE usr_id = ?;";
    private static final String SQL_FIND_SINGLE_BY_ENDUSER = "SELECT * FROM address WHERE addr_id = ?1\n" +
            "    AND addr_city = ?1\n" +
            "    AND addr_country = ?2\n" +
            "    AND addr_postal_code = ?3\n" +
            "    AND addr_street_number = ?4\n" +
            "    AND usr_id = ?5;";

    public void create(Address address) {
        Object[] values = {
            address.getStreet(),
            address.getCountry(),
            address.getCity(),
            address.getPostalCode(),
            address.getPhoneNumber(),
                address.getRegion(),
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

    public Collection findAssociatedAddresses(EndUser user) {
        List<Address> addresses = new ArrayList<>();
        Object[] values = {
                user.getId()
        };
        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_FIND_ALL_BY_ENDUSER, false, values);
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

    public Address findById(int id) {
        return find(SQL_FIND_BY_ADDRESS_ID,id);
    }

    private static Address map(ResultSet resultSet) throws SQLException {
        Address address = new Address();
        address.setId(resultSet.getInt("addr_id"));
        address.setStreet(resultSet.getString("addr_street"));
        address.setCountry(resultSet.getString("addr_country"));
        address.setCity(resultSet.getString("addr_city"));
        address.setPostalCode(resultSet.getString("addr_postal_code"));
        address.setPhoneNumber(resultSet.getString("addr_phone_number"));
        address.setRegion(resultSet.getString("addr_region"));
        EndUser user = new EndUser();
        user.setId(resultSet.getInt("usr_id"));
        address.setEndUser(user);
        return address;

        /*
        Address address = new ConcreteAddressBuilder().setStreet(resultSet.getString("addr_street"))
                .setCountry(resultSet.getString("addr_country"))
                .setCity(resultSet.getString("addr_city"))
                .setStreet(resultSet.getString("addr_street"))
                .setPostalCode(resultSet.getString("addr_postal_code"))
                .setPhoneNumber(resultSet.getString("addr_phone_number"))
                .setRegion(resultSet.getString("addr_region"))
                .setEndUser(new ConcreteEndUserBuilder().setId(resultSet.getInt("usr_id")).createEndUser())
                .setId(resultSet.getInt("addr_id"))
                .createAddress();
        return address;
        */
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

    /*

    NON PRESENTI IN ODD

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
    public Collection findSingleByEnduser(Address address) {
        List<Address> addresses = new ArrayList<>();
        Object[] values = {
                address.getEndUser().getId()
        };
        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_FIND_SINGLE_BY_ENDUSER, false, values);
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
    public Collection find(Address address) {
        return null;
    }
*/
}

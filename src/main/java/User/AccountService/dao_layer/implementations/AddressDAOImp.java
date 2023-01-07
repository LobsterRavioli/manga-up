package User.AccountService.dao_layer.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import User.AccountService.beans.Address;
import User.AccountService.dao_layer.interfaces.AddressDAO;
import utils.DAOException;
import static utils.DAOUtil.prepareStatement;

public class AddressDAOImp implements AddressDAO {

    private DataSource ds;

    public AddressDAOImp(DataSource ds){
        this.ds = ds;
    }
    private static final String CREATE_QUERY = "INSERT INTO ADDRESS (street, country, city, street, street_number, postal_code) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String DELETE_QUERY = "DELETE FROM ADDRESS WHERE id = ?;";
    private static final String UPDATE_QUERY= "UPDATE ADDRESS SET street = ?, country = ?, city = ?, street = ?, street_number = ?, postal_code = ? WHERE id = ?;";
    private static final String RETRIEVE_BY_ID = "SELECT * FROM ADDRESS WHERE id = ?;";
    private static final String RETRIEVE_BY_USER_ID = "SELECT * FROM ADDRESS WHERE user_id = ?;";
    private static final String RETRIEVE_BY_USER_EMAIL= "SELECT * FROM ADDRESS WHERE user_email = ?;";
    

    @Override
    public void create(Address address) {
        Object[] values = {
            address.getStreet(),
            address.getCountry(),
            address.getCity(),
            address.getStreet(),
            address.getStreetNumber(),
            address.getPostalCode()
    };

    try (
            Connection connection = ds.getConnection();
            PreparedStatement statement = prepareStatement(connection, CREATE_QUERY, false, values)
    ) {
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new DAOException("Creating user failed, no rows affected.");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                user.setUsername(generatedKeys.getString(1));
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

    }

    @Override
    public void update(Address address) {

    }

    @Override
    public Address retrieve(int id) {
        return null;
    }

}

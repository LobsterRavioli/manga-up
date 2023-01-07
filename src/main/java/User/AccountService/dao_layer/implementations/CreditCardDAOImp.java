package User.AccountService.dao_layer.implementations;

import User.AccountService.beans.Address;
import User.AccountService.beans.CreditCard;
import User.AccountService.beans.EndUser;
import User.AccountService.dao_layer.interfaces.CreditCardDAO;
import utils.DAOException;
import utils.Utils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static utils.DAOUtil.prepareStatement;

public class CreditCardDAOImp implements CreditCardDAO {

    private static final String SQL_EXIST_CVC =
            "SELECT cvc FROM CreditCard WHERE cvc = ?";

    DataSource ds;
    private static final String SQL_INSERT = "INSERT INTO CreditCard (cvc, number, user_id) VALUES (?, ?, ?)";

    private static final String SQL_DELETE = "DELETE FROM CreditCard WHERE number = ?";

    private static final String SQL_FIND_BY_USER_ID = "SELECT * FROM CreditCard WHERE user_id = ?;";
    CreditCardDAOImp(DataSource ds){
        this.ds = ds;
    }


    public void create(CreditCard card) {
        Object[] values = {
                Utils.hashPassword(card.getCardNumber()),
                Utils.hashPassword(card.getCvc()),
                card.getCardHolder().getId()
        };

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_INSERT, false, values)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating user failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
    public void delete(CreditCard card) {
        Object[] values = {
                card.getCardNumber()
        };

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting user failed, no rows affected.");
            } else {
                card.setCardNumber(null);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(CreditCard card) {

    }

    @Override
    public boolean existsCvc(String cvc) {
        Object[] values = {
                cvc
        };
        boolean exist;

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_EXIST_CVC, false, values);
                ResultSet resultSet = statement.executeQuery()
        ) {
            exist = resultSet.next();
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return exist;

    }

    public CreditCard find(int id) {

        return null;
    }

    public Collection list() {
        return null;
    }


    @Override
    public Collection find(EndUser user) {
        List<CreditCard> creditCards = new ArrayList<>();
        Object[] values = {
                user.getId()
        };
        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_FIND_BY_USER_ID, false, values);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                creditCards.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return creditCards;
    }

    private static CreditCard map(ResultSet resultSet) throws SQLException {
        CreditCard card = new CreditCard();
        card.setCardNumber(resultSet.getString("number"));
        card.setCvc(resultSet.getString("cvc"));
        EndUser user = new EndUser();
        user.setId(resultSet.getInt("user_id"));
        card.setCardHolder(user);
        return card;
    }


}


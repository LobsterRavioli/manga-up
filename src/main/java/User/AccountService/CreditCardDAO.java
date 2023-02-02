package User.AccountService;

import utils.DAOException;

import utils.Utils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static utils.DAOUtil.prepareStatement;

public class CreditCardDAO {

    private static final String SQL_EXIST_CREDIT_CARD_NUMBER =
            "SELECT * FROM credit_card WHERE crd_number = ?";

    DataSource ds;
    private static final String SQL_INSERT = "INSERT INTO credit_card (crd_cvc, crd_number, usr_id, crd_holder, crd_expiration_date) VALUES (?, ?, ?, ? ,?)";

    private static final String SQL_DELETE = "DELETE FROM credit_card WHERE crd_id = ?";

    private static final String SQL_FIND_SINGLE_BY_ENDUSER = "SELECT * FROM MANGA_UP.credit_card WHERE " +
            "usr_id = ?1 " +
            "AND crd_number = ?2 " +
            "AND crd_holder = ?3 " +
            "AND crd_expiration_date = ?4 " +
            "AND crd_cvc = ?5 ;";
    private static final String SQL_FIND_ALL_BY_ENDUSER = "SELECT * FROM credit_card WHERE usr_id = ?;";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM credit_card WHERE crd_id = ?;";

    public CreditCardDAO(DataSource ds){
        this.ds = ds;
    }

    public void create(CreditCard card) {
        Object[] values = {
                Utils.hash(card.getCvv()),
                String.valueOf(card.getCardNumber()),
                card.getEndUser().getId(),
                card.getCardHolder(),
                card.getExpirementDate()
        };

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values)
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating payment_card failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    card.setId(generatedKeys.getInt(1));
                } else {
                    throw new DAOException("Creating user payment_card, no generated key obtained.");
                }
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
    public void delete(CreditCard card) {
        Object[] values = {
                card.getId()
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

    public CreditCard findById(int id) {
        return find(CreditCardDAO.SQL_FIND_BY_ID,id);
    }

    private CreditCard find(String sql, Object... values) throws DAOException {
        CreditCard creditCard = null;

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, sql, false, values);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                creditCard = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return creditCard;
    }

    public List findAssociatedCards(EndUser user) {
        List<CreditCard> creditCards = new ArrayList<>();
        Object[] values = {
                user.getId()
        };
        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_FIND_ALL_BY_ENDUSER, false, values);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                CreditCard card = map(resultSet);
                card.setEndUser(user);
                creditCards.add(card);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return creditCards;
    }

    private static CreditCard map(ResultSet resultSet) throws SQLException {
        CreditCard card = new CreditCard();
        card.setId(resultSet.getInt("crd_id"));
        card.setCardNumber(resultSet.getString("crd_number"));
        card.setCvv(resultSet.getString("crd_cvc"));
        card.setCardHolder(resultSet.getString("crd_holder"));
        card.setExpirementDate(resultSet.getDate("crd_expiration_date"));
        return card;
    }


    public boolean existsCreditCardNumber(String creditCardNumber) {
        boolean exist;

        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_EXIST_CREDIT_CARD_NUMBER, false, creditCardNumber);
                ResultSet resultSet = statement.executeQuery()
        ) {
            exist = resultSet.next();
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return exist;

    }


}

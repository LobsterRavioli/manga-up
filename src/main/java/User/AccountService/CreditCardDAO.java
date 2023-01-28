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

    private static final String SQL_EXIST_CVC =
            "SELECT cvc FROM credit_card WHERE crd_cvc = ?";

    DataSource ds;
    private static final String SQL_INSERT = "INSERT INTO credit_card (crd_cvc, crd_number, usr_id, crd_holder, crd_expirement_date) VALUES (?, ?, ?, ? ,?)";

    private static final String SQL_DELETE = "DELETE FROM credit_card WHERE crd_id = ?";

    private static final String SQL_FIND_SINGLE_BY_ENDUSER = "SELECT * FROM MANGA_UP.credit_card WHERE " +
            "usr_id = ?1 " +
            "AND crd_number = ?2 " +
            "AND crd_holder = ?3 " +
            "AND crd_expirement_date = ?4 " +
            "AND crd_cvc = ?5 ;";
    private static final String SQL_FIND_ALL_BY_ENDUSER = "SELECT * FROM credit_card WHERE usr_id = ?;";

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
        return null;
    }

    public Collection findAssociatedCards(EndUser user) {
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
                creditCards.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return creditCards;
    }

    private static CreditCard map(ResultSet resultSet) throws SQLException {
        DateFormat format =  new SimpleDateFormat("yyyy-mm-dd");
        String date = format.format(resultSet.getDate("crd_expirement_date"));
        return null;
    }

/*
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




    @Override
    public Collection findSingleByEnduser(CreditCard userCard) {
        List<CreditCard> creditCards = new ArrayList<>();
        Object[] values = {
                userCard.getEndUser().getId(),
                userCard.getCardNumber(),
                userCard.getCardHolder(),
                userCard.getExpirementDate()
        };
        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_FIND_SINGLE_BY_ENDUSER, false, values);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                creditCards.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return creditCards;
    }
 */
}

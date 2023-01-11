package User.AccountService.dao_layer.implementations;

import User.AccountService.beans.CreditCardBuilder;
import User.AccountService.beans.EndUser;
import User.AccountService.beans.CreditCard;
import User.AccountService.dao_layer.interfaces.CreditCardDAO;
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

public class CreditCardDAOImp implements CreditCardDAO {

    private static final String SQL_EXIST_CVC =
            "SELECT cvc FROM credit_card WHERE crd_cvc = ?";

    DataSource ds;
    private static final String SQL_INSERT = "INSERT INTO credit_card (crd_cvc, crd_number, usr_id) VALUES (?, ?, ?)";

    private static final String SQL_DELETE = "DELETE FROM credit_card WHERE crd_number = ?";

    private static final String SQL_FIND_BY_USER_ID = "SELECT * FROM credit_card WHERE usr_id = ?;";
    public CreditCardDAOImp(DataSource ds){
        this.ds = ds;
    }


    public void create(CreditCard card) {
        Object[] values = {
                Utils.MD5(String.valueOf(card.getCardNumber())),
                Utils.MD5(card.getCvv()),
                card.getCardHolder().getId()
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


    @Override
    public Collection find(EndUser user) {
        List<CreditCard> creditCards = new ArrayList<>();
        Object[] values = {
                user.getId()
        };
        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_FIND_BY_USER_ID, false, values);
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
        CreditCard card = new CreditCardBuilder().createCreditCard();
        card.setId(resultSet.getInt("crd_id"));
        card.setCardNumber(resultSet.getString("crd_number"));
        card.setCvv(resultSet.getString("crd_cvc"));
        DateFormat format =  new SimpleDateFormat("yyyy-mm-dd");
        String data = format.format(resultSet.getDate("crd_expirement_date"));
        System.out.println(data);
        card.setExpirementDate(data);
        EndUser user = new EndUser();
        user.setId(resultSet.getInt("usr_id"));
        user.setName(resultSet.getString("crd_name_holder"));
        user.setSurname(resultSet.getString("crd_surname_holder"));
        card.setEndUser(user);
        return card;
    }




}

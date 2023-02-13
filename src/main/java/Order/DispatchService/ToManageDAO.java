package Order.DispatchService;
import User.AccountService.User;
import utils.DAOException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 *
 * Ordini da gestire
 *
 */
public class ToManageDAO {

    private DataSource ds;


    public ToManageDAO(DataSource ds)
    {
        this.ds = ds;
    }

    private static final String TO_MANAGE_TABLE = "TO_MANAGE";

    private static final String CREATE = "INSERT INTO "+TO_MANAGE_TABLE+
            " (user_name, order_id)"+
            " VALUES (?, ?) ;";

    private static final String SELECT_BY_USERNAME = "SELECT * FROM TO_MANAGE WHERE user_name = ?;";


    private static final String DELETE = "DELETE FROM "+TO_MANAGE_TABLE+" WHERE user_name = ? AND order_id = ? ;";

    // create: crea un ordine che deve essere gestito da un certo user (assegna un ordine a un gestore)
    public void create(ToManage order) throws SQLException {

        if(!order.validateCreation())
            throw new IllegalArgumentException("Invalid data");

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = ds.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setString(1, order.getUserName());
            preparedStatement.setLong(2, order.getOrderId());

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0)
                throw new DAOException("Creation of the order to be managed failed, no rows affected.");

            connection.commit();
            connection.setAutoCommit(true);
        }
        finally
        {
            try
            {
                if(preparedStatement != null)
                    preparedStatement.close();
            }
            finally
            {
                if(connection != null)
                    connection.close();
            }
        }
    }

    // delete: rimuove un ordine gestito
    public void delete(ToManage order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setString(1, order.getUserName());
            preparedStatement.setLong(2, order.getOrderId());

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0)
                throw new DAOException("Deletion of the order to be managed failed, no rows affected.");
        }
        finally
        {
            try
            {
                if(preparedStatement != null)
                    preparedStatement.close();
            }
            finally
            {
                if(connection != null)
                    connection.close();
            }
        }
    }

    public boolean hasOrders(String username) throws SQLException{

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = ds.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(SELECT_BY_USERNAME);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
            connection.commit();
            connection.setAutoCommit(true);
        }
        finally
        {
            try
            {
                if(preparedStatement != null)
                    preparedStatement.close();
            }
            finally
            {
                if(connection != null)
                    connection.close();
            }
        }

        return false;
    }

}

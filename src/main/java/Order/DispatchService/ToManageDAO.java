package Order.DispatchService;
import utils.DAOException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    private static final String DELETE = "DELETE FROM "+TO_MANAGE_TABLE+" WHERE user_name = ? AND order_id = ? ;";

    // create: crea un ordine che deve essere gestito da un certo user (assegna un ordine a un gestore)
    public void create(ToManage order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setString(1, order.getUserName());
            preparedStatement.setLong(2, order.getOrderId());

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0)
                throw new DAOException("Creation of the order to be managed failed, no rows affected.");

            connection.commit();
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
}

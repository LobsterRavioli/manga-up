package Order.DispatchService.dao_layer.implementations;

import Order.DispatchService.beans.ToManage;
import Order.DispatchService.dao_layer.interfaces.ToManageDAO;
import utils.DAOException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ToManageDAOImp implements ToManageDAO {

    private DataSource ds;

    public ToManageDAOImp(DataSource ds)
    {
        this.ds = ds;
    }

    private static final String TO_MANAGE_TABLE = "TO_MANAGE";

    private static final String CREATE = "INSERT INTO "+TO_MANAGE_TABLE+
            " (user_id, order_id)"+
            " VALUES (?, ?) ;";

    private static final String DELETE = "DELETE FROM "+TO_MANAGE_TABLE+" WHERE user_id = ? AND order_id = ? ;";

    private static final String COUNT_MANAGED = "SELECT man_user_id, COUNT(*) AS result FROM "+"manages"+" GROUP BY man_user_id ORDER BY result";
    @Override
    public void create(ToManage order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setLong(1, order.getUserId());
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

    @Override
    public int numManageOrder() throws SQLException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int count = 0;

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(COUNT_MANAGED);

            ResultSet rs = preparedStatement.executeQuery();

            rs.next(); // mi serve vedere solo la prima riga
            count = rs.getInt("result"); // CONTROLLARE SE FUNZIONA
        }
        finally {
            try
            {
                if(preparedStatement != null)
                    preparedStatement.close();
            }
            finally
            {
                if(connection != null)
                    preparedStatement.close();
            }
        }
        return count;
    }

    @Override
    public void delete(ToManage order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, order.getUserId());
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

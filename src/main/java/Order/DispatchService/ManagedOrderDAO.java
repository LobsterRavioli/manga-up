package Order.DispatchService;

import utils.DAOException;


import javax.sql.DataSource;
import java.sql.*;

public class ManagedOrderDAO {

    private DataSource ds;

    public ManagedOrderDAO(DataSource ds)
    {
        this.ds = ds;
    }

    private static final String MANAGED_ORDER_TABLE = "manages";

    private static final String CREATE = "INSERT INTO "+MANAGED_ORDER_TABLE+
            " (man_user_name, man_order_id, man_shipment_date, man_tracking_number, man_courier, man_delivery_date)"+
            " VALUES (?, ?, ?, ?, ?, ?) ;";

    private static final String DELETE = "DELETE FROM "+MANAGED_ORDER_TABLE+" WHERE man_order_id = ? AND man_user_name = ? ;";

    private static final String UPDATE = "UPDATE "+MANAGED_ORDER_TABLE+" SET man_tracking_number = ? WHERE man_order_id = ? AND man_user_name = ? ;";

    private static final String RETRIEVE = "SELECT * FROM "+MANAGED_ORDER_TABLE+" WHERE man_order_id = ? AND man_user_name = ? ;";
    private static final String RETRIEVE_BY_ORDER = "SELECT * FROM "+MANAGED_ORDER_TABLE+" WHERE man_order_id = ?;";

    public void create(ManagedOrder managedOrder) throws SQLException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setString(1, managedOrder.getUserName());
            preparedStatement.setLong(2, managedOrder.getId());
            preparedStatement.setDate(3, managedOrder.getShipmentDate());
            preparedStatement.setString(4, managedOrder.getTrackNumber());
            preparedStatement.setString(5, managedOrder.getCourierName());
            preparedStatement.setDate(6, managedOrder.getDeliveryDate());

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0)
                throw new DAOException("Creating managed order failed, no rows affected.");

           // connection.commit();
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

    public ManagedOrder retrieve(long ordId, String userName) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ManagedOrder managedOrderBean = new ManagedOrder();

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(RETRIEVE);
            preparedStatement.setLong(1, ordId);
            preparedStatement.setString(2, userName);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                managedOrderBean.setUserName(rs.getString("man_user_name"));
                managedOrderBean.setId(rs.getLong("man_order_id"));
                managedOrderBean.setDeliveryDate(rs.getDate("man_delivery_date"));
                managedOrderBean.setTrackNumber(rs.getString("man_tracking_number"));
                managedOrderBean.setCourierName(rs.getString("man_courier"));
                managedOrderBean.setShipmentDate(rs.getDate("man_shipment_date"));
            }
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
                    preparedStatement.close();
            }
        }
        return managedOrderBean;
    }


    public ManagedOrder retrieveByOrder(int ordId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ManagedOrder managedOrderBean = new ManagedOrder();

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(RETRIEVE_BY_ORDER);
            preparedStatement.setLong(1, ordId);


            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                managedOrderBean.setUserName(rs.getString("man_user_name"));
                managedOrderBean.setId(rs.getLong("man_order_id"));
                managedOrderBean.setDeliveryDate(rs.getDate("man_delivery_date"));
                managedOrderBean.setTrackNumber(rs.getString("man_tracking_number"));
                managedOrderBean.setCourierName(rs.getString("man_courier"));
                managedOrderBean.setShipmentDate(rs.getDate("man_shipment_date"));
            }
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
                    preparedStatement.close();
            }
        }
        return managedOrderBean;
    }

    public void update(ManagedOrder managedOrder) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, managedOrder.getTrackNumber());
            preparedStatement.setLong(2, managedOrder.getId());
            preparedStatement.setString(3, managedOrder.getUserName());

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0)
                throw new DAOException("Updating managed order failed, no rows affected.");
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


    public void delete(ManagedOrder managedOrder) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, managedOrder.getId());
            preparedStatement.setLong(2, managedOrder.getUserId());

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0)
                throw new DAOException("Deleting managed order failed, no rows affected.");
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

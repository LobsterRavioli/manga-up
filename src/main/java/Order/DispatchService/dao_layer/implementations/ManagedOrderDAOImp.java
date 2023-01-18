package Order.DispatchService.dao_layer.implementations;

import Order.DispatchService.beans.ManagedOrder;
import Order.DispatchService.dao_layer.interfaces.ManagedOrderDAO;
import utils.DAOException;


import javax.sql.DataSource;
import java.sql.*;

public class ManagedOrderDAOImp implements ManagedOrderDAO {

    private DataSource ds;

    public ManagedOrderDAOImp(DataSource ds)
    {
        this.ds = ds;
    }

    private static final String MANAGED_ORDER_TABLE = "manages";

    private static final String CREATE = "INSERT INTO "+MANAGED_ORDER_TABLE+
            " (man_order_id, man_user_id, man_delivery_date, man_tracking_number, man_courier, man_shipment_date)"+
            " VALUES (?, ?, ?, ?, ?, ?) ;";

    private static final String DELETE = "DELETE FROM "+MANAGED_ORDER_TABLE+" WHERE man_order_id = ? AND man_user_id = ? ;";

    private static final String UPDATE = "UPDATE "+MANAGED_ORDER_TABLE+" SET man_tracking_number = ? WHERE man_order_id = ? AND man_user_id = ? ;";

    private static final String RETRIEVE = "SELECT * FROM "+MANAGED_ORDER_TABLE+" WHERE man_order_id = ? AND man_user_id = ? ;";

    private static final String RETRIEVE_BY_DELIVERY_DATE = "SELECT * FROM "+MANAGED_ORDER_TABLE+" WHERE delivery_date = ? ;";
    private static final String RETRIEVE_BY_TRACKING_NUMBER = "SELECT * FROM "+MANAGED_ORDER_TABLE+" WHERE tracking_number = ? ;";

    private static final String COUNT_MANAGED = "SELECT COUNT(*) FROM "+MANAGED_ORDER_TABLE+" WHERE man_user_id = ? ;";
    @Override
    public void create(ManagedOrder managedOrder) throws SQLException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setLong(1, managedOrder.getId());
            preparedStatement.setLong(2, managedOrder.getUserId());
            preparedStatement.setDate(3, managedOrder.getDeliveryDate());
            preparedStatement.setString(4, managedOrder.getTrackNumber());
            preparedStatement.setString(5, managedOrder.getCourierName());
            preparedStatement.setDate(6, managedOrder.getShipmentDate());

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0)
                throw new DAOException("Creating managed order failed, no rows affected.");

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
    public ManagedOrder retrieve(long ordId, long userId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ManagedOrder managedOrderBean = new ManagedOrder();

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(RETRIEVE);
            preparedStatement.setLong(1, ordId);
            preparedStatement.setLong(2, userId);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                managedOrderBean.setId(rs.getLong("man_order_id"));
                managedOrderBean.setUserId(rs.getInt("man_user_id"));
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

    @Override
    public void update(ManagedOrder managedOrder) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, managedOrder.getTrackNumber());
            preparedStatement.setLong(2, managedOrder.getId());
            preparedStatement.setLong(3, managedOrder.getUserId());

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

    @Override
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

    @Override
    public ManagedOrder retrieveByDeliveryDate(Date deliveryDate) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ManagedOrder managedOrderBean = new ManagedOrder();

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(RETRIEVE_BY_DELIVERY_DATE);
            preparedStatement.setDate(1, deliveryDate);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                managedOrderBean.setId(rs.getLong("man_order_id"));
                managedOrderBean.setUserId(rs.getInt("man_user_id"));
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

    @Override
    public ManagedOrder retrieveByTrackNumber(String trackNumber) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ManagedOrder managedOrderBean = new ManagedOrder();

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(RETRIEVE_BY_TRACKING_NUMBER);
            preparedStatement.setString(1, trackNumber);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                managedOrderBean.setId(rs.getLong("man_order_id"));
                managedOrderBean.setUserId(rs.getInt("man_user_id"));
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
}

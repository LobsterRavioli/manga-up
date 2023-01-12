package Order.DispatchService.dao_layer.implementations;

import Order.DispatchService.beans.Order;
import Order.DispatchService.dao_layer.interfaces.OrderDAO;
import utils.DAOException;

import javax.sql.DataSource;
import java.sql.*;

public class OrderDAOImp implements OrderDAO
{
    private DataSource ds;

    public OrderDAOImp(DataSource ds)
    {
        this.ds = ds;
    }

    private static final String ORDER_TABLE = "Orders";

    private static final String CREATE = "INSERT INTO "+ORDER_TABLE+
            " (id, order_date, state, total_price, courier_id, user_id, courier)"+
            " VALUES (?, ?, ?, ?, ?, ?, ?) ;";

    private static final String DELETE = "DELETE FROM "+ORDER_TABLE+" WHERE id = ? ;";

    private static final String UPDATE = "UPDATE "+ORDER_TABLE+" SET state = ? WHERE id = ? ;";

    private static final String RETRIEVE = "SELECT * FROM "+ORDER_TABLE+" WHERE id = ? ;";

    @Override
    public void create(Order order) throws SQLException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setLong(1, order.getId());
            preparedStatement.setDate(2, order.getOrderDate());
            preparedStatement.setString(3, order.getState().toString());
            preparedStatement.setDouble(4, order.getTotalPrice());
            preparedStatement.setLong(5, order.getCourierID());
            preparedStatement.setLong(6, order.getUserID());
            preparedStatement.setString(7, order.getCourierName());

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0)
                throw new DAOException("Creating order failed, no rows affected.");

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
    public void delete(Order order) throws SQLException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, order.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0)
                throw new DAOException("Deleting order failed, no rows affected.");
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
    public void update(Order order) throws SQLException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, order.getState().toString());
            preparedStatement.setLong(2, order.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0)
                throw new DAOException("Updating order failed, no rows affected.");
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
    public Order retrieve(int id) throws SQLException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Order orderBean = new Order();

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(RETRIEVE);
            preparedStatement.setLong(1, orderBean.getId());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                orderBean.setId(rs.getLong("id"));
                orderBean.setOrderDate(rs.getDate("order_date"));
                orderBean.setState(Order.State.valueOf(rs.getString("state")));
                orderBean.setTotalPrice(rs.getDouble("total_price"));
                orderBean.setCourierID(rs.getLong("courier_id"));
                orderBean.setUserID(rs.getLong("user_id"));
                orderBean.setCourierName(rs.getString("courier"));
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
        return orderBean;
    }
}

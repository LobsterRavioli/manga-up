package Order.DispatchService.dao_layer.implementations;

import Merchandising.MerchandiseService.beans.Product;
import Order.DispatchService.beans.Courier;
import Order.DispatchService.beans.Order;
import Order.DispatchService.dao_layer.interfaces.OrderDAO;
import User.AccountService.beans.User;
import utils.DAOException;

import javax.naming.Context;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class OrderDAOImp implements OrderDAO
{
    private DataSource ds;

    public OrderDAOImp(DataSource ds)
    {
        this.ds = ds;
    }

    private static final String ORDER_TABLE = "Orders";

    private static final String CREATE = "INSERT INTO "+ORDER_TABLE+
            " (id, order_date, arrival_date, state, user_id, courier_id)"+
            " VALUES (?, ?, ?, ?, ?, ?) ;";

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
            preparedStatement.setDate(3, order.getArrivalDate());
            preparedStatement.setString(4, order.getState().toString());
            preparedStatement.setLong(5, order.getUser().getId());
            preparedStatement.setString(6, order.getCourier().getName());

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
                // orderBean.setArrivalDate("arrival_date");
                orderBean.setState(Order.State.valueOf(rs.getString("state")));
                User user = new User();
                user.setId(rs.getInt("user_id"));
                orderBean.setUser(user);
                Courier courier = new Courier();
                courier.setId(rs.getLong("courier_id"));

                /*** VALUTARE COME COLLEGARE LA TABELLA PRODOTTO ALLA TABELLA ORDINE ***/
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

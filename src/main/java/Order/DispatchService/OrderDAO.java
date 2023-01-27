package Order.DispatchService;

import User.AccountService.CreditCard;
import utils.DAOException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

public class OrderDAO
{
    private DataSource ds;

    public OrderDAO(DataSource ds)
    {
        this.ds = ds;
    }

    private static final String ORDER_TABLE = "Orders";

    private static final String CREATE = "INSERT INTO "+ORDER_TABLE+
            " (ord_id, ord_date, ord_state, ord_total_price, ord_end_user_id, crd_id)"+
            " VALUES (?, ?, ?, ?, ?, ?) ;";

    private static final String DELETE = "DELETE FROM "+ORDER_TABLE+" WHERE ord_id = ? ;";

    private static final String UPDATE = "UPDATE "+ORDER_TABLE+" SET ord_state = ? WHERE ord_id = ? ;";

    private static final String RETRIEVE = "SELECT * FROM "+ORDER_TABLE+" WHERE ord_id = ? ;";

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
            preparedStatement.setString(3, order.getState());
            preparedStatement.setDouble(4, order.getTotalPrice());
            preparedStatement.setLong(5, order.getEndUserID());
            preparedStatement.setLong(6, order.getCreditCardEndUser());

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

    public void update(Order order) throws SQLException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, order.getState());
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

    public Order retrieve(int id) throws SQLException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Order orderBean = new Order();

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(RETRIEVE);
            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                orderBean.setId(rs.getLong("ord_id"));
                orderBean.setOrderDate(rs.getDate("ord_date"));
                orderBean.setState(rs.getString("ord_state"));
                orderBean.setTotalPrice(rs.getDouble("ord_total_price"));
                orderBean.setEndUserID(rs.getInt("ord_end_user_id"));
                orderBean.setCreditCardEndUser(rs.getInt("crd_id"));
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
                    connection.close();
            }
        }
        return orderBean;
    }

    public Collection<Order> doRetrieveAll(String ordCriteria) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<Order> orders = new LinkedList<Order>();

        String selectQuery = "SELECT * FROM "+ORDER_TABLE;

        if(ordCriteria != null && !ordCriteria.equals(""))
        {
            selectQuery += " ORDER BY "+ordCriteria;
        }

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectQuery);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                Order orderBean = new Order();

                orderBean.setId(rs.getLong("ord_id"));
                orderBean.setOrderDate(rs.getDate("ord_date"));
                orderBean.setState(rs.getString("ord_state"));
                orderBean.setTotalPrice(rs.getDouble("ord_total_price"));
                orderBean.setEndUserID(rs.getInt("ord_end_user_id"));
                orderBean.setCreditCardEndUser(rs.getInt("crd_id"));

                orders.add(orderBean);
            }
        } finally {
            try {
                if(preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if(connection != null)
                    connection.close();
            }
        }

        return orders;
    }

    public Collection<Order> doRetrieveAllForSpecificUser(String orderManagerUserName, String ordCriteria) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<Order> orders = new LinkedList<Order>();

        String selectQuery = "SELECT * " +
                             "FROM Orders AS O, TO_MANAGE AS M " +
                             "WHERE O.ord_id = M.order_id AND M.user_name = ?";

        if(ordCriteria != null && !ordCriteria.equals(""))
        {
            selectQuery += " ORDER BY "+ordCriteria;
        }

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, orderManagerUserName);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                Order orderBean = new Order();

                orderBean.setId(rs.getLong("ord_id"));
                orderBean.setOrderDate(rs.getDate("ord_date"));
                orderBean.setState(rs.getString("ord_state"));
                orderBean.setTotalPrice(rs.getDouble("ord_total_price"));
                orderBean.setEndUserID(rs.getInt("ord_end_user_id"));
                orderBean.setCreditCardEndUser(rs.getInt("crd_id"));

                orders.add(orderBean);
            }
        } finally {
            try {
                if(preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if(connection != null)
                    connection.close();
            }
        }

        return orders;
    }
}
package Order.DispatchService;

import User.AccountService.EndUser;
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

    private static final String ORDER_TABLE = "ORDERS";

    private static final String CREATE = "INSERT INTO "+ORDER_TABLE+
            " (ord_date, ord_state, ord_total_price, ord_end_user_id, ord_address, ord_card)"+
            " VALUES (?, ?, ?, ?, ?, ?) ;";

    private static final String DELETE = "DELETE FROM "+ORDER_TABLE+" WHERE ord_id = ? ;";

    private static final String UPDATE = "UPDATE "+ORDER_TABLE+" SET ord_state = ? WHERE ord_id = ? ;";

    private static final String RETRIEVE = "SELECT * FROM "+ORDER_TABLE+" WHERE ord_id = ? ;";

    public void create(Order order) throws IllegalArgumentException, SQLException
    {

        if(!order.validateOrderCreation())
            throw new IllegalArgumentException("Invalid data.");


        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = ds.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1, order.getOrderDate());
            preparedStatement.setString(2, order.getState());
            preparedStatement.setDouble(3, order.getTotalPrice());
            preparedStatement.setLong(4, order.getEndUserID());
            preparedStatement.setString(5, order.getAddressEndUserInfo());
            preparedStatement.setString(6, order.getCreditCardEndUserInfo());

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0)
                throw new DAOException("Creating order failed, no rows affected.");

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getInt(1));
                } else {
                    throw new DAOException("Creating user failed, no generated key obtained.");
                }
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
                orderBean.setAddressEndUserInfo(rs.getString("ord_address"));
                orderBean.setCreditCardEndUserInfo(rs.getString("ord_card"));
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
        if(orderBean.validateOrder())
            return orderBean;
        else
            return null;
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
                orderBean.setAddressEndUserInfo(rs.getString("ord_address"));
                orderBean.setCreditCardEndUserInfo(rs.getString("ord_card"));

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
                             "FROM ORDERS AS O, TO_MANAGE AS M " +
                             "WHERE O.ord_id = M.order_id AND M.user_name = ?";

        if(ordCriteria != null && !ordCriteria.equals(""))
        {
            selectQuery += " ORDER BY "+ ordCriteria;
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
                orderBean.setAddressEndUserInfo(rs.getString("ord_address"));
                orderBean.setCreditCardEndUserInfo(rs.getString("ord_card"));
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

    public Collection retrieveOrdersAssociatedToUsers(EndUser user) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<Order> orders = new LinkedList<Order>();
        String selectQuery = "SELECT * FROM ORDERS o WHERE o.ord_end_user_id = ?;";

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectQuery);

            preparedStatement.setInt(1, user.getId());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                Order orderBean = new Order();

                orderBean.setId(rs.getLong("ord_id"));
                orderBean.setOrderDate(rs.getDate("ord_date"));
                orderBean.setState(rs.getString("ord_state"));
                orderBean.setTotalPrice(rs.getDouble("ord_total_price"));
                orderBean.setEndUserID(rs.getInt("ord_end_user_id"));
                orderBean.setAddressEndUserInfo(rs.getString("ord_address"));
                orderBean.setCreditCardEndUserInfo(rs.getString("ord_card"));
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

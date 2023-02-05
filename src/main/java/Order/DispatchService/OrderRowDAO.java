package Order.DispatchService;

import Merchandising.MerchandiseService.Manga;
import utils.DAOException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class OrderRowDAO {

    private DataSource ds;
    private static final String ORDER_ROW_TABLE = "Order_row";

    private static final String CREATE = "INSERT INTO "+ORDER_ROW_TABLE+
            " (ord_id, user_id, manga_name, manga_price, quantity)"+
            " VALUES (?, ?, ?, ?, ?) ;";

    public OrderRowDAO(DataSource ds)
    {
        this.ds = ds;
    }

    public void create(OrderRow row) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = ds.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setLong(1, row.getOrderID());
            preparedStatement.setLong(2, row.getUserId());
            preparedStatement.setString(3, row.getMangaName());
            preparedStatement.setDouble(4, row.getMangaPrice());
            preparedStatement.setInt(5, row.getQuantity());

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0)
                throw new DAOException("Creating order failed, no rows affected.");

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


    public Collection<OrderRow> retrieveOrderRowAssociatedToOrder(Order order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<OrderRow> orders = new LinkedList<OrderRow>();
        String selectQuery = "SELECT * FROM Order_row o WHERE o.ord_id = ?;";

        try
        {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectQuery);

            preparedStatement.setLong(1, order.getId());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                OrderRow orderBean = new OrderRow();
                orderBean.setOrderId(rs.getLong("ord_id"));
                orderBean.setUserId(rs.getLong("user_id"));
                orderBean.setMangaName(rs.getString("manga_name"));
                orderBean.setMangaPrice(rs.getDouble("manga_price"));
                orderBean.setQuantity(rs.getInt("quantity"));

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

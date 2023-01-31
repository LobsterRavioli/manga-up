package Order.DispatchService;

import Merchandising.MerchandiseService.Manga;
import utils.DAOException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}

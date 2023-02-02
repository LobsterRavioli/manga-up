package Order.DispatchService;

import Merchandising.MerchandiseService.Manga;

import java.sql.SQLException;
import java.util.ArrayList;
import User.AccountService.User;

public interface OrderSubmissionFacade {
    void createOrder(Order order, ArrayList<Manga> products,User selectedManager) throws Exception;
    public void executeTask(ManagedOrder managedOrder) throws SQLException;

    public static final String ORDER_SUBMISSION_FACADE = "ORDER_SUBMISSION_FACADE";
}

package Order.DispatchService;

import Merchandising.MerchandiseService.Manga;

import java.util.ArrayList;
import User.AccountService.User;

public interface OrderSubmissionFacade {
    void createOrder(Order order, ArrayList<Manga> products,User selectedManager) throws Exception;

    public static final String ORDER_SUBMISSION_FACADE = "ORDER_SUBMISSION_FACADE";
}

package Order.DispatchService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Cart.CheckoutService.Cart;
import Cart.CheckoutService.CartDAO;
import Merchandising.MerchandiseService.Manga;
import User.AccountService.EndUser;
import User.AccountService.UserDAO;
import User.AccountService.User;
import javax.sql.DataSource;

public class OrderSubmissionFacadeImp implements OrderSubmissionFacade {

    private DataSource ds;
    private OrderDAO orderDAO;
    private UserDAO uD;
    private ToManageDAO assignDAO;
    private CartDAO cartDAO;

    private OrderRowDAO orderRowDAO;
    private CartDAO cD;
    public OrderSubmissionFacadeImp(DataSource ds){
        this.ds = ds;
        this.orderDAO = new OrderDAO(ds);

    }



    public void createOrder(Order order, ArrayList<Manga> products) throws Exception{

        User selectedManager;
        OrderRow orderRow;
        orderDAO.create(order);
        for (Manga manga : products) {
             orderRow = new OrderRow(order, order.getEndUser(), manga, manga.getQuantity());
             orderRowDAO.create(orderRow);
        }
        List<User> candidatesManagers = (List<User>) uD.getAllBeginnerOrderManagers();
        if(candidatesManagers!=null){
            selectedManager = candidatesManagers.get(0);
            assignDAO.create(new ToManage(selectedManager,order));
        }else{
            String targetUserUsername = uD.getTargetOrderManagerUserName();
            assignDAO.create(new ToManage(new User(targetUserUsername,""),order));
        }

    }

}

package Order.DispatchService;

import java.sql.Date;
import java.time.LocalDate;
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

    private OrderRowDAO orderRowDAO;
    public OrderSubmissionFacadeImp(DataSource ds){
        this.ds = ds;
        this.orderDAO = new OrderDAO(ds);
        this.orderRowDAO = new OrderRowDAO(ds);
        this.uD = new UserDAO(ds);
        this.assignDAO = new ToManageDAO(ds);

    }



    public void createOrder(Order order, ArrayList<Manga> products,User selectedManager) throws Exception{

        OrderRow orderRow;
        order.setOrderDate(Date.valueOf(LocalDate.now()));
        orderDAO.create(order);
        for (Manga manga : products) {
             orderRow = new OrderRow(order, order.getEndUser(), manga, manga.getQuantity());
             orderRowDAO.create(orderRow);
        }
            assignDAO.create(new ToManage(selectedManager,order));
    }

}

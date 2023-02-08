package Order.DispatchService;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Cart.CheckoutService.Cart;
import Cart.CheckoutService.CartDAO;
import Merchandising.MerchandiseService.Manga;
import User.AccountService.*;

import javax.sql.DataSource;

public class OrderSubmissionFacadeImp implements OrderSubmissionFacade {

    private DataSource ds;
    private OrderDAO orderDAO;
    private UserDAO uD;
    private ToManageDAO assignDAO;

    private ManagedOrderDAO managedOrderDAO;

    private OrderRowDAO orderRowDAO;
    public OrderSubmissionFacadeImp(DataSource ds){
        this.ds = ds;
        this.orderDAO = new OrderDAO(ds);
        this.orderRowDAO = new OrderRowDAO(ds);
        this.uD = new UserDAO(ds);
        this.assignDAO = new ToManageDAO(ds);
        this.managedOrderDAO = new ManagedOrderDAO(ds);

    }


    public void createOrder(Order order, ArrayList<Manga> products,User selectedManager) throws Exception{

        if(order == null || products == null || selectedManager == null || order.getEndUser() == null || order.getCreditCardEndUserInfo() == null || order.getAddressEndUserInfo() == null)
            throw new IllegalArgumentException("Parametri invalidi");

        if(products.size() == 0)
            throw new IllegalArgumentException("Parametri invalidi");

        if(order.getEndUser().getId() <= 0)
            throw new IllegalArgumentException("Parametri invalidi");

        if(!CreditCard.validate(order.getEndUserCard()))
            throw new IllegalArgumentException("Parametri invalidi");

        if(!Address.validate(order.getEndUserAddress()))
            throw new IllegalArgumentException("Parametri invalidi");

        for (Manga m : products) {
            if(m.getQuantity() == 0 || m.getName() == null || m.getName().length() > 50 || m.getName().trim().equals("")|| m.getPrice() <= 0 || m.getQuantity() <= 0)
                throw new IllegalArgumentException("Parametri invalidi");
        }

        OrderRow orderRow;
        order.setOrderDate(Date.valueOf(LocalDate.now()));
        float total = 0;

        for (Manga p : products) {
            total += p.getPrice();
        }

        order.setTotalPrice(total);
        orderDAO.create(order);
        for (Manga manga : products) {
             orderRow = new OrderRow(order, order.getEndUser(), manga, manga.getQuantity());
             orderRowDAO.create(orderRow);
        }

        assignDAO.create(new ToManage(selectedManager,order));
    }

    public void executeTask(ManagedOrder managedOrder) throws SQLException
    {
        managedOrderDAO.create(managedOrder); // aggiungo l'ordine alla tabbella degli ordini gestiti
        managedOrder.setState(Order.SENT);
        orderDAO.update(managedOrder); // modifico lo stato dell'ordine nella tabella Orders
        ToManage toManage = new ToManage();
        toManage.setUserName(managedOrder.getUserName());
        toManage.setOrderId(managedOrder.getId());

        assignDAO.delete(toManage); // elimino l'ordine gestito dalla lista degli ordini da gestire (task completato)
    }

}

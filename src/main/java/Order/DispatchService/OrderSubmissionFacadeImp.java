package Order.DispatchService;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

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

    public OrderSubmissionFacadeImp(DataSource ds, OrderDAO orderDAO, UserDAO uD, ToManageDAO assignDAO, ManagedOrderDAO managedOrderDAO, OrderRowDAO orderRowDAO){
        this.ds = ds;
        this.orderDAO = orderDAO;
        this.uD = uD;
        this.assignDAO = assignDAO;
        this.managedOrderDAO = managedOrderDAO;
        this.orderRowDAO = orderRowDAO;
    }


    private static boolean validateOrderCreationParameters(Order order, ArrayList<Manga> products, User selectedManager)
    {
        if(order == null || products == null || selectedManager == null)
            return false;

        if(!order.validateOrder())
            return false;

        for(Manga m : products)
            if(!m.validateManga())
                return false;

        if(!selectedManager.validateUser())
            return false;

        return true;
    }

    public void createOrder(Order order, ArrayList<Manga> products,User selectedManager) throws Exception{


        if(!validateOrderCreationParameters(order, products, selectedManager))
            throw new IllegalArgumentException("Invalid data");

        OrderRow orderRow;
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

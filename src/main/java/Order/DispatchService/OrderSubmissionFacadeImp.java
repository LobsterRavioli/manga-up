package Order.DispatchService;

import java.sql.SQLException;
import java.util.*;

import Merchandising.MerchandiseService.Manga;
import User.AccountService.*;

import javax.sql.DataSource;

public class OrderSubmissionFacadeImp implements OrderSubmissionFacade {

    private DataSource ds;
    private OrderDAO orderDAO;
    private ToManageDAO assignDAO;
    private ManagedOrderDAO managedOrderDAO;
    private OrderRowDAO orderRowDAO;
    public OrderSubmissionFacadeImp(DataSource ds){
        this.ds = ds;
        this.orderDAO = new OrderDAO(ds);
        this.orderRowDAO = new OrderRowDAO(ds);
        this.assignDAO = new ToManageDAO(ds);
        this.managedOrderDAO = new ManagedOrderDAO(ds);

    }

    public OrderSubmissionFacadeImp(DataSource ds, OrderDAO orderDAO, UserDAO uD, ToManageDAO assignDAO, ManagedOrderDAO managedOrderDAO, OrderRowDAO orderRowDAO){
        this.ds = ds;
        this.orderDAO = orderDAO;
        this.assignDAO = assignDAO;
        this.managedOrderDAO = managedOrderDAO;
        this.orderRowDAO = orderRowDAO;
    }


    public void createOrder(Order order, ArrayList<Manga> products,User selectedManager) throws Exception{

        if(order==null || products==null || selectedManager==null){
            throw new Exception("Almeno uno dei parametri passati è nullo");
        }

        OrderRow orderRow;
        float total = 0;

        for (Manga p : products) {
            total += p.getPrice()*p.getQuantity();
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

        if(managedOrder==null){
            throw new SQLException("managedOrder passato nullo");
        }

        managedOrderDAO.create(managedOrder); // aggiungo l'ordine alla tabella degli ordini gestiti
        managedOrder.setState(Order.SENT);
        orderDAO.update(managedOrder); // modifico lo stato dell'ordine nella tabella Orders
        ToManage toManage = new ToManage();
        toManage.setUserName(managedOrder.getUserName());
        toManage.setOrderId(managedOrder.getId());

        assignDAO.delete(toManage); // elimino l'ordine gestito dalla lista degli ordini da gestire (task completato)
    }
}

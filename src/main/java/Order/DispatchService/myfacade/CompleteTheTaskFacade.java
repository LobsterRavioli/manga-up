package Order.DispatchService.myfacade;

import Order.DispatchService.*;

import javax.sql.DataSource;
import java.sql.SQLException;

/*
 *  Tutta la logica per ADEMPIERE A UN TASK ASSEGNATO A UN ORDER MANAGER.
 */
public class CompleteTheTaskFacade {

    private DataSource ds;

    private OrderDAO orderDAO;
    private ManagedOrderDAO managedOrderDAO;
    private ToManageDAO toManageDAO;

    public CompleteTheTaskFacade(DataSource ds)
    {
        this.ds = ds;

        orderDAO = new OrderDAO(ds);
        managedOrderDAO = new ManagedOrderDAO(ds);
        toManageDAO = new ToManageDAO(ds);
    }

    /*
    * Prende in input un ordine gestito da un orderManager
    * (il bean ManagedOrder in input è costruito con i dati inseriti nel form per gestirlo:
    * tracking number, delivery date ecc..)
    * ed utilizza i DAO per mantenere la coerenza delle info nel DB.
    */

    public void executeTask(ManagedOrder managedOrder) throws SQLException
    {
        managedOrderDAO.create(managedOrder); // aggiungo l'ordine alla tabbella degli ordini gestiti
        managedOrder.setState(Order.SENT);
        orderDAO.update(managedOrder); // modifico lo stato dell'ordine nella tabella Orders

        ToManage toManage = new ToManage();
        toManage.setUserName(managedOrder.getUserName());
        toManage.setOrderId(managedOrder.getId());

        toManageDAO.delete(toManage); // elimino l'ordine gestito dalla lista degli ordini da gestire (task completato)
    }
}

package Order.DispatchService.dao_layer.interfaces;

import Order.DispatchService.beans.ToManage;

import java.sql.SQLException;

/*
*
* Ordini da gestire
*
*/

public interface ToManageDAO {

    // create: crea un ordine che deve essere gestito da un certo user (assegna un ordine a un gestore)
    void create(ToManage order) throws SQLException;
    // retrieve

    // update

    // delete: rimuove un ordine gestito
    void delete(ToManage order) throws SQLException;
}

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
    private OrderDAO oD;
    private UserDAO uD;
    private ToManageDAO tD;
    private CartDAO cD;
    public OrderSubmissionFacadeImp(DataSource ds){
        this.ds = ds;
        this.oD = new OrderDAO(ds);
        this.uD = new UserDAO(ds);
        this.tD = new ToManageDAO(ds);
        this.cD = new CartDAO(ds);
    }

    @Override
    public void OrderCreation(Order order) throws Exception{
        oD.create(order);

        HashMap<Manga,Integer> productsMap = cD.retrieveByUser(new EndUser((int)order.getEndUserID()));

        for (Map.Entry <Manga, Integer> set : productsMap.entrySet()) {
            //oD.createRow(order,set); Chiamata di un metodo da aggiungere in OrdineDAO che si occupa di inserire una riga d'ordine
        }

        List<User> beginnerUDs= (List<User>) uD.getAllBeginnerOrderManagers();
        if(beginnerUDs!=null){
            User selectedBeginner = beginnerUDs.get(0);
            tD.create(new ToManage(selectedBeginner,order));
        }else{
            String targetUserUsername = uD.getTargetOrderManagerUserName();
            tD.create(new ToManage(new User(targetUserUsername,""),order));
        }
        cD.toEmptyCart(new EndUser((int)order.getEndUserID())); //Attenzione a problemi di underflow data la conversione a int...
    }

}

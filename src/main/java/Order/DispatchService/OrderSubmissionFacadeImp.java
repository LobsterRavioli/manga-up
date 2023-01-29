package Order.DispatchService;

import java.util.*;

import static java.util.Map.Entry;

import Cart.CheckoutService.CartDAO;
import Merchandising.MerchandiseService.Manga;
import Merchandising.MerchandiseService.MangaDAO;
import User.AccountService.EndUser;
import User.AccountService.UserDAO;
import User.AccountService.User;
import javax.sql.DataSource;

public class OrderSubmissionFacadeImp implements OrderSubmissionFacade {

    private DataSource ds;
    private OrderDAO orderDAO;
    private UserDAO userDAO;
    private ToManageDAO assignmentDAO;
    private CartDAO cartDAO;
    private MangaDAO mangaDAO;
    private OrderRowDAO orderRowDAO;

    public OrderSubmissionFacadeImp(DataSource ds){
        this.ds = ds;
        this.orderDAO = new OrderDAO(ds);
        this.userDAO = new UserDAO(ds);
        this.assignmentDAO = new ToManageDAO(ds);
        this.cartDAO = new CartDAO(ds);
        this.orderRowDAO = new OrderRowDAO(ds);
    }

    @Override
    public void OrderCreation(Order order) throws Exception{

        OrderRow orderRow;
        ToManage orderToAssign;
        int storeQuantity;
        EndUser user = order.getEndUser();
        User orderManagerChosen = new User();
        List<User> orderManagersCandidates = (List<User>) userDAO.getAllBeginnerOrderManagers();
        HashMap<Manga,Integer> cart = cartDAO.retrieveByUser(user);
        orderDAO.create(order);
        List<Manga> cartProducts = new LinkedList<Manga>(cart.keySet());

        if(!checkAvailability(cartProducts)){
            cartDAO.toEmptyCart(user);
            return;
        }

        for (Manga cartProduct : cartProducts) {
            storeQuantity = mangaDAO.retrieveById(cartProduct.getId()).getQuantity();
            orderRow = new OrderRow();
            orderRow.setMangaName(cartProduct.getName());
            orderRow.setMangaPrice(cartProduct.getPrice());
            orderRow.setQuantity(cartProduct.getQuantity());
            orderRow.setOrderId(order.getId());
            orderRowDAO.create(orderRow);
            cartProduct.setQuantity(storeQuantity - cartProduct.getQuantity());
            mangaDAO.updateQuantity(cartProduct);
        }

        if(orderManagersCandidates != null){
            orderManagerChosen = orderManagersCandidates.get(0);
        }
        else {
            String orderManagerUsernameCandidate = userDAO.getTargetOrderManagerUserName();
            orderManagerChosen.setUsername(orderManagerUsernameCandidate);
        }

        orderToAssign = new ToManage(orderManagerChosen,order);
        assignmentDAO.create(orderToAssign);
        cartDAO.toEmptyCart(user);
    }

    public boolean checkAvailability(List<Manga> mangas) throws Exception{
        for(Manga manga: mangas)
            if(mangaDAO.checkQuantity(manga)) return false;
        return true;

    }








}

package User.AccountService;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class UserFacadeImp implements UserFacade {

    private DataSource ds;
    private UserDAO userDAO;
    private AddressDAO addressDAO;
    private CreditCardDAO creditCardDAO;
    private EndUserDAO endUserDAO;


    public UserFacadeImp(DataSource ds){
        this.ds = ds;
        this.userDAO = new UserDAO(ds);
        this.addressDAO = new AddressDAO(ds);
        this.creditCardDAO = new CreditCardDAO(ds);
        this.endUserDAO = new EndUserDAO(ds);
    }


    private static User getRandomOrderManager(Collection<User> orderManagers)
    {
        Random random = new Random();

        int randomNumber = random.nextInt(orderManagers.size());
        User randomManager = null;

        Iterator<User> iterator = orderManagers.iterator();

        for(int currentIndex = 0; iterator.hasNext(); currentIndex++)
        {
            randomManager = iterator.next();

            if(currentIndex == randomNumber)
                break;
        }
        return randomManager;
    }

    public User managerEngagement(){
        try {
            User selectedManager;
            List<User> candidatesManagers = (List<User>) userDAO.getAllBeginnerOrderManagers();
            if (candidatesManagers != null) {
                selectedManager = getRandomOrderManager(candidatesManagers);
                return selectedManager;
            } else {
                String targetUserUsername = userDAO.getTargetOrderManagerUserName();
                selectedManager = new User(targetUserUsername, "");
                return selectedManager;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void registration(EndUser user) throws Exception{
        endUserDAO.create(user);
        Address userAddress = (Address) user.getAddresses().iterator().next();
        CreditCard userCard = (CreditCard) user.getCards().iterator().next();
        addressDAO.create(userAddress);
        creditCardDAO.create(userCard);
    }
}

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

    public UserFacadeImp(UserDAO userDAO, AddressDAO addressDAO, CreditCardDAO creditCardDAO, EndUserDAO endUserDAO) {
        this.userDAO = userDAO;
        this.addressDAO = addressDAO;
        this.creditCardDAO = creditCardDAO;
        this.endUserDAO = endUserDAO;
    }

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

        if(user == null || user.getCards() == null || user.getAddresses() == null || user.getAddresses().size() == 0 || user.getCards().size() == 0)
            throw new Exception("Utente non valido");
        Address userAddress = (Address) user.getAddresses().iterator().next();
        CreditCard userCard = (CreditCard) user.getCards().iterator().next();

        if(!Address.validate(userAddress) || !CreditCard.validate(userCard) || !EndUser.validate(user))
            throw new IllegalArgumentException("Dati non validi");

        endUserDAO.create(user);
        addressDAO.create(userAddress);
        creditCardDAO.create(userCard);

    }
}

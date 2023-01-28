package User.ProfileView;


import User.AccountService.*;
import utils.DAOException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

public class UserServicesFacadeImp {

    CreditCardDAO creditCardDAO;
    EndUserDAO endUserDAO;
    UserDAO userDAO;
    AddressDAO addressDAO;
    UserRoleDAO userRoleDAO;

    public UserServicesFacadeImp(DataSource ds){
        creditCardDAO = new CreditCardDAO(ds);
        endUserDAO = new EndUserDAO(ds);
        userDAO = new UserDAO(ds);
        addressDAO = new AddressDAO(ds);
        userRoleDAO = new UserRoleDAO(ds);
    }


    public void registration(EndUser endUser){
        try{
            if(!isEndUserValid(endUser)){
                throw new IllegalArgumentException("User is not valid");
            }
            endUserDAO.create(endUser);
        }
        catch (DAOException e){
            return;
        }


    }

    public EndUser findEndUserByEmailAndPassword(String email, String password){
        try{
            if(email == null || password == null){
                throw new IllegalArgumentException("Email or password is null");
            }
            return null;

        }
        catch (DAOException e){
            return null;
        }

    }

    public User findUserByUsernameAndPassword(String username, String password){
        try{
            if(username == null || password == null){
                throw new IllegalArgumentException("Username or password is null");
            }
            return null;
        }
        catch (DAOException e){
            return null;
        }

    }

    public void addCreditCardToEndUser(CreditCard creditCard){
        try{
            if(!isCreditCardValid(creditCard)){
                throw new IllegalArgumentException("Credit card is not valid");
            }
            creditCardDAO.create(creditCard);
        }
        catch (DAOException e){
            return;
        }

    }

    public void removeCreditCardFromEndUser(CreditCard creditCard){
        try{
            if(!isCreditCardValid(creditCard)){
                throw new IllegalArgumentException("Credit card is not valid");
            }
            creditCardDAO.delete(creditCard);
        }
        catch (DAOException e){
            return;
        }

    }


    public void addAddressToAccount(Address creditCard){
        try {
            if(!isAddressValid(creditCard)){
                throw new IllegalArgumentException("Address is not valid");
            }
            addressDAO.create(creditCard);
        }
        catch (DAOException e){
            return;
        }

    }


    public void removeAddressToAccount(Address creditCard){
        try{
            if(!isAddressValid(creditCard)){
                throw new IllegalArgumentException("Address is not valid");
            }
            addressDAO.delete(creditCard);
        }
        catch (DAOException e){
            return;
        }

    }

    public Collection findAssociatedAddresses(EndUser endUser){
        try{
            if(!isEndUserValid(endUser)){
                throw new IllegalArgumentException("User is not valid");
            }
            return addressDAO.findAssociatedAddresses(endUser);
        }
        catch (DAOException e){
            return null;
        }
    }

    public Collection findAssociatedCards(EndUser endUser){

        try {
            if(!isEndUserValid(endUser)){
                throw new IllegalArgumentException("User is not valid");
            }

            return creditCardDAO.findAssociatedCards(endUser);
        } catch (DAOException e) {
            return null;
        }
    }

    public void createUser(User user){
        try {
            if (!isUserValid(user)) {
                throw new IllegalArgumentException("User is not valid");
            }

            if (userDAO.getUserByUsername(user.getUsername()) != null) {
                throw new IllegalArgumentException("User already exists");
            }

            userDAO.createUser(user);

        } catch (SQLException e) {
            return;
        }
    }

    public void deleteUser(User user){
        try{
            if (!isUserValid(user)) {
                throw new IllegalArgumentException("User is not valid");
            }

            userDAO.removeUserByUserName(user.getUsername());
            return;

        } catch (SQLException e) {
            return;
        }


    }

    public Collection getAllUsers() {
        try {

            Collection users = null;
            users = userDAO.getAllUsers(null);
            Iterator<User> userIterator = users.iterator();
            while (userIterator.hasNext()) {
                User user = userIterator.next();
                user.setRoles(userRoleDAO.getRoles(user));
            }

            return users;

        } catch (SQLException e) {
            return null;
        }

    }

    private static boolean isUserValid(User user) {
        return user != null
                && user.getUsername() != null
                && user.getPassword() != null
                && user.getId() != 0
                && user.getRoles() != null
                && user.getRoles().size() > 0;
    }

    private static boolean isEndUserValid(EndUser endUser) {
        return endUser != null
                && endUser.getEmail() != null
                && endUser.getPassword() != null
                && endUser.getId() != 0
                && endUser.getBirthdate() != null
                && endUser.getName() != null
                && endUser.getSurname() != null
                && endUser.getPhoneNumber() != null;
    }

    private static boolean isCreditCardValid(CreditCard creditCard) {
        return creditCard != null
                && creditCard.getCardNumber() != null
                && creditCard.getCardHolder() != null
                && creditCard.getId() != 0
                && creditCard.getCvv() != null
                && creditCard.getExpirementDate() != null;
    }

    private static boolean isAddressValid(Address address) {
        return address != null
                && address.getCity() != null
                && address.getCountry() != null
                && address.getId() != 0
                && address.getStreet() != null
                && address.getPostalCode() != null
                && address.getEndUser() != null;
    }

}

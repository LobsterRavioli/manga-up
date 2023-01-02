package User.AccountService.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EndUser extends User{

    private String name;
    private String surname;
    private String number;
    private List<Address> addresses;
    private List<PaymentCard> cards;
    private Date birthdate;

    public EndUser(){}

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    public String getEmail(){
        return this.username;
    }
    public void setEmail(String email){
        this.username = email;
    }

    public EndUser(String name, String surname, String number, Date birthdate, List<Address> addresses, List<PaymentCard> cards) {
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.birthdate = birthdate;
        setAddresses(addresses);
        setCards(cards);
    }


    public EndUser(String name, String surname, String number, Date birthdate) {
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.birthdate = birthdate;
    }

    @Override
    public String getRole() {
        return this.getClass().getSimpleName().toUpperCase(Locale.ROOT);
    }


    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public List<Address> getAddresses() {
        ArrayList<Address> clonedAddressesList = new ArrayList<Address>();
        clonedAddressesList.addAll(addresses);
        return clonedAddressesList;
    }

    public void setAddresses(List<Address> addresses) {
        ArrayList<Address> clonedList = new ArrayList<Address>();
        clonedList.addAll(addresses);
        this.addresses = clonedList;
    }

    public List<PaymentCard> getCards() {
        ArrayList<PaymentCard> clonedList = new ArrayList<PaymentCard>();
        clonedList.addAll(cards);
        return clonedList;
    }

    public void setCards(List<PaymentCard> cards) {
        ArrayList<PaymentCard> clonedList = new ArrayList<PaymentCard>();
        clonedList.addAll(cards);
        this.cards = clonedList;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getBirthdate() {
        return birthdate;
    }
}

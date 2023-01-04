package User.AccountService.beans;

import java.util.*;

public class EndUser{
    int id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String password;
    private Set addresses;
    private Set cards;
    private Date birthdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EndUser(){
        cards = new HashSet();
        addresses = new HashSet();
    }

    public EndUser(int id, String name, String surname, String email, String phoneNumber, String password, Date birthdate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.birthdate = birthdate;
        this.addresses = new HashSet();
        this.cards = new HashSet();
    }

    public EndUser(String name, String surname, String email, String phoneNumber, String password, Date birthdate) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.birthdate = birthdate;
        this.addresses = new HashSet();
        this.cards = new HashSet();
    }

    public void addPaymentCard(PaymentCard c){
        cards.add(c);
    }

    public void removePaymentCard(PaymentCard c){
        cards.remove(c);
    }

    public Collection getPaymentCard(){
        return cards;
    }

    public void addAddress(Address c){
        addresses.add(c);
    }
    public void removeAddress(PaymentCard c){
        addresses.remove(c);
    }

    public Collection getAddresses(){
        return addresses;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddresses(Set addresses) {
        this.addresses = addresses;
    }

    public Set getCards() {
        return cards;
    }

    public void setCards(Set cards) {
        this.cards = cards;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EndUser endUser = (EndUser) o;
        return id == endUser.id && Objects.equals(name, endUser.name) && Objects.equals(surname, endUser.surname) && Objects.equals(email, endUser.email) && Objects.equals(phoneNumber, endUser.phoneNumber) && Objects.equals(password, endUser.password) && Objects.equals(addresses, endUser.addresses) && Objects.equals(cards, endUser.cards) && Objects.equals(birthdate, endUser.birthdate);
    }

    public boolean shallow_equals(EndUser o){
        if (o == null || getClass() != o.getClass()) return false;
        EndUser endUser = (EndUser) o;
        return id == endUser.id && Objects.equals(name, endUser.name) && Objects.equals(surname, endUser.surname) && Objects.equals(email, endUser.email) && Objects.equals(phoneNumber, endUser.phoneNumber) && Objects.equals(password, endUser.password) && Objects.equals(addresses, endUser.addresses) && Objects.equals(cards, endUser.cards) && Objects.equals(birthdate, endUser.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, phoneNumber, password, addresses, cards, birthdate);
    }

    @Override
    public String toString() {
        return "EndUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", number='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", addresses=" + addresses +
                ", cards=" + cards +
                ", birthdate=" + birthdate +
                '}';
    }

    public static boolean checkConventions(EndUser user){
        return true;
    }
}

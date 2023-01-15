package User.AccountService.beans;

import java.util.*;

public class EndUser{
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String password;
    private Set addresses;
    private Set cards;
    private Date birthdate;
    private int id;



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
        setCards(new HashSet());
        setAddresses(new HashSet());
    }

    public EndUser(int id){
        this.setId(id);
        setCards(new HashSet());
        setAddresses(new HashSet());
    }

    public EndUser(int id, String name, String password){
        this.setId(id);
        this.setName(name);
        this.setPassword(password);
        setCards(new HashSet());
        setAddresses(new HashSet());
    }

    public EndUser(String name, String surname, String email, String phoneNumber, String password, Date birthdate, Set addresses, Set cards) {
        this.setName(name);
        this.setSurname(surname);
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);
        this.setPassword(password);
        this.setBirthdate(birthdate);
        this.setAddresses(new HashSet());
        this.setCards(new HashSet());
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set getAddresses() {
        return addresses;
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

    public void addAddress(Address address){
        getAddresses().add(address);
        address.setEndUser(this);
    }

    public void removeAddress(Address address){
        getAddresses().remove(address);
        address.setEndUser(null);
    }


    public void addCards(CreditCard card){
        getCards().add(card);
        card.setEndUser(this);
    }

    public void removeCards(CreditCard card){
        getCards().remove(card);
        card.setEndUser(null);
    }

    @Override
    public String toString() {
        return "EndUser{" +
                "name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", addresses=" + getAddresses() +
                ", cards=" + getCards() +
                ", birthdate=" + getBirthdate() +
                ", id=" + getId() +
                '}';
    }
}

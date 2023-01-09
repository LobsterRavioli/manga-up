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
        cards = new HashSet();
        addresses = new HashSet();
    }

    public EndUser(String name, String surname, String email, String phoneNumber, String password, Date birthdate, Set addresses, Set cards) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.birthdate = birthdate;
        this.addresses = new HashSet();
        this.cards = new HashSet();
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
        addresses.add(address);
        address.setEndUser(this);
    }

    public void removeAddress(Address address){
        addresses.remove(address);
        address.setEndUser(null);
    }

    @Override
    public String toString() {
        return "EndUser{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", addresses=" + addresses +
                ", cards=" + cards +
                ", birthdate=" + birthdate +
                ", id=" + id +
                '}';
    }
}

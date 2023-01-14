package User.AccountService.beans;

import java.util.Date;
import java.util.Set;

public class EndUserBuilder {
    private int id;
    private String name;
    private String password;
    private String surname;
    private String email;
    private String phoneNumber;
    private Date birthdate;
    private Set addresses;
    private Set cards;

    public EndUserBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public EndUserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public EndUserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public EndUserBuilder setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public EndUserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public EndUserBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public EndUserBuilder setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public EndUserBuilder setAddresses(Set addresses) {
        this.addresses = addresses;
        return this;
    }

    public EndUserBuilder setCards(Set cards) {
        this.cards = cards;
        return this;
    }

    public EndUser createEndUser() {
        return new EndUser(id);
    }
}
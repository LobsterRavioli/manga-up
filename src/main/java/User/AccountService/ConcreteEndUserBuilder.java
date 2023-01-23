package User.AccountService;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ConcreteEndUserBuilder implements EndUserBuilder {
    private int id;
    private String name;
    private String password;
    private String surname;
    private String email;
    private String phoneNumber;
    private Date birthdate;
    private Set addresses;
    private Set cards;

    @Override
    public ConcreteEndUserBuilder setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public ConcreteEndUserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ConcreteEndUserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public ConcreteEndUserBuilder setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    @Override
    public ConcreteEndUserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public ConcreteEndUserBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public ConcreteEndUserBuilder setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    @Override
    public ConcreteEndUserBuilder setAddresses(Set addresses) {
        this.addresses = addresses;
        return this;
    }

    @Override
    public ConcreteEndUserBuilder setCards(Set cards) {
        this.cards = cards;
        return this;
    }

    @Override
    public EndUser createEndUser() {
        return new EndUser(name, surname, email, phoneNumber, password, birthdate, new HashSet(), new HashSet());
    }
}
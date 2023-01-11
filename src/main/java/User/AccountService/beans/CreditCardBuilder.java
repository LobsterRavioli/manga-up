package User.AccountService.beans;

public class CreditCardBuilder {
    private int id;
    private String cvv;
    private EndUser endUser;
    private String cardNumber;
    private String name;
    private String surname;
    private String expirementDate;

    public CreditCardBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public CreditCardBuilder setCvv(String cvv) {
        this.cvv = cvv;
        return this;
    }

    public CreditCardBuilder setEndUser(EndUser endUser) {
        this.endUser = endUser;
        return this;
    }

    public CreditCardBuilder setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public CreditCardBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CreditCardBuilder setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public CreditCardBuilder setExpirementDate(String expirementDate) {
        this.expirementDate = expirementDate;
        return this;
    }

    public CreditCard createCreditCard() {
        return new CreditCard(id, cvv, endUser, cardNumber, name, surname, expirementDate);
    }
}
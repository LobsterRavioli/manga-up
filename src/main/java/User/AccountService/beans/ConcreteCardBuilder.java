package User.AccountService.beans;

import java.util.Date;

public class ConcreteCardBuilder implements CreditCardBuilder {
    private int id;
    private String cvv;
    private EndUser endUser;
    private String cardNumber;
    private String cardHolder;
    private Date expirementDate;

    @Override
    public ConcreteCardBuilder setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public ConcreteCardBuilder setCvv(String cvv) {
        this.cvv = cvv;
        return this;
    }

    @Override
    public ConcreteCardBuilder setEndUser(EndUser endUser) {
        this.endUser = endUser;
        return this;
    }

    @Override
    public ConcreteCardBuilder setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    @Override
    public ConcreteCardBuilder setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
        return this;
    }

    @Override
    public ConcreteCardBuilder setExpirementDate(Date expirementDate) {
        this.expirementDate = expirementDate;
        return this;
    }

    @Override
    public CreditCard createCreditCard() {
        return new CreditCard(id, cvv, endUser, cardNumber, cardHolder, expirementDate);
    }

    @Override
    public ConcreteCardBuilder setExpirationDate(Date expirationDate) {
        this.expirementDate = expirationDate;
        return this;
    }
}
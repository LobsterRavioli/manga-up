package User.AccountService.beans;

public class CreditCardBuilder {
    private int id;
    private String cvv;
    private EndUser endUser;
    private String cardNumber;
    private String cardHolder;
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

    public CreditCardBuilder setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
        return this;
    }

    public CreditCardBuilder setExpirementDate(String expirementDate) {
        this.expirementDate = expirementDate;
        return this;
    }

    public CreditCard createCreditCard() {
        return new CreditCard(id, cvv, endUser, cardNumber, cardHolder, expirementDate);
    }
}
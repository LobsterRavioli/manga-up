package User.AccountService.beans;

public class CreditCard {
    private String cvc;
    private String cardNumber;
    private EndUser cardHolder;

    public CreditCard() {
    }

    public CreditCard(String cvc, String cardNumber, EndUser cardHolder) {
        this.cvc = cvc;
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
    }

    public CreditCard(String cvc, String cardNumber) {
        this.cvc = cvc;
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public EndUser getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(EndUser cardHolder) {
        this.cardHolder = cardHolder;
    }
}

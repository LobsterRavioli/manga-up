package User.AccountService.beans;

public class CreditCard {
    private int id;
    private String cvv;
    private EndUser cardHolder;

    private String cardNumber;
    private String expirementDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public CreditCard() {
    }

    public EndUser getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(EndUser cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getExpirementDate() {
        return expirementDate;
    }

    public void setExpirementDate(String expirementDate) {
        this.expirementDate = expirementDate;
    }

    public CreditCard(int id, String cvv, EndUser cardHolder, String expirementDate) {
        this.id = id;
        this.cvv = cvv;
        this.cardHolder = cardHolder;
        this.expirementDate = expirementDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

}

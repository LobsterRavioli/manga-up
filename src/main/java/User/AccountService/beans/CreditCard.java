package User.AccountService.beans;

public class CreditCard {
    private int id;
    private String cvv;
    private EndUser endUser;

    private String cardNumber;

    private String name;
    private String surname;


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
        return endUser;
    }

    public void setEndUser(EndUser endUser) {
        this.endUser = this.endUser;
    }

    public String getExpirementDate() {
        return this.expirementDate;
    }
    public void setExpirementDate(String expirementDate) {
        this.expirementDate = expirementDate;
    }
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }


    public CreditCard(int id, String cvv, EndUser endUser, String cardNumber, String name, String surname, String expirementDate) {
        this.id = id;
        this.cvv = cvv;
        this.endUser = endUser;
        this.cardNumber = cardNumber;
        this.name = name;
        this.surname = surname;
        this.expirementDate = expirementDate;
    }
}

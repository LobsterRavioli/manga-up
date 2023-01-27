package User.AccountService;

import java.util.Date;

public class CreditCard {
    private int id;
    private String cvv; // cvc
    private EndUser endUser;
    private String cardNumber;
    private String cardHolder;
    private Date expirementDate; // expirationDate

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

    public void setEndUser(EndUser newEndUser) {
        if(endUser != newEndUser){
            EndUser oldEndUser = endUser;
            endUser = newEndUser;
            if(newEndUser != null)
                newEndUser.addCards(this);
            if(oldEndUser != null)
                oldEndUser.removeCards(this);
        }
    }

    public Date getExpirementDate() {
        return this.expirementDate;
    }
    public void setExpirementDate(Date expirementDate) {
        this.expirementDate = expirementDate;
    }
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CreditCard(int id, String cvv, EndUser endUser, String cardNumber, String cardHolder, Date expirementDate) {
        this.id = id;
        this.cvv = cvv;
        this.endUser = endUser;
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.expirementDate = expirementDate;
    }

    public EndUser getEndUser() {
        return endUser;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", cvv='" + cvv + '\'' +
                ", endUser=" + endUser.getId() +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardHolder='" + cardHolder + '\'' +
                ", expirementDate=" + expirementDate +
                '}';
    }
}
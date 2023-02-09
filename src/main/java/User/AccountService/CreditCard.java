package User.AccountService;

import java.util.Date;

public class CreditCard {
    private int id;
    private String cvv;
    private EndUser endUser;
    private String cardNumber;
    private String cardHolder;
    private Date expirementDate;


    public CreditCard() {
    }

    public CreditCard(int id) {
        this.id = id;
    }

    public void setEndUser(EndUser newEndUser) {
        if(endUser != newEndUser){
            EndUser oldEndUser = endUser;
            endUser = newEndUser;
            if(newEndUser != null)
                newEndUser.addCard(this);
            if(oldEndUser != null)
                oldEndUser.removeCards(this);
        }
    }


    public CreditCard(int id, String cvv, EndUser endUser, String cardNumber, String cardHolder, Date expirementDate) {
        this.id = id;
        this.cvv = cvv;
        this.endUser = endUser;
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.expirementDate = expirementDate;
    }

    public CreditCard(String cardNumber, String cvv, String cardHolder, Date expirationDate)
    {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.cardHolder = cardHolder;
        this.expirementDate = expirationDate;
    }

    @Override
    public String toString() {
        return "cardNumber="+cardNumber
                +",cardHolder="+cardHolder
                +",expirationDate="+expirementDate;
    }

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

    public EndUser getEndUser() {
        return endUser;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public Date getExpirementDate() {
        return expirementDate;
    }

    public void setExpirementDate(Date expirementDate) {
        this.expirementDate = expirementDate;
    }

    public static boolean validate(CreditCard creditCard){
        if (creditCard.getCardNumber() == null || !creditCard.getCardNumber().matches("^[0-9]{13,16}$")) return false;
        if (creditCard.getCardHolder() == null || !creditCard.getCardHolder().matches("^[a-zA-Z\\-\\s]{1,40}$")) return false;
        if (creditCard.getCvv() == null || !creditCard.getCvv().matches("^[0-9]{3,5}$")) return false;
        if (creditCard.getExpirementDate() == null) return false;
        if (creditCard.getEndUser() == null) return false;

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;

        if (id != that.id) return false;
        if (!cvv.equals(that.cvv)) return false;
        if (!endUser.equals(that.endUser)) return false;
        if (!cardNumber.equals(that.cardNumber)) return false;
        if (!cardHolder.equals(that.cardHolder)) return false;
        return expirementDate.equals(that.expirementDate);
    }

}

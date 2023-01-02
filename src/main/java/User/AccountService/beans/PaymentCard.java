package User.AccountService.beans;

public class PaymentCard {
    private int id;
    private int userId;
    private String cvv;
    private String cardHolder;
    private String expirementDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getExpirementDate() {
        return expirementDate;
    }

    public void setExpirementDate(String expirementDate) {
        this.expirementDate = expirementDate;
    }

    public PaymentCard(int id, int userId, String cvv, String cardHolder, String expirementDate) {
        this.id = id;
        this.userId = userId;
        this.cvv = cvv;
        this.cardHolder = cardHolder;
        this.expirementDate = expirementDate;
    }
}

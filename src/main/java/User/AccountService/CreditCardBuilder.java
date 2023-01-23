package User.AccountService;

import java.util.Date;

public interface CreditCardBuilder {
    CreditCardBuilder setId(int id);

    CreditCardBuilder setCvv(String cvv);

    CreditCardBuilder setEndUser(EndUser endUser);

    CreditCardBuilder setCardNumber(String cardNumber);

    CreditCardBuilder setCardHolder(String cardHolder);

    CreditCardBuilder setExpirementDate(Date expirementDate);

    CreditCard createCreditCard();

    CreditCardBuilder setExpirationDate(Date expirationDate);
}

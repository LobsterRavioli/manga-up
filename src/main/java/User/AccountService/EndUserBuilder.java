package User.AccountService.beans;

import java.util.Date;
import java.util.Set;

public interface EndUserBuilder {
    EndUserBuilder setId(int id);

    EndUserBuilder setName(String name);

    EndUserBuilder setPassword(String password);

    EndUserBuilder setSurname(String surname);

    EndUserBuilder setEmail(String email);

    EndUserBuilder setPhoneNumber(String phoneNumber);

    EndUserBuilder setBirthdate(Date birthdate);

    EndUserBuilder setAddresses(Set addresses);

    EndUserBuilder setCards(Set cards);

    EndUser createEndUser();
}

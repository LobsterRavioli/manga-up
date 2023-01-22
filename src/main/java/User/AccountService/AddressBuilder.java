package User.AccountService.beans;

public interface AddressBuilder {
    AddressBuilder setId(int id);

    AddressBuilder setEndUser(EndUser endUser);

    AddressBuilder setCountry(String country);

    AddressBuilder setCity(String city);

    AddressBuilder setStreet(String street);

    AddressBuilder setPostalCode(String postalCode);

    AddressBuilder setPhoneNumber(String phoneNumber);

    AddressBuilder setRegion(String region);

    Address createAddress();
}

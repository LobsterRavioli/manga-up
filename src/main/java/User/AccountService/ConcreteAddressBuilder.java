package User.AccountService;

public class ConcreteAddressBuilder implements AddressBuilder {
    private int id;
    private EndUser endUser;
    private String country;
    private String city;
    private String street;
    private String postalCode;
    private String phoneNumber;
    private String region;

    @Override
    public ConcreteAddressBuilder setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public ConcreteAddressBuilder setEndUser(EndUser endUser) {
        this.endUser = endUser;
        return this;
    }

    @Override
    public ConcreteAddressBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    @Override
    public ConcreteAddressBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    @Override
    public ConcreteAddressBuilder setStreet(String street) {
        this.street = street;
        return this;
    }

    @Override
    public ConcreteAddressBuilder setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    @Override
    public ConcreteAddressBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public ConcreteAddressBuilder setRegion(String region) {
        this.region = region;
        return this;
    }

    @Override
    public Address createAddress() {
        return new Address(id, endUser, country, city, street, postalCode, phoneNumber, region);
    }
}
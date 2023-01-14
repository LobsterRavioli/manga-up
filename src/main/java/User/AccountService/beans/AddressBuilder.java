package User.AccountService.beans;

public class AddressBuilder {
    private int id;
    private EndUser endUser;
    private String country;
    private String city;
    private String street;
    private String postalCode;
    private String phoneNumber;
    private String region;

    public AddressBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public AddressBuilder setEndUser(EndUser endUser) {
        this.endUser = endUser;
        return this;
    }

    public AddressBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    public AddressBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public AddressBuilder setStreet(String street) {
        this.street = street;
        return this;
    }

    public AddressBuilder setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public AddressBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public AddressBuilder setRegion(String region) {
        this.region = region;
        return this;
    }

    public Address createAddress() {
        return new Address(id, endUser, country, city, street, postalCode, phoneNumber, region);
    }
}
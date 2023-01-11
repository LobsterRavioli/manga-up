package User.AccountService.beans;

public class Address {
    private int id;
    private EndUser endUser;
    private String country;
    private String city;
    private String street;
    private String streetNumber;
    private String postalCode;


    public Address(){}
    public Address(int id, EndUser user, String country, String city, String street, String streetNumber, String postalCode) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        setEndUser(user);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public EndUser getEndUser() {
        return endUser;
    }

    public void setEndUser(EndUser newEndUser) {
        if(endUser != newEndUser){
            EndUser oldEndUser = endUser;
            endUser = newEndUser;
            if(newEndUser != null)
                newEndUser.addAddress(this);
            if(oldEndUser != null)
                oldEndUser.removeAddress(this);
        }
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", endUser=" + endUser.getId() +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}

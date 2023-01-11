package User.AccountService.beans;

public class Address {
    private int id;
    private EndUser endUser;
    private String country;
    private String city;
    private String street;
    private String postalCode;
    private String phoneNumber;
    private String region;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Address(){}

    public Address(int id, EndUser endUser, String country, String city, String street, String postalCode, String phoneNumber, String region) {
        this.id = id;
        this.endUser = endUser;
        this.country = country;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.region = region;
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
                ", postalCode='" + postalCode + '\'' +
                '}';
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}

package User.AccountService;

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

    public Address(String country, String region, String city, String street, String postalCode)
    {
        this.country = country;
        this.region = region;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }

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
    public String toString()
    {
        return "country="+country
                +",region="+region
                +",city="+city
                +",street="+street
                +",postalCode="+postalCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (id != address.id) return false;
        if (!(this.endUser.getId() == address.getEndUser().getId())) return false;
        if (!country.equals(address.country)) return false;
        if (!city.equals(address.city)) return false;
        if (!street.equals(address.street)) return false;
        if (!postalCode.equals(address.postalCode)) return false;
        if (!phoneNumber.equals(address.phoneNumber)) return false;
        return region.equals(address.region);
    }

    public Address(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public static boolean validate(Address address) {
        if (address.getCountry() == null || address.getCountry().isEmpty()) {
            return false;
        }
        if (address.getCity() == null || address.getCity().isEmpty()) {
            return false;
        }
        if (address.getStreet() == null || address.getStreet().isEmpty()) {
            return false;
        }
        if (address.getPostalCode() == null || address.getPostalCode().isEmpty()) {
            return false;
        }
        if (address.getPhoneNumber() == null || address.getPhoneNumber().isEmpty()) {
            return false;
        }
        if (address.getRegion() == null || address.getRegion().isEmpty()) {
            return false;
        }
        if (address.getCity().matches("^[a-zA-Z]{1,163}$") == false) {return false;}

        if(address.getStreet().matches("^[a-zA-Z0-9\\-\\s]{1,40}$") == false) {return false;}

        if (address.getPostalCode().matches("^[0-9]{5}$") == false) {return false;}

        if (address.getPhoneNumber().matches("^[+][0-9]{12,15}$") == false) {return false;}

        if (address.getRegion().matches("^[a-zA-Z]{1,30}$") == false) {return false;}

        if (address.getCountry().matches("^[a-zA-Z]{1,56}$") == false) {return false;}

        return true;
    }

}

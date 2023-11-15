public class Address {
    public String id;
    public String street1;
    public String street2;
    public String city;
    public String state;
    public String zip;
    public String country;

    public Address(String id, String street1, String street2, String city, String state, String zip, String country) {
        this.id = id;
        this.street1 = street1;
        this.street2 = street2;

        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    public String getId() {
        return id;
    }
    public String getStreet1() {
        return street1;
    }
    public String getStreet2() {
        return street2;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public String getZip() {
        return zip;
    }
    public String getCountry() {
        return country;
    }

}

//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: Address.java
 */
//-------------------------------------------------------
/* Name: Address
 * Description: The class for storing address information
 * Parameters:  id - the id of the address
 *              street1 - the first line of the street address
 *              street2 - the second line of the street address
 *              city - the city of the address
 *              state - the state of the address
 *              zip - the zip code of the address
 *              country - the country of the address
 * Relations: None
 */
public class Address {
    //GLOBAL VARIABLES
    private String id; //the id of the address to be stored in the database. Will be the username
    private String street1; //the first line of the street address
    private String street2; //the second line of the street address
    private String city; //the city of the address
    private String state; //the state of the address
    private String zip; //the zip code of the address
    private String country; //the country of the address

    /* Name: Address
     * Description: The constructor for the Address class
     */
    public Address(String id, String street1, String street2, String city, String state, String zip, String country) {
        this.id = id;
        this.street1 = street1;
        this.street2 = street2;

        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    /* Name: getId
     * Description: Gets the id of the address
     * Parameters: none
     * Returns: the id of the address
     */
    public String getId() {
        return id;
    }

    /* Name: getStreet1
     * Description: Gets the first line of the street address
     * Parameters: none
     * Returns: the first line of the street address
     */
    public String getStreet1() {
        return street1;
    }

    /* Name: getStreet2
     * Description: Gets the second line of the street address
     * Parameters: none
     * Returns: the second line of the street address
     */
    public String getStreet2() {
        return street2;
    }

    /* Name: getCity
     * Description: Gets the city of the address
     * Parameters: none
     * Returns: the city of the address
     */
    public String getCity() {
        return city;
    }

    /* Name: getState
     * Description: Gets the state of the address
     * Parameters: none
     * Returns: the state of the address
     */
    public String getState() {
        return state;
    }

    /* Name: getZip
     * Description: Gets the zip code of the address
     * Parameters: none
     * Returns: the zip code of the address
     */
    public String getZip() {
        return zip;
    }

    /* Name: getCountry
     * Description: Gets the country of the address
     * Parameters: none
     * Returns: the country of the address
     */
    public String getCountry() {
        return country;
    }

}

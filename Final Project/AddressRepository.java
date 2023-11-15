//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: AddressRepository.java
 */
//-------------------------------------------------------


/* Name: AddressRepository
 * Description: The repository for addresses
 * Parameters: none
 * Relationships: has a Database
 */
public class AddressRepository {
    //GLOBAL VARIABLES
    private String filename="address.txt"; //the name of the file to store the addresses in
    private Database AddressDatabase = new Database(filename); //the database modifier to store the addresses in

    /* Name: saveAddress
     * Description: Saves an address to the database
     * Parameters: address - the address to save
     * Returns: none
     */
    public void saveAddress(Address address){
        String row=address.getId()+","+address.getStreet1()+","+address.getStreet2()+","+address.getCity()+","+address.getState()+","+address.getZip()+","+address.getCountry();
        AddressDatabase.add(row);
    }
    
}

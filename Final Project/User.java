//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: User.java
 */
//-------------------------------------------------------



//IMPORTS
import java.math.BigInteger; //for converting hashes to hex
import java.util.Base64; //for encoding/decoding hashes

/* Name: User
 * Description: Represents a user
 * Parameters: none
 * Relationships: none
 * Design: none
 */
public class User {
    //GLOBAL VARIABLES
    private String username; //The user's username
    private String hash; //The user's password hash
    private String salt; //The user's password salt
    private String firstname; //The user's first name
    private String lastname; //The user's last name
    private int counter=0; //The user's cart counter
    private String skey; //The user's cart key


    /* Name: User
     * Description: The default constructor for the class
     * Parameters: username - the user's username
     *             hash - the user's password hash
     *             salt - the user's password salt
     *             firstname - the user's first name
     *             lastname - the user's last name
     */
    public User(String username, byte[] hash, byte[] salt, String firstname, String lastname) {
        this.username = username;
        setHash(hash);
        setSalt(salt);
        this.firstname = firstname;
        this.lastname = lastname;
        newCart();
    }
    /* Name: User
     * Description: The default constructor for the class
     * Parameters: username - the user's username
     *             hash - the user's password hash
     *             salt - the user's password salt
     *             firstname - the user's first name
     *             lastname - the user's last name
     *             skey - the user's cart key
     */
    public User(String username, String hash, String salt, String firstname, String lastname, String skey) {
        this.username = username;
        this.hash = hash;
        this.salt = salt;
        this.firstname = firstname;
        this.lastname = lastname;
        this.skey=skey;
    }
    
    // getters and setters
    /* Name: getUsername
     * Description: Gets the user's username
     * Parameters: none
     * Returns: the user's username
     */
    public String getUsername() {
        return this.username;
    }

    /* Name: getFirstname
     * Description: Gets the user's first name
     * Parameters: none
     * Returns: the user's first name
     */
    public String getFirstname(){
        return this.firstname;
    }

    /* Name: getLastname
     * Description: Gets the user's last name
     * Parameters: none
     * Returns: the user's last name
     */
    public String getLastname(){
        return this.lastname;
    }

    /* Name: getHash
     * Description: Gets the user's password hash
     * Parameters: none
     * Returns: the user's password hash
     */
    public String getHash(){
        return this.hash;
    }

    /* Name: getSalt
     * Description: Gets the user's password salt
     * Parameters: none
     * Returns: the user's password salt
     */
    public byte[] getSalt(){
        return saltDecode(this.salt);
    }

    /* Name: getXSalt
     * Description: Gets the user's password salt
     * Parameters: none
     * Returns: the user's password salt
     */
    public String getXSalt(){
        return this.salt;
    }

    /* Name: setHash
     * Description: Sets the user's password hash
     * Parameters: hash - the user's password hash
     * Returns: none
     */
    public void setHash(byte[] hash){
        this.hash=hashEncode(hash);
    }

    /* Name: setSalt
     * Description: Sets the user's password salt
     * Parameters: salt - the user's password salt
     * Returns: none
     */
    public void setSalt(byte[] salt){
        this.salt=saltEncode(salt);
    }

    /* Name: saltEncode
     * Description: Encodes a salt
     * Parameters: salt - the salt to encode
     * Returns: the encoded salt
     */
    private String saltEncode(byte[] salt){
       return Base64.getEncoder().encodeToString(salt);
    }

    /* Name: saltDecode
     * Description: Decodes a salt
     * Parameters: salt - the salt to decode
     * Returns: the decoded salt
     */
    private byte[] saltDecode(String string){
        return Base64.getDecoder().decode(string);
    }

    /* Name: hashEncode
     * Description: Encodes a hash
     * Parameters: hash - the hash to encode
     * Returns: the encoded hash
     */
    private String hashEncode(byte[] hash){
        // Convert the hash to hexadecimal
        BigInteger bi = new BigInteger(1, hash);
        String hex = String.format("%0" + (hash.length << 1) + "x", bi);
        return hex;
    }

    /* Name: newCart
     * Description: Creates a new cart for the user by setting the cart key
     * Parameters: none
     * Returns: none
     */
    public void newCart() {
        this.skey = this.username+(this.counter+1);
    }

    /* Name: getCart
     * Description: Gets the user's cart key
     * Parameters: none
     * Returns: the user's cart key
     */
    public String getCart() {
        return this.skey;
    }
}

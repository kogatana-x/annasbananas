//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: UserAuthenticator.java
 */
//-------------------------------------------------------


//IMPORTS
import java.math.BigInteger; //for converting hashes to hex
import java.security.MessageDigest; //for hashing
import java.security.NoSuchAlgorithmException; //for hashing
import java.security.SecureRandom; //for generating salts

/* Name: UserAuthenticator
 * Description: Authenticates users
 * Parameters: userRepository - the repository to use to retrieve users
 * Relationships: Uses the UserRepository class
 */
class UserAuthenticator {
    //GLOBAL VARIABLES
    private UserRepository userRepository; //the repository to use to retrieve users
    private Logger logger=Logger.getInstance(); //the logger to use

    /* Name: UserAuthenticator
     * Description: The constructor for the UserAuthenticator class
     * Parameters: userRepository - the repository to use to retrieve users
     */
    public UserAuthenticator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* Name: hashPassword
     * Description: Hashes a password with a salt. Note, made public for JUnit tests
     * Parameters: password - the password to hash
     *             salt - the salt to use
     * Returns: the hashed password
     */
    public byte[] hashPassword(String password, byte[] salt) { 
        MessageDigest md = null; //the message digest to use

        //Hash the password
        try {
            md = MessageDigest.getInstance("SHA-512"); //Use SHA-512
        } catch (NoSuchAlgorithmException e) { //If there is an error, log it
            logger.logError("User Authenticator","hashPassword() - no such algorithim "+e.getMessage());
        }

        md.update(salt); //Add the salt to the message digest
        byte[] hash = md.digest(password.getBytes()); //Hash the password with the salt

        return hash;
    }

    /* Name: generateSalt
     * Description: Generates a random salt. Note, made public for the JUnit tests
     * Parameters: none
     * Returns: the salt
     */
    public byte[] generateSalt() {
        SecureRandom random = new SecureRandom(); //the random number generator to use
        byte[] salt = new byte[16]; //the salt to return
        random.nextBytes(salt); //Generate the salt

        return salt;    
    }

    /* Name: register
     * Description: registers a new user
     * Parameters:  username - the username of the user to register
     *              password - the password of the user to register
     *              firstname - the first name of the user to register
     *              lastname - the last name of the user to register
     * Returns: true if the user was registered successfully, false otherwise
     */
    public boolean register(String username, String password, String firstname, String lastname){
        byte[] salt = generateSalt(); //Generate a salt
        User user = new User(username,hashPassword(password,salt),salt,firstname,lastname); //Create a new user
        return userRepository.saveUser(user); //Save the user
    }

    /* Name: login
     * Description: authenticates a user in by comparing the hashed password with the one stored in the database
     * Parameters: username - the username of the user to log in
     *             password - the password of the user to log in
     * Returns: true if the user was logged in successfully, false otherwise
     */
    public boolean login(String username, String password)  {
        User user = userRepository.getUser(username); //Get the user from the database
        if (user == null) { //If the user does not exist, return false
            return false;
        }

        //Verify password
        String saltedHash = hashEncode(hashPassword(password, user.getSalt())); //Hash the password with the salt and encode it
        boolean result = saltedHash.equals(user.getHash()); //Compare the hashed password with the one stored in the database

        return result;
    }

    /* Name: hashEncode
     * Description: Converts a hash to hexadecimal
     * Parameters: hash - the hash to convert
     * Returns: the hash in hexadecimal
     */
    private String hashEncode(byte[] hash){
        // Convert the hash to hexadecimal
        BigInteger bi = new BigInteger(1, hash);
        String hex = String.format("%0" + (hash.length << 1) + "x", bi);
        return hex;
    }
}




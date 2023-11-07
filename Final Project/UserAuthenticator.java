/* Name: UserAuthenticator
 * Description: Authenticates users
 * Parameters: userRepository - the repository to use to retrieve users
 * Relationships: Uses the UserRepository class
 */

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

class UserAuthenticator {
    public UserRepository userRepository;

    public UserAuthenticator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /* Name: hashPassword
     * Description: Hashes a password with a salt
     * Parameters: password - the password to hash
     *             salt - the salt to use
     * Returns: the hashed password
     */
    public byte[] hashPassword(String password, byte[] salt) { 
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(salt);
        return md.digest(password.getBytes());
    }

    /* Name: generateSalt
     * Description: Generates a random salt
     * Parameters: none
     * Returns: the salt
     */
    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;    
    }

    public boolean register(String username, String password, String firstname, String lastname){
        byte[] salt = generateSalt();
        User user = new User(username,hashPassword(password,salt).toString(),salt.toString(),firstname,lastname);
        return userRepository.saveUser(user);
    }

    /* Name: login
     * Description: authenticates a user in by comparing the hashed password with the one stored in the database
     * Parameters: username - the username of the user to log in
     * Returns: true if the user was logged in successfully, false otherwise
     */
    public boolean login(String username, String password)  {
        User user = userRepository.getUser(username);
        if (user == null) {
            return false;
        }
        byte[] saltedHash = hashPassword(password, user.getSalt());
        
        boolean result = saltedHash.equals(user.getHash());

        return result;
    }

   
}




/* Name: UserAuthenticator
 * Description: Authenticates users
 * Parameters: userRepository - the repository to use to retrieve users
 * Relationships: Uses the UserRepository class
 */

import java.math.BigInteger;
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
        byte[] hash = md.digest(password.getBytes());

        return hash;
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
        User user = new User(username,hashPassword(password,salt),salt,firstname,lastname);
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
        String saltedHash = hashEncode(hashPassword(password, user.getSalt()));
        boolean result = saltedHash.equals(user.getHash());

        return result;
    }

    private String hashEncode(byte[] hash){
        // Convert the hash to hexadecimal
        BigInteger bi = new BigInteger(1, hash);
        String hex = String.format("%0" + (hash.length << 1) + "x", bi);
        return hex;
    }
}




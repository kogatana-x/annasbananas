import java.math.BigInteger;
import java.util.Base64;

public class User {
    private String username;
    private String hash;
    private String salt;
    private String firstname;
    private String lastname;
    int counter=0;
    private String skey;


    public User(String username, byte[] hash, byte[] salt, String firstname, String lastname) {
        this.username = username;
        setHash(hash);
        setSalt(salt);
        this.firstname = firstname;
        this.lastname = lastname;
        newCart();
    }
    public User(String username, String hash, String salt, String firstname, String lastname, String skey) {
        this.username = username;
        this.hash = hash;
        this.salt = salt;
        this.firstname = firstname;
        this.lastname = lastname;
        this.skey=skey;
    }
    
    // getters and setters
    public String getUsername() {
        return this.username;
    }
    public String getFirstname(){
        return this.firstname;
    }
    public String getLastname(){
        return this.lastname;
    }
    //public byte[] getHash(){
    //    return hashDecode(this.hash);
    //}
    public String getHash(){
        return this.hash;
    }
    public byte[] getSalt(){
        return saltDecode(this.salt);
    }
    public String getXSalt(){
        return this.salt;
    }
    public void setHash(byte[] hash){
        this.hash=hashEncode(hash);
    }
    public void setSalt(byte[] salt){
        this.salt=saltEncode(salt);
    }
   private String saltEncode(byte[] salt){
       return Base64.getEncoder().encodeToString(salt);
    }
    private byte[] saltDecode(String string){
        return Base64.getDecoder().decode(string);
    }

    private String hashEncode(byte[] hash){
        // Convert the hash to hexadecimal
        BigInteger bi = new BigInteger(1, hash);
        String hex = String.format("%0" + (hash.length << 1) + "x", bi);
        return hex;
    }
    private byte[] hashDecode(String string){
        return new BigInteger(string, 16).toByteArray();
    }

    public void newCart() {
        this.skey = this.username+(this.counter+1);
    }
    public String getCart() {
        return this.skey;
    }
}

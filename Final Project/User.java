public class User {
    private String username;
    private String hash;
    private String salt;
    private String firstname;
    private String lastname;
    int counter=0;
    private String skey;


    public User(String username, String hash, String salt, String firstname, String lastname) {
        this.username = username;
        this.hash = hash;
        this.salt = salt;
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
    public byte[] getHash() {
        return this.hash.getBytes();
    }
    public String getUsername() {
        return this.username;
    }
    public String getFirstname(){
        return this.firstname;
    }
    public String getLastname(){
        return this.lastname;
    }
    public byte[] getSalt() {
        return this.salt.getBytes();
    }
    public void setSalt(byte[] salt){
        this.salt=salt.toString();
    }

    public void newCart() {
        this.skey = this.username+(this.counter+1);
    }
    public String getCart() {
        return this.skey;
    }
}

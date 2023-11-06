public class User {
    private int uid;
    private String username;
    private String hash;
    private String salt;

    private Cart cart;


    public User(int uid,String username, String hash, String salt) {
        this.uid=uid;
        this.username = username;
        this.hash = hash;
        this.salt = salt;
    }
    // getters and setters
    public byte[] getHash() {
        return this.hash.getBytes();
    }
    public String getUsername() {
        return this.username;
    }

    public byte[] getSalt() {
        return this.salt.getBytes();
    }
    public int getUid() {
        return this.uid;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public Cart getCart() {
        return this.cart;
    }
}

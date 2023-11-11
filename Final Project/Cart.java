
public class Cart {
    private static Cart instance;
    private String cartID;
    private int numberOfProducts=8;
    private CartRepository CartRepository = new CartRepository();

    public static Cart getInstance(){
        if(instance==null){
            instance=new Cart();
        }
        return instance;
    }

    public void setup(String username){
        //find cart for user name. status must be false
        String[] cart=CartRepository.getCart(username);
        if(cart[0].equals("error")){
            this.cartID=CartRepository.add(username,numberOfProducts);
        }
        else{
            this.cartID=cart[0];
        }
    }

    public void addToCart(String product,String quantity) {
        CartRepository.update(cartID,product,quantity);
    }
    public void removeFromCart(String product) {
        CartRepository.update(cartID,product,"0");
    }

    public void updateCart(String product, String quantity) {
        CartRepository.update(this.cartID, product, quantity);
    }
    public void checkout() {
        CartRepository.checkout(this.cartID);
    }
    public String[] getCart() {
        String[] cart=CartRepository.getCart(this.cartID);
        return cart;
    }
 
}

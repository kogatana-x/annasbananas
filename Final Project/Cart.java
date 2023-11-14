
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
        if(cart==null||cart[0].equals("error")){
            this.cartID=CartRepository.add(username,Integer.toString(numberOfProducts));
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
    public void clearCart(){
        CartRepository.clearCart(this.cartID);
    }   

    public void update(String product, String quantity) {
        CartRepository.update(this.cartID, product, quantity);
    }
    public void checkout() {
        CartRepository.checkout(this.cartID);
    }
    private String[] getCart() {
        String[] cart=CartRepository.getCart(this.cartID);
        return cart;
    }

    public Product[] displayCart(){
        //takes cart.id and displays all products in cart
        ProductCatalog pc = new ProductCatalog(new ProductRepository());
        String[] cartProducts = getCart();
        if(cartProducts==null||cartProducts[0].equals("error")){
            return null;
        }
        Product[] products = new Product[cartProducts.length];
        System.out.println("Cart ID: " + cartProducts[0]);
        for(int x=0; x<cartProducts.length; x++){
            if(cartProducts[x].equals("0")){
                continue;
            }
            products[x] = pc.getProduct(Integer.toString(x));
        }
        return products;
    }
}

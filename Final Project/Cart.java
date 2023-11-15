
public class Cart {
    private static Cart instance;
    private String cartID;
    private CartRepository CartRepository = new CartRepository();
    public boolean isSetup;

    public static Cart getInstance(){
        if(instance==null){
            instance=new Cart();
        }
        return instance;
    }

    public void setup(String username){
        //find cart for user name. status must be false
        if(username.equals("")){return;}
        String save=CartRepository.setup(username);
        if(!username.equals("error")){
            this.cartID=save;
            //System.out.println("(Cart)Setting up cart ID-"+this.cartID+" for: "+username);
        }
        //}else{System.out.println("(Cart)Error setting up cart for: "+username);}
    }

    public void addToCart(String productId,String quantity) {
        CartRepository.updateItem(cartID,productId,quantity);
    }
    public void removeFromCart(String productId) {
        CartRepository.updateItem(cartID,productId,"0");
    }
    public void clearCart(){
        CartRepository.clearCart(this.cartID);
    }   

    public void updateItem(String fieldId, String newValue) {
        CartRepository.updateItem(this.cartID, fieldId, newValue);
    }
    public boolean checkout() {
        boolean result=CartRepository.checkout(this.cartID);
        return result;
    }
    private String[] getCart() {
        String cartString=CartRepository.getCart(this.cartID);
        String[] cart=cartString.split(",");
        return cart;
    }

    public Product[] displayCart(){
        //takes cart.id and displays all products in cart
        ProductCatalog pc = new ProductCatalog(new ProductRepository());
        String[] cartProducts = getCart();
        if(cartProducts==null||cartProducts[0].equals("error")){
            return null;
        }
        //System.out.println("(Cart-DisplayCart)Cart ID: " + cartProducts[0]+ " || Cart User: " + cartProducts[1]+ " || Cart Status: " + cartProducts[2]);
        Product[] products = new Product[cartProducts.length];
        //System.out.println("Cart ID: " + cartProducts[0]);
        //System.out.println("Cart User: " + cartProducts[1]);
        //System.out.println("Cart Status: " + cartProducts[2]);
        for(int x=3; x<cartProducts.length; x++){
            //System.out.println("(Cart-DisplayCart)Cart Product (Product#"+(x-3)+"): " + cartProducts[x]);
            if(!cartProducts[x].equals("0")){
                //System.out.println("(Cart-DisplayCart) - NON ZERO PRODUCT");
                Product temp = pc.getProduct(Integer.toString(x-3));
                temp.setQuantity(Integer.parseInt(cartProducts[x]));
                products[x]=temp;
            }
        }
        return products;
    }
}

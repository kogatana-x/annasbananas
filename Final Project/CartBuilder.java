public class CartBuilder {
    private Cart cart;

    public CartBuilder(String username) {
        if(username.equals("")){return;}
        cart = Cart.getInstance();
        cart.setup(username);
   }

    public CartBuilder addItem(String productId, String quantity) {
        cart.updateItem(productId, quantity);
        return this;
    }

    public CartBuilder removeFromCart(String productId) {
        cart.removeFromCart(productId);
        return this;
    }

    public CartBuilder clearCart(){
        cart.clearCart();
        return this;
    }
    public boolean checkout(){
        boolean result=cart.checkout();
        return result;
    }

    public Cart build() {
        return cart;
    }
    public Product[] displayCart(){
        return cart.displayCart();
    }
}
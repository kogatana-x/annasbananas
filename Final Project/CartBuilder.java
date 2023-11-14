public class CartBuilder {
    private Cart cart;

    public CartBuilder(String username) {
        cart = Cart.getInstance();
        cart.setup(username);
    }

    public CartBuilder addItem(String productId, String quantity) {
        cart.update(productId, quantity);
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
    public CartBuilder checkout(){
        cart.checkout();
        return this;
    }

    public Cart build() {
        return cart;
    }
    public Product[] displayCart(){
        return cart.displayCart();
    }
}
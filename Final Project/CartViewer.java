public class CartViewer {
    Cart cart = Cart.getInstance();

    public CartViewer(String username) {
        cart.setup(username);
    }

    public void displayCart(){
        //takes cart.id and displays all products in cart
    }
}

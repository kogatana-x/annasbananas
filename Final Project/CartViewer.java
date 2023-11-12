public class CartViewer {
    Cart cart = Cart.getInstance();

    public CartViewer(String username) {
        cart.setup(username);
    }

    public void displayCart(){
        //takes cart.id and displays all products in cart
        //cart.id;
        //cart.products;
        ProductCatalog pc = new ProductCatalog(new ProductRepository());
        String[] cartProducts = cart.getCart();

        System.out.println("Cart ID: " + cartProducts[0]);
        for(int x=0; x<cartProducts.length; x++){
            if(cartProducts[x].equals("0")){
                continue;
            }
            Product product = pc.getProduct(Integer.toString(x));
        }

    }
}

public class CartViewer {
    Cart cart = Cart.getInstance();

    public CartViewer(String username) {
        cart.setup(username);
    }

    public Product[] displayCart(){
        //takes cart.id and displays all products in cart
        ProductCatalog pc = new ProductCatalog(new ProductRepository());
        String[] cartProducts = cart.getCart();
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

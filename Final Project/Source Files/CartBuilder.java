//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: CartBuilder.java
 */
//-------------------------------------------------------

/* Name: CartBuilder
 * Description: A clean interface for updating the Cart for a user.
 * Parameters: none
 * Relationships: has a Cart
 * Design Pattern: Builder
 */
public class CartBuilder {
    //GLOBAL VARIABLES
    private Cart cart; //the cart to build

    /* Name: CartBuilder
     * Description: The constructor for the CartBuilder class
     * Parameters: username - the username of the user to build the cart for
     */
    public CartBuilder(String username) {
        if(username.equals("")){return;} //If the username isnt set, return
        cart = Cart.getInstance(); //Get the instance of the cart
        cart.setup(username); //Setup the cart
   }

   /* Name: addItem
    * Description: Adds an item to the cart
    * Parameters: productId - the id of the product to add
    *             quantity - the quantity of the product to add
    * Returns: the CartBuilder instance
    */
    public CartBuilder addItem(String productId, String quantity) {
        cart.updateItem(productId, quantity); //Update the item in the cart
        return this;
    }

    /* Name: removeItem
     * Description: Removes an item from the cart
     * Parameters: productId - the id of the product to remove
     * Returns: the CartBuilder instance
     */
    public CartBuilder removeFromCart(String productId) {
        cart.removeFromCart(productId); //Remove the item from the cart
        return this;
    }

    /* Name: clearCart
     * Description: Clears the cart
     * Parameters: none
     * Returns: the CartBuilder instance
     */
    public CartBuilder clearCart(){
        cart.clearCart(); //Clear the cart
        return this;
    }

    /* Name: checkout
     * Description: Checks out the cart
     * Parameters: none
     * Returns: whether or not the checkout was successful
     */
    public boolean checkout(){
        boolean result=cart.checkout(); //Checkout the cart
        return result;
    }

    /* Name: build
     * Description: Builds the cart
     * Parameters: none
     * Returns: the cart
     */
    public Cart build() {
        return cart;
    }

    /* Name: displayCart
     * Description: Displays the cart
     * Parameters: none
     * Returns: the cart
     */
    public Product[] displayCart(){
        return cart.displayCart(); 
    }

    /* Name: getId
     * Description: Gets the id of the cart
     * Parameters: none
     * Returns: the id of the cart
     */
    public String getId(){
        return cart.getId();
    }
}
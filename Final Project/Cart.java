//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: Cart.java
 */
//-------------------------------------------------------


/* Name: Cart
 * Description: The cart class
 * Parameters: none
 * Relationships: none
 * Design Pattern: Singleton
 */
public class Cart {
    //GLOBAL VARIABLES
    private static Cart instance; //the instance of the cart
    private String cartID; //the id of the cart. must be unique
    private CartRepository CartRepository = new CartRepository(); //the repository for the cart
    public boolean isSetup; //whether or not the cart is setup

    /* Name: getInstance
     * Description: Gets the instance of the cart
     * Parameters: none
     * Returns: the instance of the cart
     */
    public static Cart getInstance(){
        if(instance==null){
            instance=new Cart(); //create a new instance of the cart if it has not been created
        }
        return instance;
    }

    /* Name: setup
     * Description: Sets up the cart by retrieving the cart from the database, or making a new one if it does not exist
     * Parameters: username - the username of the user to set up the cart for
     * Returns: none
     */
    public void setup(String username){
        if(username.equals("")){return;} //If the username isnt set, return

        String save=CartRepository.setup(username); //Get the cart from the database

        if(!username.equals("error")){ //If the cart has been successfully retrieved/created
            this.cartID=save; //Set the cart id
            this.isSetup=true; //Set the cart to be setup
        }
    }

    /* Name: addToCart
     * Description: Adds an item to the cart
     * Parameters: productId - the id of the product to add
     *             quantity - the quantity of the product to add
     * Returns: none
    */
    public void addToCart(String productId,String quantity) {
        CartRepository.updateItem(cartID,productId,quantity); //Update the item in the database by setting the new quantity 
    }

    /* Name: removeFromCart
     * Description: Removes an item from the cart
     * Parameters: productId - the id of the product to remove
     * Returns: none
     */
    public void removeFromCart(String productId) {
        CartRepository.updateItem(cartID,productId,"0"); //Update the item in the database by setting the quantity to 0
    }

    /* Name: clearCart
     * Description: sets the quantity of all items in the cart to 0
     * Parameters: none
     * Returns: none
     */
    public void clearCart(){
        CartRepository.clearCart(this.cartID); //Update the item in the database by setting the quantity to 0 for all products
    }   

    /* Name: updateItem
     * Description: Updates an item in the cart with a new value
     * Parameters: fieldId - the id of the field to update
     *             newValue - the new value of the field
     * Returns: none
     */
    public void updateItem(String fieldId, String newValue) {
        CartRepository.updateItem(this.cartID, fieldId, newValue); //Update the field in the database with the new value
    }

    /* Name: checkout
     * Description: Checks out the cart by setting the status to 'true'
     * Parameters: none
     * Returns: true if the cart was checked out successfully, false otherwise
     */
    public boolean checkout() {
        boolean result=CartRepository.checkout(this.cartID); //Update the cart in the database by setting the status to 'true'. If the cart was successfully checked out, return true
        return result;
    }

    /* Name: getCart
     * Description: Gets the cart from the database (the row item) and extracts each field
     * Parameters: none
     * Returns: the cart from the database as a String array
     */
    private String[] getCart() {
        String cartString=CartRepository.getCart(this.cartID);
        String[] cart=cartString.split(",");
        return cart;
    }

    /* Name: displayCart
     * Description: Prepares the cart for display by getting the products from the database and setting the quantity of each product
     * Parameters: none
     * Returns: the product array with all the products currently in the cart
     */
    public Product[] displayCart(){
        ProductCatalog pc = ProductCatalog.getInstance(); //Initialize the product catalog
        String[] cartProducts = getCart(); //Get the cart from the database
        if(cartProducts==null||cartProducts[0].equals("error")){ //If the cart is null or there was an error, return null
            return null;
        }
        //System.out.println("(Cart-DisplayCart)Cart ID: " + cartProducts[0]+ " || Cart User: " + cartProducts[1]+ " || Cart Status: " + cartProducts[2]);
        Product[] products = new Product[cartProducts.length]; //Initialize the product array

        for(int x=3; x<cartProducts.length; x++){ //For each product in the cart
            if(!cartProducts[x].equals("0")){ //If the quantity is not 0
                Product temp = pc.getProduct(Integer.toString(x-3)); //Get the product from the database
                temp.setQuantity(Integer.parseInt(cartProducts[x])); //Set the quantity of the product
                products[x]=temp; //Add the product to the array
            }
        }
        return products;
    }

    /* Name: getId
     * Description: Gets the id of the cart
     * Parameters: none
     * Returns: the id of the cart
     */
    public String getId(){
        return this.cartID;
    }
}

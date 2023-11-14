/* Name: OrderProcessor
 * Description: Processes orders
 * Parameters: cart - the cart to process
 *             paymentProcessor - the payment processor to use to process payments
 * Relationships: Uses the Cart and PaymentProcessor classes
 */
public class CartRepository {
    private String filename="carts.txt";
    private int numberOfProducts=8;
    private Database CartDatabase = new Database(filename);
   

    private String nextID(){
        return Integer.toString(CartDatabase.getNumberOfRows()-1);
    }

    /* Name: add
     * Description: Adds a new cart to the database
     * Parameters: username - the username of the user who owns the cart
     *            PQ - the product-quantity pairs for the cart
     * Returns: the index of the cart that was added
     */
    public String add(String username, String mProd){
        String in=nextID();
        int mprod=Integer.parseInt(mProd);

        String row = "";
        row=row+in;
        row=row+",";
        row=row+username.trim();
        row=row+",false";

        for(int x=0; x<=mprod;x++){
            row=row+",0";
        }
        //System.out.println(row);
        CartDatabase.add(row);
        return in;
    }

    /* Name: update
     * Description: Updates the quantity of items for a product (update the cart database)
     * Parameters: cartIndex - the index of the cart to update
     *             product - the product to update
     *             quantity - the new quantity of the product 
     * Returns: true if the update was successful, false otherwise
     */
    public void update(String cartIndex, String product, String quantity){
        CartDatabase.update(cartIndex,product,quantity);
    }

    /* Name: delete
     * Description: Deletes a product from the cart. Sets the quantity to 0
     * Parameters: cartIndex - the index of the cart to update
     *             product - the product to delete
     * Returns: true if the delete was successful, false otherwise
     */
    public void clearCart(String cartIndex){
        for(int x=0;x<=numberOfProducts;x++){
            CartDatabase.update(cartIndex,Integer.toString(x),"0");
        }
    }

    /* Name: checkout
     * Description: Checks out the cart. Sets the status to true
     * Parameters: cartIndex - the index of the cart to update
     */
    public void checkout(String cartIndex){
        CartDatabase.update(cartIndex,"2","true");
    }

    //only returns carts that are not checked out
    public String[] getCart(String username){
        String[] cart=CartDatabase.returnResult(username);
        return cart;
    }
}
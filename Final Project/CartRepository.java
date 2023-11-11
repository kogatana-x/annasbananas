/* Name: OrderProcessor
 * Description: Processes orders
 * Parameters: cart - the cart to process
 *             paymentProcessor - the payment processor to use to process payments
 * Relationships: Uses the Cart and PaymentProcessor classes
 */
public class CartRepository {
    private String filename="carts.txt";
    private Database CartDatabase = new Database(filename);
    ProductCatalog productCatalog = new ProductCatalog(new ProductRepository());

   
    public double getTotalPrice(int cartIndex) {
        double total = 0;
        String[] list = CartDatabase.returnResult(Integer.toString(cartIndex)); //One row with username and many products
        Product[] products = toProduct(list);
        for (Product product : products) {
            total += product.getPrice()*product.getQuantity();
        }
        return total;
    }
   
    private Product[] toProduct(String[] list){
        Product[] products = new Product[list.length];
        for(int x=0;x<list.length;x++){
            String[] temp = list[x].split(",");
            products[x]=new Product(temp[0], temp[1], temp[2], temp[3], Double.parseDouble(temp[4]), temp[5], Integer.parseInt(temp[6]), temp[7]);
        }
        return products;
    }
    private String nextID(){
        return Integer.toString(CartDatabase.getNumberOfRows());
    }

    /* Name: add
     * Description: Adds a new cart to the database
     * Parameters: username - the username of the user who owns the cart
     *            PQ - the product-quantity pairs for the cart
     * Returns: the index of the cart that was added
     */
    public String add(String username, int mProd){
        String index=nextID();
        String row=index+","+username+",false";
        for(int x=0; x<mProd;x++){
            row+=",0";
        }
        CartDatabase.add(row);
        return index;
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
    public boolean delete(int cartIndex,int product){
        boolean result=CartDatabase.update(Integer.toString(cartIndex),Integer.toString(product),"0");
        return result;
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
//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: CartRepository.java
 */
//-------------------------------------------------------

/* Name: CartRepository
 * Description: The repository for carts. "Order Processor"
 * Parameters: none
 * Relationships: has a Database
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
    public String setup(String username){
        if(username.equals("")){return "";}

        //If there is an existing cart and if status is false - return cart ID
        String returnResult=CartDatabase.returnResultRow("1",username);
        //String status=CartDatabase.returnFieldValue(returnResult, 2); 
        //TODO - MAKE NEW CART IF STATUS IS TRUE
        String[] cart=returnResult.split(",");
        //System.out.println("(Cart Repository) Cart ID: "+cart[0]);
        if(!cart[0].equals("error")){return cart[0];}
        //if(status.equals("false")){
        //    return cart[0];
        //}
        
        String in=nextID();

        String row = "";
        row=row+in;
        row=row+",";
        row=row+username.trim();
        row=row+",false";

        for(int x=0; x<=numberOfProducts;x++){
            row=row+",0";
        }
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
    public void updateItem(String cartIndex, String productId, String quantity){
        int fieldId=Integer.parseInt(productId)+3;
        //System.out.println("(Cart Repository)Updating Cart: "+cartIndex+" || FieldId: "+fieldId+" || Product: "+productId+" || Quantity: "+quantity);
        CartDatabase.update(cartIndex,Integer.toString(fieldId),quantity);
    }

    /* Name: clearCart
     * Description: Sets the quantity to 0 for all products in the cart
     * Parameters: cartIndex - the index of the cart to update
     * Returns: None
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
    public boolean checkout(String cartIndex){
        boolean result=isAtLeastOneProduct(cartIndex);
        if(!result){
            return false;
        }
        CartDatabase.update(cartIndex,"2","true");
        return true;
    }
    private boolean isAtLeastOneProduct(String cartIndex){
        boolean result=false;
        String cartString=CartDatabase.returnResultRow("0",cartIndex);
        String[] cart=cartString.split(",");
        for(int x=3;x<cart.length;x++){
            if(!cart[x].equals("0")){
                result=true;
            }
        }
        return result;
    
    }
    //only returns carts that are not checked out
    public String getCart(String cartId){
        String cartString=CartDatabase.returnResultRow("0",cartId);
        //if(cart.length>1){System.out.println("RESULT==="+cart[1]);}
        return cartString;
    }
}
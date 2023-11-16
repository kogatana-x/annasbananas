//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: ProductFactory.java
 */
//-------------------------------------------------------


/* Name: ProductFactory
 * Description: Creates a product from a string array
 * Parameters: none
 * Relationships: has products
 * Design: Factory
 */
public class ProductFactory {

    /* Name: createProducts
     * Description: Creates an array of products from an array of string arrays
     * Parameters: parts - the array of string arrays to create products from
     * Returns: Product[] - the array of products created
     */
    public Product createProduct(String[] parts){
        String type=parts[1].trim();
        if(type.equals("Merchandise")){
            return new Merchandise(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5],parts[6], parts[7]);
        }else if(type.equals("Produce")){
            return new Produce(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5],parts[6], parts[7]);
        } else{
            return null;
        }
    }
}

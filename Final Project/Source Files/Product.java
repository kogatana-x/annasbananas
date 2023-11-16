//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: Product.java
 */
//-------------------------------------------------------


/* Name: Product
 * Description: Represents a product
 * Parameters: none
 * Relationships: none
 */
public interface Product {
    //REQUIRED METHODS FOR A PRODUCT
    public String getId ();                             //Returns the product's ID
    public String getType ();                           //Returns the product's type
    public String getName ();                           //Returns the product's name
    public String getDescription ();                    //Returns the product's description
    public double getPrice ();                          //Returns the product's price        
    public String getUnit ();                           //Returns the product's unit
    public int getQuantity ();                          //Returns the product's quantity
    public String getImage ();                          //Returns the product's image

    public void setId (String id);                      //Sets the product's ID
    public void setType (String type);                  //Sets the product's type
    public void setName (String name);                  //Sets the product's name
    public void setDescription (String description);    //Sets the product's description
    public void setPrice (double price);                //Sets the product's price
    public void setUnit (String unit);                  //Sets the product's unit
    public void setQuantity (int quantity);             //Sets the product's quantity
    public void setImage (String image);                //Sets the product's image
}
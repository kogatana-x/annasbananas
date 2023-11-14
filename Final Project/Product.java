/* Name: Product
 * Description: Represents a product
 * Parameters: none
 * Relationships: none
 */
public interface Product {
    public String getId ();
    public String getType (); 
    public String getName ();
    public String getDescription ();
    public double getPrice ();
    public String getUnit ();
    public int getQuantity ();
    public String getImage ();
    public void setId (String id);
    public void setType (String type);
    public void setName (String name);
    public void setDescription (String description);
    public void setPrice (double price);
    public void setUnit (String unit); 
    public void setQuantity (int quantity);
    public void setImage (String image);
}
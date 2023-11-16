//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: Produce.java
 */
//-------------------------------------------------------



/* Name: Produce
 * Description: Represents a product
 * Parameters: none
 * Relationships: is a Product
 */
public class Produce implements Product {
    //GLOBAL VARIABLES
    private String id; //The product's ID
    private String type; //The product's type
    private String name; //The product's name
    private String description; //The product's description
    private double price; //The product's price
    private String unit; //The product's unit
    private int quantity; //The product's quantity
    private String image; //The product's image

    /* Name: Product
     * Description: The default constructor for the class
     * Parameters:  id - the product's ID
     *              type - the product's type
     *              name - the product's name
     *              description - the product's description
     *              price - the product's price
     *              unit - the product's price unit
     *              quantity - the product's quantity
     *              image - the product's image
     */
    public Produce (String id, String type, String name, String description, String price, String unit, String quantity, String image) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.price = Double.parseDouble(price);
        this.unit = unit;
        this.quantity = Integer.parseInt(quantity);
        this.image = image;
    }

    /* Name: getId
     * Description: Gets the product's ID
     * Parameters: none
     * Returns: String - the product's ID
     */
    public String getId () {
        return id;
    }

    /* Name: getType
     * Description: Gets the product's type
     * Parameters: none
     * Returns: String - the product's type
     */
    public String getType () {
        return type;
    }

    /* Name: getName
     * Description: Gets the product's name
     * Parameters: none
     * Returns: String - the product's name
     */
    public String getName () {
        return name;
    }

    /* Name: getDescription
     * Description: Gets the product's description
     * Parameters: none
     * Returns: String - the product's description
     */
    public String getDescription () {
        return description;
    }

    /* Name: getPrice
     * Description: Gets the product's price
     * Parameters: none
     * Returns: double - the product's price
     */
    public double getPrice () {
        return price;
    }

    /* Name: getUnit
     * Description: Gets the product's unit
     * Parameters: none
     * Returns: String - the product's unit
     */
    public String getUnit () {
        return unit;
    }

    /* Name: getQuantity
     * Description: Gets the product's quantity
     * Parameters: none
     * Returns: int - the product's quantity
     */
    public int getQuantity () {
        return quantity;
    }

    /* Name: getImage
     * Description: Gets the product's image
     * Parameters: none
     * Returns: String - the product's image
     */
    public String getImage () {
        return image;
    }

    /* Name: setId
     * Description: Sets the product's ID
     * Parameters: id - the product's ID
     * Returns: none
     */
    public void setId (String id) {
        this.id = id;
    }

    /* Name: setType
     * Description: Sets the product's type
     * Parameters: type - the product's type
     * Returns: none
     */
    public void setType (String type) {
        this.type = type;
    }

    /* Name: setName
     * Description: Sets the product's name
     * Parameters: name - the product's name
     * Returns: none
     */
    public void setName (String name) {
        this.name = name;
    }

    /* Name: setDescription
     * Description: Sets the product's description
     * Parameters: description - the product's description
     * Returns: none
     */
    public void setDescription (String description) {
        this.description = description;
    }

    /* Name: setPrice
     * Description: Sets the product's price
     * Parameters: price - the product's price
     * Returns: none
     */
    public void setPrice (double price) {
        this.price = price;
    }

    /* Name: setUnit
     * Description: Sets the product's unit
     * Parameters: unit - the product's unit
     * Returns: none
     */
    public void setUnit (String unit) {
        this.unit = unit;
    }

    /* Name: setQuantity
     * Description: Sets the product's quantity
     * Parameters: quantity - the product's quantity
     * Returns: none
     */
    public void setQuantity (int quantity) {
        this.quantity = quantity;
    }

    /* Name: setImage
     * Description: Sets the product's image
     * Parameters: image - the product's image
     * Returns: none
     */
    public void setImage (String image) {
        this.image = image;
    }
}
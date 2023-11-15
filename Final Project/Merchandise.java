//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: Merchandise.java
 */
//-------------------------------------------------------

/* Name: Merchandise
 * Description: Represents a product
 * Parameters: none
 * Relationships: none
 * Design: is a Product
 */
public class Merchandise implements Product {
    private String id; //The id of the product. must be unique
    private String type; //The type of the product
    private String name; //The name of the product
    private String description; //The description of the product
    private double price; //The price of the product
    private String unit; //The unit of the product price (e.g. USD)
    private int quantity; //The quantity of the product
    private String image; //The image of the product

    /* Name: Merchandise
     * Description: The constructor for the Merchandise class
     * Parameters:  id - the id of the product
     *              type - the type of the product
     *              name - the name of the product
     *              description - the description of the product
     *              price - the price of the product
     *              unit - the unit of the product price
     *              quantity - the quantity of the product
     *              image - the image link to the product
     */
    public Merchandise (String id, String type, String name, String description, String price, String unit, String quantity, String image) {
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
     * Description: Gets the id of the product
     * Parameters: none
     * Returns: the id of the product
     */
    public String getId () {
        return id;
    }

    /* Name: getType
     * Description: Gets the type of the product
     * Parameters: none
     * Returns: the type of the product
     */
    public String getType () {
        return type;
    }

    /* Name: getName
     * Description: Gets the name of the product
     * Parameters: none
     * Returns: the name of the product
     */
    public String getName () {
        return name;
    }

    /* Name: getDescription
     * Description: Gets the description of the product
     * Parameters: none
     * Returns: the description of the product
     */
    public String getDescription () {
        return description;
    }

    /* Name: getPrice
     * Description: Gets the price of the product
     * Parameters: none
     * Returns: the price of the product
     */
    public double getPrice () {
        return price;
    }

    /* Name: getUnit
     * Description: Gets the unit of the product
     * Parameters: none
     * Returns: the unit of the product
     */
    public String getUnit () {
        return unit;
    }

    /* Name: getQuanitity
     * Description: Gets the quantity of the product
     * Parameters: none
     * Returns: the quantity of the product
     */
    public int getQuantity () {
        return quantity;
    }

    /* Name: getImage
     * Description: Gets the image of the product
     * Parameters: none
     * Returns: the image of the product
     */
    public String getImage () {
        return image;
    }

    /* Name: setId
     * Description: Sets the id of the product
     * Parameters: id - the id of the product
     * Returns: none
     */
    public void setId (String id) {
        this.id = id;
    }

    /* Name: setType
     * Description: Sets the type of the product
     * Parameters: type - the type of the product
     * Returns: none
     */
    public void setType (String type) {
        this.type = type;
    }
    /* Name: setName
     * Description: Sets the name of the product
     * Parameters: name - the name of the product
     * Returns: none
     */
    public void setName (String name) {
        this.name = name;
    }

    /* Name: setDescription
     * Description: Sets the description of the product
     * Parameters: description - the description of the product
     * Returns: none
     */
    public void setDescription (String description) {
        this.description = description;
    }

    /* Name: setPrice
     * Description: Sets the price of the product
     * Parameters: price - the price of the product
     * Returns: none
     */
    public void setPrice (double price) {
        this.price = price;
    }

    /* Name: setUnit
     * Description: Sets the unit price of the product
     * Parameters: unit - the unit of the product price (e.g. USD)
     * Returns: none
     */
    public void setUnit (String unit) {
        this.unit = unit;
    }

    /* Name: setQuantity
     * Description: Sets the quantity of the product
     * Parameters: quantity - the quantity of the product
     * Returns: none
     */
    public void setQuantity (int quantity) {
        this.quantity = quantity;
    }

    /* Name: setImage
     * Description: Sets the image of the product
     * Parameters: image - the image of the product
     * Returns: none
     */
    public void setImage (String image) {
        this.image = image;
    }
}
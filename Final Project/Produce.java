/* Name: Produce
 * Description: Represents a product
 * Parameters: none
 * Relationships: none
 */
public class Produce implements Product {
    private String id;
    private String type;
    private String name;
    private String description;
    private double price;
    private String unit;
    private int quantity;
    private String image;

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

    public String getId () {
        return id;
    }
    public String getType () {
        return type;
    }
    public String getName () {
        return name;
    }
    public String getDescription () {
        return description;
    }
    public double getPrice () {
        return price;
    }
    public String getUnit () {
        return unit;
    }
    public int getQuantity () {
        return quantity;
    }
    public String getImage () {
        return image;
    }
    public void setId (String id) {
        this.id = id;
    }
    public void setType (String type) {
        this.type = type;
    }
    public void setName (String name) {
        this.name = name;
    }
    public void setDescription (String description) {
        this.description = description;
    }
    public void setPrice (double price) {
        this.price = price;
    }
    public void setUnit (String unit) {
        this.unit = unit;
    }
    public void setQuantity (int quantity) {
        this.quantity = quantity;
    }
    public void setImage (String image) {
        this.image = image;
    }
}
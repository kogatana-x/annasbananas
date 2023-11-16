//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: ProductRepository.java
 */
//-------------------------------------------------------


/* Name: ProductRepository
 * Description: Manages the products in the database
 * Parameters: connection - the connection to the database
 * Relationships: Uses the Product class
 */
public class ProductRepository {
    //GLOBAL VARIABLES
    private String filename="products.txt"; //The name of the file to store the products in
    private ProductFactory ProductFactory = new ProductFactory();//The factory to create products
    private Database ProductDatabase;


    /* Name: ProductRepository
     * Description: The constructor for the ProductRepository class
     */
    public ProductRepository(){
        filename="products.txt"; 
        ProductDatabase = new Database(filename);//The database to store the products in
    }

    /* Name: ProductRepository
     * Description: The constructor for the ProductRepository class. Used for JUnit testing
     */
    public ProductRepository(String filename){
        this.filename=filename;
        ProductDatabase = new Database(filename);
    }
    /* Name: saveProduct
     * Description: Saves a product to the database
     * Parameters: product - the product to save
     * Returns: boolean - whether the product was saved successfully
     */
    public boolean saveProduct(Product product){
        int index = ProductDatabase.isInDB(product.getId()); //Check if the product is already in the database
        if(index==-1){//If the product is not in the database, add it
            String row=product.getId()+","+product.getType()+","+product.getName()+","+product.getDescription()+","+product.getPrice()+","+product.getUnit()+","+product.getQuantity()+","+product.getImage();
            ProductDatabase.add(row);
            return true;
        }
        return false;

    }
    /* Name: getAllProducts
     * Description: Gets all products from the database
     * Parameters: none
     * Returns: Product[] - an array of all products
     */
    public Product[] getAllProducts(){
        Product[] products = new Product[getNumberOfProducts()]; //Create an array of products
        for(int x=0;x<products.length;x++){ //For each product in the database, add it to the array
            if(getProduct(Integer.toString(x))!=null){ //If the product exists, add it to the array
                products[x]=getProduct(Integer.toString(x)); //Add the product to the array
            }
        }
        return products;
    }
    
    /* Name: getNumberOfProducts
     * Description: Gets the number of products in the database
     * Parameters: none
     * Returns: int - the number of products in the database
     */
    public int getNumberOfProducts(){
        return ProductDatabase.getNumberOfRows();
    }

    /* Name: getProduct
     * Description: Gets a product from the database
     * Parameters: id - the ID of the product to get
     * Returns: Product - the product with the given ID
     */
    public Product getProduct(String id){
        String partString = ProductDatabase.returnResultRow("0",id); //Get the product from the database
        String[] parts=partString.split(","); //Split the product into its parts
        if(parts==null){return null; } //If the product does not exist, return null
        if(parts.length>1){ //If the product exists, create it
            return ProductFactory.createProduct(parts); //Create the product
        }
        return null;
    }

}

//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: ProductCatalog.java
 */
//-------------------------------------------------------



/* Name: ProductCatalog
 * Description: Manages the products in the catalog
 * Parameters: productRepository - the repository to use to retrieve products
 * Relationships: has a ProductRepository class. has many products
 * Design: Singleton
 */
public class ProductCatalog {
    //GLOBAL VARIABLES
    private ProductRepository productRepository; //The repository to use to retrieve products
    
    /* Name: ProductCatalog
     * Description: The default constructor for the class
     * Parameters: productRepository - the repository to use to retrieve products
     */
    private ProductCatalog(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    /* Name: getInstance
     * Description: Gets the instance of the ProductCatalog class
     * Parameters: none
     * Returns: ProductCatalog - the instance of the ProductCatalog class
     */
    public static ProductCatalog getInstance() {
        ProductCatalog productCatalog = new ProductCatalog(new ProductRepository()); //Create a new instance of the ProductCatalog class
        return productCatalog;
    }

    /* Name: getProduct
     * Description: Gets a product from the repository
     * Parameters: id - the ID of the product to get
     * Returns: Product - the product with the given ID
     */
    public Product getProduct(String id) {
        Product product = productRepository.getProduct(id); //Get the product from the repository
        return product;
    }

    /* Name: getAllProducts
     * Description: Gets all products from the repository
     * Parameters: none
     * Returns: Product[] - an array of all products
     */
    public Product[] getAllProducts(){
        Product[] products = productRepository.getAllProducts(); //Get all products from the repository
        return products;
    }

    /* Name: getProductsByType
     * Description: Gets all products of a given type from the repository
     * Parameters: type - the type of products to get
     * Returns: Product[] - an array of all products of the given type
     */
    public boolean addProduct(Product product) {
        boolean result=false;
        result = productRepository.saveProduct(product); //Get all products of the given type from the repository
        return result;
    }

}
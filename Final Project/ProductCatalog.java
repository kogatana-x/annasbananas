/* Name: ProductCatalog
 * Description: Manages the products in the catalog
 * Parameters: productRepository - the repository to use to retrieve products
 * Relationships: Uses the ProductRepository class
 */
public class ProductCatalog {
    private ProductRepository productRepository;
    
    public ProductCatalog(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(String id) {
        Product product = productRepository.getProduct(id);

        return product;
    }
    public Product[] getAllProducts(){
        Product[] products = new Product[productRepository.getNumberOfProducts()-1];
        for(int x=0;x<products.length;x++){
            if(productRepository.getProduct(Integer.toString(x))!=null){
                products[x]=productRepository.getProduct(Integer.toString(x));
            }
        }
        return products;
    }

    public boolean addProduct(Product product) {
        boolean result=false;
        result = productRepository.saveProduct(product);

        return result;
    }
    public boolean updateProduct(Product product) {
        boolean result=false;
        result = productRepository.updateProduct(product);

        return result;
    }
    public boolean deleteProduct(String id) {
        boolean result=false;
        result = productRepository.deleteProduct(id);

        return result;
    }
}
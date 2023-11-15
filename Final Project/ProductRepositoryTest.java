import org.junit.Test;
import static org.junit.Assert.*;

public class ProductRepositoryTest {
    
    @Test
    public void testSaveProduct() {
        ProductRepository repo = new ProductRepository();
        Product product = new Produce("1", "type", "name", "description", "10.0", "unit", "5", "image");
        boolean result = repo.saveProduct(product);
        assertTrue(result);

        product = new Merchandise("1", "type", "name", "description", "10.0", "unit", "5", "image");
        result = repo.saveProduct(product);
        assertTrue(result);

    }
    
    @Test
    public void testGetAllProducts() {
        ProductRepository repo = new ProductRepository();
        Product product1 = new Produce("1", "type1", "name1", "description1", "10.0", "unit1", "5", "image1");
        Product product2 = new Merchandise("2", "type2", "name2", "description2", "20.0", "unit2", "10", "image2");
        repo.saveProduct(product1);
        repo.saveProduct(product2);
        Product[] products = repo.getAllProducts();
        assertEquals(2, products.length);
        assertEquals(product1, products[0]);
        assertEquals(product2, products[1]);
    }
    
    @Test
    public void testGetNumberOfProducts() {
        ProductRepository repo = new ProductRepository();
        Product product1 = new Produce("1", "type1", "name1", "description1", "10.0", "unit1", "5", "image1");
        Product product2 = new Merchandise("2", "type2", "name2", "description2", "20.0", "unit2", "10", "image2");
        repo.saveProduct(product1);
        repo.saveProduct(product2);
        int result = repo.getNumberOfProducts();
        assertEquals(2, result);
    }
    
    @Test
    public void testGetProduct() {
        ProductRepository repo = new ProductRepository();
        Product product = new Merchandise("1", "type", "name", "description", "10.0", "unit", "5", "image");
        repo.saveProduct(product);
        Product result = repo.getProduct("1");
        assertEquals(product, result);

        product = new Produce("2", "type", "name", "description", "10.0", "unit", "5", "image");
        repo.saveProduct(product);
        result = repo.getProduct("1");
        assertEquals(product, result);
    }
}
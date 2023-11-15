import org.junit.Test;
import static org.junit.Assert.*;

public class ProductRepositoryTest {
    
    @Test
    public void testSaveProduct() {
        ProductRepository repo = new ProductRepository("testproducts.txt");
        Product product = new Produce("1", "type", "name", "description", "10.0", "unit", "5", "image");
        boolean result = repo.saveProduct(product);
        assertTrue(result);

        product = new Merchandise("2", "type", "name", "description", "10.0", "unit", "5", "image");
        result = repo.saveProduct(product);
        assertTrue(result);

    }
        
    @Test
    public void testGetNumberOfProducts() {
        ProductRepository repo = new ProductRepository("testproducts.txt");
        Product product1 = new Produce("5", "type1", "name1", "description1", "10.0", "unit1", "5", "image1");
        Product product2 = new Merchandise("6", "type2", "name2", "description2", "20.0", "unit2", "10", "image2");
        repo.saveProduct(product1);
        repo.saveProduct(product2);
        int result = repo.getNumberOfProducts();
        assertEquals(7, result);
    }
    
    @Test
    public void testGetProduct() {
        ProductRepository repo = new ProductRepository("testproducts.txt");
        Product product = new Merchandise("7", "Merchandise", "name", "description", "10.0", "unit", "5", "image");
        repo.saveProduct(product);
        Product result = repo.getProduct("7");
        assertEquals(product.getId(), result.getId());

        product = new Produce("8", "Produce", "name", "description", "10.0", "unit", "5", "image");
        repo.saveProduct(product);
        result = repo.getProduct("8");
        assertEquals(product.getId(), result.getId());
    }
}
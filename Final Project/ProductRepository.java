/* Name: ProductRepository
 * Description: Manages the products in the database
 * Parameters: connection - the connection to the database
 * Relationships: Uses the Product class
 */
public class ProductRepository {
    private String filename="products.txt";
    Database ProductDatabase = new Database(filename);

    public boolean saveProduct(Product product){
        boolean result = ProductDatabase.isInDB(product.getId());
        if(!result){
            String row=product.getId()+","+product.getType()+","+product.getName()+","+product.getDescription()+","+product.getPrice()+","+product.getUnit()+","+product.getQuantity()+","+product.getImage();
            ProductDatabase.add(row);
            return true;
        }
        return false;

    }
    
    public int getNumberOfProducts(){
        return ProductDatabase.getRows();
    }

    public Product getProduct(String id){
        String[] parts = ProductDatabase.returnResult(id);
        if(parts.length>1){
            return new Product(parts[0], parts[1], parts[2], parts[3], Double.parseDouble(parts[4]), parts[5], Integer.parseInt(parts[6]), parts[7]);
        }
        return null;
    }
    

    public boolean updateProduct(Product product){
        String row=product.getId()+","+product.getType()+","+product.getName()+","+product.getDescription()+","+product.getPrice()+","+product.getUnit()+","+product.getQuantity()+","+product.getImage();
        boolean result=ProductDatabase.update(row,0);
        return result;
    }

    public boolean deleteProduct(String id){
        boolean result=ProductDatabase.delete(id);
        return result;
    }
}

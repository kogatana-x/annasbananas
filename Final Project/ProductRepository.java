/* Name: ProductRepository
 * Description: Manages the products in the database
 * Parameters: connection - the connection to the database
 * Relationships: Uses the Product class
 */
public class ProductRepository {
    private String filename="products.txt";
    Database ProductDatabase = new Database(filename);
    ProductFactory ProductFactory = new ProductFactory();

    public boolean saveProduct(Product product){
        int index = ProductDatabase.isInDB(product.getId());
        if(index==-1){
            String row=product.getId()+","+product.getType()+","+product.getName()+","+product.getDescription()+","+product.getPrice()+","+product.getUnit()+","+product.getQuantity()+","+product.getImage();
            ProductDatabase.add(row);
            return true;
        }
        return false;

    }
    public Product[] getAllProducts(){
        Product[] products = new Product[getNumberOfProducts()];
        for(int x=0;x<products.length;x++){
            if(getProduct(Integer.toString(x))!=null){
                products[x]=getProduct(Integer.toString(x));
            }
        }
        return products;
    }
    
    public int getNumberOfProducts(){
        return ProductDatabase.getNumberOfRows();
    }

    public Product getProduct(String id){
        String[] parts = ProductDatabase.returnResult(id);
        if(parts==null){return null; }
        if(parts.length>1){
            return ProductFactory.createProduct(parts);
        }
        return null;
    }
    

    /*public boolean updateProduct(Product product,int field, String value){
        String row=product.getId()+","+product.getType()+","+product.getName()+","+product.getDescription()+","+product.getPrice()+","+product.getUnit()+","+product.getQuantity()+","+product.getImage();
        boolean result=ProductDatabase.update(row,0);
        return result;
    }

    public boolean deleteProduct(String id){
        boolean result=ProductDatabase.delete(id);
        return result;
    }*/
}

public class ProductFactory {

    public Product createProduct(String[] parts){
        String type=parts[1].trim();
        if(type.equals("Merchandise")){
            return new Merchandise(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5],parts[6], parts[7]);
        }else if(type.equals("Produce")){
            return new Produce(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5],parts[6], parts[7]);
        } else{
            return null;
        }
    }
}

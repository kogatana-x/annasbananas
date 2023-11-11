
public class Cart {
    private static Cart instance;
    private String cartID;
    private Integer[][] PQ=new Integer[10][2]; //pidQ[0][0] = pid, pidQ[0][1] = quantity 
    private CartRepository CartRepository = new CartRepository();

    public static Cart getInstance(){
        if(instance==null){
            instance=new Cart();
        }
        return instance;
    }

    public void setup(String username){
        //find cart for user name. status must be false
        String[] cart=CartRepository.getCart(username);
        if(cart[0].equals("error")){
            //NEW CART
            for(int x=0;x<PQ.length;x++){
                this.PQ[x][0]=x;
                this.PQ[x][1]=0;
            }
            this.cartID=CartRepository.add(username,PQ);
        }
        else{
            //parse cart
            this.cartID=cart[0];
            String[] rows = cart[2].substring(1, cart[2].length()-1).split("\\]\\[");
            this.PQ = new Integer[rows.length][];
            for(int i = 0; i < rows.length; i++){
                String[] cols = rows[i].split("\\.");
                this.PQ[i] = new Integer[cols.length];
                for(int j = 0; j < cols.length; j++){
                    this.PQ[i][j] = Integer.parseInt(cols[j]);
                }
            }
        }
    }
    public void addToCart(String product,String quantity) {
        CartRepository.update(cartID,product,quantity);
    }
    public void removeFromCart(String product) {
        CartRepository.update(cartID,product,"0");
    }
    public void clearCart() {
        CartRepository.clear(this.cartID);
    }
    public void updateCart(String product, String quantity) {
        CartRepository.update(this.cartID, product, quantity);
    }
    public void checkout() {
        CartRepository.checkout(this.cartID);
    }
 

}

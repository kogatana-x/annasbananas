import java.io.IOException;
import java.net.Socket;


public class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }
    private String getSourceInfo(Socket socket){ //TODO Logger
        String ip = socket.getInetAddress().getHostAddress();
        int port = socket.getPort();
        return ip + ":" + port;
    }
   
    @Override
    public void run(){
        try{
            HTMLParser parser = new HTMLParser("html/",socket);
            parser.parseRawStrings();
            String method=parser.getMethod();
            String path=parser.getPath();
            System.out.println("(ClientHandler): "+method+" "+path+" from " + getSourceInfo(socket));
            parser.getValues();
            String cookie=parser.getCookie();
            String username=cookie;
            //if(!cookie.equals("")){cart = new CartBuilder(cookie);}
            /*User user;
            if(!cookie.equals("")){
                UserRepository userRepo = new UserRepository();
                user=userRepo.getUser(cookie);
            }*/


            String filename="garbage";
            String mimeType = "text/html";
            byte[] body=null;

            boolean result=false;

            //REQUEST/FORM BASED PATHS >> 
            if(method.equals("POST")&&path.equals("/register")){
                String password="",firstname="",lastname="";
                filename="/finished-registration.html";
                mimeType = "text/html";
                method="GET";
                if (parser.values==null){return;}

                for (int x=0;x<parser.values.size();x++) {

                    if (parser.values.get(x).contains("username")) {
                        x++;
                        username = parser.values.get(x);
                    } else if (parser.values.get(x).contains("password")) {
                        x++;
                        password = parser.values.get(x);
                    } else if (parser.values.get(x).contains("firstname")) {
                        x++;
                        firstname = parser.values.get(x);
                    } else if (parser.values.get(x).contains("lastname")) {
                        x++;
                        lastname = parser.values.get(x);
                    }
                }
                UserAuthenticator authenticator = new UserAuthenticator(new UserRepository());
                result=authenticator.register(username,password,firstname,lastname);
                String res;
                if(result){
                    res="success";
                    path="/finished-registration.html"; 
                }
                else{
                    res="failure";
                    filename="/register.html";
                    path="/register.html?showAlert=true";
                    parser.redirect(path);
                }
                body=parser.readImage(filename);
                System.out.println("Registration attempt "+res+" from " + getSourceInfo(socket) + " with " + username);
            } 
            
            else if(method.equals("POST")&&path.equals("/login")){
                method="GET";
                String password="";
                for (int x=0;x<parser.values.size();x++) {
                    if (parser.values.get(x).contains("username")) {
                        x++;
                        username = parser.values.get(x);
                    } else if (parser.values.get(x).contains("password")) {
                        x++;
                        password = parser.values.get(x);
                    } 
                }
                UserAuthenticator authenticator = new UserAuthenticator(new UserRepository());
                String res;
                result=authenticator.login(username, password);
                if(result){
                    res="success";
                    filename="/products.html";
                    path="/products.html";
                    parser.setCookie(username,31536000);
                }
                else{
                    res="failure";
                    filename="/login.html";
                    path="/login.html?showAlert=true";
                    parser.redirect(path);
                }
                mimeType = "text/html";
                body=parser.readImage(filename);
                System.out.println("Login attempt "+res+" from " + getSourceInfo(socket) + " with " + username);
            } 
            
            //CAN ONLY DO THESE W/ A LOGGED IN USER
            else if(method.equals("GET")&&path.equals("/payments.html")){ //TODO
                //filename="/payments.html";
                //mimeType = "text/html";
                path="http://"+socket.getLocalAddress().toString()+":8081/payments.html";
                System.out.println("PATH REDIRECT TO: "+path);
                parser.redirect(path);
                System.out.println("Payment attempt success from " + getSourceInfo(socket));
                return;
            } 
                
            else if(method.equals("POST")&&path.equals("/addToCart")){
                CartBuilder cart = new CartBuilder(cookie);

                String productId = "";
                String quantity="";
                String rez="failed";
                path="/products.html";
                method="GET";
                if(cookie.equals("")){
                    //NEED TO BE LOGGED IN: 
                    path="/products.html?showAlert=true";
                    parser.redirect(path);
                }
                else{
                    for (int x=0;x<parser.values.size();x++) {
                        if (parser.values.get(x)==null){break;}
                        else if (parser.values.get(x).equals("productId")) {
                            x++;
                            productId = parser.values.get(x);
                        }
                        else if(parser.values.get(x).equals("quantity")){
                            x++;
                            quantity=parser.values.get(x);
                        }
                    }
                    rez="success";
                }
                filename="/products.html";
                cart=cart.addItem(productId, quantity);               
                System.out.println("Add to cart attempt "+rez+" from " + username +" "+ getSourceInfo(socket) + " with productId " + productId+" and quantity "+quantity);
                
            }      
            
            else if(method.equals("POST")&&path.equals("/checkout")){ //TODO
                CartBuilder cart = new CartBuilder(cookie);

                String res;
                result=cart.checkout();
                if(result){
                    res="success";
                    path="/address.html";
                    filename="/address.html";
                    mimeType = "text/html";
                    body=parser.readImage(filename);
                    parser.redirect(path);
                }
                else{
                    res="failure";
                    filename="/cart.html";
                    path="/cart.html?showAlert=true";
                    parser.redirect(path);
                }
                System.out.println("Checkout attempt "+res+" from " + getSourceInfo(socket));
                //String username="";
                //authenticator.checkout(username, productId);
                //filename="finished-registration.html";
                } 
                
            else if(path.equals("/logout")){
                System.out.println("Logout from " + getSourceInfo(socket) + " with username " + username);
                parser.setCookie(username,0);
                filename="/login.html";
                mimeType = "text/html";
                path="/login.html";
                parser.redirect(path);
                return;
            } 
        
            else if(method.equals("POST")&&path.equals("/address")){
                String address1="";
                String address2="";
                String city="";
                String state="";
                String zip="";
                String country="";

                if(cookie.equals("")){
                    //NEED TO BE LOGGED IN: 
                    path="/address.html?showAlert=true";
                    parser.redirect(path);
                }
                else{
                    for (int x=0;x<parser.values.size();x++) {
                        if (parser.values.get(x)==null){break;}
                        else if (parser.values.get(x).equals("street-address-1")) {
                            x++;
                            address1 = parser.values.get(x);
                        }
                        else if (parser.values.get(x).equals("street-address-2")) {
                            x++;
                            address2 = parser.values.get(x);
                        }
                        else if(parser.values.get(x).equals("city")){
                            x++;
                            city=parser.values.get(x);
                        }
                        else if(parser.values.get(x).equals("state")){
                            x++;
                            state=parser.values.get(x);
                        }
                        else if(parser.values.get(x).equals("zipcode")){
                            x++;
                            zip=parser.values.get(x);
                        }
                        else if(parser.values.get(x).equals("country")){
                            x++;
                            country=parser.values.get(x);
                        }
                    }
                    parser.setCookie(username,31536000);

                    AddressRepository addressRepo = new AddressRepository();
                    addressRepo.saveAddress(new Address(username,address1,address2,city,state,zip,country));
                    parser.setCookie(username,31536000);

                    path="http://"+socket.getLocalAddress().toString()+":8081/payments.html";
                    System.out.println("PATH REDIRECT TO: "+path);
                    parser.redirect(path);
                    return;
                }
                System.out.println("Checkout-Address attempt from " + username +" "+ getSourceInfo(socket));
                
            } 
            else if(method.equals("POST")&&path.equals("/payment")){
                String address="";
                String city="";
                String state="";
                String zip="";
                String country="";
                String card="";
                String exp="";
            }
            //HTML-BASED FILE PATHS >> 
            if(path.startsWith("/products.html")){
                ProductCatalog productCatalog = new ProductCatalog(new ProductRepository());
                Product[] products = productCatalog.getAllProducts();

                HTMLDisplay display = new HTMLDisplay();

                StringBuilder html = display.displayProductCatalog(products);
                filename="products.html";
                mimeType = "text/html";
                String productsHtml = parser.replace("products.html","<div id=\"product-list\"></div>", html.toString());
                body=productsHtml.getBytes();
                // Send the generated HTML as the response
            }

            else if(path.startsWith("/cart.html")){
                CartBuilder cart = new CartBuilder(cookie);

                Product[] cartProducts = cart.displayCart();
                HTMLDisplay display = new HTMLDisplay();
                StringBuilder html = display.displayCart(cartProducts);
                String cartHtml="";
                filename="cart.html";
                mimeType = "text/html";
                cartHtml = parser.readFile(filename);
                String finalHTML = cartHtml.replace("<div id=\"cart-list\"></div>", html.toString());
                body=finalHTML.getBytes();
            }

            if(filename.equals("garbage")){
                int isQuery = path.indexOf("?");
                if (path.contains(".html")) {
                    if(isQuery!=-1){filename = path.substring(1,isQuery);}
                    else{filename = path.substring(1);}
                    mimeType = "text/html";
                } else if (path.endsWith(".css")) {
                    filename = path.substring(1); 
                    mimeType = "text/css";
                } else if (path.endsWith(".js")) {
                    filename = path.substring(1);  
                    mimeType = "application/javascript";
                } else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
                    filename = path.substring(1); 
                    mimeType = "image/jpeg";
                } else if (path.endsWith(".png")){
                    filename = path.substring(1);
                    mimeType = "image/png"; 
                }
                else if (path.endsWith(".ico")){
                    filename = path.substring(1);
                    mimeType = "image/x-icon";
                }
                else if (path.endsWith(".gif")){
                    filename = path.substring(1);
                    mimeType = "image/gif";
                }
                else if(path.equals("/site.webmanifest")){
                    filename="site.webmanifest";
                    mimeType = "application/manifest+json";
                }else {
                    filename="index.html";
                    mimeType = "text/html";
                }
                body = parser.readImage(filename);
            }
            
            parser.sendResponse("200 OK", mimeType, body);
    }
        finally{
            try{
                socket.close();
            }
            catch(IOException e){
                System.out.println("Error closing socket: " + e.getMessage());
            }
        }
    }
}




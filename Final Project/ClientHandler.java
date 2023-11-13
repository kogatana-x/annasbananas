import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public class ClientHandler implements Runnable {
    private Socket socket;
    private String username="";

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }
    private String getSourceInfo(Socket socket){ //TODO Logger
        String ip = socket.getInetAddress().getHostAddress();
        int port = socket.getPort();
        return ip + ":" + port;
    }
    private String getRequestInfo(BufferedReader reader) throws IOException{
        String line = reader.readLine();
        if (line == null) {
            return null;
        }
        String[] parts = line.split(" ");
        if (parts.length < 2) {
            return null;
        }
        return parts[1];
    }
   
    @Override
    public void run(){

        HTMLParser parser = new HTMLParser(socket);
        parser.parseRawStrings();
        String method=parser.getMethod();
        String path=parser.getPath();
        System.out.println(method+" Request from " + getSourceInfo(socket) + " for " + path);

        parser.getValues();
       // String cookie = parser.getCookie();

        String filename="garbage";
        String mimeType = "text/html";

        boolean result=false;

        //REQUEST/FORM BASED PATHS >> 
        if(method.equals("POST")&&path.equals("/register")){
            String password="",firstname="",lastname="";
            path="/register.html";
            method="GET";
            if (parser.values==null){return;}

            for (int x=0;x<parser.values.size();x++) {
                //System.out.println(parser.values.get(x));

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
               filename="finished-registration.html"; 
            }
            else{
                res="failure";
                path="/register.html?showAlert=true";
                parser.redirect(path);
            }
           System.out.println("Registration attempt "+res+" from " + getSourceInfo(socket) + " with " + username);
        } 
        
        else if(method.equals("POST")&&path.equals("/login")){
            String password="";
            for (int x=0;x<parser.values.size();x++) {
                if (parser.values.get(x)==null){break;}
                else if (parser.values.get(x).equals("username")) {
                    x++;
                    username = parser.values.get(x);
                } else if (parser.values.get(x).equals("password")) {
                    x++;
                    password = parser.values.get(x);
                } 
           }
           UserAuthenticator authenticator = new UserAuthenticator(new UserRepository());
           String res;
           result=authenticator.login(username, password);
           if(result){
                res="success";
                filename="index.html";
            }
            else{
                res="failure";
                path="/login.html?showAlert=true";
                parser.redirect(path);
            }
           System.out.println("Login attempt "+res+" from " + getSourceInfo(socket) + " with username " + username);
        } 
        
        //CAN ONLY DO THESE W/ A LOGGED IN USER
        else if(method.equals("POST")&&path.equals("/payments")){
                String cardNumber = "";
                String cardName = "";
                String cardExpiry = "";
                String cardCVC = "";
                String cardZip = "";
                for (int x=0;x<parser.values.size();x++) {
                    if (parser.values.get(x)==null){break;}
                    else if (parser.values.get(x).equals("cardNumber")) {
                        x++;
                        cardNumber = parser.values.get(x);
                    } else if (parser.values.get(x).equals("cardName")) {
                        x++;
                        cardName = parser.values.get(x);
                    } else if (parser.values.get(x).equals("cardExpiry")) {
                        x++;
                        cardExpiry =parser.values.get(x);
                    } else if (parser.values.get(x).equals("cardCVC")) {
                        x++;
                        cardCVC = parser.values.get(x);
                    } else if (parser.values.get(x).equals("cardZip")) {
                        x++;
                        cardZip = parser.values.get(x);
                    }
            }
                    System.out.println("Payment attempt from " + getSourceInfo(socket) + " with card number " + cardNumber);
                    PaymentAuthenticator authenticator = new PaymentAuthenticator(new PaymentRepository());
                    String username="";
                    authenticator.pay(username, cardNumber, cardName, cardExpiry, cardCVC, cardZip);
                        
                    filename="finished-registration.html";
            } 
            
        else if(method.equals("POST")&&path.equals("/addToCart")){
            String productId = "";
            String rez="failed";
            path="/products.html";
            method="GET";
            if(username.equals("")){
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
                }
                rez="success";
            }
            filename="/products.html";
            System.out.println("Add to cart attempt "+rez+" from " + username + getSourceInfo(socket) + " with productId " + productId);
            //CartAuthenticator authenticator = new CartAuthenticator(new CartRepository());
            //String username="";
            //authenticator.addToCart(username, productId);
            
        } 
            
        else if(method.equals("POST")&&path.equals("/checkout")){
                String productId = "";
                for (int x=0;x<parser.values.size();x++) {
                    if (parser.values.get(x)==null){break;}
                    else if (parser.values.get(x).equals("productId")) {
                        x++;
                        productId = parser.values.get(x);
                    }
            }
            System.out.println("Checkout attempt from " + getSourceInfo(socket) + " with productId " + productId);
            //CartAuthenticator authenticator = new CartAuthenticator(new CartRepository());
            //String username="";
            //authenticator.checkout(username, productId);
            //filename="finished-registration.html";
            } 
            
        else if(method.equals("POST")&&path.equals("/logout")){
                //String username="";
                //System.out.println("Logout attempt from " + getSourceInfo(socket) + " with username " + username);
                //UserAuthenticator authenticator = new UserAuthenticator(new UserRepository());
                //authenticator.logout(username);
                //filename="finished-registration.html";
            } 
       
        //HTML-BASED FILE PATHS >> 
        else if(path.startsWith("/products.html")){
            ProductCatalog productCatalog = new ProductCatalog(new ProductRepository());
            Product[] products = productCatalog.getAllProducts();

            HTMLDisplay display = new HTMLDisplay();

            StringBuilder html = display.getString(products);
            String productsHtml="";
            filename="products.html";
            mimeType = "text/html";
            try{ productsHtml = new String(Files.readAllBytes(Paths.get("html/products.html")), StandardCharsets.UTF_8);}
            catch(IOException ex){System.out.println("Server exception: " + ex.getMessage());}
            String finalHTML = productsHtml.replace("<div id=\"product-list\"></div>", html.toString());
            // Send the generated HTML as the response
            parser.sendResponse("200 OK", "text/html", finalHTML);
            System.out.println("Products page requested from " + getSourceInfo(socket));
        }

        else if(path.startsWith("/cart.html")){
            CartViewer cartViewer = new CartViewer(username);
            Product[] cartProducts = cartViewer.displayCart();
            HTMLDisplay display = new HTMLDisplay();
            StringBuilder html = display.getString(cartProducts);
            String cartHtml="";
            filename="cart.html";
            mimeType = "text/html";
            try{ cartHtml = new String(Files.readAllBytes(Paths.get("html/cart.html")), StandardCharsets.UTF_8);}
            catch(IOException ex){System.out.println("Server exception: " + ex.getMessage());}
            String finalHTML = cartHtml.replace("<div id=\"product-list\"></div>", html.toString());
            // Send the generated HTML as the response
            parser.sendResponse("200 OK", "text/html", finalHTML);
            System.out.println("Products page requested from " + getSourceInfo(socket));
        }
        // Map the requested path to a file
       //else if(path.startsWith("/register.html")){

       //}
        if(filename.equals("garbage")){
            System.out.println("in path thing");
            if (path.contains(".html")) {
                filename = path.substring(1,path.indexOf("?"));    
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
            else {
                System.out.println("sending you to index");
                filename="index.html";
                mimeType = "text/html";
            }
        }
        boolean isBinary = mimeType.startsWith("image/");

        // Read the file into a byte array
        try{
            byte[] fileBytes = Files.readAllBytes(Paths.get("html/" + filename));

        // Send an HTTP response with the content
        OutputStream output = socket.getOutputStream();
        output.write(("HTTP/1.1 200 OK\r\nContent-Type: " + mimeType + "\r\n\r\n").getBytes());

        if (isBinary) {
            // Write the binary data directly to the output
            output.write(fileBytes);
        } else {
            // Convert the byte array to a string and write it to the output
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(new String(fileBytes));
        }

        output.close();
        } catch(InvalidPathException x){
            System.out.println("File not found: " + x.getMessage());
            parser.sendResponse("404 Not Found", "text/html", "<h1>404 Not Found</h1>");
        }catch(NoSuchFileException e){
            System.out.println("File not found: " + e.getMessage());
            parser.sendResponse("404 Not Found", "text/html", "<h1>404 Not Found</h1>");
        }catch (IOException ex) {

        }
    }
}




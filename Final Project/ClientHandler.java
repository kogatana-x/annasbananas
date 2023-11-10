import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public class ClientHandler implements Runnable {
    private Socket socket;

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

        parser.getValues();
       // String cookie = parser.getCookie();

        String filename="garbage";
        String mimeType = "text/html";

        boolean result=false;

        if(method.equals("POST")&&path.equals("/register")){
            String username="",password="",firstname="",lastname="";
            for (int x=0;x<parser.values.length;x++) {
                if (parser.values[x]==null){break;}
                else if (parser.values[x].equals("username")) {
                    x++;
                    username = parser.values[x];
                } else if (parser.values[x].equals("password")) {
                    x++;
                    password = parser.values[x];
                } else if (parser.values[x].equals("firstname")) {
                    x++;
                    firstname = parser.values[x];
                } else if (parser.values[x].equals("lastname")) {
                    x++;
                    lastname = parser.values[x];
                }
           }
           UserAuthenticator authenticator = new UserAuthenticator(new UserRepository());
           result=authenticator.register(username,password,firstname,lastname);
           String res;
           if(result){res="success";}else{res="failure";}
           System.out.println("Registration attempt "+res+" from " + getSourceInfo(socket) + " with username " + username);
           filename="finished-registration.html"; //TODO add a page for failed login and update username if logged in
           //NOTE: does not auto-login with new user account
        } 
        
        else if(method.equals("POST")&&path.equals("/login")){
            String username="",password="";
            for (int x=0;x<parser.values.length;x++) {
                if (parser.values[x]==null){break;}
                else if (parser.values[x].equals("username")) {
                    x++;
                    username = parser.values[x];
                } else if (parser.values[x].equals("password")) {
                    x++;
                    password = parser.values[x];
                } 
           }
           UserAuthenticator authenticator = new UserAuthenticator(new UserRepository());
           String res;
           result=authenticator.login(username, password);
           if(result){res="success";}else{res="failure";}
           System.out.println("Login attempt "+res+" from " + getSourceInfo(socket) + " with username " + username);
        
           filename="index.html"; //TODO add a page for failed login and update username if logged in

        } 
        
        else if(method.equals("POST")&&path.equals("/payments")){
            String cardNumber = "";
            String cardName = "";
            String cardExpiry = "";
            String cardCVC = "";
            String cardZip = "";
            for (int x=0;x<parser.values.length;x++) {
                if (parser.values[x]==null){break;}
                else if (parser.values[x].equals("cardNumber")) {
                    x++;
                    cardNumber = parser.values[x];
                } else if (parser.values[x].equals("cardName")) {
                    x++;
                    cardName = parser.values[x];
                } else if (parser.values[x].equals("cardExpiry")) {
                    x++;
                    cardExpiry = parser.values[x];
                } else if (parser.values[x].equals("cardCVC")) {
                    x++;
                    cardCVC = parser.values[x];
                } else if (parser.values[x].equals("cardZip")) {
                    x++;
                    cardZip = parser.values[x];
                }
           }
                System.out.println("Payment attempt from " + getSourceInfo(socket) + " with card number " + cardNumber);
                PaymentAuthenticator authenticator = new PaymentAuthenticator(new PaymentRepository());
                String username="";
                authenticator.pay(username, cardNumber, cardName, cardExpiry, cardCVC, cardZip);
                    
                filename="finished-registration.html";
        } 
        
        if(path.startsWith("/products.html")){
            ProductCatalog productCatalog = new ProductCatalog(new ProductRepository());
            Product[] products = productCatalog.getAllProducts();
            StringBuilder html = new StringBuilder();
            html.append("<div class=\"product-grid\">");

            for (Product product : products){
                html.append("<div class=\"product-card\">");
                html.append("<h2>").append(product.getName()).append("</h2>");
                html.append("<p><i> ").append(product.getDescription()).append("</i></p>");
                html.append("<p>Price: $").append(product.getPrice()).append("</p>");
                html.append("<p>Quantity Available: ").append(product.getQuantity()).append("</p>");
                html.append("<img src='").append(product.getImage()).append("' alt='Product Image'>");
                html.append("<form action='/addToCart' method='post'>");
                html.append("<input type='hidden' name='productId' value='").append(product.getId()).append("'>");
                html.append("<input type='submit' value='Add to Cart'>");
                html.append("</form>");
                html.append("</div>");
            }
            html.append("</div>");

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
            // Map the requested path to a file
           else if(filename.equals("garbage")){
                if (path.endsWith(".html")) {
                    filename = path.substring(1);    
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
        } catch(NoSuchFileException e){
            System.out.println("File not found: " + e.getMessage());
            parser.sendResponse("404 Not Found", "text/html", "<h1>404 Not Found</h1>");
        }catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}




//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: ClientHandler.java
 */
//-------------------------------------------------------

//IMPORTS
import java.io.IOException; //for input/output exceptions
import java.net.Socket; //for sockets

/* Name: ClientHandler
 * Description: Async client worker thread for handling a client connection
 * Parameters: socket - the socket to use to communicate with the client/server
 * Relationships: has a Logger. Extends Thread.
 */
public class ClientHandler extends Thread {
    //GLOBAL VARIABLES
    private Socket socket; //the socket to use to communicate with the client/server
    private Logger logger=Logger.getInstance(); //the logger to use
    private HTMLDisplay parser; //Create a new HTML display for displaying the product catalog

    /* Name: ClientHandler
     * Description: The constructor for the ClientHandler class
     * Parameters: socket - the socket to use to communicate with the client/server
     */
    public ClientHandler(Socket socket) {
        this.socket = socket;
        parser = new HTMLDisplay("html/",socket); //the parser to use for sending and recieving resposnes
    }

    /* Name: getSourceInfo
     * Description: Gets the source information from the client connections
     * Parameters: socket - the socket to use to communicate with the client/server
     * Returns: the ip address and port of the client used to connect to the server
     */
    private String getSourceInfo(Socket socket){ 
        String ip = socket.getInetAddress().getHostAddress(); //Get the ip address of the client
        int port = socket.getPort(); //Get the port of the client
        return ip + ":" + port; //format the ip and port as a string and return
    }
   
    /* Name: run
     * Description: The main method for the ClientHandler thread.
     *             Parses the request from the client and sends the response.
     * Parameters: none
     * Returns: none
     */
    @Override
    public void run(){
        //INSTANCE VARIABLES

        //Parse the request from the client
        try{
            //PARSE THE REQUEST
            parser.parseRawStrings(); //Parse the raw strings from the client
            String method=parser.getMethod(); //Get the method from the request
            String path=parser.getPath(); //Get the path from the request
            
            //System.out.println("(ClientHandler): "+method+" "+path+" from " + getSourceInfo(socket));
            logger.logAccess(getSourceInfo(socket),method,path); //Log the access
            
            //SET SESSION STATE
            parser.getValues(); //Get the values from the request
            String cookie=parser.getCookie(); //Get the cookie from the request
            String username=cookie; //Get the username from the cookie
            
            //SET BASE RESPONSE VARIABLES
            String filename="garbage";
            String mimeType = "text/html";
            byte[] body=null;
            boolean result=false;

            //REQUEST/FORM BASED PATHS >> 
            //CAN DO THESE W/ OR W/O A LOGGED IN USER
            //NEW USER REGISTRATION REQUEST - RECIEVED FROM HTML FORM in register.html
            if(method.equals("POST")&&path.equals("/register")){
                String password="",firstname="",lastname=""; //the password, first name, and last name of the user to register
                filename="/finished-registration.html"; //the filename to use for the response
                mimeType = "text/html"; //the mime type to use for the response
                method="GET"; //the method to use for the response
                if (parser.values==null){return;} //If there are no values, return

                //Get the values from the request
                for (int x=0;x<parser.values.size();x++) {

                    if (parser.values.get(x).contains("username")) { //If the value is the username
                        x++;   //The next value is the username value
                        username = parser.values.get(x); //Save the username
                    } else if (parser.values.get(x).contains("password")) { //If the value is the password
                        x++; //The next value is the password value
                        password = parser.values.get(x); //Save the password
                    } else if (parser.values.get(x).contains("firstname")) { //If the value is the first name
                        x++; //The next value is the first name value
                        firstname = parser.values.get(x); //Save the first name
                    } else if (parser.values.get(x).contains("lastname")) { //If the value is the last name
                        x++; //The next value is the last name value
                        lastname = parser.values.get(x); //Save the last name
                    }
                }
                //Register the user
                UserAuthenticator authenticator = new UserAuthenticator(new UserRepository()); //Create a new user authenticator
                result=authenticator.register(username,password,firstname,lastname); //Register the user
                String res; 
                if(result){ //If the user was registered successfully
                    res="success";
                    path="/finished-registration.html";  //redirect the user to finished-registration.html
                }
                else{  //If the user was not registered successfully
                    res="failure";
                    filename="/register.html";
                    path="/register.html?showAlert=true"; //redirect the user to register.html with an alert
                    parser.redirect(path);
                }
                body=parser.readImage(filename);                 //prepare the response

                logger.logAuth(getSourceInfo(socket), "register", username, res); //Log the registration attempt
                //System.out.println("Registration attempt "+res+" from " + getSourceInfo(socket) + " with " + username);
            } 
            
            //USER LOGIN REQUEST - RECIEVED FROM HTML FORM in login.html
            else if(method.equals("POST")&&path.equals("/login")){
                method="GET"; //the method to use for the response
                String password=""; //the password of the user to login

                //Get the values from the request
                for (int x=0;x<parser.values.size();x++) {
                    if (parser.values.get(x).contains("username")) { //If the value is the username
                        x++; //The next value is the username value
                        username = parser.values.get(x); //Save the username
                    } else if (parser.values.get(x).contains("password")) { //If the value is the password
                        x++; //The next value is the password value
                        password = parser.values.get(x); //Save the password
                    } 
                }

                //Login the user
                UserAuthenticator authenticator = new UserAuthenticator(new UserRepository()); //Create a new user authenticator
                String res; 
                result=authenticator.login(username, password); //Login the user
                if(result){ //If the user was logged in successfully
                    res="success";
                    filename="/products.html"; //redirect the user to products.html
                    path="/products.html"; 
                    parser.setCookie(username,31536000); //set the cookie for the user (never expires)
                }
                else{
                    res="failure"; //If the user was not logged in successfully
                    filename="/login.html"; //redirect the user to login.html
                    path="/login.html?showAlert=true"; //redirect the user to login.html with an alert
                    parser.redirect(path); //prepare the response
                }
                //prepare the response to send back to the client
                mimeType = "text/html"; 
                body=parser.readImage(filename);
                //Log the login attempt
                logger.logAuth(getSourceInfo(socket), "login", username, res);
                //System.out.println("Login attempt "+res+" from " + getSourceInfo(socket) + " with " + username);
            } 
            
            //CAN ONLY DO THESE W/ A LOGGED IN USER
            //REQUEST PATH IS PAYMENTS.HTML REDIRECT TO THE PAYMENT SERVER - THIS SHOULD NOT BE REACHED, BUT JUST IN CASE THERE IS A BACKEND ERROR
            else if(method.equals("GET")&&path.equals("/payments.html")){ 
                //Redirect the user to the payment server
                path="http://"+socket.getLocalAddress().toString()+":8081/payments.html";
                System.out.println("PATH REDIRECT TO: "+path);
                parser.redirect(path);
                //Log the payment attempt
                logger.logTransaction(getSourceInfo(socket), "payment", "redirected to external payment server");
                //System.out.println("Payment attempt success from " + getSourceInfo(socket));
                return;
            } 
            //IF THE SERVER RECIEVED FORM REQUEST FROM products.html to add a product to the cart
            else if(method.equals("POST")&&path.equals("/addToCart")){
                CartBuilder cart = new CartBuilder(cookie); //Create a new cart builder

                String productId = ""; //the product id to add to the cart
                String quantity=""; //the quantity of the product to add to the cart
                method="GET"; //the method to use for the response
                
                if(cookie.equals("")){ //If the user is not logged in
                    //NEED TO BE LOGGED IN: 
                    path="/products.html?showAlert=true"; //redirect the user to products.html with an alert
                    parser.redirect(path);
                }
                else{ //If the user is logged in
                    //Get the values from the request
                    for (int x=0;x<parser.values.size();x++) {
                        if (parser.values.get(x)==null){break;} //If there are no more values, break
                        else if (parser.values.get(x).equals("productId")) { //If the value is the product id
                            x++; //The next value is the product id value
                            productId = parser.values.get(x); //Save the product id
                        }
                        else if(parser.values.get(x).equals("quantity")){ //If the value is the quantity
                            x++; //The next value is the quantity value
                            quantity=parser.values.get(x); //Save the quantity
                        }
                    }
                }
                filename="/products.html"; //redirect the user to products.html
                cart=cart.addItem(productId, quantity); //prepare the response
                //System.out.println("Add to cart attempt "+rez+" from " + username +" "+ getSourceInfo(socket) + " with productId " + productId+" and quantity "+quantity);
            }      
            //THE REQUEST RECIEVED ORIGINATES FROM cart.html - IF CHECKOUT BUTTON IS PRESSED
            else if(method.equals("POST")&&path.equals("/checkout")){  
                CartBuilder cart = new CartBuilder(cookie); //Create a new cart builder

                String res; 
                result=cart.checkout(); //Checkout the cart
                if(result){ //If the cart was checked out successfully
                    res="success"; 
                    path="/address.html"; //redirect the user to address.html for address input to process the order
                    filename="/address.html"; 
                    mimeType = "text/html";
                    
                    body=parser.readImage(filename); //prepare the response
                    parser.redirect(path); //send the redirect
                }
                else{ //If the cart was not checked out successfully
                    res="failure"; 
                    filename="/cart.html";
                    path="/cart.html?showAlert=true"; //redirect the user to cart.html with an alert
                    parser.redirect(path);
                }
                //Log the checkout attempt
                logger.logTransaction(getSourceInfo(socket), "checkout", "attempt "+res+" for cart "+cart.getId());
                //System.out.println("Checkout attempt "+res+" from " + getSourceInfo(socket));
            } 
                
            //USER PRESSES LOGOUT BUTTON FOLLOWING LOGIN
            else if(path.equals("/logout")){
                //System.out.println("Logout from " + getSourceInfo(socket) + " with username " + username);
                parser.setCookie(username,0); //set the cookie to expire
                filename="/login.html"; //redirect the user to login.html
                mimeType = "text/html";
                path="/login.html";
                parser.redirect(path); 
                body=parser.readImage(filename); //prepare the response
                //Log the logout attempt
                logger.logAuth(getSourceInfo(socket), "logout", username, "success");
                return;
            } 
        
            //THE REQUEST RECIEVED ORIGINATES FROM address.html - IF THE USER PRESSES THE SUBMIT BUTTON
            else if(method.equals("POST")&&path.equals("/address")){
                String address1=""; //the first line of the address
                String address2=""; //the second line of the address
                String city=""; //the city of the address
                String state=""; //the state of the address
                String zip=""; //the zip code of the address
                String country=""; //the country of the address

                //Determine wheter the user is logged in or not
                if(cookie.equals("")){ //If the user is not logged in
                    //NEED TO BE LOGGED IN: 
                    path="/address.html?showAlert=true"; //redirect the user to address.html with an alert
                    parser.redirect(path);
                }
                else{ //If the user is logged in
                    //Get the values from the request
                    for (int x=0;x<parser.values.size();x++) {
                        if (parser.values.get(x)==null){break;} //If there are no more values, break
                        else if (parser.values.get(x).equals("street-address-1")) { //If the value is the first line of the address
                            x++; //The next value is the first line of the address value
                            address1 = parser.values.get(x); //Save the first line of the address
                        }
                        else if (parser.values.get(x).equals("street-address-2")) { //If the value is the second line of the address
                            x++; //The next value is the second line of the address value
                            address2 = parser.values.get(x); //Save the second line of the address
                        }
                        else if(parser.values.get(x).equals("city")){ //If the value is the city
                            x++; //The next value is the city value
                            city=parser.values.get(x); //Save the city
                        }
                        else if(parser.values.get(x).equals("state")){ //If the value is the state
                            x++; //The next value is the state value
                            state=parser.values.get(x); //Save the state
                        }
                        else if(parser.values.get(x).equals("zipcode")){ //If the value is the zip code
                            x++; //The next value is the zip code value
                            zip=parser.values.get(x); //Save the zip code
                        }
                        else if(parser.values.get(x).equals("country")){ //If the value is the country
                            x++; //The next value is the country value
                            country=parser.values.get(x); //Save the country
                        }
                    }
                    //System.out.println("Address attempt from " + username +" "+ getSourceInfo(socket) + " with address " + address1+" "+address2+" "+city+" "+state+" "+zip+" "+country);
                    //Save the address
                    AddressRepository addressRepo = new AddressRepository(); //Create a new address repository
                    addressRepo.saveAddress(new Address(username,address1,address2,city,state,zip,country)); //Save the address
                    parser.setCookie(username,31536000); //set the cookie for the user (never expires) to ensure that it is sent

                    path="http://"+socket.getLocalAddress().toString()+":8081/payments.html"; //redirect the user to the payment server
                    System.out.println("(Client Handler) Redirected user "+ username +" to: "+path);
                    parser.redirect(path);
                    //Log the checkout attempt
                    logger.logTransaction(getSourceInfo(socket),"checkout", "attempt success with address ");
                    return;
                }
                //System.out.println("Checkout-Address attempt from " + username +" "+ getSourceInfo(socket));            
            } 
            // THE REQUEST RECIEVED ORIGINATES FROM address.html - IF THE USER PRESSES THE SUBMIT BUTTON
            else if(method.equals("POST")&&path.equals("/payment")){
                //System.out.println("Payment attempt from " + username +" "+ getSourceInfo(socket));
                //Redirect the user to the payment server
                path="http://"+socket.getLocalAddress().toString()+":8080/finished-registration.html";
                
                System.out.println("(Client Handler) Redirected user "+ username +" to: "+path);
                
                //Log the payment attempt
                logger.logTransaction(getSourceInfo(socket), "payment", "redirected to external payment server");
                
                parser.redirect(path); //send the redirect
                return;
            }
            //HTML-BASED FILE PATHS >> 
            //products.html is a special case because it is generated dynamically
            if(path.startsWith("/products.html")){
                ProductCatalog productCatalog = ProductCatalog.getInstance(); //Initialize the product catalog
                Product[] products = productCatalog.getAllProducts(); //Get all the products from the product catalog

 
                StringBuilder html = parser.displayProductCatalog(products); //Display the product catalog
                filename="products.html"; //the filename to use for the response
                mimeType = "text/html"; //the mime type to use for the response
                String productsHtml = parser.replace("products.html","<div id=\"product-list\"></div>", html.toString()); //replace the product list in the html with the generated product list 
                body=productsHtml.getBytes(); //prepare the generated HTML as the response
            }
            //cart.html is also a special case because it is generated dynamically
            else if(path.startsWith("/cart.html")){
                CartBuilder cart = new CartBuilder(cookie); //Create a new cart builder

                Product[] cartProducts = cart.displayCart(); //Get the products from the cart
                StringBuilder html = parser.displayCart(cartProducts); //get the html for the cart products to display 
                String cartHtml=""; 
                filename="cart.html"; //the filename to use for the response
                mimeType = "text/html"; //the mime type to use for the response
                
                cartHtml = parser.readFile(filename); //read the cart.html file
                String finalHTML = cartHtml.replace("<div id=\"cart-list\"></div>", html.toString()); //replace the cart list in the html with the generated cart list
                
                body=finalHTML.getBytes(); //prepare the generated HTML as the response
            }

            //PROCESS THE OTHER FILE REQUESTS TO THE SERVER
            if(filename.equals("garbage")){
                int isQuery = path.indexOf("?"); //check if there is a query in the path
                if (path.contains(".html")) {                                                //If the path is an html file
                    if(isQuery!=-1){filename = path.substring(1,isQuery);}          //If there is a query, get the filename from the path
                    else{filename = path.substring(1);}                             //If there is no query, get the filename from the path
                    mimeType = "text/html";                                                    //the mime type to use for the response
                } 
                else if (path.endsWith(".css")) {                                       //If the path is a css file
                    filename = path.substring(1);                                   //get the filename from the path
                    mimeType = "text/css";                                                     //the mime type to use for the response
                } 
                else if (path.endsWith(".js")) {                                        //If the path is a js file
                    filename = path.substring(1);                                   //get the filename from the path
                    mimeType = "application/javascript";                                       //the mime type to use for the response
                } 
                else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {      //If the path is a jpg or jpeg file
                    filename = path.substring(1);                                   //get the filename from the path
                    mimeType = "image/jpeg";                                                   //the mime type to use for the response
                } 
                else if (path.endsWith(".png")){                                        //If the path is a png file
                    filename = path.substring(1);                                   //get the filename from the path
                    mimeType = "image/png";                                                    //the mime type to use for the response
                }
                else if (path.endsWith(".ico")){                                        //If the path is an icon file
                    filename = path.substring(1);                                   //get the filename from the path
                    mimeType = "image/x-icon";                                                 //the mime type to use for the response
                }
                else if (path.endsWith(".gif")){                                        //If the path is a gif file
                    filename = path.substring(1);                                   //get the filename from the path
                    mimeType = "image/gif";                                                    //the mime type to use for the response
                }
                else if(path.equals("/site.webmanifest")){                             //If the path is the site manifest
                    filename="site.webmanifest";                                                //get the filename from the path
                    mimeType = "application/manifest+json";                                     //the mime type to use for the response
                }else {
                    filename="index.html";                                                      //get the filename from the path
                    mimeType = "text/html";                                                     //the mime type to use for the response
                }
                body = parser.readImage(filename);
            }
            
            parser.sendResponse("200 OK", mimeType, body); //send the response
    }
        finally{ //Close the socket
            try{
                socket.close();
            }
            catch(IOException e){ //If there is an error, log it
                logger.logError("Banana Web Server","Error closing socket: " + e.getMessage());
            }
        }
    }
}




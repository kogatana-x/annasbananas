//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: PaymentWorker.java
 */
//-------------------------------------------------------



//IMPORTS
import java.io.IOException; //for input/output exceptions
import java.net.Socket; //for sockets

/* Name: PaymentWorker
 * Description: The class for handling payment requests.
 * Relations: has a Database, Logger, and HTMLParser. Extends thread
 */
public class PaymentWorker extends Thread {
    //GLOBAL VARIABLES
    private Socket socket; //the socket to read from
    Database database = new Database("payments.txt"); //the database to use

    /* Name: PaymentWorker
     * Description: The constructor for the PaymentWorker class.
     * Parameters: socket - the socket to read from
     * Returns: none
     */
    public PaymentWorker(Socket socket) {
        this.socket = socket;
    }

    /* Name: run
     * Description: The method for running the thread.
     *              Reads the request from the socket.
     *              Parses the request.
     *              Gets the payment information from the request.
     *              Authenticates the payment.
     *              Logs the transaction.
     *              Redirects the client to the finished registration page.
     * Parameters: none
     */
    public void run() {
        Logger logger = Logger.getInstance(); //Initialize the logger

        try{ //Try to read the request
            HTMLParser parser = new HTMLParser("payment-html/",socket); //Initialize the parser
            parser.parseRawStrings(); //Parse the request
            String method=parser.getMethod(); //Get the method from the request
            String path=parser.getPath(); //Get the path from the request
            //System.out.println("(PaymentWorker): "+method+" "+path+" from " + getSourceInfo(socket));
            logger.logAccess(getSourceInfo(socket), method, path); //Log the access
            parser.getValues(); //Get the values from the request
            String cookie=parser.getCookie(); //Get the cookie from the request

            //Initialize the variables for the response
            String filename="garbage"; 
            String mimeType = "text/html";
            byte[] body=null;


            //PROCESS THE PAYMENT REQUEST
            //If the request is a payment request (submitted from payments.html form)
            if(method.equals("POST")&&path.equals("/payments")){ 
                String cardNumber = ""; //Initialize the variables for the card information
                String cardName = ""; //Initialize the variables for the card information
                String cardExpiry = ""; //Initialize the variables for the card information
                String cardCVC = ""; //Initialize the variables for the card information
                String cardZip = ""; //Initialize the variables for the card information

                //Get the card information from the request
                for (int x=0;x<parser.values.size();x++) {
                    if (parser.values.get(x)==null){break;} //If there are no more values, break
                    else if (parser.values.get(x).trim().equals("cardName")) { //If the value is cardName
                        x++; //Then the cardname value is in the next field
                        cardName = parser.values.get(x); //Get the cardName
                    } 
                    else if (parser.values.get(x).equals("cardNumber")) { //If the value is cardNumber
                        x++; //Then the cardNumber value is in the next field
                        cardNumber = parser.values.get(x); //Get the cardNumber
                    } 
                    else if (parser.values.get(x).equals("cardExpiry")) { //If the value is cardExpiry
                        x++; //Then the cardExpiry value is in the next field
                        cardExpiry =parser.values.get(x); //Get the cardExpiry
                    } 
                    else if (parser.values.get(x).equals("cardCVC")) { //If the value is cardCVC
                        x++; //Then the cardCVC value is in the next field
                        cardCVC = parser.values.get(x); //Get the cardCVC
                    } 
                    else if (parser.values.get(x).equals("cardZip")) { //  If the value is cardZip
                        x++; //Then the cardZip value is in the next field
                        cardZip = parser.values.get(x); //Get the cardZip
                    }
                }
                //System.out.println("Payment attempt from " + getSourceInfo(socket) + " with card number " + cardNumber);
                
                //Save the payment information to the database / "authenticate it"
                PaymentAuthenticator authenticator = new PaymentAuthenticator(new PaymentRepository()); //Initialize the payment authenticator
                authenticator.pay(cookie, cardName, cardNumber, cardExpiry, cardCVC, cardZip); //Authenticate the payment
                
                //Log the transaction
                logger.logTransaction(getSourceInfo(socket), "payment", " for user "+cookie+" with card number ending in " + cardNumber.substring(cardNumber.length() - 4));

                //Redirect the client to the finished registration page
                path="http://"+socket.getLocalAddress().toString()+":8080/finished-registration.html";
                System.out.println("(PaymentWorker) Client redirected to: "+path);
                parser.redirect(path);
            } 
            //PROCESS THE OTHER FILE REQUESTS TO THE SERVER
            else if(filename.equals("garbage")){
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
                }else {                                                                 //If the path is not a file
                    filename="404.html";
                    mimeType = "text/html";
                }
                body = parser.readImage(filename);
                parser.sendResponse("200 OK", mimeType, body);

            }
        }
        finally{ //Close the socket
            try{
                socket.close();
            }
            catch(IOException e){ //If there is an error, log it
                logger.logError("Payment Server","Error closing socket: " + e.getMessage());
            }
        }
    }

    /* Name: getSourceInfo
     * Description: Gets the source information from the socket.
     * Parameters: socket - the socket to get the source information from
     * Returns: the source information (client ip and port connected from)
     */
    private String getSourceInfo(Socket socket){ 
        String ip = socket.getInetAddress().getHostAddress(); //get the ip from the socket
        int port = socket.getPort(); //get the port from the socket
        return ip + ":" + port; //return the source information formated as ip:port
    }
}

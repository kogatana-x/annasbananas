//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: BananaPaymentServer.java
 */
//-------------------------------------------------------

//IMPORTS
import java.io.IOException; //for input/output exceptions
import java.net.ServerSocket; //for server sockets
import java.net.Socket; //for sockets
import java.util.concurrent.ExecutorService; //for thread pools
import java.util.concurrent.Executors; //for thread pools


/* Name: BananaPaymentServer
 * Description: The server class for running the site. This server is used for payment processing. 
 *              Listens on TCP port 8081
 * Parameters: none
 * Relationships: implements Runnable. Has a Logger
 */
public class BananaPaymentServer implements Runnable{
    //GLOBAL VARIABLES
    private static final int PORT = 8081; //the port to listen on

    /* Name: run
     * Description: The main method for running the server.
     *              Starts a new Payment Worker thread for each client that connects
     * Parameters: none
     * Returns: none
     */
    public void run() {
        Logger logger = Logger.getInstance(); //Initialize the logger
        ExecutorService executorService = Executors.newFixedThreadPool(10); //Initialize the thread pool

        //Start the server on the default port
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Payment Server is listening on port " + PORT);

            //Listen for new connections on repeat
            while (true) {
                Socket socket = serverSocket.accept(); //Accept the connection
                //System.out.println("New client connected"); 
                executorService.execute(new PaymentWorker(socket)); //Start a new thread to handle the connection
                
            }
        } catch (IOException ex) { //If an exception is thrown, log it
            logger.logError("Banana Payment Server","Server Exception Thrown: " + ex.getMessage());
        }
    }
}


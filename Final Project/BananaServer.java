//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: BananaServer.java
 */
//-------------------------------------------------------


//IMPORTS
import java.io.IOException; //for input/output exceptions
import java.net.ServerSocket; //for server sockets
import java.net.Socket; //for sockets
import java.util.concurrent.ExecutorService; //for thread pools
import java.util.concurrent.Executors; //for thread pools


/* Name: BananaServer
 * Description: The main class for running the site. This server is the eCommerce site.
 *              Listens on TCP port 8080
 * Parameters: none
 * Relationships: has a Logger
 */
public class BananaServer {
    //GLOBAL VARIABLES
    private static final int PORT = 8080; //the default port to listen on

    /* Name: main
     * Description: The main method for running the server.
     *              Starts the payment server on a new thread.
     *              Starts a new Client Handler thread for each client that connects.
     * Parameters: args - the command line arguments. None are processed
     * Returns: none
     */
    public static void main(String[] args) {
        Logger logger= Logger.getInstance(); //Initialize the logger
        ExecutorService executorService = Executors.newFixedThreadPool(10); //Initialize the thread pool

        //Start the server on the default port
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            //Starts the payment server
            BananaPaymentServer BananaPaymentServer = new BananaPaymentServer();
            Thread Thread = new Thread(BananaPaymentServer); //Make the payment server thread
            Thread.start(); //Start the payment server thread

            //Listen for new client connections on repeat
            while (true) {
                Socket socket = serverSocket.accept(); //Accept the connection
                //System.out.println("New client connected");
                executorService.execute(new ClientHandler(socket)); //Start a new thread to handle the connection
            }
        } catch (IOException ex) { //If there is an error, log it
            logger.logError("Banana Web Server","Server Exception Thrown: " + ex.getMessage());
        }
    }
}


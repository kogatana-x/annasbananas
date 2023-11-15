import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/* Name: BananaPaymentServer
 * Description: The server class for running the site
 * Parameters: none
 * Relationships: none
 */

public class BananaPaymentServer implements Runnable{
    private static final int PORT = 8081;

    public void run() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Payment Server is listening on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                executorService.execute(new PaymentWorker(socket));
                
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}


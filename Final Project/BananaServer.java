import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/* Name: BananaServer
 * Description: The main class for running the site
 * Parameters: none
 * Relationships: none
 */

public class BananaServer {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                //System.out.println("New client connected");

                executorService.execute(new ClientHandler(socket));
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }
    private String getSourceInfo(Socket socket){
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
    public void run() {
        try {
            // Read the first line of the HTTP request
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = reader.readLine();
            if (line == null) {
                return;
            }

            // Parse the requested path from the first line
            String[] parts = line.split(" ");
            if (parts.length < 2) {
                return;
            }
            String path = parts[1];

            // Map the requested path to a file
            String filename;
            String mimeType;
            if (path.equals("/products.html")) {
                filename = "products.html";
                mimeType = "text/html";
            } else if (path.equals("/cart.html")) {
                filename = "cart.html";
                mimeType = "text/html";
            } else if (path.equals("/login.html")) {
                filename = "login.html";
                mimeType = "text/html";
            } else if (path.equals("/about.html")) {
                filename = "about.html";
                mimeType = "text/html";
            } else if (path.equals("/register.html")) {
                filename = "register.html";
                mimeType = "text/html";
           } else if (path.equals("/payments.html")) {
                filename = "payments.html";
                mimeType = "text/html";
            } else if (path.endsWith(".css")) {
                filename = path.substring(1);  // remove the leading '/'
                mimeType = "text/css";
            } else if (path.endsWith(".js")) {
                filename = path.substring(1);  // remove the leading '/'
                mimeType = "application/javascript";
            } else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
                filename = path.substring(1);  // remove the leading '/'
                mimeType = "image/jpeg";
            }else {
                filename = "index.html";
                mimeType = "text/html";
            }

            boolean isBinary = mimeType.startsWith("image/");

            // Read the file into a byte array
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
            
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
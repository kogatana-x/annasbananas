import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
    public void run() {
        try {
            // Read the first line of the HTTP request
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = reader.readLine();
            if (line == null) {return;}

            // Parse the requested path from the first line
            String[] parts = line.split(" ");
            if (parts.length < 2) {
                return;
            }
            String method = parts[0];
            String path = parts[1];
            String filename="garbage";
            String mimeType = "text/html";

            // If the method is POST and the path is "/login", parse the request body
            if (method.equals("POST") && path.equals("/login")) {
                String username = null;
                String password = null;

                // Read the headers and the blank line
                while (!(line = reader.readLine()).equals("")) {}

                // Read the request body
                StringBuilder requestBody = new StringBuilder();
                while (reader.ready()) {
                    requestBody.append((char) reader.read());
                }
                String[] pairs = requestBody.toString().split("&");
                for (String pair : pairs) {
                    parts = pair.split("=");
                    if (parts.length == 2) {
                        if (parts[0].equals("username")) {
                            username = URLDecoder.decode(parts[1], StandardCharsets.UTF_8.name());
                        } else if (parts[0].equals("password")) {
                            password = URLDecoder.decode(parts[1], StandardCharsets.UTF_8.name());
                        }
                    }
                }
                System.out.println("Login attempt from " + getSourceInfo(socket) + " with username " + username);
                // Save the username and password
                // TODO: Implement this

                return;
            }
            else if(method.equals("POST")&&path.equals("/register")){
                String username = null;
                String password = null;
                String password2 = null;

                // Read the headers and the blank line
                while (!(line = reader.readLine()).equals("")) {}

                // Read the request body
                StringBuilder requestBody = new StringBuilder();
                while (reader.ready()) {
                    requestBody.append((char) reader.read());
                }
                String[] pairs = requestBody.toString().split("&");
                for (String pair : pairs) {
                    parts = pair.split("=");
                    if (parts.length == 2) {
                        if (parts[0].equals("username")) {
                            username = URLDecoder.decode(parts[1], StandardCharsets.UTF_8.name());
                        } else if (parts[0].equals("password")) {
                            password = URLDecoder.decode(parts[1], StandardCharsets.UTF_8.name());
                        }
                    }
                System.out.println("Register attempt from " + getSourceInfo(socket) + " with username " + username);
                // Save the username and password
                filename="finished-registration.html";

            }
        }
            else if (method.equals("POST") && path.equals("/payments")){
                String cardNumber = null;
                String cardName = null;
                String cardExpiry = null;
                String cardCVC = null;
                String cardZip = null;

                // Read the headers and the blank line
                while (!(line = reader.readLine()).equals("")) {}

                // Read the request body
                StringBuilder requestBody = new StringBuilder();
                while (reader.ready()) {
                    requestBody.append((char) reader.read());
                }
                String[] pairs = requestBody.toString().split("&");
                for (String pair : pairs) {
                    parts = pair.split("=");
                    if (parts.length == 2) {
                        if (parts[0].equals("cardNumber")) {
                            cardNumber = URLDecoder.decode(parts[1], StandardCharsets.UTF_8.name());
                        } else if (parts[0].equals("cardName")) {
                            cardName = URLDecoder.decode(parts[1], StandardCharsets.UTF_8.name());
                        } else if (parts[0].equals("cardExpiry")) {
                            cardExpiry = URLDecoder.decode(parts[1], StandardCharsets.UTF_8.name());
                        } else if (parts[0].equals("cardCVC")) {
                            cardCVC = URLDecoder.decode(parts[1], StandardCharsets.UTF_8.name());
                        } else if (parts[0].equals("cardZip")) {
                            cardZip = URLDecoder.decode(parts[1], StandardCharsets.UTF_8.name());
                        }
                    }
                }
                System.out.println("Payment attempt from " + getSourceInfo(socket) + " with card number " + cardNumber);
                // Save the payment into into the db
                filename="finished-registration.html";
            }
            
            // Map the requested path to a file

            if(filename.equals("garbage")){
                if (path.endsWith(".html")) {
                    filename = path.substring(1);  // remove the leading '/'    
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
                } else {
                    filename="index.html";
                    mimeType = "text/html";
                }
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
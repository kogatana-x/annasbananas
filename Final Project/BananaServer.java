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
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/* Name: BananaServer
 * Description: The main class for running the site
 * Parameters: none
 * Relationships: none
 */

public class BananaServer {
    private static final int PORT = 8080;
    public HashMap<String,User> Sessions = new HashMap<String, User>(); //TODO definitley a security risk but i give no fucks right now

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
    public void run(){

        HTMLParser parser = new HTMLParser(socket);
        String[] values=parser.getValues();
        String method=parser.getMethod();
        String path=parser.getPath();

        String filename="garbage";
        String mimeType = "text/html";

        User user;
        boolean result=false;

        if(method.equals("POST")&&path.equals("/login")){
            int count=0;
            String username,password;
            for (String value : values) {
                if (value.equals("username")) {
                    username = URLDecoder.decode(values[count+1], StandardCharsets.UTF_8.name());
                } else if (value.equals("password")) {
                    password = URLDecoder.decode(values[count+1], StandardCharsets.UTF_8.name());
                }
                count++;
           }
           UserAuthenticator authenticator = new UserAuthenticator(new UserRepository());
           result=authenticator.login(username, password);
           System.out.println("Login attempt "+result+" from " + getSourceInfo(socket) + " with username " + username);

        }
                // Authenticate the user
                filename="index.html";
                UserAuthenticator authenticator = new UserAuthenticator(new UserRepository());
                byte[] salt = authenticator.generateSalt();
                User user = authenticator.userRepository.getUser(username);
                boolean result=false;
                if(user!=null){result=authenticator.login(username, password); }
                //TODO otherwise say bad username or password
                //set session token

        } else if(method.equals("POST")&&path.equals("/register")){

        } else if(method.equals("POST")&&path.equals("/payments")){

        } else if(path.startsWith("/search")){

        }


    }
  /*  
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
                // Authenticate the user
                filename="index.html";
                UserAuthenticator authenticator = new UserAuthenticator(new UserRepository());
                byte[] salt = authenticator.generateSalt();
                User user = authenticator.userRepository.getUser(username);
                boolean result=false;
                if(user!=null){result=authenticator.login(username, password); }
                //TODO otherwise say bad username or password
                //set session token
            }
            else if(method.equals("POST")&&path.equals("/register")){
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
                System.out.println("Registering user "+username+ " from "+getSourceInfo(socket));
                // Save the username and password
                filename="finished-registration.html";
                UserAuthenticator authenticator = new UserAuthenticator(new UserRepository());
                byte[] salt = authenticator.generateSalt();
                String hash=authenticator.hashPassword(password, salt).toString();
                User user = new User(0,username, hash, salt.toString());
                authenticator.userRepository.saveUser(user); 
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
            
            // If the path starts with "/search", parse the query parameters
            else if (path.startsWith("/search")) {
                String query = null;

                // Check if there are query parameters
                int questionMarkIndex = path.indexOf("?");
                if (questionMarkIndex != -1) {
                    // Get the query parameters
                    String queryParams = path.substring(questionMarkIndex + 1);

                    // Split the parameters by "&"
                    String[] pairs = queryParams.split("&");
                    for (String pair : pairs) {
                        // Split the name and value by "="
                        String[] stuff = pair.split("=");
                        if (stuff.length == 2) {
                            // Check if the name is "query"
                            if (stuff[0].equals("query")) {
                                query = URLDecoder.decode(stuff[1], StandardCharsets.UTF_8.name());
                            }
                        }
                    System.out.println(query);
                    }
                }
            }

            // Map the requested path to a file
            if(filename.equals("garbage")){
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
    }*/
}




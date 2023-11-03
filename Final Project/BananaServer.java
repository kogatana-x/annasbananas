import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class User {
    private String username;
    private String password;
    private Cart cart;
    // getters and setters
    public byte[] getHash() {
        return null;
    }
    public byte[] getSalt() {
        return null;
    }
    public String getUsername() {
        return null;
    }
    public void setId(String string) {
    }
    public void setUsername(String string) {
    }
    public void setPassword(byte[] bytes) {
    }
    public void setSalt(byte[] bytes) {
    }
}

class Product {
    private String id;
    private String name;
    private String description;
    private double price;
    // getters and setters
}

class Cart {
    private List<Product> products;
    // methods to add and remove products
}

/* Name: UserAuthenticator
 * Description: Authenticates users
 * Parameters: userRepository - the repository to use to retrieve users
 * Relationships: Uses the UserRepository class
 */
class UserAuthenticator {
    private UserRepository userRepository;

    public UserAuthenticator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* Name: hashPassword
     * Description: Hashes a password with a salt
     * Parameters: password - the password to hash
     *             salt - the salt to use
     * Returns: the hashed password
     */
    private byte[] hashPassword(String password, byte[] salt) { //TODO client should do this lol
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(salt);
        return md.digest(password.getBytes());
    }

    /* Name: generateSalt
     * Description: Generates a random salt
     * Parameters: none
     * Returns: the salt
     */
    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;    
    }

    /* Name: login
     * Description: authenticates a user in by comparing the hashed password with the one stored in the database
     * Parameters: username - the username of the user to log in
     * Returns: true if the user was logged in successfully, false otherwise
     */
    public boolean login(String username, String hash) throws SQLException {
        User user = userRepository.getUser(username);
        if (user == null) {
            return false;
        }
        byte[] saltedHash = hashPassword(hash, user.getSalt());
        return Arrays.equals(saltedHash, user.getHash());
    }
}

/* Name: ProductCatalog
 * Description: Manages the products in the catalog
 * Parameters: productRepository - the repository to use to retrieve products
 * Relationships: Uses the ProductRepository class
 */
class ProductCatalog {
    private ProductRepository productRepository;
    // methods to add, remove, and retrieve products
}

/* Name: UserRepository
 * Description: Manages the users in the database
 * Parameters: connection - the connection to the database
 * Relationships: Uses the User class
 */
class UserRepository {
    private Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    /* Name: saveUser
     * Description: Saves a user to the database
     * Parameters: user - the user to save
     * Returns: none
     */
    public void saveUser(User user) throws SQLException {
        String sql = "INSERT INTO User (username, hashed_password, salt) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setBytes(2, user.getHash());
            statement.setBytes(3, user.getSalt());
            statement.executeUpdate();
        }
    }

    /* Name: getUser
     * Description: Retrieves a user from the database
     * Parameters: username - the username of the user to retrieve
     * Returns: the user, or null if no user with that username exists
     */
    public User getUser(String username) throws SQLException {
        String sql = "SELECT * FROM User WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getBytes("hashed_password"));
                user.setSalt(rs.getBytes("salt"));
                return user;
            } else {
                return null;
            }
        }
    }
}

/* Name: ProductRepository
 * Description: Manages the products in the database
 * Parameters: connection - the connection to the database
 * Relationships: Uses the Product class
 */
class ProductRepository {
    // implementation of ProductRepository
    // methods to add, remove, and retrieve products
}

/* Name: OrderProcessor
 * Description: Processes orders
 * Parameters: cart - the cart to process
 *             paymentProcessor - the payment processor to use to process payments
 * Relationships: Uses the Cart and PaymentProcessor classes
 */
class OrderProcessor {
    private Cart cart;
    private PaymentProcessor paymentProcessor;
    // method to place orders
}

/* Name: PaymentProcessor
 * Description: Processes payments
 * Parameters: none
 * Relationships: none
 */
class PaymentProcessor {
    // method to process payments
}

/* Name: Logger
 * Description: Logs events and transactions
 * Parameters: none
 * Relationships: none
 */
class Logger {
    private static Logger instance;
    // methods to log events and transactions
    // private constructor and public static getInstance() method to implement the singleton pattern
}


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
                System.out.println("New client connected");

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
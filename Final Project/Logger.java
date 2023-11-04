/* Name: Logger
 * Description: Logs events and transactions
 * Parameters: none
 * Relationships: none
 */
public class Logger {
    private static Logger instance;

    private Logger() {}

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String sourceIP, int sourcePort, String path) {
        System.out.println("Request from " + sourceIP + ":" + sourcePort + " for " + path);
    }
}
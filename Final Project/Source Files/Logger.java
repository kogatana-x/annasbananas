//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: Logger.java
 */
//-------------------------------------------------------

//IMPORTS
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/* Name: Logger
 * Description: Logs events and transactions
 * Parameters: none
 * Relationships: none
 * Design: Singleton
 */
public class Logger {
    //GLOBAL VARIABLES
    private static Logger instance; //The instance of the logger
    private static String directory="logs/"; //The directory to store the logs
    private Database logDatabase; //The database to store the logs

    private Logger(){}

    /* Name: getInstance
     * Description: Gets the instance of the logger
     * Parameters: none
     * Returns: The instance of the logger
     */
    public static synchronized Logger getInstance() { 
        if (instance == null) { //If there is no instance of the logger
            instance = new Logger(); //Create a new instance of the logger
        }
        return instance;
    }

    /* Name: logAccess
     * Description: Logs a resource access event for all request types
     * Parameters:  source - the source of the request
     *              method - the method of the request
     *              path - the path of the request
     * Returns: none
     */
    public void logAccess(String source, String method, String path) {
        logDatabase = new Database(directory+"access.log"); //Initialize the database
        String timestamp = getTimeStamp(); //Get the timestamp
        logDatabase.add(timestamp+" "+method+" "+path+" from " + source); //Add the log to the database
    }

    /* Name: logError
     * Description: Logs an error event for the server
     * Parameters:  source - the source of the error
     *              error - the error message
     * Returns: none
     */
    public void logError(String source, String error) {
        logDatabase = new Database(directory+"error.log"); //Initialize the database
        String timestamp = getTimeStamp(); //Get the timestamp
        logDatabase.add(timestamp+" "+ source + "- caused error: " + error); //Add the log to the database
    }

    /* Name: logAuth
     * Description: Logs an authentication event for the server (user logins, logouts, and registrations)
     * Parameters:  source - the source of the request
     *              type - the type of authentication event
     *              username - the username of the user
     *              result - the result of the authentication event
     * Returns: none
     */
    public void logAuth(String source, String type, String username, String result){
        logDatabase = new Database(directory+"auth.log"); //Initialize the database
        String timestamp = getTimeStamp(); //Get the timestamp
        logDatabase.add(timestamp+" "+type+" with "+username+ " " + result+" from " + source); //Add the log to the database
    }

    /* Name: logTransaction
     * Description: Logs a transaction event for the server (checkout, payment, order, address updates)
     * Parameters:  source - the source of the request
     *              type - the type of transaction event
     *              transaction - the transaction that occurred
     * Returns: none
     */
    public void logTransaction(String source, String type, String transaction) {
        logDatabase = new Database(directory+"transaction.log"); //Initialize the database
        String timestamp = getTimeStamp(); //Get the timestamp
        logDatabase.add(timestamp+" "+type+" from " + source + " caused transaction: " + transaction); //Add the log to the database
    }

    /* Name: getTimeStamp
     * Description: Gets the current timestamp formated as YYYY/MM/DD HH:MM:SS
     * Parameters: none
     * Returns: The current timestamp
     */
    private String getTimeStamp() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String timestamp = dtf.format(now);
        return timestamp;
    }
}
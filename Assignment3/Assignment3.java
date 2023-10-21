/* Name: Anna Andler
 * Course: SE450 - Object Oriented Programming
 * Date: 10/21/2023
 * File: Assignment3.java
 * Contains Class(es): OracleDatabase, SqlServerDatabase, DatabaseConnectionFactory, Assignment3
 * Contains Interface(s): Database
 */

package Assignment3;

/* Name: Database
 * Description: Interface for Database
 * Methods: connect()
 */
interface Database {
    /* Name: connect
     * Description: Connects to the database
     * Parameters: None
     * Return: None
     */
    void connect();
}

/* Name: OracleDatabase
 * Description: Implements Database for Oracle-specific database
 * Relation: Implements Database
 */
class OracleDatabase implements Database {
    //Global Variables
    private int port=1521; //Default port for Oracle Database

    //Constructor - Use Port Passed to Constructor
    OracleDatabase(int port) {
        this.port = port; //Set port to the port passed to the constructor
    }
    //Default Constructor - Use Default Port
    OracleDatabase(){}

    /* Name: connect
     * Description: Connects to the Oracle database
     * Parameters: None
     * Return: None
     */
    @Override
    public void connect() {
        System.out.println("Connecting to Oracle database on port "+port+"...");
    }
}

/* Name: MSSqlDatabase
 * Description: Implements Database for SQL Server-specific database
 * Relation: Implements Database
 */
class MSSqlDatabase implements Database {
    //Global Variables
    private int port=1433; //Default port for SQL Server Database

    //Constructor - Use Port Passed to Constructor
    MSSqlDatabase(int port) {
        this.port = port; //Set port to the port passed to the constructor
    }
    //Default Constructor - Use Default Port
    MSSqlDatabase(){}

    /* Name: connect
     * Description: Connects to the SQL Server database
     * Parameters: None
     * Return: None
     */
    @Override
    public void connect() {
        System.out.println("Connecting to SQL Server database on TCP port "+port+"...");
    }
}

/* Name: SqlDatabase 
 * Description: Implements Database for SQL-specific database
 * Relation: Implements Database
 */
class SqlDatabase implements Database{
    //Global Variables
    private int port=3306; //Default port for SQL Database

    //Constructor - Use Port Passed to Constructor
    SqlDatabase(int port) {
        this.port = port; //Set port to the port passed to the constructor
    }
    //Default Constructor - Use Default Port
    SqlDatabase(){}

    /* Name: connect
     * Description: Connects to the SQL database
     * Parameters: None
     * Return: None
     */
    @Override
    public void connect() {
        System.out.println("Connecting to SQL database on port "+port+"...");
    }
}

/* Name: DatabaseFactory
 * Description: Factory Database interface for creating a new database
 * Relation: None
 */
interface DatabaseFactory {
    /* Name: createDatabase
     * Description: Returns database object
     * Parameters: integer contianing the Database port, or nothing for default port
     * Return: Database
     */
    Database createDatabase(int port);
    Database createDatabase();
}

/* Name: OracleDatabaseFactory
 * Description: Implements DatabaseFactory for Oracle-specific database
 * Relation: Implements DatabaseFactory
 */
class OracleDatabaseFactory implements DatabaseFactory {
    /* Name: createDatabase
     * Description: Returns OracleDatabase object
     * Parameters: integer contianing the Database port, or nothing for default port
     * Return: Database
     */
    @Override
    public Database createDatabase(int port) {
        return new OracleDatabase(port);
    }
    @Override
    public Database createDatabase() {
        return new OracleDatabase();
    }
}

/* Name: MsSqlDatabaseFactory
 * Description: Implements DatabaseFactory for MS SQL Server-specific database
 * Relation: Implements DatabaseFactory
 */
class MsSqlDatabaseFactory implements DatabaseFactory {
    /* Name: createDatabase
     * Description: Returns SqlServerDatabase object
     * Parameters: integer contianing the Database port, or nothing for default port
     * Return: Database
     */
    @Override
    public Database createDatabase(int port) {
        return new MSSqlDatabase(port);
    }
    @Override
    public Database createDatabase() {
        return new MSSqlDatabase();
    }
}

/* Name: SqlDatabaseFactory
 * Description: Implements DatabaseFactory for SQL-specific database
 * Relation: Implements DatabaseFactory
 */
class SqlDatabaseFactory implements DatabaseFactory {
    /* Name: createDatabase
     * Description: Returns SqlServerDatabase object
     * Parameters: integer contianing the Database port, or nothing for default  port
     * Return: Database
     */
    @Override
    public Database createDatabase(int port) {
        return new SqlDatabase(port);
    }
    @Override
    public Database createDatabase() {
        return new SqlDatabase();
    }
}

/* Name: Assignment3
 * Description: Main class for Assignment 3
 */
public class Assignment3 {
    public static void main(String[] args) {
        //Connect to an Oracle Database
        DatabaseFactory oracleFactory = new OracleDatabaseFactory();
        Database oracleDatabase = oracleFactory.createDatabase();

        //Connect to an SQL Server Database
        DatabaseFactory msSqlFactory = new MsSqlDatabaseFactory();
        Database msSqlDatabase = msSqlFactory.createDatabase();

        //Connect to an SQL Database
        DatabaseFactory sqlFactory = new SqlDatabaseFactory();
        Database sqlDatabase = sqlFactory.createDatabase();
        
    }
}
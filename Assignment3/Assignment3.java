/* Name: Anna Andler
 * Course: SE450 - Object Oriented Programming
 * Date: 10/21/2023
 * File: Assignment3.java
 * Contains Class(es): OracleDatabase, SqlServerDatabase, DatabaseConnectionFactory, Assignment3
 * Contains Interface(s): Database
 */

package edu.depaul;

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
class DatabaseFactory {
    public static Database createDatabase(String type, int port){
        if(type.equalsIgnoreCase("Oracle")){
            return new OracleDatabase(port);
        }
        else if(type.equalsIgnoreCase("MSSqlServer")){
            return new MSSqlDatabase(port);
        }
        else if(type.equalsIgnoreCase("Sql")){
            return new SqlDatabase(port);
        }
        else{
            return null;
        }
    }
}

/* Name: Assignment3
 * Description: Main class for Assignment 3
 */
public class Assignment3 {
    public static void main(String[] args) {
        //Connect to an Oracle Database
        Database oracleDatabase = DatabaseFactory.createDatabase("Oracle", 1521);
        oracleDatabase.connect();

        //Connect to an SQL Server Database
        Database msSqlDatabase = DatabaseFactory.createDatabase("MSSqlServer", 1433);
        msSqlDatabase.connect();

        //Connect to an SQL Database
        Database sqlDatabase = DatabaseFactory.createDatabase("Sql", 3306);
        sqlDatabase.connect();
    }
}
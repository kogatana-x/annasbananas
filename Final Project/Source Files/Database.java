//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: Database.java
 */
//-------------------------------------------------------


//IMPORTS
import java.io.BufferedReader; //for reading file input from the buffer
import java.io.BufferedWriter; //for writing file output to the buffer
import java.io.File; //for accessing files
import java.io.FileReader; //for reading files
import java.io.FileWriter; //for writing to files
import java.io.IOException; //for input/output exceptions


/* Name: Database
 * Description: A class for handling database interactions.
 *              The database is a text file with comma separated values.
 * Parameters: filepath - the path to the database file
 * Relationships: has a Logger
 */
public class Database {
    //GLOBAL VARIABLES
    private String filepath="database/"; //name of the database file
    private Logger logger=Logger.getInstance(); //Initialize the logger

    /* Name: Database
     * Description: Constructor for the Database class
     * Parameters: filepath - the path to the database file
     */
    public Database(String filepath) {
        this.filepath+=filepath; //Add the filepath to the database name
    }

    /* Name: add
     * Description: Adds a row to the database
     * Parameters: row as a String to add to the database
     * Returns: none
     */
    public void add(String row){
        //Attempt to add the row to the database
        try (FileWriter writer = new FileWriter(filepath, true)) { //Open the database file for writing
            //row=comma(row);
            writer.write(row + "\n"); //Add the row to the database with a newline
        } catch (IOException e) { //If there is an error, log it
            logger.logError("Database","Add() - Error writing to file: "+filepath); 
        }
    }

    /* Name: comma
     * Description: Adds a comma after every space in a string
     * Parameters: row as a String to add commas to
     * Returns: the row with commas added
     */
    /*private String comma(String row){
        //Add a comma after every space in the row
        for(int i=0;i<row.length();i++){
            if(row.charAt(i)==' '){
                row=row.substring(0,i)+","+row.substring(i+1);
            }
        }
        return row;
    } */

    /* Name: drop
     * Description: Drops the database
     * Parameters: none
     * Returns: none
     */
    public void drop(){
        //Attempt to drop the database
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath));         //Open the database file for reading
             FileWriter writer = new FileWriter(filepath, false)) { //Open the database file for writing

            // Read the first line
            String firstLine = reader.readLine();

            // Overwrite the file with the first line
            writer.write(firstLine != null ? firstLine : "");

        } catch (IOException e) { //If there is an error, log it
            logger.logError("Database","Drop() - Error writing to file: "+filepath);
        }
    }

    /* Name: isInDB
     * Description: Checks if a row is in the database
     * Parameters: id - the id of the row to check for
     * Returns: the index of the row if it is in the database, -1 otherwise
     */
    public int isInDB(String id){
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            int count=0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String value=parts[0].trim();
                if (value.equals(id)) {
                    return count;
                }
                count++;
            }
        } catch (IOException e) {
            return -1;
        }
        // No user with that username exists
        return -1;
    }
    
    /* Name: returnResultRow
     * Description: Returns a row from the database given the index and field to match
     * Parameters: index - the index of the field to match
     *             field - the field to match
     * Returns: the row from the database if it is in the database, "error" otherwise
     */
    public String returnResultRow(String index, String field){
        int fieldIndex=Integer.parseInt(index); //Convert the index to an int
        field=field.trim(); //Remove any whitespaces from the field

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) { //Open the database file for reading
            String line;
            while ((line = reader.readLine()) != null) { //Read each line of the database
                String save=line; //Save the line temporarily
                String[] parts = save.split(","); //Split the line into parts
                if(parts.length<=fieldIndex){return "error";} //If the field index is out of bounds, return an error

                String value=parts[fieldIndex].trim(); //Get the value of the field, removing whitespace
                if (value.equals(field)) { //If the value matches the field
                    return save; //Return the row
                }
            }
            reader.close(); //Close the reader

        } catch (IOException e) { //If there is an error, log it
            logger.logError("Database","returnResutlRow() - Error reading file: "+filepath);
            return "error";
        }
        // No user with that username exists
        return "error";
    }

    /* Name: returnFieldValue
     * Description: Returns a field value from a row in the database
     * Parameters: row - the row to get the field value from
     *             returnIndex - the index of the field to return
     * Returns: the field value from the row if it is in the database, "error" otherwise
     */
    public String returnFieldValue(String row, int returnIndex){

        /*if(filepath.equals("database/carts.txt")){
            System.out.println("(Database)In returnFieldValue || Return Field Index: "+returnIndex);
            System.out.println(row);
        } */

        String[] result=row.split(","); //Split the row into parts
        if(result.length<=returnIndex){ //If the return index is out of bounds, return an error
            return "error";
        }

        String value= result[returnIndex].trim(); //Get the value of the field, removing whitespace
        //System.out.println(" || Value: "+value); //DEBUG
        return value;
    }   

    /* Name: getNumberOfRows
     * Description: Returns the number of rows in the database
     * Parameters: none
     * Returns: the number of rows in the database. if an error occurs, returns -1
     */
    public int getNumberOfRows(){
        int count=1;
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) { //Open the database file for reading
            while ((reader.readLine()) != null) { //Read each line of the database
                count++; //Increment the count
            }
        } catch (IOException e) { //If there is an error, log it
            return -1;
        }
        return count;
    }

    /* Name: getAll
     * Description: Returns all rows in the database
     * Parameters: none
     * Returns: an array of all rows in the database
     *          if an error occurs, returns null
     */
    public String[] getAll(){
        String[] result=new String[getNumberOfRows()]; //Create an array to hold the rows
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) { //Open the database file for reading
            String line; 
            int count=0;
            while ((line = reader.readLine()) != null) { //Read each line of the database
                result[count]=line; //Add the line to the array
                count++;
            }
        } catch (IOException e) { //If there is an error, log it
            logger.logError("Database","getAll() - Error reading file: "+filepath);
            return null;
        }
        return result;
    }

    /* Name: update
     * Description: Updates a field in a row in the database
     * Parameters: rowId - the id of the row to update
     *             fieldId - the id of the field to update
     *             newValue - the new value of the field
     * Returns: none
     */
    public void update(String rowId, String fieldId, String newValue){
        int field=Integer.parseInt(fieldId); //Convert the field id to an int
        String result=returnResultRow("0",rowId); //Get the row to update
        String[] parts=result.split(","); //Split the row into parts

        if(parts.length<=field){ //If the field index is out of bounds, return an error and log it
            logger.logError("Database","update() - Index out of bounds: "+fieldId +" for row: "+rowId);
            return;
        }

        //CREATE THE NEW ROW
        parts[field]=newValue.trim(); //Update the field
        String save=""; 
        for(int x=0;x<parts.length-1;x++){ //Rebuild the row
            save=save+parts[x]+","; //Add each part to the row
        }
        save=save+parts[parts.length-1]; //Add the last part to the row

        //REMOVE THE OLD ROW AND ADD THE NEW ROW
        int deleteIndex=isInDB(rowId); //Get the index of the row to delete
        delete(deleteIndex); //Delete the row
        add(save); //Add the updated row
    }

    /* Name: delete
     * Description: Deletes a row from the database
     * Parameters: rowNumber - the index of the row to delete
     * Returns: none
     */
    public void delete(int rowNumber) {
        File inputFile = new File(filepath); //Open the database file
        File tempFile = new File("myTempFile.txt"); //Create a temporary file

        try { //Attempt to delete the row
            BufferedReader reader = new BufferedReader(new FileReader(inputFile)); //Open the database file for reading
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile)); //Open the temporary file for writing

            String currentLine;
            int count = 0;
            while ((currentLine = reader.readLine()) != null) { //Read each line of the database
                // Skip the line if the current line number matches rowNumber
                if (count == rowNumber) { //If the current line is the row to delete, skip it
                    count++; //Increment the count
                    continue; //Skip the line
                }
                writer.write(currentLine + System.getProperty("line.separator")); //Add the line to the temporary file
                count++;
            }
            writer.close(); //Close the writer
            reader.close(); //Close the reader

            // Delete the original file
            if (!inputFile.delete()) { 
                logger.logError("Database","delete() - could not delete line in: "+filepath);
                return;
            }

            // Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inputFile))
                logger.logError("Database","delete() - could not rename file: "+filepath);

        } catch (IOException e) { //If there is an error, log it
            logger.logError("Database","delete() - Error reading file: "+filepath);
        }
    }

    /* Name: print
     * Description: Prints a row from the database. For Debugging purposes
     * Parameters: parts - the row to print
     * Returns: none
     */
     /*private void print(String[] parts){
        System.out.println("(Database) DATABASE DEBUGGER for "+filepath+" :");
        System.out.println("(Database)Row Length="+parts.length);
        //int y=0;
        for(int x=0;x<parts.length;x++){
            System.out.print(parts[x].trim()+" | ");
        //    y++;
        }
        System.out.println();
        //System.out.println("(Database)Counted Row Length="+y);
    } */

}

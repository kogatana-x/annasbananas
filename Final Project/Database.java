import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Database {
    public String filepath="database/"; //name of the database file

    public Database(String filepath) {
        this.filepath+=filepath;
    }

    /* Name: add
     * Description: Adds a row to the database
     * Parameters: row as a String to add to the database
     * Returns: none
     */
    public void add(String row){
        try (FileWriter writer = new FileWriter(filepath, true)) {
            row=comma(row);
            writer.write(row + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Name: comma
     * Description: Adds a comma after every space in a string
     * Parameters: row as a String to add commas to
     * Returns: the row with commas added
     */
    private String comma(String row){
        for(int i=0;i<row.length();i++){
            if(row.charAt(i)==' '){
                row=row.substring(0,i)+","+row.substring(i+1);
            }
        }
        return row;
    }


    /* Name: drop
     * Description: Drops the database
     * Parameters: none
     * Returns: none
     */
    public void drop(){
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath));
             FileWriter writer = new FileWriter(filepath, false)) {

            // Read the first line
            String firstLine = reader.readLine();

            // Overwrite the file with the first line
            writer.write(firstLine != null ? firstLine : "");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isInDB(String username){
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");
                String value=parts[0].trim();
                if (value.equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // No user with that username exists
        return false;
    }
    public String[] returnResult(String username){
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");
                String value=parts[0].trim();
                if (value.equals(username)) {
                    return parts;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // No user with that username exists
        return null;
    }
}

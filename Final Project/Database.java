import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
            e.printStackTrace();
            return -1;
        }

        // No user with that username exists
        return -1;
    }
    public String[] returnResult(String id){
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");
                String value=parts[0].trim();
                if (value.equals(id)) {
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

    public int getNumberOfRows(){
        int count=1;
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        return count;
    }
  /*  public String[] getAll(){
        String[] result=new String[getNumberOfRows()];
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            int count=0;
            while ((line = reader.readLine()) != null) {
                result[count]=line;
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }*/
        
    public boolean update(String row, String field, String newValue){ //TODO
        int fieldIndex=isInDB(field);

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath));
             FileWriter writer = new FileWriter(filepath, false)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String value=parts[0].trim();
                if (value.equals(row.split(",")[fieldIndex].trim())) {
                    line=row;
                }
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean delete(String row){ //TODO
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath));
             FileWriter writer = new FileWriter(filepath, false)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String value=parts[0].trim();
                if (!value.equals(row.split(",")[0].trim())) {
                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

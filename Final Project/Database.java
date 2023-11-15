import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
        //System.out.println("(Database)ADDING TO DATABASE: "+filepath+"\n"+row);
        try (FileWriter writer = new FileWriter(filepath, true)) {
            row=comma(row);
            writer.write(row + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to file");
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
            return -1;
        }
        // No user with that username exists
        return -1;
    }
    
    public String returnResultRow(String index, String field){
        int fieldIndex=Integer.parseInt(index);
        field=field.trim();

        //ArrayList<String> results = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String save=line;
                String[] parts = save.split(",");
                if(parts.length<=fieldIndex){return "error";}

                String value=parts[fieldIndex].trim();
                //if(filepath.equals("database/carts.txt")){System.out.println("Trying to find: "+field+" || Found: "+value);}
                if (value.equals(field)) {
                    return save;
                }
            }
            reader.close();

            //System.out.println("Results Size: "+results.size());
        } catch (IOException e) {
            System.out.println("(Database)Error reading file");
            return "error";
        }
        return "error";
    }

    /*public String returnFieldValue(String row, int returnIndex){
        if(filepath.equals("database/carts.txt")){
            System.out.println("(Database)In returnFieldValue || Return Field Index: "+returnIndex);
            System.out.println(row);
        }
        String[] result=row.split(",");
        if(result.length<=returnIndex){
            return "error";
        }

        String value= result[returnIndex].trim();
        System.out.println(" || Value: "+value);
        return value;
    } */  

    public int getNumberOfRows(){
        int count=1;
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            while ((reader.readLine()) != null) {
                count++;
            }
        } catch (IOException e) {
            return -1;
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
    public void update(String rowId, String fieldId, String newValue){
        //System.out.println("(Database)In Update");
        int field=Integer.parseInt(fieldId);
        String result=returnResultRow("0",rowId);
        String[] parts=result.split(",");
        if(parts.length<=field){
            System.out.println("(Database)Error: Field Index Out of Bounds ("+field+") - "+result);
            return;
        }
        parts[field]=newValue.trim();
        String save="";
        for(int x=0;x<parts.length-1;x++){
            save=save+parts[x]+",";
        }
        save=save+parts[parts.length-1];
        //System.out.println("(Database)Saving: "+save);
        int deleteIndex=isInDB(rowId);
        //System.out.println("(Database)Deleting: INDEX="+deleteIndex);
        delete(deleteIndex);
        add(save);
    }

    public void delete(int rowNumber) {
        File inputFile = new File(filepath);
        File tempFile = new File("myTempFile.txt");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            int count = 0;
            while ((currentLine = reader.readLine()) != null) {
                // Skip the line if the current line number matches rowNumber
                if (count == rowNumber) {
                    count++;
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
                count++;
            }
            writer.close();
            reader.close();

            // Delete the original file
            if (!inputFile.delete()) {
                System.out.println("(Database) Could not delete file");
                return;
            }

            // Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inputFile))
                System.out.println("(Database) Could not rename file");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void print(String[] parts){
        System.out.println("(Database) DATABASE DEBUGGER for "+filepath+" :");
        System.out.println("(Database)Row Length="+parts.length);
        //int y=0;
        for(int x=0;x<parts.length;x++){
            System.out.print(parts[x].trim()+" | ");
        //    y++;
        }
        System.out.println();
        //System.out.println("(Database)Counted Row Length="+y);
    }

}

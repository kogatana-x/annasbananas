import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HTMLParser{
    private Socket socket;
    private String method;
    private String path;
    private BufferedReader reader;
    private String line;
    
    public HTMLParser(Socket socket){
        this.socket=socket;
        try{
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            line = reader.readLine();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        
    }

    //STEP 1 - Extract Raw Request Info
    private String[] parseRaw(){
        String[] parts={""};

        if (line == null) {return null;}

        // Parse the requested path from the first line
        parts = line.split(" ");
        if (parts.length < 2) {
            return null;
        }

        return parts;
    }
    
    //STEP 2 - Extract Method + Request Path
    private void parseRawStrings(){
        String[] parts=parseRaw();
        this.method = parts[0];
        this.path = parts[1];
    }

    //STEP 3 - Extract Request Parameters
    private String[] parseRawParameters(){
        parseRawStrings();

        // Read the headers and the blank line
       try{
            while (!(line = reader.readLine()).equals("")) {}

            // Read the request body
            StringBuilder requestBody = new StringBuilder();
            while (reader.ready()) {
                requestBody.append((char) reader.read());
            }
            String[] pairs = requestBody.toString().split("&");
            return pairs;
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
    
    //STEP 4 - Decode Values
    public String[] getValues(){
        String[] pairs=parseRawParameters();
        String[] parts={""};
        String[] temp={""};
        int x=0;

        for (String pair : pairs) {
            temp = pair.split("="); //TODO "&"
            for(String part:temp){
                try{
                    parts[x]= URLDecoder.decode(part, StandardCharsets.UTF_8.name());
                    x++;
                } catch (IOException ex) {}                
            }
        }
        print(parts); //TODO DEBUG/REMOVE
        return parts;
    }
    public void print(String[] parts){
        for(int x=0;x<parts.length;x++){
           if(x%2==0){System.out.print(parts[x]+"=");} 
           else{System.out.println(parts[x]);}
        }
    }

    public String getMethod(){
        return this.method;
    }
    public String getPath(){
        return this.path;
    }
    
}

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
    private String cookie;
    private OutputStream output;
    private BufferedReader reader;
    private String line;
    public String[] values;
    
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
        String[] parts;

        if (line == null) {
            String[] msg={"error"};
            return msg;
        }
        else if (line.startsWith("Cookie:")) {
            // Extract the cookie value
            parts = line.split(":");
            if (parts.length > 1) {
                String cookies = parts[1].trim();
                for (String cookie : cookies.split(";")) {
                    if (cookie.startsWith("session=")) {
                        this.cookie = cookie.split("=")[1];
                        break;
                    }
                }
            }
        }
        // Parse the requested path from the first line
        else{
            parts = line.split(" ");
            if (parts.length < 2) {
                String[] msg={"error"};
                return msg;
            }
        }

        return parts;
    }
    
    //STEP 2 - Extract Method + Request Path
    public void parseRawStrings(){
        String[] parts=parseRaw();
        if(parts==null){return;}
        if(parts.length>1){
            if(!parts[0].equals("=")){
                this.method = parts[0];
                this.path = parts[1];
            }
        }
        
    }

    //STEP 3 - Extract Request Parameters
    private String[] parseRawParameters(){
        //parseRawStrings();
        
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
            String[] error={"error"};
             return error;
        }
    }
    
    //STEP 4 - Decode Values
    public void getValues(){
        String[] pairs=parseRawParameters();
        if(pairs[0].equals("error")){return;}

        int len = pairs.length*2;

        String[] parts=new String[len*2];
        String[] temp=new String[len];

        int x=0;
        for (String pair : pairs) {
            temp = pair.split("="); 
            for(String part:temp){
                if(!part.equals("=")){
                    try{
                        parts[x]= URLDecoder.decode(part, StandardCharsets.UTF_8.name());
                        x++;
                    } catch (IOException ex) {} 
                }               
            }
        }
        //print(parts);
        this.values=parts;
        
    }
    public void print(String[] parts){
        for(int x=0;x<parts.length;x++){
           if(parts[x]==null){return;}
            else if(x%2==0){System.out.print(parts[x]+"=");} 
           else{System.out.println(parts[x]);}
        }
    }

    public String getMethod(){
        return this.method;
    }
    public String getPath(){
        return this.path;
    }
    
    public void sendResponse(String status, String contentType, String body) {
        try {
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
    
            writer.println("HTTP/1.1 " + status);
            writer.println("Content-Type: " + contentType);
            writer.println("Content-Length: " + body.length());
            writer.println(); // blank line between headers and content
            writer.println(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCookie(){
        return this.cookie;
    }
    public void setCookie(User user){
        // Set a cookie with the session key
        String response = "HTTP/1.1 200 OK\r\n" +
                        "Set-Cookie: session=" + user.getCart() + "\r\n" +
                        "\r\n";
        try{
            output.write(response.getBytes(StandardCharsets.UTF_8));
        }
        catch(IOException ex){
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

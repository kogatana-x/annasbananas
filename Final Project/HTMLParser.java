import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class HTMLParser{
    private Socket socket;
    private String method="";
    private String directory="";
    private String path="";
    private String cookie="";
    private String cookieString="";
    private OutputStream output;
    private BufferedReader reader;
    private String line;
    public boolean setCookie=false;
    public ArrayList<String> values;
    
    public HTMLParser(String directory,Socket socket){
       this.socket=socket;
        this.directory=directory;
        try{
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            line = reader.readLine();
            //output=socket.getOutputStream();

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

            do{ 
                line = reader.readLine();
                if(line==null){return null;}
                // Read the request body
                StringBuilder requestBody = new StringBuilder();
                while (reader.ready()) {
                    requestBody.append((char) reader.read());
                }
                String[] pairs = requestBody.toString().split("&");
                return pairs;
            }while(!line.equals(""));
        } catch (IOException ex) {
            String[] error={"error"};
             return error;
        }
    }
    
    //STEP 4 - Decode Values
    public void getValues(){
        String[] pairs=parseRawParameters();
        if(pairs==null||pairs[0].equals("error")){return;}

        ArrayList<String> parts=new ArrayList<String>();

        for (String pair : pairs) {
            String[] temp2 = pair.split("\n");
            for(String part:temp2){
                if(part.contains("Cookie:")){
                    //System.out.println(part);
                    this.cookie=part.substring(16);
                    this.cookieString="Set-Cookie: session="+this.cookie+"; Max-Age=31536000; Path=/; SameSite=Lax";
                    //System.out.println("COOKIE: "+this.cookie);
                }
            }
            String[] temp = pair.split("="); 
            for(String part:temp){
               // System.out.println(part);
                try{
                   parts.add(URLDecoder.decode(part, StandardCharsets.UTF_8.name()).trim());
                } 
                catch (IOException ex) {} 
                catch (IllegalArgumentException ex) {}
            }               
        }
       // print(parts);
        this.values=parts;    
    }
    
    public void print(ArrayList<String> parts){
        for(int x=0;x<parts.size();x++){
           if(parts.get(x)==null){return;}
            else if(x%2==0){System.out.print(parts.get(x)+"=");} 
           else{System.out.println(parts.get(x));}
        }
    }

    public String getMethod(){
        return this.method;
    }
    public String getPath(){
        return this.path;
    }
    
    public void sendResponse(String status, String contentType, byte[] body) {
        try{
            output = socket.getOutputStream();
            boolean isBinary = contentType.startsWith("image/");
            StringBuilder html = new StringBuilder();
            html.append("HTTP/1.1 " + status+"\r\n");
            html.append("Content-Type: " + contentType+"; charset=UTF-8\r\n");
            html.append("Content-Length: " + body.length+"\r\n");
            if(!this.cookie.equals("")){
                html.append(this.cookieString+"\r\n");
            }
            html.append("\r\n"); // blank line between headers and content
            byte[] headerBytes = html.toString().getBytes();
            output.write(headerBytes);
            if (isBinary) {
                // Write the binary data directly to the output
                output.write(body);
            } else {
                if(!cookie.equals("")){
                    String navbar="<a href=\"login.html\" class=\"login\" style=\"float: right; display: block; color: black; text-align: center; padding: 14px 16px; text-decoration: none; font-size: 17px;\">Login</a>";
                    String newnavbar="<a href=\"logout\" class=\"login\" style=\"float: right; display: block; color: black; text-align: center; padding: 14px 16px; text-decoration: none; font-size: 17px;\">Logout</a>";
                    String cart = "<a href=\"cart.html\" class=\"cart\" style=\"float: right; display: block; color: black; text-align: center; padding: 14px 16px; text-decoration: none; font-size: 17px;\"></a>";
                    String newcart = "<a href=\"cart.html\" class=\"cart\" style=\"float: right; display: block; color: black; text-align: center; padding: 14px 16px; text-decoration: none; font-size: 17px;\">Cart</a>";

                    String file=new String(body);
                    file=file.replace(navbar,newnavbar);
                    file=file.replace(cart,newcart);
                    output.write(file.getBytes());
                }
                else{output.write(body);} // Write the text data directly to the output
                // Write the text data directly to the output
            }
        } 
        catch (NullPointerException x){}
        catch(IOException e){
            System.out.println("Error sending response: " + e.getMessage());
        }
    }
    
    public byte[] readImage(String filename){
        try{
            byte[] file= Files.readAllBytes(Paths.get(directory + filename));
            return file;
             
        }
        catch(InvalidPathException x){
            System.out.println("File not found: " + x.getMessage());
            byte[] body=readImage("404.html");
            sendResponse("404 Not Found", "text/html",body );
        }catch(NoSuchFileException e){
            System.out.println("File not found: " + e.getMessage());
            byte[] body=readImage("404.html");
            sendResponse("404 Not Found", "text/html", body);
        }catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
    
    public String readFile(String filename){
        return new String(readImage(filename));
    }

    public void redirect(String location) {
        try {
            output = socket.getOutputStream();
            StringBuilder html = new StringBuilder();
            html.append("HTTP/1.1 302 Found\r\n");
            html.append("Location: " + location + "\r\n");
            if(!this.cookie.equals("")){
                html.append(this.cookieString+"\r\n");
            }
            html.append("\r\n"); // blank line between headers and content
            byte[] headerBytes = html.toString().getBytes();
            output.write(headerBytes);
        } catch (IOException e) {
            System.out.println("Error sending redirect: " + e.getMessage());
        }
    }

    public String getCookie(){
        return this.cookie;
    }

    public void setCookie(String cookie, int age){
        this.cookie=cookie;
        this.cookieString="Set-Cookie: session="+this.cookie+"; Max-Age=age; Path=/; SameSite=Lax";
        this.setCookie=true;
    }

    public String replace(String filename, String old, String newString){
        String file=readFile(filename);
        String newFile=file.replace(old,newString);
        return newFile;
    }

}

//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: HTMLParser.java
 */
//-------------------------------------------------------

//IMPORTS
import java.io.BufferedReader; //for reading input
import java.io.ByteArrayOutputStream;
import java.io.IOException; //for input/output exceptions
import java.io.InputStreamReader; //for reading input
import java.io.OutputStream; //for writing output
import java.net.Socket; //for sockets
import java.net.URLDecoder; //for decoding URL strings
import java.nio.charset.StandardCharsets; //for character sets
import java.nio.file.Files; //for reading files
import java.nio.file.InvalidPathException; //for invalid paths
import java.nio.file.NoSuchFileException; //for missing files
import java.nio.file.Paths; //for file paths
import java.util.ArrayList; //for array lists

/* Name: HTMLParser
 * Description: This class parses HTTP requests and generates HTTP responses
 * Parameters: directory - the directory to read files from
 *             socket - the socket to read from and write to
 * Relationships: has a Logger
 */
public class HTMLParser{
    private Socket socket; //the socket to read from and write to
    private String method=""; //the HTTP method
    private String directory=""; //the directory to read files from
    private String path=""; //the path to the requested file
    private String cookie=""; //the cookie
    private String cookieString=""; //the cookie string
    private OutputStream output; //the output stream
    private BufferedReader reader; //the input stream
    private String line; //the current line of input
    private Logger logger= Logger.getInstance(); //the logger
    public boolean setCookie=false; //whether or not to set the cookie 
    public ArrayList<String> values; //the values from the request
    
    /* Name: HTMLParser
     * Description: The constructor for the HTMLParser class.
     *              Initializes the socket and directory.
     * Parameters: directory - the directory to read files from
     *             socket - the socket to read from and write to
     * Returns: none
     */
    public HTMLParser(String directory,Socket socket){
       this.socket=socket;
        this.directory=directory;
        try{
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); //Initialize the input stream
            line = reader.readLine(); //Read the first line of input
            //output=socket.getOutputStream();

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage()); //If there is an error, log it
            ex.printStackTrace(); //Print the stack trace
        }
        
    }

    //STEP 1 - Extract Raw Request Info
    /* Name: parseRaw
     * Description: This method parses the raw request info.
     * Parameters: none
     * Returns: parts - an array of strings containing the raw request info
     */
    private String[] parseRaw(){
        String[] parts;

        if (line == null) { //If there is no input, return null
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
    /* Name: parseRawStrings
     * Description: This method parses the raw request info and extracts the method and path.
     * Parameters: none
     * Returns: none
     */
    public void parseRawStrings(){
        String[] parts=parseRaw(); //Parse the raw request info
        if(parts==null){return;} //If there is no input, return
        if(parts.length>1){ //If there is input
            if(!parts[0].equals("=")){ //If the first part is not an equals sign
                this.method = parts[0]; //Set the method
                this.path = parts[1]; //Set the path
            }
        }
        
    }

    //STEP 3 - Extract Request Parameters
    /* Name: parseRawParameters
     * Description: This method parses the raw request info and extracts the parameters.
     * Parameters: none
     * Returns: pairs - an array of strings containing the parameters
     */
    public String[] parseRawParameters(){
        //parseRawStrings();
        
        // Read the headers and the blank line
       try{

            do{ 
                line = reader.readLine(); //Read the next line 
                if(line==null){return null;} //If there is no input, return null
                // Read the request body
                StringBuilder requestBody = new StringBuilder(); //Initialize the request body
                while (reader.ready()) { //While there is input
                    requestBody.append((char) reader.read()); //Read the input
                }
                String[] pairs = requestBody.toString().split("&"); //Split the input into pairs
                return pairs; //Return the pairs
            }while(!line.equals("")); //While there is input
        } catch (IOException ex) { //If there is an error, return 'error'
            String[] error={"error"};
             return error;
        }
    }
    
    //STEP 4 - Decode Values
    /* Name: getValues
     * Description: This method parses the raw request info and extracts the values.
     * Parameters: none
     * Returns: none
     */
    public void getValues(){
        String[] pairs=parseRawParameters(); //Parse the raw request info
        if(pairs==null||pairs[0].equals("error")){return;} //If there is no input, return

        ArrayList<String> parts=new ArrayList<String>(); //Initialize the array list

        for (String pair : pairs) { //For each pair
            //FIND THE COOKIE>>>>>>>>>>
            String[] temp2 = pair.split("\n"); //Split the pair into parts
            for(String part:temp2){ //For each part
                if(part.contains("Cookie:")){ //If the part contains 'Cookie'
                    this.cookie=part.substring(16); //Set the cookie
                    this.cookieString="Set-Cookie: session="+this.cookie+"; Max-Age=31536000; Path=/; SameSite=Lax";
                    //System.out.println("COOKIE: "+this.cookie);
                }
            }
            //GET THE FORM VALUES/PARAMETERS >>>>>>>>>>
            String[] temp = pair.split("=");  //Split the pair into parts
            for(String part:temp){ //For each part
               // System.out.println(part);
                try{
                   parts.add(URLDecoder.decode(part, StandardCharsets.UTF_8.name()).trim()); //Add the part to the array list
                } 
                catch (IOException ex) {}  //If there is an error, handle silently
                catch (IllegalArgumentException ex) {}
            }               
        }
       // print(parts);
        this.values=parts;    //Set the values internally
    }
    
    /* Name: print
     * Description: This method prints the values.
     * Parameters: parts - an array list of strings containing the values
     * Returns: none
     */
    /*private void print(ArrayList<String> parts){
        for(int x=0;x<parts.size();x++){
           if(parts.get(x)==null){return;}
            else if(x%2==0){System.out.print(parts.get(x)+"=");} 
           else{System.out.println(parts.get(x));}
        }
    } */

    /* Name: getMethod
     * Description: This method returns the HTTP method.
     * Parameters: none
     * Returns: method - a string containing the HTTP method
     */
    public String getMethod(){
        return this.method;
    }

    /* Name: getPath
     * Description: This method returns the path.
     * Parameters: none
     * Returns: path - a string containing the path
     */
    public String getPath(){
        return this.path;
    }
    
    /* Name: sendResponse
     * Description: This method sends an HTTP response.
     * Parameters: status - the HTTP status code (e.g., 200 OK, 404 Not Found, etc.)
     *             contentType - the content type (mimeType)
     *             body - the body of the response as a byte array
     * Returns: none
     */
    public void sendResponse(String status, String contentType, byte[] body) {
        try{ //Try to send the response
            output = socket.getOutputStream(); //Initialize the output stream
            boolean isBinary = contentType.startsWith("image/");         //Check if the content type is binary
            
            // Build the response header
            StringBuilder html = new StringBuilder();                           //Initialize the HTML string
            html.append("HTTP/1.1 " + status+"\r\n");                           //Write the status
            html.append("Content-Type: " + contentType+"; charset=UTF-8\r\n");  //Write the content type
            html.append("Content-Length: " + body.length+"\r\n");               //Write the content length
            
            if(!this.cookie.equals("")){                               //If there is a cookie
                html.append(this.cookieString+"\r\n");                          //Write the cookie
            }
            html.append("\r\n");                                            // blank line between headers and content
            
            // Write the response header
            byte[] headerBytes = html.toString().getBytes();
            output.write(headerBytes);
            
            
            if (isBinary) {
                // Write the binary data directly to the output
                output.write(body);
            } else { //If the content type is not binary
                if(!cookie.equals("")||this.setCookie==true){ //If there is a cookie
                    //Adjust the navbar to have a logout button and the cart button to be visible
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
        catch (NullPointerException x){} //If there is an error, handle silently
        catch(IOException e){ //If there is an error, print it
            logger.logError("HTMLParser","Error sending response: " + e.getMessage());
            //System.out.println("Error sending response: " + e.getMessage());
        }
    }
    
    public byte[] readImage(String filename){
        try{
            byte[] file= Files.readAllBytes(Paths.get(directory + filename)); //Read the file
            return file; 
             
        }
        //Log the error and send a 404 respone instead
        catch(InvalidPathException x){ 
            logger.logError("HTMLParser","Invalid path requested: " + x.getMessage());
            //System.out.println("File not found: " + x.getMessage());
            byte[] body=readImage("404.html");
            sendResponse("404 Not Found", "text/html",body );
        }
        catch(NoSuchFileException e){  //Log the error and send a 404 respone instead
            logger.logError("HTMLParser","File not found: " + e.getMessage());
            //System.out.println("File not found: " + e.getMessage());
            byte[] body=readImage("404.html");
            sendResponse("404 Not Found", "text/html", body);
        }
        catch (IOException ex) { //Log the error and send a 500 respone instead
            logger.logError("HTMLParser","Server exception: " + ex.getMessage());
            byte[] body=readImage("503.html");
            sendResponse("503 Service Unavailable", "text/html", body);
            //System.out.println("Server exception: " + ex.getMessage());
            //ex.printStackTrace();
        }
        return null;
    }
    
    /* Name: readFile
     * Description: This method reads a file.
     * Parameters: filename - the name of the file to read
     * Returns: file - a string containing the file
     */
    public String readFile(String filename){
        return new String(readImage(filename));
    }

    /* Name: redirect
     * Description: This method sends a redirect response.
     * Parameters: location - the location to redirect to
     * Returns: none
     */
    public void redirect(String location) {
        try { //Try to send the redirect
            output = socket.getOutputStream(); //Initialize the output stream
            StringBuilder html = new StringBuilder(); //Initialize the HTML string
            html.append("HTTP/1.1 302 Found\r\n"); //Write the status
            html.append("Location: " + location + "\r\n"); //Write the location
            if(!this.cookie.equals("")){ //If there is a cookie
                html.append(this.cookieString+"\r\n"); //Write the cookie
            }
            html.append("\r\n"); // blank line between headers and content
            byte[] headerBytes = html.toString().getBytes(); //Convert the HTML string to bytes
            output.write(headerBytes); //Write the bytes to the output
        } catch (IOException e) { //If there is an error, print it
            logger.logError("HTMLParser","Error sending redirect: " + e.getMessage());
           // System.out.println("Error sending redirect: " + e.getMessage());
        }
    }

    /* Name: getCookie
     * Description: This method returns the cookie.
     * Parameters: none
     * Returns: cookie - a string containing the cookie
     */
    public String getCookie(){
        return this.cookie;
    }

    /* Name: setCookie
     * Description: This method sets the cookie.
     * Parameters: cookie - the cookie to set
     *            age - the age of the cookie
     * Returns: none
     */
    public void setCookie(String cookie, int age){
        this.cookie=cookie; //Set the cookie
        this.cookieString="Set-Cookie: session="+this.cookie+"; Max-Age=age; Path=/; SameSite=Lax"; //Set the cookie string
        if(age==0){ //If the age is 0
            this.setCookie=false; //Do not set the cookie b/c it is now expired
        }else{
            this.setCookie=true; //Set the cookie if the cookie is not expired
        }
    }

    /* Name: replace
     * Description: This method replaces a string in a file.
     * Parameters: filename - the name of the file to read
     *             old - the string to replace
     *             newString - the string to replace with
     * Returns: newFile - a string containing the new file
     */
    public String replace(String filename, String old, String newString){
        String file=readFile(filename); //Read the file
        String newFile=file.replace(old,newString); //Replace the string
        return newFile; //Return the new file
    }


    /* Name: setLine
     * Description: This method returns the current line of input. Used for JUnit tests only
     * Parameters: line - a string containing the current line of input
     * Returns: None
     */
    public void setLine(String line){
        this.line=line;
    }

    /* Name: setReader
     * Description: This method returns the current line of input. Used for JUnit tests only
     * Parameters: reader - a buffered reader object containing the current line of input
     * Returns: None
     */
    public void setReader(BufferedReader reader){
        this.reader=reader;
    }

    /* Name: setReader
     * Description: This method returns the current line of output. Used for JUnit tests only
     * Parameters: output - a ByteArrayOutputStream object containing the current line of output
     * Returns: None
     */
    public void setOutput(ByteArrayOutputStream output){
        this.output=output;
    }

}

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class HTMLParserTest {
    
    
    @Test
    public void testParseRawStrings() {
        // Test case 1: null input
        HTMLParser parser1 = new HTMLParser("", new Socket());
        parser1.parseRawStrings();
        assertEquals("", parser1.getMethod());
        assertEquals("", parser1.getPath());
        
        // Test case 2: invalid input
        HTMLParser parser2 = new HTMLParser("");
        parser2.setLine("invalid input");
        parser2.parseRawStrings();
        assertEquals("invalid", parser2.getMethod());
        assertEquals("input", parser2.getPath());
        
        // Test case 3: valid input
        HTMLParser parser3 = new HTMLParser("");
        parser3.setLine("GET /index.html HTTP/1.1");
        parser3.parseRawStrings();
        assertEquals("GET", parser3.getMethod());
        assertEquals("/index.html", parser3.getPath());
    }
    
    @Test
    public void testParseRawParameters() {
        // Test case 1: null input
        HTMLParser parser1 = new HTMLParser("");
        assertNull(parser1.parseRawParameters());
        
        // Test case 2: invalid input
        HTMLParser parser2 = new HTMLParser("");
        parser2.setLine("GET /index.html HTTP/1.1");
        assertNull(parser2.parseRawParameters());
        
        // Test case 3: valid input
        HTMLParser parser3 = new HTMLParser("");
        parser3.setLine("POST /login HTTP/1.1");
        String requestBody = "username=johndoe\npassword=secret";
        InputStream inputStream = new ByteArrayInputStream(requestBody.getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        parser3.setReader(reader);
        String[] expected3 = {"password=secret"};
        assertArrayEquals(expected3, parser3.parseRawParameters());
    }
    
    @Test
    public void testGetValues() {
        // Test case 1: null input
        HTMLParser parser1 = new HTMLParser("");
        parser1.getValues();
        assertNull(parser1.values);
        
        // Test case 2: invalid input
        HTMLParser parser2 = new HTMLParser("");
        parser2.setLine("GET /index.html HTTP/1.1");
        parser2.getValues();
        assertNull(parser2.values);
        
        // Test case 3: valid input
        HTMLParser parser3 = new HTMLParser("");
        parser3.setLine("POST /login HTTP/1.1");
        String requestBody = "\nusername=johndoe&\npassword=secret";
        InputStream inputStream = new ByteArrayInputStream(requestBody.getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        parser3.setReader(reader);
        parser3.getValues();
        ArrayList<String> expected3 = new ArrayList<String>();
        expected3.add("username");
        expected3.add("johndoe");
        expected3.add("password");
        expected3.add("secret");
        assertEquals(expected3, parser3.values);
    }
    
    @Test
    public void testSendResponse() {
        // Test case 1: text response
        HTMLParser parser1 = new HTMLParser("");
        ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
        parser1.setOutput(outputStream1);
        String status1 = "200 OK";
        String contentType1 = "text/html";
        String body1 = "<html><body><h1>Hello, world!</h1></body></html>";
        boolean result = parser1.sendResponse(status1, contentType1, body1.getBytes());
        assertEquals(false,result);
        
        // Test case 2: binary response
        HTMLParser parser2 = new HTMLParser("");
        ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
        parser2.setOutput(outputStream2); 
        String status2 = "200 OK";
        String contentType2 = "image/jpeg";
        byte[] body2 = {0x12, 0x34, 0x56, 0x78};
        byte[] expectedBytes2 = ("HTTP/1.1 " + status2 + "\r\n" +
                                "Content-Type: " + contentType2 + "; charset=UTF-8\r\n" +
                                "Content-Length: " + body2.length + "\r\n" +
                                "\r\n").getBytes();
        byte[] expectedBytes2WithBody = new byte[expectedBytes2.length + body2.length];
        System.arraycopy(expectedBytes2, 0, expectedBytes2WithBody, 0, expectedBytes2.length);
        System.arraycopy(body2, 0, expectedBytes2WithBody, expectedBytes2.length, body2.length);
        result=parser2.sendResponse(status2, contentType2, body2);
        assertEquals(false,result);
    }
    
}
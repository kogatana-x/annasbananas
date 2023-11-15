import java.io.IOException;
import java.net.Socket;

public class PaymentWorker extends Thread {
    private Socket socket;
    Database database = new Database("payments.txt");

    public PaymentWorker(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        try{
            HTMLParser parser = new HTMLParser("payment-html/",socket);
            parser.parseRawStrings();
            String method=parser.getMethod();
            String path=parser.getPath();
            System.out.println("(PaymentWorker): "+method+" "+path+" from " + getSourceInfo(socket));
            parser.getValues();
            String cookie=parser.getCookie();
            System.out.println("COOKIE: "+cookie);
            String filename="garbage";
            String mimeType = "text/html";
            byte[] body=null;


            if(method.equals("POST")&&path.equals("/payments")){ //TODO
                String cardNumber = "";
                String cardName = "";
                String cardExpiry = "";
                String cardCVC = "";
                String cardZip = "";
                for (int x=0;x<parser.values.size();x++) {
                    if (parser.values.get(x)==null){break;}
                    else if (parser.values.get(x).equals("FNLN")) {
                        x++;
                        cardName = parser.values.get(x);
                    } 
                    else if (parser.values.get(x).equals("cardNumber")) {
                        x++;
                        cardNumber = parser.values.get(x);
                    } 
                    else if (parser.values.get(x).equals("cardExpiry")) {
                        x++;
                        cardExpiry =parser.values.get(x);
                    } 
                    else if (parser.values.get(x).equals("cardCVC")) {
                        x++;
                        cardCVC = parser.values.get(x);
                    } 
                    else if (parser.values.get(x).equals("cardZip")) {
                        x++;
                        cardZip = parser.values.get(x);
                    }
                }
                System.out.println("Payment from "+cookie+" || cardName "+cardName+" || cardNumber "+cardNumber+" || cardExpiry "+cardExpiry+" || cardCVC "+cardCVC+" || cardZip "+cardZip);
                System.out.println("Payment attempt from " + getSourceInfo(socket) + " with card number " + cardNumber);
                PaymentAuthenticator authenticator = new PaymentAuthenticator(new PaymentRepository());
                authenticator.pay(cookie, cardName, cardNumber, cardExpiry, cardCVC, cardZip);
                //filename="/finished-registration.html";
                //mimeType = "text/html";
                path="http://"+socket.getLocalAddress().toString()+":8080/finished-registration.html";
                //System.out.println("PATH REDIRECT TO: "+path);
                parser.redirect(path);
            } 
            else if(filename.equals("garbage")){
                int isQuery = path.indexOf("?");
                if (path.contains(".html")) {
                    if(isQuery!=-1){filename = path.substring(1,isQuery);}
                    else{filename = path.substring(1);}
                    mimeType = "text/html";
                } else if (path.endsWith(".css")) {
                    filename = path.substring(1); 
                    mimeType = "text/css";
                } else if (path.endsWith(".js")) {
                    filename = path.substring(1);  
                    mimeType = "application/javascript";
                } else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
                    filename = path.substring(1); 
                    mimeType = "image/jpeg";
                } else if (path.endsWith(".png")){
                    filename = path.substring(1);
                    mimeType = "image/png"; 
                }
                else if (path.endsWith(".ico")){
                    filename = path.substring(1);
                    mimeType = "image/x-icon";
                }
                else if (path.endsWith(".gif")){
                    filename = path.substring(1);
                    mimeType = "image/gif";
                }
                else if(path.equals("/site.webmanifest")){
                    filename="site.webmanifest";
                    mimeType = "application/manifest+json";
                }else {
                    filename="404.html";
                    mimeType = "text/html";
                }
                body = parser.readImage(filename);
                parser.sendResponse("200 OK", mimeType, body);

            }
        }
        finally{
            try{
                socket.close();
            }
            catch(IOException e){
                System.out.println("Error closing socket: " + e.getMessage());
            }
        }
    }
    private String getSourceInfo(Socket socket){ //TODO Logger
        String ip = socket.getInetAddress().getHostAddress();
        int port = socket.getPort();
        return ip + ":" + port;
    }
}

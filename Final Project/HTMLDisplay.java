import java.net.Socket;

public class HTMLDisplay extends HTMLParser{
    public HTMLDisplay(Socket socket){
        super(socket);
    }
    public StringBuilder getString(Product[] products){
        StringBuilder html = new StringBuilder();
        html.append("<div class=\"product-grid\">");

        for (Product product : products){
            if(product!=null){
                html.append("<div class=\"product-card\">");
                html.append("<h2>").append(product.getName()).append("</h2>");
                html.append("<p><i> ").append(product.getDescription()).append("</i></p>");
                html.append("<p>Price: $").append(product.getPrice()).append("</p>");
                html.append("<p>Quantity Available: ").append(product.getQuantity()).append("</p>");
                html.append("<img src='").append(product.getImage()).append("' alt='Product Image'>");
                html.append("<form action='/addToCart' method='post'>");
                html.append("<input type='hidden' name='productId' value='").append(product.getId()).append("'>");
                html.append("<input type='submit' value='Add to Cart'>");
                html.append("</form>");
                html.append("</div>");
            }
        }
        html.append("</div>");

        return html;
    }
}

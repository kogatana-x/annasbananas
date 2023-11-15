public class HTMLDisplay {

    public StringBuilder displayProductCatalog(Product[] products){
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
                html.append("<label for=quantity style=\"display: block; margin-bottom: 10px;\">Quantity:</label>");
                html.append("<input type='number' id='quantity' name='quantity' min='1' max='999' value='1' style='width: 10%; padding: 5px; margin: 5px; border-radius: 5px;'>");                html.append("<input type='hidden' name='productId' value='").append(product.getId()).append("'>");
                html.append("<input class='add-to-cart' data-product-id="+product.getId()+" type='submit' value='Add to Cart'>");
                html.append("</form>");
                html.append("</div>");
            }
        }
        html.append("</div>");
        return html;
    }
    public StringBuilder displayCart(Product[] products){
        double total=0;
        StringBuilder html = new StringBuilder();
        html.append("<div class=\"cart-container\">");

        for (Product product : products){
            if(product!=null){
                html.append("<div class=\"cart-item\">");
                    html.append("<img src='").append(product.getImage()).append("' alt='Product Image'>");
                    html.append("<div class=\"cart-item-details\">");
                        html.append("<h2>").append(product.getName()).append("</h2>");
                        html.append("<p><i> ").append(product.getDescription()).append("</i></p>");
                        html.append("<form action='/updateCart' method='post'>");
                        html.append("<input type='hidden' name='productId' value='").append(product.getId()).append("'>");
                        html.append("<label for=quantity style=\"display: block; margin-bottom: 10px;\">Quantity:</label>");
                        html.append("<input type='number' id='quantity' name='quantity' min='1' max='999' value="+product.getQuantity() +" style='width: 10%; padding: 5px; margin: 5px; border-radius: 5px;'>");                html.append("<input type='hidden' name='productId' value='").append(product.getId()).append("'>");
                        html.append("<input class='update-cart' data-product-id="+product.getId()+" type='submit' value='Update Item'>");
                        html.append("</form>");
                    html.append("</div>");
                    html.append("<div class=\"cart-item-price\">");
                        html.append("<p>Unit Price: $").append(product.getPrice()).append("</p>");
                        double subTotal=product.getPrice()*product.getQuantity();
                        html.append("<p>Sub Total: $").append(subTotal).append("</p>");
                    html.append("</div>");
                html.append("</div>");
                total+=subTotal;
            }
        }
        html.append("<div class=total>");
            html.append("<h2>Order Total: $").append(total).append("</h2>");
        html.append("</div>");
        html.append("</div>");
        return html;
    }
}

//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: HTMLDisplay.java
 */
//-------------------------------------------------------

/* Name: HTMLDisplay
 * Description: This class is used to generate HTML for the site.
 * Parameters: none
 * Relationships: none
 */
public class HTMLDisplay {

    /* Name: displayProductCatalog
     * Description: This method generates HTML for the product catalog.
     * Parameters: products - an array of products to display
     * Returns: html - a StringBuilder containing the HTML for the product catalog
     */
    public StringBuilder displayProductCatalog(Product[] products){
        StringBuilder html = new StringBuilder(); //Initialize the HTML string
        html.append("<div class=\"product-grid\">"); //Start the product grid

        for (Product product : products){ //For each product in the array
            if(product!=null){ //If the product is not null
                //Generate the HTML for the product
                html.append("<div class=\"product-card\">");
                html.append("<h2>").append(product.getName()).append("</h2>");
                html.append("<p><i> ").append(product.getDescription()).append("</i></p>");
                html.append("<p>Price: $").append(product.getPrice()).append("</p>");
                html.append("<p>Quantity Available: ").append(product.getQuantity()).append("</p>");
                html.append("<img src='").append(product.getImage()).append("' alt='Product Image'>");
                //Add the add to cart form
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

    /* Name: displayCart
     * Description: This method generates HTML for the cart.
     * Parameters: products - an array of products to display
     * Returns: html - a StringBuilder containing the HTML for the cart
     */
    public StringBuilder displayCart(Product[] products){
        double total=0; //Initialize the cart total
        StringBuilder html = new StringBuilder(); //Initialize the HTML string
        html.append("<div class=\"cart-container\">"); //Start the cart container

        for (Product product : products){ //For each product in the array
            if(product!=null){ //If the product is not null
                //Generate the HTML for the product
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
                total+=subTotal; //Add the subtotal to the total
            }
        }
        //Generate the HTML for the total
        html.append("<div class=total>");
            html.append("<h2>Order Total: $").append(total).append("</h2>");
        html.append("</div>");
        html.append("</div>");
        return html;
    }
}

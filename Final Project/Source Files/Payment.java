//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: Payment.java
 */
//-------------------------------------------------------

/* Name: Payment
 * Description: The class for storing payment information
 * Returns: none
 * Relationships: none
 */
public class Payment {
    private String username; //The username of the user
    private String cardNumber; //The card number of the user
    private String cardName; //The name on the card of the user
    private String cardExpiry; //The expiry date of the card of the user
    private String cardCVC; //The CVC of the card of the user
    private String cardZip; //The zip code of the card of the user

    /* Name: Payment
     * Description: The constructor for the Payment class
     * Parameters:  username - the username of the user
     *              cardNumber - the card number of the user
     *              cardName - the name on the card of the user
     *              cardExpiry - the expiry date of the card of the user
     *              cardCVC - the CVC of the card of the user
     *              cardZip - the zip code of the card of the user
     */
    public Payment(String username,  String cardName, String cardNumber,String cardExpiry, String cardCVC, String cardZip) {
        this.username = username.trim();
        this.cardName = cardName.trim();
        this.cardNumber = cardNumber.trim();
        this.cardExpiry = cardExpiry.trim();
        this.cardCVC = cardCVC.trim();
        this.cardZip = cardZip.trim();
    }

    // getters and setters
    /* Name: getUsername
     * Description: Gets the username of the user
     * Parameters: none
     * Returns: the username of the user
     */
    public String getUsername() {
        return this.username;
    }

    /* Name: getCardNumber
     * Description: Gets the card number of the user
     * Parameters: none
     * Returns: the card number of the user
     */
    public String getCardNumber() {
        return this.cardNumber;
    }

    /* Name: getCardName
     * Description: Gets the name on the card of the user
     * Parameters: none
     * Returns: the name on the card of the user
     */
    public String getCardName() {
        return this.cardName;
    }

    /* Name: getCardExpiry
     * Description: Gets the expiry date of the card of the user
     * Parameters: none
     * Returns: the expiry date of the card of the user
     */
    public String getCardExpiry() {
        return this.cardExpiry;
    }

    /* Name: getCardCVC
     * Description: Gets the CVC of the card of the user
     * Parameters: none
     * Returns: the CVC of the card of the user
     */
    public String getCardCVC() {
        return this.cardCVC;
    }

    /* Name: getCardZip
     * Description: Gets the zip code of the card of the user
     * Parameters: none
     * Returns: the zip code of the card of the user
     */
    public String getCardZip() {
        return this.cardZip;
    }
    
}

//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: PaymentAuthenticator.java
 */
//-------------------------------------------------------


/* Name: PaymentAuthenticator
 * Description: The class for authenticating payments
 * Parameters: none
 * Relationships: has a PaymentRepository
 */
public class PaymentAuthenticator {
    private PaymentRepository paymentRepository; //The repository for payments

    /* Name: PaymentAuthenticator
     * Description: The constructor for the PaymentAuthenticator class
     * Parameters: paymentRepository - the repository for payments
     * Returns: none
     */
    public PaymentAuthenticator(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /* Name: pay
     * Description: Pays for the items in the cart
     * Parameters:  username - the username of the user
     *              cardNumber - the card number of the user
     *              cardName - the name on the card of the user
     *              cardExpiry - the expiry date of the card of the user
     *              cardCVC - the CVC of the card of the user
     *              cardZip - the zip code of the card of the user
     * Returns: none
     */
    public void pay(String username, String cardNumber, String cardName, String cardExpiry, String cardCVC, String cardZip){
        Payment payment = new Payment(username,cardNumber,cardName,cardExpiry,cardCVC,cardZip); //Create a new payment
        paymentRepository.savePayments(payment); // Save the payment
    }
}

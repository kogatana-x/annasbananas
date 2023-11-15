
//-------------------------------------------------------
/* Name: Anna Andler
 * Class: SE 450
 * Date: 11/15/2023
 * Project: Final Project - eCommerce Site (Anna's Bananas)
 * File Name: PaymentRepository.java
 */
//-------------------------------------------------------

/* Name: PaymentRepository
 * Description: The class for the repository for payments
 * Parameters: none
 * Relationships: has a Database
 */
public class PaymentRepository {
    private String filename="payments.txt"; //The filename of the database
    Database PaymentDatabase = new Database(filename); //The database for payments

    /* Name: savePayments
     * Description: Saves the payment to the database
     * Parameters:  payment - the payment to save
     * Returns: none
     */
    public void savePayments(Payment payment){ 
        String row=payment.getUsername()+","+payment.getCardNumber()+","+payment.getCardExpiry()+","+payment.getCardCVC()+","+payment.getCardZip(); //Create a string of the payment
        PaymentDatabase.add(row); //Add the payment to the database
    }

    /* Name: getPayment
     * Description: Gets the payment from the database
     * Parameters:  username - the username of the user
     * Returns: the payment of the user
     */
    public Payment getPayment(String username){
        String partString = PaymentDatabase.returnResultRow("1",username); //Get the payment from the database
        String[] parts=partString.split(","); //Split the payment into parts

        if(parts.length>1){ //If the payment exists
            return new Payment(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]); //Return the payment
        }
        return null;
    }

}

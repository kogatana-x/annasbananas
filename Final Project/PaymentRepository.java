
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
    private String filename; //The filename of the database
    private Database PaymentDatabase; //The database for payments

    /* Name: PaymentRepository
     * Description: default constructor for payment repository. Sets the filename to payments.txt
     */
    public PaymentRepository(){
        filename="payments.txt";
        PaymentDatabase = new Database(filename);
    }
    /* Name: PaymentRepository
     * Description: constructor for payment repository. Sets the filename to the parameter. Used for JUnit Testing
     */
    public PaymentRepository(String filename){
        this.filename=filename;
        PaymentDatabase = new Database(filename);
    }
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
        String partString = PaymentDatabase.returnResultRow("0",username); //Get the payment from the database
        String[] parts=partString.split(","); //Split the payment into parts
        if(parts.length>1){ //If the payment exists
            return new Payment(parts[0].trim(), "",parts[1], parts[2], parts[3], parts[4]); //Return the payment
        }
        return null;
    }

}

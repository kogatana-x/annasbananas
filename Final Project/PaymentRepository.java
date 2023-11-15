
public class PaymentRepository {
    private String filename="payments.txt";
    Database PaymentDatabase = new Database(filename);

    public void savePayments(Payment payment){
        //int index = PaymentDatabase.isInDB(payment.getUsername());
        //System.out.println("Payment Save index: "+index);
        //if(index==-1){
            String row=payment.getUsername()+","+payment.getCardName()+","+payment.getCardNumber()+","+payment.getCardExpiry()+","+payment.getCardCVC()+","+payment.getCardZip();
            System.out.println("Payment Save row: "+row);
            PaymentDatabase.add(row);
        //    return true;
        //}
        //return false;

    }

    public Payment getPayment(String username){
        String partString = PaymentDatabase.returnResultRow("1",username);
        String[] parts=partString.split(",");

        if(parts.length>1){
            return new Payment(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
        }
        return null;
    }

}

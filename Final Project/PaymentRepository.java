
public class PaymentRepository {
    private String filename="payments.txt";
    Database PaymentDatabase = new Database(filename);

    public boolean savePayments(Payment payment){
        boolean result = PaymentDatabase.isInDB(payment.getUsername());
        if(!result){
            String row=payment.getUsername()+","+payment.getCardNumber()+","+payment.getCardName()+","+payment.getCardExpiry()+","+payment.getCardCVC()+","+payment.getCardZip();
            PaymentDatabase.add(row);
            return true;
        }
        return false;

    }

    public Payment getPayment(String username){
        String[] parts = PaymentDatabase.returnResult(username);
        if(parts.length>1){
            return new Payment(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
        }
        return null;
    }

}

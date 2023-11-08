import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PaymentRepository {
    private String filename="payments.txt";

    public boolean savePayments(Payment payment){
        Payment rez = getPayment(payment.getCardNumber());
        if(rez==null){
            Database PaymentDatabase = new Database(filename);
            String row=payment.getUsername()+","+payment.getCardNumber()+","+payment.getCardName()+","+payment.getCardExpiry()+","+payment.getCardCVC()+","+payment.getCardZip();
            PaymentDatabase.add(row);
            return true;
        }
        return false;

    }

    public Payment getPayment(String username){
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(username)) {
                    // Found the payment in the system, return the index
                    return new Payment(parts[0], parts[1], parts[2], parts[3], parts[4],parts[5]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // No user with that username exists
        return null;
    }

}

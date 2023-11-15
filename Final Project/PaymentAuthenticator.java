public class PaymentAuthenticator {
    public PaymentRepository paymentRepository;

    public PaymentAuthenticator(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void pay(String username, String cardNumber, String cardName, String cardExpiry, String cardCVC, String cardZip){
        Payment payment = new Payment(username,cardNumber,cardName,cardExpiry,cardCVC,cardZip);
        paymentRepository.savePayments(payment);
    }
}

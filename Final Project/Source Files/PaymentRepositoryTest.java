import org.junit.Test;
import static org.junit.Assert.*;

public class PaymentRepositoryTest {
    
    @Test
    public void testSaveAndGetPayment() {
        PaymentRepository paymentRepo = new PaymentRepository("test-payment.txt");
        Payment payment = new Payment("testuser", "name","1234567890123456", "12/23", "123", "12345");
        paymentRepo.savePayments(payment);
        Payment retrievedPayment = paymentRepo.getPayment("testuser");
        assertEquals(payment.getUsername(), retrievedPayment.getUsername());
        assertEquals(payment.getCardNumber(), retrievedPayment.getCardNumber());
        assertEquals(payment.getCardExpiry(), retrievedPayment.getCardExpiry());
        assertEquals(payment.getCardCVC(), retrievedPayment.getCardCVC());
        assertEquals(payment.getCardZip(), retrievedPayment.getCardZip());
    }
    
    @Test
    public void testGetNonexistentPayment() {
        PaymentRepository paymentRepo = new PaymentRepository("test-payment.txt");
        Payment retrievedPayment = paymentRepo.getPayment("nonexistentuser");
        assertNull(retrievedPayment);
    }
}
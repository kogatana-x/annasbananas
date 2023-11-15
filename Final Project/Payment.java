public class Payment {
    private String username;
    private String cardNumber;
    private String cardName;
    private String cardExpiry;
    private String cardCVC;
    private String cardZip;

    public Payment(String username,  String cardName, String cardNumber,String cardExpiry, String cardCVC, String cardZip) {
        this.username = username.trim();
        this.cardName = cardName.trim();
        this.cardNumber = cardNumber.trim();
        this.cardExpiry = cardExpiry.trim();
        this.cardCVC = cardCVC.trim();
        this.cardZip = cardZip.trim();
    }

    // getters and setters
    public String getUsername() {
        return this.username;
    }
    public String getCardNumber() {
        return this.cardNumber;
    }
    public String getCardName() {
        return this.cardName;
    }
    public String getCardExpiry() {
        return this.cardExpiry;
    }
    public String getCardCVC() {
        return this.cardCVC;
    }
    public String getCardZip() {
        return this.cardZip;
    }
    
}

public class CreditCardPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid via Credit Card: " + amount);
    }
}
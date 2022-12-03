public class PaymentCard {
    private double balance;

    public PaymentCard(double openingBalance) {
        balance = openingBalance;
    }
    public void eatAffordably() {
        if (balance >= 2.60) {
            balance -= 2.60;
        }
    }
    public void eatHeartily() {
        if (balance >= 4.60) {
            balance -= 4.60;
        }
    }
    public void addMoney(double amount) {
        if (amount >= 0.0) {
            balance += amount;
        }
        if (balance >= 150.0) {
            balance = 150.0;
        }
    }
    public String toString() {
        return "The card has a balance of " + balance + " euros";
    }
}

public class Debt {
    private double balance;
    private double interestRate;

    public Debt(double initialBalance, double initialInterestRate) {
        balance = initialBalance;
        interestRate = initialInterestRate;
    }

    public void printBalance() {
        System.out.println(balance);
    }
    public void waitOneYear() {
        balance *= interestRate;
    }

}

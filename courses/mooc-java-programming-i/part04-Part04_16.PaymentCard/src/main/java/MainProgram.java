
public class MainProgram {

    public static void main(String[] args) {
        // Scanner scanner = new Scanner(System.in);

        // Here you can write code to test that PaymentCard works as intended
        // be sure to erase the extra code for the last part of the exercise!
        PaymentCard paul = new PaymentCard(20.0);
        PaymentCard matt = new PaymentCard(30.0);
        paul.eatHeartily();
        matt.eatAffordably();
        System.out.println("Paul: " + paul);
        System.out.println("Matt: " + matt);
        paul.addMoney(20.0);
        matt.eatHeartily();
        System.out.println("Paul: " + paul);
        System.out.println("Matt: " + matt);
        paul.eatAffordably();
        paul.eatAffordably();
        matt.addMoney(50.0);
        System.out.println("Paul: " + paul);
        System.out.println("Matt: " + matt);
    }
}


import java.util.Scanner;

public class GiftTax {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Value of the gift?");
        int value = Integer.valueOf(scan.nextLine());

        double lower = 0.0;
        double rate = 0.0;
        double exceeding = 0.0;

        if (value >= 1000000) {
            lower = 142100.0;
            rate = 0.17;
            exceeding = (value * 1.0) - 1000000.0;
        } else if (value >= 200000) {
            lower = 22100.0;
            rate = 0.15;
            exceeding = (value * 1.0) - 200000.0;
        } else if (value >= 55000) {
            lower = 4700.0;
            rate = 0.12;
            exceeding = (value * 1.0) - 55000.0;
        } else if (value >= 25000) {
            lower = 1700.0;
            rate = 0.10;
            exceeding = (value * 1.0) - 25000.0;
        } else if (value >= 5000) {
            lower = 100.0;
            rate = 0.08;
            exceeding = (value * 1.0) - 5000.0;
        }

        double tax = lower + exceeding * rate;
        if (tax == 0.0) {
            System.out.println("No tax!");
        } else {
            System.out.println("Tax: " + tax);
        }
    }
}

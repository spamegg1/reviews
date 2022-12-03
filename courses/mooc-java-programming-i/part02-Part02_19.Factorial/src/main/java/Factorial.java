
import java.util.Scanner;

public class Factorial {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Give a number:");
        int number = Integer.valueOf(scanner.nextLine());

        int product = 1;
        for (int i = 2; i <= number; i++) {
            product *= i;
        }

        System.out.println("Factorial: " + product);
    }
}

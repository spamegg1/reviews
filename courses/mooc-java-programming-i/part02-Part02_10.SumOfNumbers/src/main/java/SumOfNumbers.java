
import java.util.Scanner;

public class SumOfNumbers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int number = -1;
        int total = 0;

        while (true) {
            System.out.println("Give a number:");
            number = Integer.valueOf(scanner.nextLine());

            if (number == 0) {
                break;
            }

            total += number;

        }

        System.out.println("Sum of the numbers: " + total);
    }
}


import java.util.Scanner;

public class NumberOfNumbers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int number = -1;
        int count = 0;

        while (true) {
            System.out.println("Give a number:");
            number = Integer.valueOf(scanner.nextLine());
            if (number == 0) {
                break;
            }
            count += 1;
        }

        System.out.println("Number of numbers: " + count);
    }
}


import java.util.Scanner;

public class AverageOfPositiveNumbers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int number = -1;
        double count = 0.0;
        double total = 0.0;

        while (true) {
            System.out.println("Give a number:");
            number = Integer.valueOf(scanner.nextLine());

            if (number == 0) {
                break;
            }
            if (number > 0) {
                total += number * 1.0;
                count += 1.0;
            }
        }

        if (count == 0.0) {
            System.out.println("Cannot calculate the average");
        } else {
            System.out.println("Average of the numbers: " + total / count);
        }
    }
}


import java.util.Scanner;

public class SimpleCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Write your program here
        System.out.println("Give the first number:");
        int first = Integer.valueOf(scanner.nextLine());
        System.out.println("Give the second number:");
        int second = Integer.valueOf(scanner.nextLine());

        int added = first + second;
        int subbed = first - second;
        int multed = first * second;
        double divved = (first * 1.0) / (second * 1.0);

        System.out.println(first + " + " + second + " = " + added);
        System.out.println(first + " - " + second + " = " + subbed);
        System.out.println(first + " * " + second + " = " + multed);
        System.out.println(first + " / " + second + " = " + divved);
    }
}

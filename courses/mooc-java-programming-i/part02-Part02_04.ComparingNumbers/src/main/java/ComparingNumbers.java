
import java.util.Scanner;

public class ComparingNumbers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int first = Integer.valueOf(scanner.nextLine());
        int second = Integer.valueOf(scanner.nextLine());
        if (first < second) {
            System.out.println(first + " is smaller than " + second);
        } else if (first == second) {
            System.out.println(first + " is equal to " + second);
        } else {
            System.out.println(first + " is greater than " + second);
        }
    }
}

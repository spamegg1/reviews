
import java.util.Scanner;

public class SumOfASequence {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Last number?");
        int last = Integer.valueOf(scanner.nextLine());

        int total = 0;
        for (int i = 0; i <= last; i++) {
            total += i;
        }

        System.out.println("The sum is " + total);
    }
}

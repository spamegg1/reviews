
import java.util.Scanner;

public class RepeatingBreakingAndRemembering {

    public static void main(String[] args) {

        // This exercise is worth five exercise points, and it is
        // gradually extended part by part.

        // If you want, you can send this exercise to the server
        // when it's just partially done. In that case the server will complain about
        // the parts you haven't done, but you'll get points for the finished parts.

        Scanner scanner = new Scanner(System.in);

        int command = 0;
        int total = 0;
        int count = 0;
        int even = 0;
        int odd = 0;

        while (true) {
            System.out.println("Give numbers:");
            command = Integer.valueOf(scanner.nextLine());
            if (command == -1) {
                break;
            }
            total += command;
            count += 1;
            if (command % 2 == 0) {
                even++;
            } else {
                odd++;
            }

        }

        double average = (total * 1.0) / (count * 1.0);

        System.out.println("Thx! Bye!");
        System.out.println("Sum: " + total);
        System.out.println("Numbers: " + count);
        System.out.println("Average: " + average);
        System.out.println("Even: " + even);
        System.out.println("Odd: " + odd);
    }
}


import java.util.Scanner;

public class OnlyPositives {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int command = 0;

        while (true) {
            System.out.println("Give a number:");
            command = Integer.valueOf(scanner.nextLine());

            while (command < 0) {
                System.out.println("Unsuitable number");
                System.out.println("Give a number:");
                command = Integer.valueOf(scanner.nextLine());
            }

            if (command == 0) {
                break;
            }
            System.out.println(command * command);
        }
    }
}

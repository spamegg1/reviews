
import java.util.Scanner;

public class LiquidContainers {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int first = 0;
        int second = 0;

        while (true) {
            System.out.println("First: " + first + "/100");
            System.out.println("Second: " + second + "/100");

            System.out.print("> ");

            String input = scan.nextLine();
            if (input.equals("quit")) {
                break;
            }

            String[] parts = input.split(" ");

            String command = parts[0];
            int amount = Integer.valueOf(parts[1]);

            int newFirst = 0;
            int newSecond = 0;
            int movedAmount = 0;

            if (command.equals("add")) {
                if (amount >= 0) {
                    newFirst = first + amount;

                    if (newFirst > 100) {
                        newFirst = 100;
                    }

                    first = newFirst;
                }
            }

            if (command.equals("move")) {
                if (amount >= 0) {
                    newFirst = first - amount;
                    movedAmount = amount;

                    if (newFirst < 0) {
                        movedAmount = first;
                        newFirst = 0;
                    }

                    newSecond = second + movedAmount;
                    if (newSecond > 100) {
                        newSecond = 100;
                    }

                    first = newFirst;
                    second = newSecond;
                }
            }

            if (command.equals("remove")) {
                if (amount >= 0) {
                    newSecond = second - amount;

                    if (newSecond < 0) {
                        newSecond = 0;
                    }

                    second = newSecond;
                }
            }
        }
    }
}

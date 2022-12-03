
import java.util.Scanner;

public class LiquidContainers2 {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // Container container = new Container();
        // System.out.println(container);

        // container.add(50);
        // System.out.println(container);
        // System.out.println(container.contains());

        // container.remove(60);
        // System.out.println(container);

        // container.add(200);
        // System.out.println(container);

        Container first = new Container();
        Container second = new Container();

        while (true) {
            System.out.println("First: " + first);
            System.out.println("Second: " + second);

            System.out.print("> ");

            String input = scan.nextLine();
            if (input.equals("quit")) {
                break;
            }

            String[] parts = input.split(" ");

            String command = parts[0];
            int amount = Integer.valueOf(parts[1]);

            if (command.equals("add")) {
                first.add(amount);
            }

            if (command.equals("move")) {
                int oldFirst = first.contains();
                first.remove(amount);

                if (first.contains() == 0) {
                    second.add(oldFirst);
                } else {
                    second.add(amount);
                }
            }

            if (command.equals("remove")) {
                second.remove(amount);
            }
        }
    }
}

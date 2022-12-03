import java.util.Scanner;

public class TextUI {
    private Scanner scanner;
    private SimpleDictionary sd;

    public TextUI(Scanner scanner, SimpleDictionary sd) {
        this.sd = sd;
        this.scanner = scanner;
    }

    public void start() {
        while(true) {
            System.out.println("Command:");
            String command = scanner.nextLine();

            if (command.equals("end")) {
                System.out.println("Bye bye!");
                return;
            }

            if (command.equals("add")) {
                System.out.println("Word:");
                String word = scanner.nextLine();

                System.out.println("Translation:");
                String translation = scanner.nextLine();

                sd.add(word, translation);
            } else if (command.equals("search")) {
                System.out.println("To be translated:");
                String query = scanner.nextLine();
                String result = sd.translate(query);
                if (result == null) {
                    System.out.println("Word " + query + " was not found");
                } else {
                    System.out.println("Translation: " + result);
                }
            } else {
                System.out.println("Unknown command");
            }
        }
    }
}

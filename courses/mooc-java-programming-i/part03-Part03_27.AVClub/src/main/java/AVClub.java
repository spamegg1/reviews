
import java.util.Scanner;

public class AVClub {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String msg = scanner.nextLine();
            if (msg.equals("")) {
                break;
            }

            String[] splitMsg = msg.split(" ");
            for (String word : splitMsg) {
                if (word.contains("av")) {
                    System.out.println(word);
                }
            }
        }
    }
}

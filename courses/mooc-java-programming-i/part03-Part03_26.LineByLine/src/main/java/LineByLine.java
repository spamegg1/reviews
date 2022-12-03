
import java.util.Scanner;

public class LineByLine {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String msg = scanner.nextLine();
            if (msg.equals("")) {
                break;
            }

            String[] splitMsg = msg.split(" ");
            for (String word : splitMsg) {
                System.out.println(word);
            }
        }
    }
}

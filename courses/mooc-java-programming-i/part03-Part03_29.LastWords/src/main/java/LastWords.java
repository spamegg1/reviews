
import java.util.Scanner;

public class LastWords {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String msg = scanner.nextLine();
            if (msg.equals("")) {
                break;
            }

            String[] splitMsg = msg.split(" ");
            System.out.println(splitMsg[splitMsg.length - 1]);
        }
    }
}

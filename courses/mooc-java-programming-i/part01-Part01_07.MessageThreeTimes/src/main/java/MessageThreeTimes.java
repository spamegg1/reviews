
import java.util.Scanner;

public class MessageThreeTimes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Write a message:");
        // Write your program here

        String msg = scanner.nextLine();

        System.out.println(msg);
        System.out.println(msg);
        System.out.println(msg);
    }
}

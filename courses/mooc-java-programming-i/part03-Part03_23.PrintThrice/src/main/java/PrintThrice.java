
import java.util.Scanner;

public class PrintThrice {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Write your program here
        System.out.println("Give a word:");
        String string = scanner.nextLine();

        System.out.print(string);
        System.out.print(string);
        System.out.print(string);
    }
}

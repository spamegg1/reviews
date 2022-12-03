
import java.util.Scanner;

public class IntegerInput {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // write your program here
        System.out.println("Give a number:");
        int value = Integer.valueOf(scanner.nextLine());
        String s = String.valueOf(value);
        System.out.println("You gave the number " + s);
    }
}

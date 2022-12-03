
import java.util.Scanner;

public class BooleanInput {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // write your program here
        System.out.println("Write something:");
        Boolean value = Boolean.valueOf(scanner.nextLine());
        String s = String.valueOf(value);
        System.out.println("True or false? " + s);
    }
}

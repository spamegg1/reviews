
import java.util.Scanner;

public class DifferentTypesOfInput {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // Write your program here
        System.out.println("Give a string:");
        String string = scan.nextLine();

        System.out.println("Give an integer:");
        Integer integer = Integer.valueOf(scan.nextLine());
        String intString = String.valueOf(integer);

        System.out.println("Give a double:");
        double dble = Double.valueOf(scan.nextLine());
        String doubleString = String.valueOf(dble);

        System.out.println("Give a boolean:");
        boolean bool = Boolean.valueOf(scan.nextLine());
        String booleanString = String.valueOf(bool);

        System.out.println("You gave the string " + string);
        System.out.println("You gave the integer " + intString);
        System.out.println("You gave the double " + doubleString);
        System.out.println("You gave the boolean " + booleanString);

    }
}

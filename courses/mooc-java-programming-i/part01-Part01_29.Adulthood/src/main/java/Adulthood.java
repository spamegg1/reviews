
import java.util.Scanner;

public class Adulthood {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // Write your program here
        System.out.println("How old are you?");
        int year = Integer.valueOf(scan.nextLine());
        if (year >= 18) {
            System.out.println("You are an adult");
        } else {
            System.out.println("You are not an adult");
        }
    }
}

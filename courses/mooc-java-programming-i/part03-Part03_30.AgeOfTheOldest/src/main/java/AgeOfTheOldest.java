
import java.util.Scanner;

public class AgeOfTheOldest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int oldest = 0;

        while (true) {
            String nameAge = scanner.nextLine();
            if (nameAge.equals("")) {
                break;
            }

            String[] nameAndAge = nameAge.split(",");
            int age = Integer.valueOf(nameAndAge[1]);
            if (age > oldest) {
                oldest = age;
            }
        }
        System.out.println("Age of the oldest: " + oldest);
    }
}

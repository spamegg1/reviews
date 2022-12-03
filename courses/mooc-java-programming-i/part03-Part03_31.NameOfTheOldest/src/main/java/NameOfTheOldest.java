
import java.util.Scanner;

public class NameOfTheOldest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String nameOfOldest = "";
        int oldest = 0;

        while (true) {
            String nameAge = scanner.nextLine();
            if (nameAge.equals("")) {
                break;
            }

            String[] nameAndAge = nameAge.split(",");
            String name = nameAndAge[0];
            int age = Integer.valueOf(nameAndAge[1]);

            if (age > oldest) {
                oldest = age;
                nameOfOldest = name;
            }
        }
        System.out.println("Name of the oldest: " + nameOfOldest);
    }
}
